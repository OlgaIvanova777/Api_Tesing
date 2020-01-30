package core;

import beans.Boards;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static core.TrelloConstants.*;
import static org.hamcrest.Matchers.lessThan;

public class TrelloApi {
   // private static String URL = String.format(TRELLO_NEW_BOARD_API_URL, TRELLO_API_KEY_VALUE, TRELLO_API_TOKEN_VALUE);

    //builder pattern
    private TrelloApi() {
    }
    private HashMap<String, String> params = new HashMap<String, String>();

    public static ApiBuilder with() {
        TrelloApi api = new TrelloApi();
        return new ApiBuilder(api);
    }

    public static class ApiBuilder {
        TrelloApi trelloApi;

        private ApiBuilder(TrelloApi gcApi) {
            trelloApi = gcApi;
        }

/*        public ApiBuilder text(String text) {
            trelloApi.params.put(PARAM_TEXT, text);
            return this;
        }

        public ApiBuilder options(String options) {
            trelloApi.params.put(PARAM_OPTIONS, options);
            return this;
        }

        public ApiBuilder language(Languages language) {
            trelloApi.params.put(PARAM_LANG, language.languageCode);
            return this;
        }*/

        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
        public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

        public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

        public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

        public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
*/
        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";

        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
        */
        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
        */

        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
        */
        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";

        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
        */
        /*       public static final String TRELLO_CREATE_NEW_BOARD =
                        "https://api.trello.com/1/boards/?name=%s&defaultLabels=true&defaultLists=true&keepFromSource=none&prefs_permissionLevel=private&prefs_voting=disabled&prefs_comments=members&prefs_invitations=members&prefs_selfJoin=true&prefs_cardCovers=true&prefs_background=blue&prefs_cardAging=regular&key=%s&token=%s";
                public static final String TRELLO_GET_BOARDS = "curl https://api.trello.com/1/members/me/boards?fields=name,url&key=%s&token=%s";

                public static final String TRELLO_GET_LISTS = "https://api.trello.com/1/boards/%s/lists?cards=none&card_fields=all&filter=open&fields=all&key=%s&token=%s";

                public static final String TRELLO_CREATE_NEW_CARD = "https://api.trello.com/1/cards?name=%s&desc=%s&idList=idList&keepFromSource=all&key=%s&token=%s";

                public static final String TRELLO_GET_CARD = "https://api.trello.com/1/cards/%s?attachments=false&attachment_fields=all&members=false&membersVoted=false&checkItemStates=false&checklists=none&checklist_fields=all&board=false&list=false&pluginData=false&stickers=false&sticker_fields=all&customFieldItems=false&key=%s&token=%s";
        */




 //TRELLO_NEW_BOARD_API_URL
        public ApiBuilder new_board(String name, String defaultLabels, String defaultLists,
                                    String keepFromSource, String prefs_permissionLevel, String prefs_voting,
                                    String prefs_comments, String prefs_invitations, String prefs_selfJoin,
                                    String prefs_cardCovers, String prefs_background, String prefs_cardAging,
                                    String key, String token) {
            trelloApi.params.put(NAME_PARAM, name);
            trelloApi.params.put(DEFAULT_LABELS_PARAM, defaultLabels);
            trelloApi.params.put(DEFAULT_LISTS_PARAM, defaultLists);
          /*  trelloApi.params.put(DESC_PARAM, desc);
            trelloApi.params.put(ID_ORGANIZATION_PARAM, idOrganization);
            trelloApi.params.put(ID_BOARD_SOURCE_PARAM, idBoardSource);*/
            trelloApi.params.put(KEEP_FROM_SOURCE_PARAM, keepFromSource);
//            trelloApi.params.put(POWER_UPS_PARAM, powerUps);
            trelloApi.params.put(PREFS_PERMISSION_LEVEL_PARAM, prefs_permissionLevel);
            trelloApi.params.put(PREFS_VOTING_PARAM, prefs_voting);
            trelloApi.params.put(PREFS_COMMENTS_PARAM, prefs_comments);
            trelloApi.params.put(PREFS_INVITATIONS_PARAM, prefs_invitations);
            trelloApi.params.put(PREFS_SELF_JOIN_PARAM, prefs_selfJoin);
            trelloApi.params.put(PREFS_CARD_COVERS_PARAM, prefs_cardCovers);
            trelloApi.params.put(PREFS_BACKGROUND_PARAM, prefs_background);
            trelloApi.params.put(PREFS_CARD_AGING_PARAM, prefs_cardAging);
            trelloApi.params.put(KEY_PARAM, key);
            trelloApi.params.put(TOKEN_PARAM, token);
            return this;
        }
        public ApiBuilder new_board(String name) {
            return new_board(name, "true", "true",
                    "none", "private", "disabled",
                    "members", "members", "true",
                    "true","blue" , "regular",
                    TRELLO_API_KEY_VALUE, TRELLO_API_TOKEN_VALUE);
        }



/*
    public static final String ACTIONS_PARAM = "actions";
    public static final String BOARD_STARS_PARAM = "boardStars";
    public static final String CARDS_PARAM = "cards";
    public static final String CARD_PLUGIN_DATA_PARAM = "card_pluginData";
    public static final String CHECKLISTS_PARAM = "checklists";
    public static final String CUSTOM_FIELDS_PARAM = "customFields";
    public static final String FIELDS_PARAM = "fields";
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
        public static final String ID = "id";
*/



        //TRELLO_NEW_BOARD_API_URL
        public ApiBuilder board(String actions, String boardStars, String cards,
                                    String card_pluginData, String checklists, String customFields,
                                    String fields,String lists,
                                    String members, String memberships, String membersInvited,
                                    String membersInvited_fields, String pluginData, String organization,
                                    String organization_pluginData, String myPrefs, String tags,
                                    String key, String token) {
           // trelloApi.params.put(ID, id);
            trelloApi.params.put(ACTIONS_PARAM, actions);
            trelloApi.params.put(BOARD_STARS_PARAM, boardStars);
            trelloApi.params.put(CARDS_PARAM, cards);
            trelloApi.params.put(CARD_PLUGIN_DATA_PARAM, card_pluginData);
            trelloApi.params.put(CHECKLISTS_PARAM, checklists);
            trelloApi.params.put(CUSTOM_FIELDS_PARAM, customFields);
            trelloApi.params.put(FIELDS_PARAM, fields);
        //    trelloApi.params.put(LABELS_PARAM, labels);
            trelloApi.params.put(LISTS_PARAM, lists);
            trelloApi.params.put(MEMBERS_PARAM, members);
            trelloApi.params.put(MEMBERSHIPS_PARAM, memberships);
            trelloApi.params.put(MEMBERS_INVITED_PARAM, membersInvited);
            trelloApi.params.put(MEMBERS_INVITED_FIELDS_PARAM, membersInvited_fields);
            trelloApi.params.put(PLUGIN_DATA_PARAM, pluginData);
            trelloApi.params.put(ORGANIZATION_PARAM, organization);
            trelloApi.params.put(ORGANIZATION_PLUGIN_DATA_PARAM, organization_pluginData);
            trelloApi.params.put(MY_PREFS_PARAM, myPrefs);
            trelloApi.params.put(TAGS_PARAM, tags);
            trelloApi.params.put(KEY_PARAM, key);
            trelloApi.params.put(TOKEN_PARAM, token);
            return this;
        }
        public ApiBuilder board() {
            return board("all", "none", "none",
                    "false", "none", "false",
                    "name,desc,descData,closed,idOrganization,pinned,url,shortUrl,prefs,labelNames", "open",
                    "none","none" , "none",
                    "all", "false", "false",
                    "false","false","false",
                    TRELLO_API_KEY_VALUE, TRELLO_API_TOKEN_VALUE);
        }


  /*      public ApiBuilder new_card(String options) {
            trelloApi.params.put(NAME_PARAM, options);
            return this;
        }

        public ApiBuilder lists(String options) {
            trelloApi.params.put(NAME_PARAM, options);
            return this;
        }*/



        public Response callGetApi(String URL) {
            return RestAssured.with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .get(URL).prettyPeek();
        }

        public Response callPostApi(String URL) {
            return RestAssured.with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .post(URL).prettyPeek();
        }
    }

    //get ready Boards answers list form api response
    public static List<Boards> getTrelloBoardsAnswers(Response response){

        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Boards>>() {}.getType());
    }
/*    //get ready Lists answers list form api response
    public static List<Lists> getTrelloListAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Lists>>() {}.getType());
    }
    //get ready Cards answers list form api response
    public static List<Cards> getTrelloCardsAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Cards>>() {}.getType());
    }*/

    //set base request and response specifications tu use in tests
    public static ResponseSpecification successResponse(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration(){
        return new RequestSpecBuilder()
                .setAccept(ContentType.XML)
                .setRelaxedHTTPSValidation()
                .addHeader("custom header2", "header2.value")
                .addQueryParam("requestID", new Random().nextLong())
                .setBaseUri(TRELLO_NEW_BOARD_API_URL)
                .build();
    }






    public static void main(String[] args) {
        System.out.println();
    }
}
