import beans.Boards;
import core.TrelloApi;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSenderOptions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.List;

import static core.TrelloConstants.TRELLO_ALL_BOARDS_API_URL;
import static core.TrelloConstants.TRELLO_API_KEY_VALUE;
import static core.TrelloConstants.TRELLO_API_TOKEN_VALUE;
import static core.TrelloConstants.TRELLO_NEW_BOARD_API_URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class TrelloTests {

    // use base request and response specifications to form request and validate response.
//    @Test
//    public void useBaseRequestAndResponseSpecifications() {
//        RestAssured
//                .given(TrelloApi.baseRequestConfiguration())
//                .get().prettyPeek()
//                .then().specification(TrelloApi.successResponse());
//    }

    @Test
    public void reachBuilderUsage() {
        TrelloApi.with()
                .callApi(TRELLO_ALL_BOARDS_API_URL, Method.GET)
                .then().specification(TrelloApi.successResponse());
    }

    @Test
    public void checkBoardsForMember() {



/*
        Object newBoardId = RestAssured.given()
                .log().everything()
                .baseUri(TRELLO_NEW_BOARD_API_URL)
                .contentType(ContentType.JSON)
                .body("{\"name\": \"BOARD_"  + "\", \"key\": \"" + TRELLO_API_KEY_VALUE +
                        "\", \"token\": \"" + TRELLO_API_TOKEN_VALUE + "\"}")
                .when()
                .post()
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract().as(Boards.class).getId();
*/





        Boards answer = TrelloApi.getAnswer(TrelloApi.with()
                .withUrl(TRELLO_NEW_BOARD_API_URL)
                .name("NEWOLIBOARD")
                .callApi(Method.POST));

        TrelloApi.with()
                .withUrl(TRELLO_ALL_BOARDS_API_URL)
                .id(answer.getId())
                .callApi(Method.GET).then().statusCode(200);



/*
        List<Boards> answers =
                TrelloApi.getAnswers(
                        TrelloApi.with()

                              //  .method(Method.GET)
                                .callApi(TRELLO_ALL_BOARDS_API_URL, Method.GET));


        System.out.println("OLIOLI    " + board.getId() + "   " + board.getName());

        assertThat("expected number of answers is wrong.", answers.size(), equalTo(6));*/
    }

    @Test
    public void CreateANewBoard(){

    }



    @Test
    public void checkCardDetails() {

        Boards answer = TrelloApi.getAnswer(
                TrelloApi.with().callApi("" + "", Method.GET));
        assertThat(answer.getName(), equalTo(""));
        assertThat(answer.getDesc(), equalTo("Farmina Vet Life 5 kg"));
        assertThat(answer.getId(), equalTo(""));
    }


    @Test
    public void checkCardsAreInCorrectStateOnBoard() {
        SoftAssertions softly = new SoftAssertions();
        String cardsURL = "%s%s/cards";

        List<Boards> columns =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .callApi(TRELLO_NEW_BOARD_API_URL + "" + "/lists", Method.GET));

        String column0 = columns.get(0).getId();
        String column1 = columns.get(1).getId();
        String column2 = columns.get(2).getId();

        List<Boards> cards0 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .callApi(String.format(cardsURL, "", column0), Method.GET));

        List<Boards> cards1 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .callApi(String.format(cardsURL, "", column1), Method.GET));

        List<Boards> cards2 =
                TrelloApi.getAnswers(
                        TrelloApi.with()
                                .callApi(String.format(cardsURL, "", column2), Method.GET));

        softly.assertThat(columns.size()).isEqualTo(3);
        softly.assertThat(columns.get(0).getName()).isEqualTo("To Do");
        softly.assertThat(columns.get(1).getName()).isEqualTo("Doing");
        softly.assertThat(columns.get(2).getName()).isEqualTo("Done");

        softly.assertThat(cards0.size() + cards1.size() + cards2.size()).isEqualTo(5);

        softly.assertThat(cards0.get(0).getDesc()).isEqualTo("Farmina Vet Life 5 kg");
        softly.assertThat(cards1.get(0).getName()).isEqualTo("Complete API Testing mentoring");
        softly.assertThat(cards1.get(1).getName()).isEqualTo("Complete Mobile Testing mentoring");
        softly.assertThat(cards2.get(0).getName()).isEqualTo("Successfully pass the assessment");
        softly.assertThat(cards2.get(0).getDesc()).isEqualTo("Submit documents\nPrepare to technical interview\nDo the best");
    }
}
