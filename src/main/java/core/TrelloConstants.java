package core;

public class TrelloConstants {
    public static final String TRELLO_API_KEY_VALUE = "YOUR_KEY";
    public static final String TRELLO_API_TOKEN_VALUE = "YOUR_TOKEN";

    public static final String TRELLO_ALL_BOARDS_API_URL = "https://api.trello.com/1/members/me/boards?fields=name,url";
    public static final String TRELLO_NEW_BOARD_API_URL = "https://api.trello.com/1/boards/";

    public static final String NAME_PARAM = "name";
    public static final String KEY_PARAM = "key";
    public static final String TOKEN_PARAM = "token";
    public static final int CHAR_LIMIT = 16385;
}
