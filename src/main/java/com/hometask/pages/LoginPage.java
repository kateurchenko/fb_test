package com.hometask.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by kate on 11/24/19.
 */
public class LoginPage extends Page {

    public final static String BASE_URL = "https://facebook.com/";
    @FindBy(how = How.XPATH, using = "//input[@type='submit']")
    @CacheLookup
    private WebElement loginButton;
    @FindBy(how = How.XPATH, using = "//input[@type='email']")
    @CacheLookup
    private WebElement login;
    @FindBy(how = How.XPATH, using = "//input[@type='password']")
    @CacheLookup
    private WebElement password;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static LoginPage open(WebDriver driver) {
        driver.get(BASE_URL);
        waitPageToBeLoaded(driver, 20);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public static LoginPage open(WebDriver driver, String pageToOpen) {
        driver.get(pageToOpen);
        waitPageToBeLoaded(driver, 20);
        return PageFactory.initElements(driver, LoginPage.class);
    }

    public void submit() {
        loginButton.submit();
    }

    public void setName(String name) {
        login.click();
        login.clear();
        login.sendKeys(name);
    }

    public void setPassword(String name) {
        password.click();
        password.clear();
        password.sendKeys(name);
    }

    public TestPage login(String userName, String password) {
        setName(userName);
        setPassword(password);
        submit();
        waitPageToBeLoaded(driver, 20);
        return PageFactory.initElements(driver, TestPage.class);
    }
}
