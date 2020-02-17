import beans.Boards;
import core.AppProperties;
import core.BoardApi;
import io.restassured.http.Method;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import java.util.Random;

import static core.TrelloConstants.CHAR_LIMIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class TrelloTests {
    public static final String TRELLO_NEW_BOARD_API_URL = AppProperties.getStringProperty("new_board_url");

    private final String NAME = generateRandomName(10);

    @Test
    public void createAndDeleteNewBoard() {

        Boards board = BoardApi.getBoard(BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(NAME)
                .callApi(Method.POST));

        BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .spec(BoardApi.successResponse());

        BoardApi.removeBoard(board);

        BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .spec(BoardApi.boardNotFound());
    }

    @Test
    public void renameBoard() {
        String new_name = "New name for the board";

        Boards board = BoardApi.getBoard(BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(NAME)
                .callApi(Method.POST));

        BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .name(new_name)
                .callApi(Method.PUT)
                .then()
                .spec(BoardApi.successResponse());

        String name = BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL + board.getId())
                .callApi(Method.GET)
                .then()
                .extract().as(Boards.class).getName();

        BoardApi.removeBoard(board);

        assertThat(name, is(new_name));
    }

    @Test
    public void incorrectBoardName(){
        String incorrect_name = generateRandomName(CHAR_LIMIT);

        BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL)
                .name(incorrect_name)
                .callApi(Method.POST)
                .then()
                .spec(BoardApi.badRequest("invalid value for name"));
    }

    @Test
    public void checkBoardInfo() {
        SoftAssertions softly = new SoftAssertions();

        Boards board = BoardApi.getBoard(BoardApi.with()
                .url(TRELLO_NEW_BOARD_API_URL +
                        BoardApi.getBoard(BoardApi.with()
                                .url(TRELLO_NEW_BOARD_API_URL)
                                .name(NAME)
                                .callApi(Method.POST))
                                .getId()
                )
                .callApi(Method.GET));

        BoardApi.removeBoard(board);

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
