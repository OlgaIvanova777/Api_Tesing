package core;

import beans.Boards;
import beans.Cards;
import beans.Lists;
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
    private static String URL = String.format(TRELLO_USER_API_URI, TRELLO_API_KEY, TRELLO_API_TOKEN);

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


        public ApiBuilder board(String options) {
            trelloApi.params.put(NAME_PARAM, options);
            return this;
        }

        public ApiBuilder card(String options) {
            trelloApi.params.put(NAME_PARAM, options);
            return this;
        }

        public ApiBuilder lists(String options) {
            trelloApi.params.put(NAME_PARAM, options);
            return this;
        }

        public Response callApi() {
            return RestAssured.with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .get(URL).prettyPeek();
        }
    }

    //get ready Boards answers list form api response
    public static List<Boards> getTrelloBoardsAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Boards>>() {}.getType());
    }
    //get ready Boards answers list form api response
    public static List<Lists> getTrelloListAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Lists>>() {}.getType());
    }
    //get ready Boards answers list form api response
    public static List<Cards> getTrelloCardsAnswers(Response response){
        return new Gson().fromJson( response.asString().trim(), new TypeToken<List<Cards>>() {}.getType());
    }

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
                .setBaseUri(URL)
                .build();
    }


    public static void main(String[] args) {
        System.out.println();
    }
}
