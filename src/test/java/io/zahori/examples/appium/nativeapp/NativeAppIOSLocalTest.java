package io.zahori.examples.appium.nativeapp;

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

public class NativeAppIOSLocalTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");

        // Automation driver
        capabilities.setCapability("appium:automationName", "XCUITest");

        // Device: (optional. If not provided, Appium will automatically find the device matching other capabilities)
        // - Option A: Local simulator
        capabilities.setCapability("appium:deviceName", "iPad mini (6th generation)");
        // - Option B: Local real iOS device
        //capabilities.setCapability("appium:udid", "<YOUR_IOS_UDID>");

        // App:
        // - Option A: App already installed
        capabilities.setCapability("bundleId", "com.saucelabs.mydemoapp.ios");
        // - Option B: App not installed (simulators)
        //capabilities.setCapability("appium:app", new File("src/test/resources/apps/native/My Demo App.app").getAbsolutePath());
        // - Option C: App not installed (real iOS device)
        //capabilities.setCapability("appium:app", new File("src/test/resources/apps/native/SauceLabs-Demo-App.ipa").getAbsolutePath());

        // Driver
        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void nativeAppIOSLocal() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);

        // Catalog
        WebElement article1 = driver.findElement(AppiumBy.name("Sauce Labs Backpack"));
        article1.click();
        WebElement addPlus = driver.findElement(AppiumBy.name("AddPlus Icons"));
        addPlus.click();
        WebElement addToCart = driver.findElement(AppiumBy.name("AddToCart"));
        addToCart.click();
        WebElement goToCatalog = driver.findElement(AppiumBy.name("Catalog-tab-item"));
        goToCatalog.click();

        // Cart
        WebElement goToCart = driver.findElement(AppiumBy.name("Cart-tab-item"));
        goToCart.click();
        WebElement proceedToCheckout = driver.findElement(AppiumBy.name("ProceedToCheckout"));
        proceedToCheckout.click();

        // Login
        WebElement username = driver.findElement(AppiumBy.name("bob@example.com"));
        username.click();
        WebElement login = driver.findElement(AppiumBy.name("Login"));
        login.click();

        // Checkout (page 1)
        WebElement fullName = driver.findElement(AppiumBy.xpath("//*[@name='Full Name*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        fullName.sendKeys("Linus Torvalds");

        //driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Done']")).click();
        WebElement address = driver.findElement(AppiumBy.xpath("//*[@name='Address Line 1*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        address.sendKeys("address line 1");
        WebElement city = driver.findElement(AppiumBy.xpath("//*[@name='City*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        city.sendKeys("Madrid");
        WebElement zipCode = driver.findElement(AppiumBy.xpath("//*[@name='Zip Code*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        zipCode.sendKeys("28080");
        WebElement country = driver.findElement(AppiumBy.xpath("//*[@name='Country*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        country.sendKeys("Spain");

        AppiumUtils.hideKeyboard(driver);
        WebElement goToPayment = driver.findElement(AppiumBy.name("To Payment"));
        goToPayment.click();

        // Checkout (page 2)
        WebElement fullName2 = driver.findElement(AppiumBy.xpath("//*[@name='Full Name*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        fullName2.sendKeys("Linus Torvalds");
        WebElement cardNumber = driver.findElement(AppiumBy.xpath("//*[@name='Card Number*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        cardNumber.sendKeys("1111222233334444");
        WebElement expirationDate = driver.findElement(AppiumBy.xpath("//*[@name='Expiration Date*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        expirationDate.sendKeys("0325");
        WebElement cvv = driver.findElement(AppiumBy.xpath("//*[@name='Security Code*']/following-sibling::XCUIElementTypeOther[1]/XCUIElementTypeTextField"));
        cvv.sendKeys("123");

        AppiumUtils.hideKeyboard(driver);
        WebElement reviewOrder = driver.findElement(AppiumBy.name("Review Order"));
        reviewOrder.click();

        // Checkout (page 3)
        WebElement placeOrder = driver.findElement(AppiumBy.name("Place Order"));
        placeOrder.click();

        // Checkout (confirm page)
        WebElement checkoutCompleteted = driver.findElement(AppiumBy.name("Checkout Complete"));
        if (checkoutCompleteted.isDisplayed()) {
            System.out.println("Checkout completed");
        } else {
            System.out.println("Checkout failed!");
        }
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
