package core;

import beans.Boards;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.TrelloConstants.*;
import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.lessThan;

public class TrelloApi {
    //builder pattern
    private TrelloApi() {
    }

    private static Map<String, String> params = new HashMap<String, String>() {
        {
            put(KEY_PARAM, TRELLO_API_KEY_VALUE);
            put(TOKEN_PARAM, TRELLO_API_TOKEN_VALUE);
        }
    };

    private Method method;

    public static ApiBuilder with() {
        TrelloApi api = new TrelloApi();
        return new ApiBuilder(api);
    }

    public static class ApiBuilder {
        private TrelloApi trelloApi;
        String baseUrl;

        private ApiBuilder(TrelloApi gcApi) {
            trelloApi = gcApi;
        }

        public ApiBuilder withUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public ApiBuilder id(String id) {
            trelloApi.params.put(ID_PARAM, id);
            return this;
        }

        public ApiBuilder name(String name) {
            trelloApi.params.put(NAME_PARAM, name);
            return this;
        }

//        public ApiBuilder createBoard(ApiBuilder builder)
//        {
//
//            return builder;
//        }

        public Response callApi(String endPoint, Method method) {
            return RestAssured.with()
                    .queryParams(trelloApi.params)
                    //      .pathParams(trelloApi.pathParams)
//                    .spec(baseRequestConfiguration().baseUri(endPoint))
                    .log().all()
                    .request(method)
                    .prettyPeek();
        }

        public Response callApi(Method method) {
            final RequestSpecification specification;
            switch (method) {
                case POST:
                    specification = basePOSTRequestConfiguration();
                    break;
                case GET:
                    specification = baseGETRequestConfiguration();
                    break;
                default:
                    throw new RuntimeException("Method not supported:" + method);
            }

            return RestAssured
                    .given()
                    .spec(specification)
                    .baseUri(this.baseUrl)
                    .log().all()
                    .when()
                    .request(method)
                    .prettyPeek();
        }
    }

    //get ready Boards answers list form api response
    public static List<Boards> getAnswers(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<List<Boards>>() {
        }.getType());
    }

    public static Boards getAnswer(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Boards>() {
        }.getType());
    }

    public static Boards getBoard(Response response) {
        return response.getBody().as(Boards.class);
    }


    //set base request and response specifications to use in tests
    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .build();
    }

    public static ResponseSpecification boardNotFound() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "close")
                .expectResponseTime(lessThan(20000L))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static RequestSpecification baseRequestConfiguration(String endPoint) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .setBaseUri(endPoint).addQueryParam("")
                .build();
    }

    private static RequestSpecification basePOSTRequestConfiguration() {

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
//                .setBody("{\"name\": \"BOARD_"  + "\", \"key\": \"" + TRELLO_API_KEY_VALUE +
//                        "\", \"token\": \"" + TRELLO_API_TOKEN_VALUE + "\"}")
                .setBody(TrelloApi.params)
                .setRelaxedHTTPSValidation()
                .build();
    }

    private static RequestSpecification baseGETRequestConfiguration() {
        return new RequestSpecBuilder()
                .addQueryParams(TrelloApi.params)
                .setAccept(ContentType.JSON)
                .setRelaxedHTTPSValidation()
                .build();
    }
}
