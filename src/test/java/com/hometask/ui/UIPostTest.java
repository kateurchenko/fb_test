package com.hometask.ui;

import com.hometask.Constants;
import com.hometask.pages.LoginPage;
import com.hometask.pages.TestPage;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class UIPostTest extends BaseTest {

    private TestPage testPage;


    @BeforeTest
    public void initPageObjects() {
        testPage = LoginPage
                .open(driver, Constants.BASE_URL + Constants.TEST_PAGE_ID)
                .login(Constants.USER_NAME, Constants.USER_PASS);
    }

    @Test(priority = 0)
    public void testCreatePost() {
        int sizeBefore = testPage.getPostsList().size();
        testPage
                .clickOnCreateNewPost()
                .setPostMessage("new message")
                .setPostCaption("new caption")
                .setPostPicture("new url to pic")
                .sharePost();
        Assert.assertEquals(testPage.getPostsList().size(), sizeBefore + 1, "Post was not created");
    }

    @Test(priority = 1)
    public void testUpdateLastPost() {
        String updated_text = "Updated text";
        testPage.updatePost(updated_text);
        Assert.assertEquals(testPage.getLastPost().getText(), updated_text, "Post was not updated");
    }

    @Test(priority = 2)
    public void testDeleteLastPost() {
        List<WebElement> postsList = testPage.getPostsList();
        int sizeBefore = postsList.size();

        testPage.deleteLastPost();
        Assert.assertEquals(testPage.getPostsList().size(), sizeBefore + 1, "Post was not created");
    }
}
