package net.thucydides.ebi.cucumber.pages.webservices;

import com.fasterxml.jackson.databind.ObjectMapper;


import net.thucydides.ebi.cucumber.framework.beans.realm.Realm;

import net.thucydides.ebi.cucumber.framework.context.RealmGetServiceContext;
import net.thucydides.ebi.cucumber.framework.context.RealmPostServiceContext;
import net.thucydides.ebi.cucumber.framework.helpers.RestServiceHelper;
import net.thucydides.ebi.cucumber.framework.hooks.ScenarioHook;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by rakeshnambiar on 03/08/2017.
 */
public class realmServiceImpl {
    private static String idValue = null;
    private static String response = null;

    public void postRealm(String name, String description, String format) throws Exception {
        try{
            String xmlRequest = null;
            if(name !=null) {
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
            response = RestServiceHelper.postRequest(xmlRequest, format);

            if(RestServiceHelper.responseCode == 201) {
                Realm realmResp = new Realm();
                ObjectMapper objectMapper = new ObjectMapper();
                JSONObject xmlJSONObj = XML.toJSONObject(response);
                String jsonStr = xmlJSONObj.get("realm").toString();
                realmResp = objectMapper.readValue(jsonStr, Realm.class);
                RealmPostServiceContext.realm = realmResp;
                idValue = RealmPostServiceContext.realm.getId().toString();
            }
        }catch (Exception e){
            throw new Exception("ERROR: POST operation failed");
        }
    }

    public void getRealm() throws Exception {
        try{
            String realmDetails = RestServiceHelper.getRequest(idValue);
            if(RestServiceHelper.responseCode !=400){
                Realm realmResp = new Realm();
                ObjectMapper objectMapper = new ObjectMapper();
                JSONObject xmlJSONObj = XML.toJSONObject(realmDetails);
                String jsonStr = xmlJSONObj.get("realm").toString();
                realmResp = objectMapper.readValue(jsonStr, Realm.class);
                RealmGetServiceContext.realm = realmResp;
            }
        }catch (Exception e){
            throw new Exception("ERROR: GET operation failed");
        }
    }

    public void clearReAlm() throws Exception {
        try{
            if(idValue != null) {
                RestServiceHelper.deleteRequest(idValue);
            }
        }catch (Exception e){
            throw new Exception("ERROR: While Deleting the Realm");
        }
    }

    public boolean verifyIdKey(String operation) throws Exception {
        try{
            String key = null;
            if(operation.equalsIgnoreCase("POST")){
                key = RealmPostServiceContext.realm.getKey();
            } else {
                idValue = RealmGetServiceContext.realm.getId().toString();
                key = RealmGetServiceContext.realm.getKey();
            }
            ScenarioHook.getScenario().write("id - " + idValue);
            ScenarioHook.getScenario().write("key - " + key);
            return idValue.length() > 0 & key.length() > 0;
        }catch (Exception e){
            throw new Exception("ERROR: While verifying the Id and Key");
        }
    }

    public boolean verifyNameDesc(String operation, String name, String desc) throws Exception {
        boolean match = false;
        try{
            String respName = "";
            String respDesc = "";
            if(operation.equalsIgnoreCase("POST")){
                respName = RealmPostServiceContext.realm.getName();
                respDesc = RealmPostServiceContext.realm.getDescription();
            } else {
                respName = RealmGetServiceContext.realm.getName();
                respDesc = RealmGetServiceContext.realm.getDescription();
            }
            if(respName.equalsIgnoreCase(name)){
                if(respDesc.equalsIgnoreCase(desc)){
                    match = true;
                } else {
                    ScenarioHook.getScenario().write("Description NOT proper - " + RealmPostServiceContext.realm.getDescription());
                }
            } else {
                ScenarioHook.getScenario().write("Name NOT proper - " + RealmPostServiceContext.realm.getName());
            }
        }catch (Exception e){
            throw new Exception("ERROR: While verifying the Name and Description");
        }
        return match;
    }

    public boolean verifyErrorResponse(){
        try{
            return response.contains("Duplicate realm name");
        }catch (Exception e){
            return false;
        }
    }
}
