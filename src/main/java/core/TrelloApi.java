package core;

public class TrelloApi {

    //builder pattern
    private TrelloApi() {}

    public static final String YANDEX_SPELLER_API_URI =
            "https://api.trello.com/1/members/me/?key=%s&token=%s";
    public static String yourAPIKey = System.getenv("TrelloAPIKey");
    public static String yourAPIToken = System.getenv("TrelloAPIToken");

    public static void main(String[] args) {
        System.out.println(String.format(YANDEX_SPELLER_API_URI,yourAPIKey,yourAPIToken));
    }
}
