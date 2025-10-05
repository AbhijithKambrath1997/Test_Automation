package rest.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import commonUtil.templates.TestTemplate;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest.endpoints.PetEndpoints;
import rest.payloads.Pet;

import java.util.List;

public class PetTest {

    private final List<String> statuses = List.of("available", "pending", "sold");
    private final TestTemplate template = TestTemplate.create();
    private final Faker faker = template.getFaker();
    private final PetEndpoints petEndpoints = new PetEndpoints();

    @Test
    public void createPet() {

        Pet pet = Pet.builder()
                .id(faker.idNumber().hashCode())
                .name(faker.artist().name())
                .status(statuses.getFirst())
                .build();

        JsonNode categoryNode = pet.buildNode(faker.idNumber().hashCode(), faker.artist().name());
        JsonNode tagNode = pet.buildNode(faker.idNumber().hashCode(), faker.artist().name());
        pet.setCategory(categoryNode);
        pet.setTags(List.of(tagNode));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.valueToTree(pet);

        Response response = petEndpoints.createPet(node);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);


    }
}
