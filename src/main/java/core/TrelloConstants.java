package core;

public class TrelloConstants {
    public static final String TRELLO_API_URI =
            "https://api.trello.com/1/members/me/?key=%s&token=%s";
    public static final String TRELLO_API_KEY = System.getenv("TrelloAPIKey");
    public static final String TRELLO_API_TOKEN = System.getenv("TrelloAPIToken");
}

