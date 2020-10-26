package alararestaurant.service;

import alararestaurant.domain.dtos.OrderItemDto;
import alararestaurant.domain.dtos.OrderXmlDto;
import alararestaurant.domain.dtos.OrderXmlRootDto;
import alararestaurant.domain.entities.*;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final String ORDERS_XML_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/ExamPrep1/AlaraRestaurant/src/main/resources/files/orders.xml";

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final EmployeeRepository employeeRepository;
    private final OrderItemRepository orderItemRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, EmployeeRepository employeeRepository, OrderItemRepository orderItemRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper mapper, ValidationUtil validationUtil) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.employeeRepository = employeeRepository;
        this.orderItemRepository = orderItemRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return fileUtil.readFile(ORDERS_XML_FILE_PATH);
    }

    @Override
    public String importOrders() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        OrderXmlRootDto orderXmlRootDto = xmlParser.importXMl(OrderXmlRootDto.class, ORDERS_XML_FILE_PATH);

        for (OrderXmlDto dto : orderXmlRootDto.getOrderXmlDtoList()) {

            Order order = new Order();

            order.setCustomer(dto.getCustomer());

            if (!validationUtil.isValid(order)) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            order.setDateTime(LocalDateTime.parse(dto.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

            Employee employee = this.employeeRepository.findByName(dto.getEmployee()).orElse(null);
            if (employee == null) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            order.setEmployee(employee);

            try {
                order.setType(OrderType.valueOf(dto.getType()));
            } catch (IllegalArgumentException e) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            List<OrderItem> orderItems = new ArrayList<>();

            for (OrderItemDto orderItemDto : dto.getItemsRootDto().getOrderItemDtoList()) {

                OrderItem orderItem = mapper.map(orderItemDto, OrderItem.class);
                if (!validationUtil.isValid(orderItem)) {
                    break;
                }
                Item item = this.itemRepository.findByName(orderItemDto.getName()).orElse(null);
                if (item == null) {
                    break;
                }

                orderItem.setItem(item);

                orderItems.add(orderItem);
            }

            if (orderItems.size() != dto.getItemsRootDto().getOrderItemDtoList().size()) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            this.orderRepository.saveAndFlush(order);

            orderItems.forEach(oi -> {
                oi.setOrder(order);
                orderItemRepository.saveAndFlush(oi);
                sb.append("Order for ").append(order.getCustomer()).append(" on ").append(dto.getDateTime());
            });

        }

        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();

        this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getEmployee().getPosition().getName().equals("Burger Flipper"))
                .sorted(((o1, o2) -> {
                    int compare = o1.getEmployee().getName().compareTo(o2.getEmployee().getName());

                    if (compare == 0) {
                        return Integer.compare(o1.getId(), o2.getId());
                    }

                    return compare;
                }))
                .forEach(o -> sb.append(o.toString()));

        return sb.toString();
    }
}
