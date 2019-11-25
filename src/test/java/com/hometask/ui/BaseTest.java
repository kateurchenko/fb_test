package com.hometask.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.util.HashMap;

public class BaseTest {

    protected WebDriver driver;

    @BeforeSuite
    public void initTestSuite() throws IOException {
        WebDriverManager.chromedriver().setup();
        HashMap<String, Object> map = new HashMap<>();
        map.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", map);
        driver = new ChromeDriver(options);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
