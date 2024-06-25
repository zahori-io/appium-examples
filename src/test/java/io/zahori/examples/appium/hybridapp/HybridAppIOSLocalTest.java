package io.zahori.examples.appium.hybridapp;

import io.appium.java_client.AppiumBy;
import io.zahori.examples.appium.common.AppiumUtils;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class HybridAppIOSLocalTest {

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

        // App:
        // - Option A: App already installed
        capabilities.setCapability("bundleId", "org.reactjs.native.example.wdiodemoapp");
        // - Option B: App not installed (simulators)
        // capabilities.setCapability("appium:app", new File("src/test/resources/apps/hybrid/wdiodemoapp.app").getAbsolutePath());
        // - Option C: App not installed (real iOS device)
        //capabilities.setCapability("appium:app", new File("src/test/resources/apps/hybrid/xxx.ipa").getAbsolutePath());

        // Driver
        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void hybridAppIOSLocal() throws InterruptedException {
        WebElement webviewLink = driver.findElement(AppiumBy.name("Webview"));
        webviewLink.click(); // loads https://webdriver.io in a webview

        // WEBVIEW
        AppiumUtils.switchToWebContext(driver); // ["NATIVE_APP",""WEBVIEW_78416.1""]

        System.out.println("WEBVIEW Url: " + driver.getCurrentUrl());
        // System.out.println(driver.getPageSource());

        //WebElement closeBanner = driver.findElement(AppiumBy.xpath("//div[@id='__docusaurus']/div[2]/button"));
        //closeBanner.click();
        WebElement menu = driver.findElement(AppiumBy.xpath("//*[@class='navbar__toggle clean-btn']"));
        menu.click();

        TimeUnit.SECONDS.sleep(3);

        WebElement menuFirstOption = driver.findElement(AppiumBy.xpath("//ul[@class='menu__list']/li/a[1]"));
        menuFirstOption.click();

        // NATIVE
        AppiumUtils.switchToNativeContext(driver);

        WebElement formsLink = driver.findElement(AppiumBy.name("Forms"));
        formsLink.click();

        WebElement nativeInput = driver.findElement(AppiumBy.name("text-input"));
        nativeInput.sendKeys("Native input text");

        TimeUnit.SECONDS.sleep(2);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
