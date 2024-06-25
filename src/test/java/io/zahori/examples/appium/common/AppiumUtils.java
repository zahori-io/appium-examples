package io.zahori.examples.appium.common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

public class AppiumUtils {

    public static WebDriver createAppiumDriver(String appiumUrl, MutableCapabilities capabilities) throws MalformedURLException {
        WebDriver webDriver = null;

        String platformName = capabilities.getCapability("platformName").toString();
        if ("Android".equalsIgnoreCase(platformName)) {
            webDriver = new AndroidDriver(new URL(appiumUrl), capabilities);
        }
        if ("iOS".equalsIgnoreCase(platformName)) {
            webDriver = new IOSDriver(new URL(appiumUrl), capabilities);
        }

        if (webDriver == null) {
            throw new RuntimeException("No supported value for capability 'platformName': '" + platformName + "'. Valid values: 'Android' or 'iOS'");
        }

        // max time to wait presence of elements
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        return webDriver;
    }

    public static boolean isDisplayed(WebDriver driver, By by) {
        try {
            WebElement webElement = driver.findElement(by);
            return webElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static WebElement findElement(WebDriver driver, By by) {
        int retry = 1;
        while (retry < 10 && !isDisplayed(driver, by)) {
            retry++;
            swipeVertical(driver, 50, 300, 50);
        }
        return driver.findElement(by);
    }

    public static void hideKeyboard(WebDriver driver) {
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        if (driver instanceof IOSDriver) {
            ((IOSDriver) driver).hideKeyboard();
        }
    }

    public static void swipeVertical(WebDriver driver, int x, int startY, int endY) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence seqSwipe = new Sequence(finger, 1);

            seqSwipe.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), x, startY));
            seqSwipe.addAction(finger.createPointerDown(0));

            seqSwipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), x, endY));
            seqSwipe.addAction(finger.createPointerUp(0));
            ((AppiumDriver) driver).perform(Arrays.asList(seqSwipe));
        } catch (Exception e) {
            System.out.println("Unable to swipe: " + e.getMessage());
        }
    }

    public static void switchToNativeContext(WebDriver driver) {
        if (driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            androidDriver.context("NATIVE_APP");
        }
        if (driver instanceof IOSDriver) {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.context("NATIVE_APP");
        }
    }

    public static void switchToWebContext(WebDriver driver) throws InterruptedException {
        switchToWebContextMobile(driver);

        String context = getContext(driver);
        int maxRetries = 15;
        int retries = 0;
        while (context.contains("NATIVE") && retries <= maxRetries) {
            retries++;
            TimeUnit.SECONDS.sleep(1);
            System.out.println("(retry " + retries + "/" + maxRetries + ") Waiting 1 second to find a WEBVIEW...");

            switchToWebContextMobile(driver);
            context = getContext(driver);
        }
        if (context.contains("NATIVE")) {
            throw new RuntimeException("WEBVIEW is not present");
        }
    }

    private static void switchToWebContextMobile(WebDriver driver) {
        if (driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            switchToWebContextAndroid(androidDriver);
        }
        if (driver instanceof IOSDriver) {
            IOSDriver iosDriver = (IOSDriver) driver;
            switchToWebContextIOS(iosDriver);
        }
    }

    private static void switchToWebContextAndroid(AndroidDriver androidDriver) {
        ArrayList<String> contexts = new ArrayList(androidDriver.getContextHandles());
        System.out.println("getContextHandles: ");
        for (String context : contexts) {
            System.out.println("- context: " + context);
            if (context.contains("WEBVIEW")) {
                androidDriver.context(context);
            }
        }
    }

    private static void switchToWebContextIOS(IOSDriver iOSDriver) {
        ArrayList<String> contexts = new ArrayList(iOSDriver.getContextHandles());
        System.out.println("getContextHandles: ");
        for (String context : contexts) {
            System.out.println("- context: " + context);
            if (context.contains("WEBVIEW")) {
                iOSDriver.context(context);
            }
        }
    }

    private static String getContext(WebDriver driver) {
        if (isAndroidDriver(driver)) {
            return ((AndroidDriver) driver).getContext();
        }
        if (isIOSDriver(driver)) {
            return ((IOSDriver) driver).getContext();
        }
        throw new RuntimeException("Error getting context for driver: driver is neither AndroidDriver nor IOSDriver");
    }

    public static boolean isMobileDriver(WebDriver driver) {
        return isAndroidDriver(driver) || isIOSDriver(driver);
    }

    public static boolean isAndroidDriver(WebDriver driver) {
        return driver instanceof AndroidDriver;
    }

    public static boolean isIOSDriver(WebDriver driver) {
        return driver instanceof IOSDriver;
    }

}
