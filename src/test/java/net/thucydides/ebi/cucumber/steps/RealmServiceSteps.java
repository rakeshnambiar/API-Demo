package net.thucydides.ebi.cucumber.steps;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import net.thucydides.core.steps.ScenarioSteps;

import net.thucydides.ebi.cucumber.framework.helpers.PropertyHelper;
import net.thucydides.ebi.cucumber.framework.helpers.RestServiceHelper;
import net.thucydides.ebi.cucumber.framework.hooks.ScenarioHook;
import net.thucydides.ebi.cucumber.pages.webservices.realmServiceImpl;
import org.jruby.RubyProcess;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Created by rakeshnambiar on 03/08/2017.
 */
public class RealmServiceSteps extends ScenarioSteps {
    private String userName;
    private String description;
    private static String serviceURI;

    realmServiceImpl realmService = new realmServiceImpl();

    @After
    public void tierDown() throws Exception {
        realmService.clearReAlm();
    }

    /*TC_01*/
    @Given("^I have Realm Service URL$")
    public void i_have_Realm_Service_URL() throws Throwable {
        serviceURI = PropertyHelper.getBaseUrl();
        if(!serviceURI.substring(serviceURI.length()-1).equalsIgnoreCase("/")){
            serviceURI = serviceURI + "/";
        }
        RestAssured.baseURI = serviceURI;
        ScenarioHook.getScenario().write("REST Service URL" + serviceURI);
    }

    @When("^I post the XML request with \"([^\"]*)\", \"([^\"]*)\"$")
    public void i_post_the_XML_request_with(String name, String desc) throws Throwable {
        userName = name;
        description = desc;
        ScenarioHook.getScenario().write("Service Request : Username - " + userName + " Description : " + description);
        realmService.postRealm(userName, description, "XML");
    }

    @Then("^Service should returns the success code (\\d+)$")
    public void service_should_returns_the_success_code(int expRespCode) throws Throwable {
        Assert.assertTrue("ERROR: Invalid Response Code - " + RestServiceHelper.responseCode,
                expRespCode == RestServiceHelper.responseCode);
    }

    @Then("^Response should contain the mandatory tag values id, key$")
    public void response_should_contain_the_mandatory_tag_values_id_key() throws Throwable {
        Assert.assertTrue("ERROR: Id, Key verification FAILED",realmService.verifyIdKey("POST"));
    }

    @Then("^Requested name, description should available in the response$")
    public void requested_name_description_should_available_in_the_response() throws Throwable {
        Assert.assertTrue("ERROR: Name, Description verification FAILED", realmService.verifyNameDesc( "POST", userName, description));
    }

    /*TC_02*/
    @When("^I try to make a POST Request again$")
    public void i_try_to_make_a_POST_Request_again() throws Throwable {
        ScenarioHook.getScenario().write("Re-POSTing the Request");
        realmService.postRealm(userName, description, "XML");
    }

    @Then("^Service should return the error response (\\d+)$")
    public void service_should_return_the_error_response_along_with_message(int expRespCode) throws Throwable {
        Assert.assertTrue("ERROR: Err Description NOT proper", realmService.verifyErrorResponse());
        Assert.assertTrue("ERROR: Invalid Response Code - " + RestServiceHelper.responseCode,
                expRespCode == RestServiceHelper.responseCode);
    }


    /*TC_03*/
    @When("^I post the XML request with \"([^\"]*)\", \"([^\"]*)\" in JSON format$")
    public void i_post_the_XML_request_with_in_JSON_format(String name, String desc) throws Throwable {
        userName = name;
        description = desc;
        ScenarioHook.getScenario().write("Service Request : Username - " + userName + " Description : " + description);;
        realmService.postRealm(userName, description, "json");
    }

    @Then("^Service should return the error response (\\d+) for the unsupported media type$")
    public void service_should_return_the_error_response_for_the_unsupported_media_type(int expRespCode) throws Throwable {
        Assert.assertTrue("ERROR: Invalid Response Code - " + RestServiceHelper.responseCode,
                expRespCode == RestServiceHelper.responseCode);
    }


    /*TC_04*/
    @When("^I successfully posted the XML request with \"([^\"]*)\", \"([^\"]*)\"$")
    public void i_successfully_posted_the_XML_request_with(String name, String desc) throws Throwable {
        userName = name;
        description = desc;
        realmService.postRealm(userName, description,"XML");
        Assert.assertTrue("ERROR: Id, Key verification FAILED for POST",realmService.verifyIdKey("POST"));
    }

    @When("^Retrieving the request which I have posted$")
    public void retrieving_the_request_which_I_have_posted() throws Throwable {
        realmService.getRealm();
    }

    @Then("^GET Response should contain the mandatory tag values id, key$")
    public void get_Response_should_contain_the_mandatory_tag_values_id_key() throws Throwable {
        Assert.assertTrue("ERROR: Id, Key verification FAILED for GET",realmService.verifyIdKey("GET"));
    }

    @Then("^Requested name, description should available in the GET response$")
    public void requested_name_description_should_available_in_the_GET_response() throws Throwable {
        Assert.assertTrue("ERROR: Name, Description verification FAILED", realmService.verifyNameDesc( "GET", userName, description));
    }


}
