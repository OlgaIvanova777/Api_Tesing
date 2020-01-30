import beans.Boards;
import core.TrelloApi;
import core.TrelloConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static core.TrelloConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.lessThan;


public class TrelloTests {
    private final String BOARD_ID = "5e31b574d56d512ae05a650d";
    private final String TRELLO_BOARD_API_URL = TRELLO_NEW_BOARD_API_URL + BOARD_ID;
    private final String TRELLO__BOARD_WITH_CARDS_API_URL = TRELLO_BOARD_API_URL + "/cards";
  //  private final String TRELLO_CARD_API_URL = TRELLO_NEW_BOARD_API_URL + "/%s";


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
    public void trelloApiCallsWithDifferentReadMethods() {
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
    }

    // different http methods calls
    @Test
    public void trelloApiCallsWithDifferentWriteMethods() {
        String card_id = "5e31b5a9e4b3d630c081bd9d";
        int number = (int) (Math.random() * 1000 + 51);

       /* //POST
        System.out.println("\n====================POST START=================================================");
        RestAssured
                .given()
               // .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .when()
                .body(TrelloApi.with().new_board("BOARD_"+ number))
                .post(TRELLO_NEW_BOARD_API_URL)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(201);
        System.out.println("\n====================POST FINISH=================================================");*/


        //PUT
        System.out.println("\n====================PUT START=================================================");
        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log().everything()
                .when()
                .param(NAME_PARAM, "Order cat's food ASAP")
                .put(TRELLO_NEW_CARDS_API_URL + card_id)
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200);
        System.out.println("\n====================PUT FINISH=================================================");


       //DELETE
/*        RestAssured
                .given()
                .params(KEY_PARAM, TRELLO_API_KEY_VALUE, TOKEN_PARAM, TRELLO_API_TOKEN_VALUE)
                .log()
                .everything()
                .delete(TRELLO_NEW_BOARD_API_URL + "591c26f96ab496d404b32a2d").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .statusLine("HTTP/1.1 200 OK");*/
    }


/*    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(YandexSpellerApi.baseRequestConfiguration())
                .param(PARAM_TEXT, WRONG_WORD_EN)
                .get().prettyPeek()
                .then().specification(YandexSpellerApi.successResponse());
    }

    @Test
    public void reachBuilderUsage(){
        YandexSpellerApi.with()
                .language(Languages.UK)
                .options("5")
                .text(WRONG_WORD_UK)
                .callApi()
                .then().specification(YandexSpellerApi.successResponse());
    }*/
//
//






    @Test
    public void CheckBoardDetails() {
        //GET
        RestAssured
                .given()
                .param(TrelloConstants.ID, "5e31b574d56d512ae05a650d")
                .log().everything()
                .get(TRELLO_NEW_BOARD_API_URL)
                .prettyPeek();
        System.out.println("\n=====================================================================");
    }

    @Test
    public void CheckBoardDetails_2() {
        List<Boards> answers =
                        TrelloApi.getTrelloBoardsAnswers(
                                TrelloApi.with().board().callGetApi(TRELLO_BOARD_API_URL));
        //assertThat("expected number of answers is wrong.", answers.size(), equalTo(3));
        System.out.println("\n=====================================================================");
        System.out.println("answers.get(0).totalMembersPerBoard " + answers.get(0).totalMembersPerBoard);
   //     assertThat(answers.get(0).totalMembersPerBoard, equalTo("1"));

    }
}
