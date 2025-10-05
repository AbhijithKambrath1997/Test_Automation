package restAssured.test;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import commonUtils.templates.TestTemplate;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;
import rest.endpoints.UserEndpoints;
import rest.payloads.User;

public class UserTest {

    private static final String UNKNOWN = "unknown";
    private static final String MESSAGE = "message";
    private static final String CODE = "code";
    private static final String TYPE = "type";

    private final UserEndpoints userEndpoints = new UserEndpoints();
    private final Faker faker = new Faker();
    private final TestTemplate template = TestTemplate.create();

    @Test(priority = 1)
    public void testCreateUser() {
        User user = buildUser();
        Response response = createUser(user);

        template.print("Validate User Creation");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        verifyResponse(String.valueOf(user.getId()), response);
    }

    @Test(priority = 2)
    public void testReadUser() {
        User user = buildUser();
        createUser(user);

        template.print("Validate User Created");
        /*verifyUser(user);*/
    }

    @Test(priority = 3)
    public void testUpdateUser() {
        User user = buildUser();
        createUser(user);
        user.setEmail(faker.internet().safeEmailAddress());

        template.print("Update User");
        Response response = userEndpoints.updateUser(user, user.getUsername());

        template.print("Validate User Update");
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        verifyResponse(String.valueOf(user.getId()), response);

        template.print("Verify User Updated");
        /*verifyUser(user);*/
    }

    @Test(priority = 4)
    public void testDeleteUser() {
        User user = buildUser();
        createUser(user);

        template.print("Delete User");
        userEndpoints.deleteUser(user.getUsername());
    }

    private User buildUser() {
        template.print("Build User");
        Name name = faker.name();
        return User.builder()
                .id(faker.idNumber().hashCode())
                .username(name.username())
                .firstName(name.firstName())
                .lastName(name.lastName())
                .email(faker.internet().safeEmailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .build();
    }

    private Response createUser(User user) {
        template.print("Create User");
        Response response = userEndpoints.createUser(user);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        return response;
    }

    private void verifyResponse(String message, Response response) {
        JSONObject object = new JSONObject();
        object.put(CODE, 200);
        object.put(TYPE, UNKNOWN);
        object.put(MESSAGE, message);
        JSONAssert.assertEquals(object.toString(), response.asString(), JSONCompareMode.LENIENT);
    }
}
