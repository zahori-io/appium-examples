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

public class WebAppAndroidLocalTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");

        // Automation driver
        capabilities.setCapability("appium:automationName", "UIAutomator2");

        // Device: (optional. If not provided, Appium will automatically find the device matching other capabilities)
        // - Option A: Local emulator
        capabilities.setCapability("deviceName", "emulator-5554");
        // - Option B: Local real android
        //capabilities.setCapability("appium:udid", "<YOUR_ANDROID_DEVICE_ID>");

        // Browser
        capabilities.setCapability("browserName", "chrome");

        // Driver
        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void webAppAndroidLocal() throws InterruptedException {
        driver.get("https://www.wikipedia.org/");

        WebElement language = driver.findElement(By.xpath("//a[contains(@id,'-es')]"));
        language.click();

        WebElement alreadyDonatedButton = driver.findElement(By.xpath("//button[contains(@class,'frb-iad-link')]"));
        alreadyDonatedButton.click();

        WebElement hideDonationButton = driver.findElement(By.xpath("//button[@name='frb-iad-hide-button']"));
        hideDonationButton.click();

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
