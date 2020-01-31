import beans.Objects;
import core.TrelloApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static core.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.lessThan;


public class TrelloTests {
    public static final String CARD_ID = "5e31b5a9e4b3d630c081bd9d";
    private final String BOARD_ID = "5e31b574d56d512ae05a650d";
    private final String TRELLO_BOARD_API_URL = TRELLO_NEW_BOARD_API_URL + BOARD_ID;
    private final String TRELLO__BOARD_WITH_CARDS_API_URL = TRELLO_BOARD_API_URL + "/cards";
    private final String NEW_CARD_NAME = "Order cat's food ASAP";

    @Test
    public void simpleTrelloApiCall() {
        RestAssured
                .given()
                .queryParam(ACTIONS_PARAM, "All")
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .param(FIELDS_PARAM, "name,desc,descData,closed,idOrganization,pinned,url,shortUrl,prefs,labelNames")
                .accept(ContentType.JSON)
                .header("x-trello-version", "1.1963.0")
                .and()
                .body("backgroundImageScaled")
                .log().everything()
                .when()
                .get(TRELLO_BOARD_API_URL)
                .prettyPeek()
                .then()

                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(Matchers.allOf(
                        Matchers.stringContainsInOrder(Arrays.asList(BOARD_ID, "TrelloTestBoard")),
                        Matchers.containsString("https://trello.com/"))
                )
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)); // Milliseconds
    }

    // different http methods calls
    @Test
    public void trelloApiCallsWithDifferentMethods() {
        int number = (int) (Math.random() * 1000 + 51);

        //GET
        System.out.println("\n====================GET START=================================================");
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .get(TRELLO__BOARD_WITH_CARDS_API_URL)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        System.out.println("\n====================GET FINISH=================================================");

        //HEAD
        System.out.println("\n====================HEAD START=================================================");
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .head(TRELLO__BOARD_WITH_CARDS_API_URL)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        System.out.println("\n====================HEAD FINISH=================================================");

        //OPTIONS
        System.out.println("\n====================OPTIONS START=================================================");
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .options(TRELLO__BOARD_WITH_CARDS_API_URL)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
        System.out.println("\n====================OPTIONS FINISH=================================================");

        //POST
        System.out.println("\n====================POST START=================================================");

        Object newBoardId = RestAssured.given()
                .log().everything()
                .baseUri(TRELLO_NEW_BOARD_API_URL)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"BOARD_" + number + "\", \"key\": \"" + TRELLO_API_KEY_VALUE +
                        "\", \"token\": \"" + TRELLO_API_TOKEN_VALUE + "\"}")
                .when()
                .post()
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().as(Objects.class).getId();
        System.out.println("\n====================POST FINISH=================================================");


        //PUT
        System.out.println("\n====================PUT START=================================================");
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .when()
                .param(NAME_PARAM, NEW_CARD_NAME)
                .put(TRELLO_NEW_CARDS_API_URL + CARD_ID)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200);
        System.out.println("\n====================PUT FINISH=================================================");


        System.out.println("\n====================DELETE START=================================================");

        //DELETE
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log()
                .everything()
                .delete(TRELLO_NEW_BOARD_API_URL + newBoardId)
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusLine("HTTP/1.1 200 OK");
        System.out.println("\n====================DELETE FINISH=================================================");
    }


    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(TrelloApi.baseRequestConfiguration())
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .get().prettyPeek()
                .then().specification(TrelloApi.successResponse());
    }

    @Test
    public void reachBuilderUsage() {
        TrelloApi.with()
                .key()
                .token()
                .callGetApi(TRELLO_ALL_BOARDS_API_URL)
                .then().specification(TrelloApi.successResponse());
    }

    @Test
    public void CheckBoardsForMember() {

        List<Objects> answers =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .key()
                                .token()
                                .callGetApi(TRELLO_ALL_BOARDS_API_URL));

        assertThat("expected number of answers is wrong.", answers.size(), equalTo(5));
    }

    @Test
    public void checkCardsDetails() {

        Objects answer = TrelloApi.getAnswer(
                TrelloApi.with().key().token().callGetApi(TRELLO_NEW_CARDS_API_URL + CARD_ID));
        assertThat(answer.getName(), equalTo(NEW_CARD_NAME));
        assertThat(answer.getDesc(), equalTo("Farmina Vet Life 5 kg"));
        assertThat(answer.getId(), equalTo(CARD_ID));
    }


    @Test
    public void CheckBoardsForMember2() {

        List<Objects> columns =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .key()
                                .token()
                                .callGetApi(TRELLO_NEW_BOARD_API_URL + BOARD_ID + "/lists"));

        String column0 = columns.get(0).getId();
        String column1 = columns.get(1).getId();
        String column2 = columns.get(2).getId();

        List<Objects> cards0 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .key()
                                .token()
                                .callGetApi(TRELLO_LIST_API_URL + column0) + "/cards"));

        List<Objects> cards1 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .key()
                                .token()
                                .callGetApi(TRELLO_LIST_API_URL + column1+ "/cards"));

        List<Objects> cards2 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .key()
                                .token()
                                .callGetApi(TRELLO_LIST_API_URL + column2+ "/cards"));


        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(columns.size()).isEqualTo(3);
        softly.assertThat(columns.get(0).getName()).isEqualTo("To Do");
        softly.assertThat(columns.get(2).getName()).isEqualTo("Doing");
        softly.assertThat(columns.get(3).getName()).isEqualTo("Done");


/*    @Test
    public void testClearRA() {
          RestAssured.requestSpecification = TrelloApi.baseRequestConfiguration();


        given()
                .log().everything()
                .baseUri(TRELLO_NEW_BOARD_API_URL)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"someboardname7771\", \"key\": \"cfec53e08b8393f77dd65ba6525039c8\", \"token\": \"2f58ade2a9219cc72027f740317288f8afbf0045a49cbbacfdcf2c9fe3612610\"}")
                .when()
                .post()
                .prettyPeek()
                .then()
                .statusCode(200)
                .assertThat().body("name", equalTo("someboardname7771"));
    }

    @Test
    public void testClearRA1() {
        RestAssured.requestSpecification = TrelloApi.baseRequestConfiguration();


        Objects as = given()
                .log().everything()
                .baseUri(TRELLO_NEW_BOARD_API_URL)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"someboardname7771\", \"key\": \"cfec53e08b8393f77dd65ba6525039c8\", \"token\": \"2f58ade2a9219cc72027f740317288f8afbf0045a49cbbacfdcf2c9fe3612610\"}")
                .when()
                .post()
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().as(Objects.class);

        assertThat(as.getName(), equalTo("someboardname7771"));
        System.out.println(as);



        Object newBoardId88 = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log().everything()
                .when()
                .body(TrelloApi.with().new_board("BOARD_" + number).callPostApi(TRELLO_NEW_BOARD_API_URL))
                .then()
                .statusCode(201)
    }*/
    }
}
