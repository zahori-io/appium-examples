package io.zahori.examples.appium.nativeapp;


import io.appium.java_client.AppiumBy;
import io.zahori.examples.appium.common.AppiumUtils;
import java.net.MalformedURLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

public class NativeAppAndroidLocalTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");

        // Automation driver
        capabilities.setCapability("appium:automationName", "UIAutomator2");

        // Device: (optional. If not provided, Appium will automatically find the device matching other capabilities)
        // - Option A: Local emulator
        //capabilities.setCapability("deviceName", "emulator-5554");
        // - Option B: Local real android
        //capabilities.setCapability("appium:udid", "<YOUR_ANDROID_DEVICE_ID>");
        
        // App:
        // - Option A: App already installed
        capabilities.setCapability("appium:appPackage", "com.saucelabs.mydemoapp.android");
        capabilities.setCapability("appium:appActivity", ".view.activities.SplashActivity");
        // - Option B: App not installed
        //capabilities.setCapability("appium:app", new File("src/test/resources/apps/native/mda-2.0.2-23.apk").getAbsolutePath());

        // Driver
        driver = AppiumUtils.createAppiumDriver("http://127.0.0.1:4723", capabilities);
    }

    @Test
    public void nativeAppAndroidLocal() {
        // Catalog
        WebElement article1 = AppiumUtils.findElement(driver, AppiumBy.xpath("//*[@text='Sauce Labs Backpack']/preceding-sibling::android.widget.ImageView"));
        article1.click();

        WebElement addPlus = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/plusIV"));
        addPlus.click();

        WebElement addToCart = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"));
        addToCart.click();
        //WebElement menu = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV"));
        //menu.click();
        //WebElement catalog = driver.findElement(AppiumBy.xpath("//*[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV']"));
        //catalog.click();

        // Cart
        WebElement goToCart = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartRL"));
        goToCart.click();

        WebElement proceedToCheckout = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"));
        proceedToCheckout.click();

        // Login
        WebElement username = AppiumUtils.findElement(driver, AppiumBy.xpath("//*[@text='bod@example.com']"));
        username.click();

        WebElement login = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn"));
        login.click();

        // Checkout (page 1)
        WebElement fullName = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/fullNameET"));
        fullName.sendKeys("Linus Torvalds");

        //driver.findElement(AppiumBy.xpath("//XCUIElementTypeButton[@name='Done']")).click();
        WebElement address = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/address1ET"));
        address.sendKeys("address line 1");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement city = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/cityET"));
        city.sendKeys("Madrid");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement zipCode = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/zipET"));
        zipCode.sendKeys("28080");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement country = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/countryET"));
        country.sendKeys("Spain");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement goToPayment = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn"));
        goToPayment.click();

        // Checkout (page 2)
        WebElement fullName2 = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET"));
        fullName2.sendKeys("Linus Torvalds");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement cardNumber = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/cardNumberET"));
        cardNumber.sendKeys("1111222233334444");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement expirationDate = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/expirationDateET"));
        expirationDate.sendKeys("0325");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement cvv = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/securityCodeET"));
        cvv.sendKeys("123");
        //WebDriverUtils.hideKeyboard(driver);

        WebElement reviewOrder = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn"));
        reviewOrder.click();

        // Checkout (page 3)
        WebElement placeOrder = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/paymentBtn"));
        placeOrder.click();

        // Checkout (confirm page)
        WebElement checkoutCompleteted = AppiumUtils.findElement(driver, AppiumBy.id("com.saucelabs.mydemoapp.android:id/completeTV"));
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
