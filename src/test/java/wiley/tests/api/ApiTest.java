package wiley.tests.api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertTrue;

public class ApiTest {

    ResponseSpecification respSpec;
    Response response;
    String url;
    String delay;

    @Test(description = "Проверка запроса автокомплита")
    public void autocompleteTest() {
        respSpec = new ResponseSpecBuilder()
                .expectBody(matchesJsonSchemaInClasspath("json-schemas/autocomplete-schema.json"))
                .build();

        url = "https://www.wiley.com/en-us/search/autocomplete/comp_00001H9J?term=Java";
        response = given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .spec(respSpec)
                .body("suggestions.term", hasSize(4))
                .body("pages", hasSize(4))
                .body("pages.title", hasSize(4))
                .extract().response();

        List<Map<String, String>> terms = response.jsonPath().getList("suggestions");
        for (Map term : terms) {
            assertTrue(term.get("term").toString().contains("<span class=\"search-highlight\">java</span>"));
        }

        List<Map<String, String>> pages = response.jsonPath().getList("pages");
        for (Map page : pages) {
            assertTrue(page.get("title").toString().contains("Wiley"));
        }
    }

    @Test(description = "POST/delay/{delay} returns a delayed response (max of 10 seconds)")
    public void binDelayTest() {
        url = "https://httpbin.org/delay/";

        // Тест падает, время ответа более 10 секунд
        delay = "11";

        given()
                .when()
                .post(url + delay)
                .then()
                .statusCode(200)
                .and().time(lessThan(10L), TimeUnit.SECONDS);
    }

    @Test(description = "GET/image/png returns a simple PNG image")
    public void contentImgTest() {
        url = "https://httpbin.org/image/png";
        given()
                .when()
                .get(url)
                .then()
                .statusCode(200)
                .contentType("image/png");
    }
}
