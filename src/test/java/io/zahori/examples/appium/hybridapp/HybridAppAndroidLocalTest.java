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

public class HybridAppAndroidLocalTest {

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

        // App:
        // - Option A: App already installed
        capabilities.setCapability("appium:appPackage", "com.wdiodemoapp");
        capabilities.setCapability("appium:appActivity", ".MainActivity");
        // - Option B: App not installed
        //capabilities.setCapability("appium:app", new File("src/test/resources/apps/hybrid/android.wdio.native.app.v1.0.8.apk").getAbsolutePath());

        // Driver
        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void hybridAppAndroidLocal() throws InterruptedException {
        WebElement webviewLink = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Webview']"));
        webviewLink.click(); // loads https://webdriver.io in a webview

        // WEBVIEW
        AppiumUtils.switchToWebContext(driver);
        // ["NATIVE_APP","WEBVIEW_com.wdiodemoapp"]

        System.out.println("WEBVIEW Url: " + driver.getCurrentUrl());
        // System.out.println(driver.getPageSource());

        WebElement closeBanner = driver.findElement(AppiumBy.xpath("//div[@id='__docusaurus']/div[2]/button"));
        closeBanner.click();

        WebElement menu = driver.findElement(AppiumBy.xpath("//*[@class='navbar__toggle clean-btn']"));
        menu.click();

        WebElement menuFirstOption = driver.findElement(AppiumBy.xpath("(//ul[@class='menu__list']/li/a)[1]"));
        menuFirstOption.click();

        // NATIVE
        AppiumUtils.switchToNativeContext(driver);

        WebElement formsLink = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='Forms']"));
        formsLink.click();

        WebElement nativeInput = driver.findElement(AppiumBy.xpath("//*[@resource-id='RNE__Input__text-input']"));
        nativeInput.sendKeys("Native input text");

        TimeUnit.SECONDS.sleep(2);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
