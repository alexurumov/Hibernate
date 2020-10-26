package alararestaurant.service;

import alararestaurant.domain.dtos.ItemJsonImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private static final String ITEMS_JSON_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/ExamPrep1/AlaraRestaurant/src/main/resources/files/items.json";

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, FileUtil fileUtil, Gson gson, ModelMapper mapper, ValidationUtil validationUtil) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        return this.fileUtil.readFile(ITEMS_JSON_FILE_PATH);
    }

    @Override
    public String importItems(String items) throws IOException {

        StringBuilder sb = new StringBuilder();

        ItemJsonImportDto[] itemJsonImportDtos = gson.fromJson(items, ItemJsonImportDto[].class);

        for (ItemJsonImportDto dto : itemJsonImportDtos) {
            Item item = mapper.map(dto, Item.class);

            if (!validationUtil.isValid(item)) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            if (this.itemRepository.findByName(item.getName()).orElse(null) != null) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            Category category = this.categoryRepository.findByName(dto.getCategory()).orElse(null);

            if (category == null) {
                category = new Category();
                category.setName(dto.getCategory());
            }

            if (!validationUtil.isValid(category)) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            categoryRepository.saveAndFlush(category);

            item.setCategory(category);
            itemRepository.saveAndFlush(item);
            sb.append("Record ").append(item.getName()).append(" successfully imported.").append(System.lineSeparator());
        }

        return sb.toString();
    }
}
