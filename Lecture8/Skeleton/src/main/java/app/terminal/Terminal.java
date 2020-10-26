package app.terminal;

import app.domain.dto.PersonDto;
import app.domain.model.Person;
import app.services.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class Terminal implements CommandLineRunner {

    private static final String PERSON_JSON = "{\n" +
            "  \"firstName\": \"Mitko\",\n" +
            "  \"phoneNumbers\": [\n" +
            "    {\n" +
            "      \"number\": \"2222222\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"number\": \"3333333\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"number\": \"1111111\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"address\": {\n" +
            "    \"city\": \"Sofia\",\n" +
            "    \"country\": \"Bulgaria\",\n" +
            "    \"street\": \"Vitoshka 15\"\n" +
            "  }\n" +
            "}";

    private final PersonService personService;

    @Autowired
    public Terminal(PersonService personService) {
        this.personService = personService;
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {

//        gsonToJson();

        gsonFromJson();

    }

    private void gsonFromJson() {

        Gson gson = new GsonBuilder().create();

        PersonDto dto = gson.fromJson(PERSON_JSON, PersonDto.class);

        ModelMapper modelMapper = new ModelMapper();

        Person person = modelMapper.map(dto, Person.class);
        person.getPhoneNumbers().forEach(ph -> ph.setPerson(person));

        this.personService.save(person);
    }

    private void gsonToJson() {
        Person p = personService.getById(1L);

        if (p == null) {
            System.out.println("ERROR");
            return;
        }

        PersonDto dto = new PersonDto(p);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        System.out.println(gson.toJson(dto));
    }
}
