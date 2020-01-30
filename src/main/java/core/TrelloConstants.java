package core;

public class TrelloConstants {
    public static final String TRELLO_API_KEY = System.getenv("TrelloAPIKey");
    public static final String TRELLO_API_TOKEN = System.getenv("TrelloAPIToken");
    public static final String TRELLO_USER_API_URI = "https://api.trello.com/1/members/me/?key=%s&token=%s";
    public static final String TRELLO_CREATE_NEW_BOARD =
            "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
    public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

    public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

    public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

    public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";

    public static final String BOARD_NAME = "OliTestBoard";

    public static final String NAME_PARAM = "name";
    public static final String ID_PARAM = "id";
    public static final String URL_PARAM = "url";
    public static final String TEXT_PARAM = "text";
}


       /*    {
             "name": "TrelloTestBoard",
            "id": "5e31b574d56d512ae05a650d",
            "url": "https://trello.com/b/oswwtYGT/trellotestboard"
            },*/

/*
    key
            cfec53e08b8393f77dd65ba6525039c8
token
        2f58ade2a9219cc72027f740317288f8afbf0045a49cbbacfdcf2c9fe3612610
        */
