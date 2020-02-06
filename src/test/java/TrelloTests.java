import beans.Boards;
import core.TrelloApi;
import io.restassured.http.Method;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Random;

import static core.TrelloConstants.CHAR_LIMIT;
import static core.TrelloConstants.TRELLO_NEW_BOARD_API_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class TrelloTests {

    private final String NAME = generateRandomName(10);

    @Test
    public void createAndDeleteNewBoard() {

        Boards board = TrelloApi.getAnswer(TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(NAME)
                .callApi(Method.POST), Boards.class);

        TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .spec(TrelloApi.successResponse());

        TrelloApi.removeBoard(board);

        TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .spec(TrelloApi.boardNotFound());
    }

    @Test
    public void renameBoard() {
        String new_name = "New name for the board";

        Boards board = TrelloApi.getAnswer(TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(NAME)
                .callApi(Method.POST), Boards.class);

        TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .name(new_name)
                .callApi(Method.PUT)
                .then()
                .spec(TrelloApi.successResponse());

        String name = TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .extract().as(Boards.class).getName();

        TrelloApi.removeBoard(board);

        assertThat(name, is(new_name));
    }

    @Test
    public void incorrectBoardName(){
        String incorrect_name = generateRandomName(CHAR_LIMIT);

        TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(incorrect_name)
                .callApi(Method.POST)
                .then()
                .spec(TrelloApi.badRequest());
    }

    @Test
    public void checkBoardInfo() {
        SoftAssertions softly = new SoftAssertions();

        Boards board = TrelloApi.getAnswer(TrelloApi.with()
                .url(TRELLO_NEW_BOARD_API_URL +
                        TrelloApi.getAnswer(TrelloApi.with()
                                .url(TRELLO_NEW_BOARD_API_URL)
                                .name(NAME)
                                .callApi(Method.POST), Boards.class)
                                .getId()
                )
                .callApi(Method.GET), Boards.class);

        TrelloApi.removeBoard(board);

        softly.assertThat(board.getName()).isEqualTo(NAME);
        softly.assertThat(board.getUrl()).contains(NAME);
        softly.assertThat(board.getPrefs().getPermissionLevel()).isEqualTo("private");
        softly.assertThat(board.getLabelNames().getGreen()).isEmpty();
    }


    private String generateRandomName (int targetStringLength){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
