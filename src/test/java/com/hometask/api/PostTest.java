package com.hometask.api;

import com.hometask.Constants;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by kate on 11/24/19.
 */
public class PostTest {
    public static final String PAGE_ENDPOINT = Constants.TEST_PAGE_ID + "/feed";
    FacebookClient client;

    @DataProvider
    public static Object[][] getTextValuesForNegativeTest() {
        return new Object[][]{{""}, {RandomStringUtils.randomAlphanumeric(63207)}};
    }

    @BeforeTest
    public void init() {
        client = new DefaultFacebookClient(Constants.PAGE_ACCESS_TOKEN, Version.VERSION_2_10);
    }

    /**
     * add also test for publishing photo/video/link/add comment on post;
     * create textual post with: 1 symbol, 63206 symbol, 63207 symbol (negative),
     * without 'message' parameter(negative), with null value(negative)
     */
    @Test
    public void createPostTest() {
        FacebookType message = client.publish(PAGE_ENDPOINT,
                FacebookType.class,
                Parameter.with("message", "test post message"),
                Parameter.with("caption", "duck"));
        String postId = message.getId();
        getPostsList()
                .stream()
                .filter(d -> d.getId().equals(Constants.TEST_PAGE_ID + "_" + postId))
                .findAny()
                .orElseThrow(() -> new AssertionError(String.format("Message with %s was not posted!", postId)));
    }

    @Test(dataProvider = "getTextValuesForNegativeTest")
    public void createPostNegativeTest(String input) {
        FacebookOAuthException message = client.publish(PAGE_ENDPOINT,
                FacebookOAuthException.class,
                Parameter.with("message", input));
        assertEquals((int) message.getHttpStatusCode(), 400, "Status code is incorrect");
        assertEquals(message.getErrorMessage(), "Invalid parameter", "Error message is incorrect");
    }

    private List<Post> getPostsList() {
        return client.fetchConnection(PAGE_ENDPOINT, Post.class).getData();
    }

    /**
     * add also test on update different media type content(e.g. photo, video, images, links)in post
     * add also edit with: remove all text(negative?), all media,
     */
    @Test
    public void updateLastPostTest() {
        List<Post> postsList = getPostsList();
        Post lastPost = postsList.get(0);
        String message = lastPost.getMessage();

        client.deleteObject(lastPost.getId());

        String updatedValue = message + " updated!";
        client.publish(PAGE_ENDPOINT,
                FacebookType.class,
                Parameter.with("message", updatedValue));

        postsList = getPostsList();
        lastPost = postsList.get(0);
        assertEquals(lastPost.getMessage(), updatedValue, "Message was not updated");
    }

    /**
     * add also test on delete first, delete all, delete post with incorrect id
     */
    @Test
    public void deleteLastPostTest() {
        List<Post> postsList = getPostsList();
        Post lastPost = postsList.get(0);
        boolean isDeleted = client.deleteObject(lastPost.getObjectId());
        Assert.assertTrue(isDeleted, "Post was not deleted");
    }

}
