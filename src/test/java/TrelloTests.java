import beans.Boards;
import core.TrelloApi;
import core.TrelloConstants;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static core.TrelloConstants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.lessThan;


public class TrelloTests {
    private String ID = "5e31b574d56d512ae05a650d";
    private String TRELLO_BOARD_API_URL = TRELLO_NEW_BOARD_API_URL + ID;
////
//
//
      @Test
    public void simpleSpellerApiCall() {
        RestAssured
                .given()
//                .queryParam(PARAM_TEXT, WRONG_WORD_EN)
//                .params(PARAM_LANG, Languages.EN, "CustomParameter", "valueOfParam")
                .accept(ContentType.JSON)
                .header("x-trello-version", "1.1963.0")
                .and()
                //.body("some body payroll")
                .log().everything()
                .when()
                .get(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek()
                .then()

                .assertThat()
                .statusCode(HttpStatus.SC_OK)
            //    .body(Matchers.allOf(
        //                Matchers.stringContainsInOrder(Arrays.asList(WRONG_WORD_EN, RIGHT_WORD_EN)),
                     //   Matchers.containsString("\"code\":1")))
                .contentType(ContentType.JSON)
                .time(lessThan(20000L)); // Milliseconds
    }

    // different http methods calls
    @Test
    public void spellerApiCallsWithDifferentMethods() {
        //GET
        RestAssured
                .given()
        //        .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .get(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //POST
        RestAssured
                .given()
             //   .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .post(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //HEAD
        RestAssured
                .given()
         //       .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .head(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //OPTIONS
        RestAssured
                .given()
        //        .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .options(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //PUT
        RestAssured
                .given()
      //          .param(PARAM_TEXT, WRONG_WORD_EN)
                .log().everything()
                .put(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //PATCH
        RestAssured
                .given()
          //      .param(PARAM_TEXT, WRONG_WORD_EN)
                .log()
                .everything()
                .patch(TRELLO_NEW_BOARD_API_URL + ID)
                .prettyPeek();
        System.out.println("\n=====================================================================");

        //DELETE
        RestAssured
                .given()
              //  .param(PARAM_TEXT, WRONG_WORD_EN)
                .log()
                .everything()
                .delete(TRELLO_NEW_BOARD_API_URL + ID).prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .statusLine("HTTP/1.1 405 Method not allowed");
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
