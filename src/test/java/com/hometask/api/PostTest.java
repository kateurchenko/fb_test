package com.hometask.api;

import com.hometask.Constants;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
import com.restfb.types.Post;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by kate on 11/24/19.
 */
public class PostTest {
    public static final String PAGE_ENDPOINT = Constants.TEST_PAGE_ID + "/feed";
    FacebookClient client;

    @BeforeTest
    public void init() {
        client = new DefaultFacebookClient(Constants.TEST_USER_ACCESS_TOKEN, Version.VERSION_2_10);
    }

    /**
     * add also test for publishing photo/video/link/add comment on post
     */
    @Test
    public void createPostTest() {
        FacebookType message = client.publish(PAGE_ENDPOINT,
                FacebookType.class,
                Parameter.with("message", "test post message"),
                Parameter.with("picture", "https://onlineimagetools.com/images/examples-onlineimagetools/duckling.gif"),
                Parameter.with("caption", "duck"));
        String postId = message.getId();
        getPostsList()
                .stream()
                .filter(d -> d.getId().equals(postId))
                .findAny()
                .orElseThrow(() -> new AssertionError(String.format("Message with %s was not posted!", postId)));
    }

    private List<Post> getPostsList() {
        return client.fetchConnection(PAGE_ENDPOINT, Post.class).getData();
    }

    /**
     * add also test on update different media type content(e.g. photo, video, images, links)in post
     */
    @Test
    public void updateLastPostTest() {
        List<Post> postsList = getPostsList();
        Post lastPost = postsList.get(postsList.size() - 1);
        String message = lastPost.getMessage();

        client.deleteObject(lastPost.getId());

        String updatedValue = message + " updated!";
        client.publish(PAGE_ENDPOINT,
                FacebookType.class,
                Parameter.with("message", updatedValue));

        postsList = getPostsList();
        lastPost = postsList.get(postsList.size() - 1);
        Assert.assertEquals(lastPost.getMessage(), updatedValue, "Message was not updated");
    }

    /**
     * add also test on delete first, delete all, delete post with incorrect id
     */
    @Test
    public void deleteLastPostTest() {
        List<Post> postsList = getPostsList();
        Post lastPost = postsList.get(postsList.size() - 1);
        boolean isDeleted = client.deleteObject(lastPost.getObjectId());
        Assert.assertTrue(isDeleted, "Post was not deleted");
    }

}
