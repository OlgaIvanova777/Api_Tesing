package core;

import beans.Boards;
import beans.Cards;
import beans.Lists;
import beans.Objects;
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

import java.lang.reflect.Type;
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

        public ApiBuilder id(String id) {
            trelloApi.params.put(ID, id);
            return this;
        }

        public ApiBuilder name(String name) {
            trelloApi.params.put(NAME_PARAM, name);
            return this;
        }

        public ApiBuilder key(){
            trelloApi.params.put(KEY_PARAM, TRELLO_API_KEY_VALUE);
            return this;
        }
        public ApiBuilder token() {
            trelloApi.params.put(TOKEN_PARAM, TRELLO_API_TOKEN_VALUE);
            return this;
        }



 //TRELLO_NEW_BOARD_API_URL
        public ApiBuilder new_board(String name /*,String defaultLabels, String defaultLists,
                                    String keepFromSource, String prefs_permissionLevel, String prefs_voting,
                                    String prefs_comments, String prefs_invitations, String prefs_selfJoin,
                                    String prefs_cardCovers, String prefs_background, String prefs_cardAging*/,
                                    String key, String token) {
            trelloApi.params.put(NAME_PARAM, name);
            trelloApi.params.put(KEY_PARAM, key);
            trelloApi.params.put(TOKEN_PARAM, token);
            return this;
        }
      public ApiBuilder new_board(String name) {
            return new_board(name /*, "true", "true",
                    "none", "private", "disabled",
                    "members", "members", "true",
                    "true","blue" , "regular"*/,
                    TRELLO_API_KEY_VALUE, TRELLO_API_TOKEN_VALUE);
        }


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
    public static List<Objects> getTrelloBoardsAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Objects>>() {}.getType());
    }

    //set base request and response specifications to use in tests
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
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .setBaseUri(TRELLO_ALL_BOARDS_API_URL)
                .build();
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
