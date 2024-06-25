package io.zahori.examples.appium.webapp;

import io.zahori.examples.appium.common.AppiumUtils;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class WebAppIOSLocalTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");

        // Automation driver
        capabilities.setCapability("appium:automationName", "XCUITest");

        // Device: (optional. If not provided, Appium will automatically find the device matching other capabilities)
        // - Option A: Local simulator
        capabilities.setCapability("appium:deviceName", "iPhone 15 Pro");
        // - Option B: Local real iOS device
        //capabilities.setCapability("appium:udid", "<YOUR_IOS_UDID>");

        // Browser
        capabilities.setCapability("browserName", "safari");

        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void webAppIOSLocal() throws InterruptedException {
        driver.get("https://www.wikipedia.org/");

        WebElement language = driver.findElement(By.xpath("//a[contains(@id,'-es')]"));
        language.click();

        try {
            WebElement alreadyDonatedButton = driver.findElement(By.xpath("//button[contains(@class,'frb-iad-link')]"));
            alreadyDonatedButton.click();

            WebElement hideDonationButton = driver.findElement(By.xpath("//button[@name='frb-iad-hide-button']"));
            hideDonationButton.click();
        } catch (Exception e) {

        }

        WebElement searchButton = driver.findElement(By.id("searchIcon"));
        searchButton.click();

        WebElement searchInput = driver.findElement(By.xpath("(//input[@name='search'])[2]"));
        searchInput.sendKeys("Linus Torvalds");

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
