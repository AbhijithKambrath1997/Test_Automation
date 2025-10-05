package appium.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class AppiumSetUp {

    private static final String DEFAULT_DEVICE = "emulator-5554";
    private static final String DEFAULT_VERSION = "16.0";
    private static final String SERVER_URL = "http://127.0.0.1:4723";

    /**
     * ✅ Base method: builds options
     *
     * @param device      - Device Name
     * @param version     - Device version
     * @param appPath     - App Path if specific app is mentioned
     * @param appPackage  - App Package if specific app to be opened
     * @param appActivity - App Activity if specific app to be opened
     * @return UiAutomator2Options
     */
    //
    private UiAutomator2Options buildOptions(String device, String version, String appPath,
                                             String appPackage, String appActivity) {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setAutomationName("UiAutomator2");
        options.setPlatformName("Android");
        options.setDeviceName(device);
        options.setPlatformVersion(version);

        if (appPath != null && !appPath.isBlank()) {
            options.setApp(appPath);
        }
        if (appPackage != null && appActivity != null &&
                !appPackage.isBlank() && !appActivity.isBlank()) {
            options.setAppPackage(appPackage);
            options.setAppActivity(appActivity);
        }

        return options;
    }

    /**
     * Run with default emulator only
     *
     * @return AndroidDriver
     */
    public AndroidDriver runDefault() {
        return createDriver(buildOptions(DEFAULT_DEVICE, DEFAULT_VERSION,
                null, null, null));
    }

    /**
     * Run with APK install
     *
     * @param appPath -> App Path
     * @return AndroidDriver
     */
    public AndroidDriver runWithApp(String appPath) {
        return createDriver(buildOptions(DEFAULT_DEVICE, DEFAULT_VERSION,
                appPath, null, null));
    }

    /**
     * Run with appPackage + appActivity (if app already installed)
     *
     * @param appPackage  -> App Package
     * @param appActivity -> App Activity
     * @return AndroidDriver
     */
    public AndroidDriver runWithPackage(String appPackage, String appActivity) {
        return createDriver(buildOptions(DEFAULT_DEVICE, DEFAULT_VERSION,
                null, appPackage, appActivity));
    }

    /**
     * Run with custom device + version
     *
     * @param device  -> DeviceName
     * @param version -> Device Version
     * @return AndroidDriver
     */
    public AndroidDriver runCustom(String device, String version) {
        return createDriver(buildOptions(device, version,
                null, null, null));
    }

    /**
     * Common driver creator
     *
     * @param options -> UiAutomator2Options
     * @return AndroidDriver
     */
    private AndroidDriver createDriver(UiAutomator2Options options) {
        try {
            URL url = URI.create(SERVER_URL).toURL();
            return new AndroidDriver(url, options);
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium server URL: " + SERVER_URL, e);
        }
    }

}
