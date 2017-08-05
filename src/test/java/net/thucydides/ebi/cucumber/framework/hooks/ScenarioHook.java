package net.thucydides.ebi.cucumber.framework.hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.ScenarioImpl;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.ebi.cucumber.framework.helpers.PropertyHelper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static net.thucydides.core.webdriver.ThucydidesWebDriverSupport.getDriver;
import static net.thucydides.ebi.cucumber.framework.hooks.initialization.extractBytes;

/**
 * Created by rakeshnbr on 04/01/2017.
 */
public class ScenarioHook {

    final static Logger logger = Logger.getLogger(ScenarioHook.class.getName());
    public static Scenario scenario;
    EnvironmentVariables environmentVariables;
    private static boolean logCreated = false;
    private static String logName;

    @Before(order = 1)
    public void KeepScenario(Scenario scenario) throws Exception {
        try {
            ScenarioHook.scenario = scenario;
            this.setScenario(scenario);
        }catch (Exception e){}
    }



    public void setScenario(Scenario scenario) {
        ScenarioHook.scenario = scenario;
    }

    public static Scenario getScenario() {
        return scenario;
    }

    public static void takeScreenshot(){
        try {
            scenario.embed(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
