import beans.Boards;
import core.BoardApi;
import io.restassured.http.Method;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

import static core.TrelloConstants.CHAR_LIMIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.apache.commons.lang3.RandomStringUtils;

public class TrelloTests {
    private final String NAME = RandomStringUtils.random(10, true, true);

    @Test
    public void createAndDeleteNewBoard() {

        Boards board = BoardApi.getBoard(BoardApi.with()
                .name(NAME)
                .callApi(Method.POST));

        BoardApi.with()
                .id(board.getId())
                .callApi(Method.GET)
                .then()
                .spec(BoardApi.successResponse());

        BoardApi.removeBoard(board);

        BoardApi.with()
                .id(board.getId())
                .callApi(Method.GET)
                .then()
                .spec(BoardApi.boardNotFound());
    }

    @Test
    public void renameBoard() {
        String new_name = "New name for the board";

        Boards board = BoardApi.getBoard(BoardApi.with()
                .name(NAME)
                .callApi(Method.POST));

        BoardApi.with()
                .id(board.getId())
                .name(new_name)
                .callApi(Method.PUT)
                .then()
                .spec(BoardApi.successResponse());

        String name = BoardApi.with()
                .id(board.getId())
                .callApi(Method.GET)
                .then()
                .extract().as(Boards.class).getName();

        BoardApi.removeBoard(board);

        assertThat(name, is(new_name));
    }

    @Test
    public void incorrectBoardName(){
        String incorrect_name = RandomStringUtils.random(CHAR_LIMIT, true, true);

        BoardApi.with()
                .name(incorrect_name)
                .callApi(Method.POST)
                .then()
                .spec(BoardApi.badRequest("invalid value for name"));
    }

    @Test
    public void checkBoardInfo() {
        SoftAssertions softly = new SoftAssertions();

        Boards board = BoardApi.getBoard(BoardApi.with()
                .id(
                        BoardApi.getBoard(BoardApi.with()
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
}
