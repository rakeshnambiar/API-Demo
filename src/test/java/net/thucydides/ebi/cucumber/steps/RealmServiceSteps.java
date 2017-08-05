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

    @Given("^I have Realm Service URL$")
    public void i_have_Realm_Service_URL() throws Throwable {
        serviceURI = PropertyHelper.getBaseUrl();
        RestAssured.baseURI = serviceURI;
        ScenarioHook.getScenario().write("REST Service URL" + serviceURI);
    }

    @When("^I post the XML request with \"([^\"]*)\", \"([^\"]*)\"$")
    public void i_post_the_XML_request_with(String name, String desc) throws Throwable {
        userName = name;
        description = desc;
        ScenarioHook.getScenario().write("Service Request : Username - " + userName + " Description : " + description);
    }

    @Then("^Service should returns the success code (\\d+)$")
    public void service_should_returns_the_success_code(int expRespCode) throws Throwable {
        realmService.postRealm(userName, description);
        Assert.assertTrue("ERROR: Invalid Response Code - " + RestServiceHelper.responseCode,
                expRespCode == RestServiceHelper.responseCode);
    }

    @Then("^Response should contain the mandatory tag values id, key$")
    public void response_should_contain_the_mandatory_tag_values_id_key() throws Throwable {
        Assert.assertTrue("ERROR: Id, Key verification FAILED",realmService.verifyIdKey());
    }

    @Then("^Requested name, description should available in the response$")
    public void requested_name_description_should_available_in_the_response() throws Throwable {
        Assert.assertTrue("ERROR: Name, Description verification FAILED", realmService.verifyNameDesc(userName, description));

    }

}
