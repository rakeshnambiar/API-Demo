package net.thucydides.ebi.cucumber.framework.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.ebi.cucumber.framework.hooks.ScenarioHook;
import org.json.JSONObject;
import org.json.XML;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

/**
 * Created by rakeshnambiar on 04/08/2017.
 */
public class RestServiceHelper {
    public static int responseCode = 0;
    private static RequestSpecification restHeader;
    private static Response response;


    public static String postRequest(String request) throws Exception {
        String responseStr = null;
        try {
            request = request.replace("\n"," ");
            request = request.replace("\\"," ");
            response = with().contentType("application/xml").body(request).when().post(RestAssured.baseURI);

            responseCode = response.getStatusCode();
            responseStr= response.asString();
            if(ScenarioHook.getScenario()== null){
                System.out.println("----------------------------Response----------------------------------------");
                System.out.println(responseStr);
            }else{
                ScenarioHook.getScenario().write("--------------------------------------------------------------------");
                ScenarioHook.getScenario().write("Response Received : \n"+response.prettyPrint());
            }
        }catch (Exception e){
            throw new Exception("ERROR: Posting the REST Request");
        }
        return responseStr;
    }

    public static void deleteRequest(String value) throws Exception {
        try{
            ScenarioHook.getScenario().write("Mode of Operation : DELETE");
            response = with().contentType("application/xml").delete(value);
            responseCode = response.getStatusCode();
        }catch (Exception e){
            throw new Exception("ERROR: While making a DELETE request");
        }
    }

    public static void getRequest() throws Exception {
        try{
            ScenarioHook.getScenario().write("Mode of Operation : GET");
        }catch (Exception e){
            throw new Exception("ERROR: While making a GET request");
        }
    }
}
