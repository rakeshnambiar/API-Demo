package net.thucydides.ebi.cucumber.runCukes.realm;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.ebi.cucumber.tags.Realm;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(tags = {"@TC03_RealmAPITest"},
        format = {"pretty", "html:target/Destination/TC03_RealmAPITest",
                "json:target/cucumber-report/TC03_RealmAPITest.json"},
        features= {"src/test/resources/features/realm/Realm-Service.feature"},
        glue = {"net.thucydides.ebi.cucumber"})

@Category({Realm.class})
public class TC03_RealmAPITest {
}
