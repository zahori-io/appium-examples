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

public class WebAppAndroidCloudTest {

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

        cloudOptions.put("deviceName", "Google Pixel 7 Pro"); ////////
        //capabilities.setCapability("platformVersion", "14.0");

        cloudOptions.put("appiumVersion", "2.4.1");
        cloudOptions.put("interactiveDebugging", "true");
        cloudOptions.put("acceptInsecureCerts", "true");

        cloudOptions.put("projectName", "Wikipedia");
        cloudOptions.put("buildName", "build n");
        cloudOptions.put("sessionName", "TC-Search");
        capabilities.setCapability("bstack:options", cloudOptions);

        driver = AppiumUtils.createAppiumDriver("http://hub.browserstack.com/wd/hub", capabilities);
    }

    @Test
    public void webPageAndroidCloud() throws InterruptedException {
        driver.get("https://www.wikipedia.org/");

        WebElement language = driver.findElement(By.xpath("//a[contains(@id,'-es')]"));
        language.click();

        //WebElement alreadyDonatedButton = driver.findElement(By.xpath("//button[contains(@class,'frb-iad-link')]"));
        //alreadyDonatedButton.click();
        //WebElement hideDonationButton = driver.findElement(By.xpath("//button[@name='frb-iad-hide-button']"));
        //hideDonationButton.click();
        WebElement searchButton = driver.findElement(By.id("searchIcon"));
        searchButton.click();

        WebElement searchInput = driver.findElement(By.xpath("(//input[@name='search'])[2]"));
        searchInput.sendKeys("Linus Torvalds");

        TimeUnit.SECONDS.sleep(2);
        WebElement firstFind = driver.findElement(By.xpath("(//li[@class='page-summary']/a)[1]"));
        firstFind.click();

        TimeUnit.SECONDS.sleep(2);

        WebElement firstParagraph = driver.findElement(By.xpath("(//div[@id='bodyContent']//p)[1]"));
        System.out.println("--> First paragraph: " + firstParagraph.getText());

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
