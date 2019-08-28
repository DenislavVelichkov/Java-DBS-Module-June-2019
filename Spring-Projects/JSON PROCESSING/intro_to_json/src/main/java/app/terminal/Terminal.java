package app.terminal;

import app.domain.dto.PersonDto;
import app.domain.model.Person;
import app.service.PersonService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Terminal implements CommandLineRunner {
    private final static String personJson =
    "{\"firstName\":\"Mitko\",\"lastName\":\"Mitov\",\"phoneNumbers\":[{\"phoneNumber\":\"" +
    "1111\"}],\"address\":{\"city\":\"Sofia\",\"country\":\"Bulgaria\",\"street\":\"1000\"}}";

    private PersonService personService;

    public Terminal(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void run(String... strings) {
        gsonToJson();
        gsonFromJson();
    }

    private void gsonToJson() {
        Person person = this.personService.getById(1L);

        if (person == null) {
            System.out.println("Error!");
        }

        PersonDto personDto = new PersonDto(person);


        Gson gson = new GsonBuilder()
                        .excludeFieldsWithoutExposeAnnotation()
                        .create()
            ;

        System.out.println(gson.toJson(personDto));
    }

    private void gsonFromJson() {
        Gson gson = new GsonBuilder().create();
        PersonDto dto = gson.fromJson(personJson, PersonDto.class);

        ModelMapper mapper = new ModelMapper();
        Person person = mapper.map(dto, Person.class);
        person.getPhoneNumbers().forEach(ph -> ph.setPerson(person));
        this.personService.save(person);
    }

    /*Write Json to file*/

    /*
    <dependency>
    <groupId>com.googlecode.json-simple</groupId>
    <artifactId>json-simple</artifactId>
    <version>1.1.1</version>
    </dependency>
    */

    /* JSONObject obj = new JSONObject();
        obj.put("name", "mkyong.com");
        obj.put("age", 100);

        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");

        obj.put("messages", list);

        try (FileWriter file = new FileWriter("c:\\projects\\test.json")) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.print(obj);*/
}
