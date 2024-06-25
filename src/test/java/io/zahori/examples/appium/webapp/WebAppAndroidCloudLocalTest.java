package io.zahori.examples.appium.webapp;

import io.zahori.examples.appium.common.AppiumUtils;
import io.zahori.examples.appium.common.Cloud;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebAppAndroidCloudLocalTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Capabilities generator: https://www.browserstack.com/docs/automate/capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "android");

        // Browser
        capabilities.setCapability("browserName", "chrome");
        //capabilities.setCapability("browserVersion", "102.0");

        capabilities.setCapability("language", "Es");
        capabilities.setCapability("locale", "Es");

        // Browserstack specific options
        HashMap<String, Object> cloudOptions = new HashMap<>();
        cloudOptions.put("userName", Cloud.USER_NAME);
        cloudOptions.put("accessKey", Cloud.ACCESS_KEY);

        cloudOptions.put("deviceName", "Google Pixel 7 Pro");
        //capabilities.setCapability("platformVersion", "14.0");

        cloudOptions.put("appiumVersion", "2.4.1");
        cloudOptions.put("interactiveDebugging", "true");
        cloudOptions.put("acceptInsecureCerts", "true");

        cloudOptions.put("projectName", "HelloWorld local");
        cloudOptions.put("buildName", "build n");
        cloudOptions.put("sessionName", "TC localhost");
        
        // TO CREATE A TUNNEL BETWEEN BROWSERSTACK AND LOCAL ENVIRONMENTS USING BROWSERSTACK AGENT:
        // Step 1: Run the agent in the local environment: ./BrowserStackLocal --key <YOUR_ACCESS_KEY> --force-local
        // Step 2: Set the following capability
        cloudOptions.put("local", "true");
        
        capabilities.setCapability("bstack:options", cloudOptions);

        driver = AppiumUtils.createAppiumDriver("http://hub.browserstack.com/wd/hub", capabilities);
    }

    @Test
    public void webAppAndroidCloudLocal() throws InterruptedException {
        driver.get("http://localhost:50000");

        WebElement firstnameInput = driver.findElement(By.id("firstname"));
        firstnameInput.sendKeys("My first name");
        
        WebElement lastnameInput = driver.findElement(By.id("lastname"));
        lastnameInput.sendKeys("My last name");
        
        TimeUnit.SECONDS.sleep(3);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
