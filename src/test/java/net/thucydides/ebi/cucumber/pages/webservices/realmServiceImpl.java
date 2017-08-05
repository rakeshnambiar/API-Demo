package net.thucydides.ebi.cucumber.pages.webservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;


import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import net.thucydides.core.reports.html.ResultRankingFormatter;
import net.thucydides.core.reports.json.JSONConverter;
import net.thucydides.ebi.cucumber.framework.beans.realm.ObjectFactory;
import net.thucydides.ebi.cucumber.framework.beans.realm.Realm;

import net.thucydides.ebi.cucumber.framework.context.RealmServiceContext;
import net.thucydides.ebi.cucumber.framework.helpers.RestServiceHelper;
import net.thucydides.ebi.cucumber.framework.hooks.ScenarioHook;
import net.thucydides.ebi.cucumber.steps.RealmServiceSteps;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Test;

import java.io.IOException;


import static io.restassured.http.ContentType.JSON;
import static net.thucydides.ebi.cucumber.framework.context.RealmServiceContext.realm;

/**
 * Created by rakeshnambiar on 03/08/2017.
 */
public class realmServiceImpl {
    private static String idValue = null;

    public void postRealm(String name, String description) throws Exception {
        try{
            String xmlRequest = null;
            if(name.length() > 0) {
                xmlRequest = "<realm name=\"var_name\"> \n" +
                        "   <description>var_Description</description> \n" +
                        "</realm>";
            } else {
                throw new Exception("ERROR: Invalid Request");
            }
            xmlRequest = xmlRequest.replace("var_name", name);
            xmlRequest = xmlRequest.replace("var_Description", description);
            ScenarioHook.getScenario().write("--------------- Request XML ---------------");
            ScenarioHook.getScenario().write(xmlRequest);
            String response = RestServiceHelper.postRequest(xmlRequest);
            if(RestServiceHelper.responseCode != 400 & RestServiceHelper.responseCode != 404) {
                Realm realmResp = new Realm();
                ObjectMapper objectMapper = new ObjectMapper();
                JSONObject xmlJSONObj = XML.toJSONObject(response);
                String jsonStr = xmlJSONObj.get("realm").toString();
                realmResp = objectMapper.readValue(jsonStr, Realm.class);
                RealmServiceContext.realm = realmResp;
            }

        }catch (Exception e){
            throw new Exception("ERROR: POST operation failed");
        }
    }

    public void getRealm() throws Exception {
        try{

        }catch (Exception e){
            throw new Exception("ERROR: GET operation failed");
        }
    }

    public void clearReAlm() throws Exception {
        try{
            RestServiceHelper.deleteRequest(idValue);
        }catch (Exception e){
            throw new Exception("ERROR: While Deleting the Realm");
        }
    }

    public boolean verifyIdKey() throws Exception {
        try{
            idValue = RealmServiceContext.realm.getId().toString();
            String key = RealmServiceContext.realm.getKey().toString();
            ScenarioHook.getScenario().write("id - " + idValue);
            ScenarioHook.getScenario().write("key - " + key);
            if(idValue.length() > 0 & key.length() >0 ){
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            throw new Exception("ERROR: While verifying the Id and Key");
        }
    }

    public boolean verifyNameDesc(String name, String desc) throws Exception {
        boolean match = false;
        try{
            if(RealmServiceContext.realm.getName().equalsIgnoreCase(name)){
                if(RealmServiceContext.realm.getDescription().equalsIgnoreCase(desc)){
                    match = true;
                } else {
                    ScenarioHook.getScenario().write("Description NOT proper - " + RealmServiceContext.realm.getDescription());
                }
            } else {
                ScenarioHook.getScenario().write("Name NOT proper - " + RealmServiceContext.realm.getName());
            }
        }catch (Exception e){
            throw new Exception("ERROR: While verifying the Name and Description");
        }
        return match;
    }

}
