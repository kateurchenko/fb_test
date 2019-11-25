package com.hometask.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/**
 * Created by kate on 11/24/19.
 */
public class TestPage extends Page {

    @FindBy(how = How.XPATH, using = "//*[@data-testid='status-attachment-mentions-input']")
    private WebElement createPost;

    @FindBy(how = How.XPATH, using = "//*[@role='presentation' and contains(@class,'navigationFocus')]']")
    private WebElement textField;

    @FindBy(how = How.XPATH, using = "//*[@data-testid='react-composer-post-button']")
    private WebElement shareNowButton;

    @FindBy(how = How.XPATH, using = "//a[@data-feed-option-name='FeedDeleteOption']")
    private WebElement deletePostButton;

    @FindBy(how = How.XPATH, using = "//a[@data-feed-option-name='FeedEditOption']")
    private WebElement editPostButton;

    @FindBy(how = How.XPATH, using = "//a[@value='Delete']")
    private WebElement confirmDeletePostButton;

    @FindBy(how = How.XPATH, using = "//*[@data-testid='status-attachment-mentions-input']")
    private WebElement newPostText;

    @FindBy(how = How.XPATH, using = "//span[@data-text='true']")
    private WebElement postText;

    @FindBy(how = How.XPATH, using = "//button[@data-testid='react-composer-post-button']")
    private WebElement saveButton;

    @FindBy(how = How.XPATH, using = "//*[@role='article']")
    private List<WebElement> articles;

    public TestPage(WebDriver driver) {
        super(driver);
    }

    public TestPage clickOnCreateNewPost() {
        createPost.click();
        return this;
    }

    public TestPage setPostCaption(String input) {
        return this;
    }

    public TestPage setPostPicture(String input) {
        return this;
    }

    public TestPage sharePost() {
        shareNowButton.click();
        return this;
    }

    public List<WebElement> getPostsList() {
        scrollDownThePage();
        return articles;
    }

    public void deleteLastPost() {
        getLastPostMenuButton().click();
        deletePostButton.click();
        confirmDeletePostButton.click();
    }

    //make seperate Menu widget class
    private WebElement getLastPostMenuButton() {
        return driver.findElement(By.xpath("(//*[@aria-label=\"Story options\"])[1]"));
    }

    public void updatePost(String newText) {
        getLastPostMenuButton().click();
        editPostButton.click();
        setTextToElement(postText, newText);
        saveButton.click();
    }

    public WebElement getLastPost() {
        return getPostsList().get(0);
    }

    public TestPage setPostMessage(String text) {
        setTextToElement(newPostText, text);
        return this;
    }
}
