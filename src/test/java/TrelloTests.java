import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static core.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;


public class TrelloTests {
    @Test
    public void simpleTrelloApiCall() {
   /*     RestAssured
                .given()
//                .queryParam(PARAM_TEXT, WRONG_WORD_EN)
//                .params(PARAM_LANG, Languages.EN, "CustomParameter", "valueOfParam")
                .accept(ContentType.JSON)
                .auth().basic(TRELLO_API_KEY, TRELLO_API_TOKEN)
                .header("custom header1", "header1.value")
                .and()
                .body("some body payroll")
                .log().everything()
                .when()
                .get(YANDEX_SPELLER_API_URI)
                .prettyPeek()
                .then()

                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.stringContainsInOrder(Arrays.asList(WRONG_WORD_EN, RIGHT_WORD_EN)),
                        Matchers.containsString("\"code\":1")))
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)); // Milliseconds*/
    }

}
