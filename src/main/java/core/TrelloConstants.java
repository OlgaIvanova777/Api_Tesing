package core;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class TrelloConstants {
    public static final String TRELLO_API_KEY_VALUE = System.getenv("TrelloAPIKey");
    public static final String TRELLO_API_TOKEN_VALUE = System.getenv("TrelloAPIToken");

    public static final String TRELLO_NEW_BOARD_API_URL = "https://api.trello.com/1/boards/";
    public static final String TRELLO_NEW_CARDS_API_URL = "https://api.trello.com/1/cards/";
    public static final String TRELLO_ALL_BOARDS_API_URL = "https://api.trello.com/1/members/me/boards?fields=name,url";


    public static final String BOARD_NAME = "OliTestBoard";

    public static final String NAME_PARAM = "name";
    public static final String KEY_PARAM = "key";
    public static final String TOKEN_PARAM = "token";
    public static final String DEFAULT_LABELS_PARAM = "defaultLabels";
    public static final String DEFAULT_LISTS_PARAM = "defaultLists";
    public static final String DESC_PARAM = "desc";
//    public static final String ID_ORGANIZATION_PARAM = ID + "Organization";
//    public static final String ID_BOARD_SOURCE_PARAM = ID + "BoardSource";
    public static final String KEEP_FROM_SOURCE_PARAM = "keepFromSource";
    public static final String POWER_UPS_PARAM = "powerUps";
    public static final String PREFS_PERMISSION_LEVEL_PARAM = "prefs_permissionLevel";
    public static final String PREFS_VOTING_PARAM = "prefs_voting";
    public static final String PREFS_COMMENTS_PARAM = "prefs_comments";
    public static final String PREFS_INVITATIONS_PARAM = "prefs_invitations";
    public static final String PREFS_SELF_JOIN_PARAM = "prefs_selfJoin";
    public static final String PREFS_CARD_COVERS_PARAM = "prefs_cardCovers";
    public static final String PREFS_BACKGROUND_PARAM = "prefs_background";
    public static final String PREFS_CARD_AGING_PARAM = "prefs_cardAging";
    public static final String ACTIONS_PARAM = "actions";
    public static final String BOARD_STARS_PARAM = "boardStars";
    public static final String CARDS_PARAM = "cards";
    public static final String CARD_PLUGIN_DATA_PARAM = "card_pluginData";
    public static final String CHECKLISTS_PARAM = "checklists";
    public static final String CUSTOM_FIELDS_PARAM = "customFields";
    public static final String FIELDS_PARAM = "fields";
    public static final String ID = "id";
    public static final String LABELS_PARAM = "labels";
    public static final String LISTS_PARAM = "lists";
    public static final String MEMBERS_PARAM = "members";
    public static final String MEMBERSHIPS_PARAM = "memberships";
    public static final String MEMBERS_INVITED_PARAM = "membersInvited";
    public static final String MEMBERS_INVITED_FIELDS_PARAM = "membersInvited_fields";
    public static final String PLUGIN_DATA_PARAM = "pluginData";
    public static final String ORGANIZATION_PARAM = "organization";
    public static final String ORGANIZATION_PLUGIN_DATA_PARAM = "organization_pluginData";
    public static final String MY_PREFS_PARAM = "myPrefs";
    public static final String TAGS_PARAM = "tags";
    public static final String COLOR_PARAM = "color";

/*
    @FunctionalInterface
    public interface TripleFunction<M extends String, T, U, R> {
        R replace(M str, T key, U value);
    }


    final static TripleFunction<String, String, String, String> replaceFunction = String::replace;

    static {

        final AtomicReference<String> result = new AtomicReference<>(TRELLO_NEW_BOARD_API_URL);
        HashMap<String, String> dictionary = new HashMap<>();
        dictionary.put("KEY1", "VALUE1");

        dictionary.forEach((key, value) -> result.set(replaceFunction.replace(result.get(), key, value)));

        System.out.println("New String = " + result.get());
    }*/
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
