package com.hometask.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {

    protected WebDriver driver;

    /*
     * Constructor injecting the WebDriver interface
     *
     * @param webDriver
     */
    public Page(WebDriver driver) {
        this.driver = driver;
    }

    protected static boolean waitPageToBeLoaded(final WebDriver driver, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until((ExpectedCondition<Boolean>) input -> {
                try {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                } catch (Exception ex) {
                    return false;
                }
            });
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public void setTextToElement(WebElement element, String text) {
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    protected void scrollDownThePage() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
