package core;

public class TrelloConstants {
    public static final String TRELLO_API_KEY_VALUE = System.getenv("TrelloAPIKey");
    public static final String TRELLO_API_TOKEN_VALUE = System.getenv("TrelloAPIToken");

    public static final String TRELLO_ALL_BOARDS_API_URL = "https://api.trello.com/1/members/me/boards?fields=name,url";
    public static final String TRELLO_NEW_BOARD_API_URL = "https://api.trello.com/1/boards/";
    public static final String TRELLO_NEW_CARDS_API_URL = "https://api.trello.com/1/cards/";
    public static final String TRELLO_LIST_API_URL = "https://api.trello.com/1/lists/";

    public static final String NAME_PARAM = "name";
    public static final String KEY_PARAM = "key";
    public static final String TOKEN_PARAM = "token";
    public static final String ACTIONS_PARAM = "actions";

    public static final String FIELDS_PARAM = "fields";
    public static final String ID = "id";
}
