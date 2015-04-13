
import java.io.File;
import java.io.IOException;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Tweeter {
 
	private final static String CONSUMER_KEY = "Ml6x6F1EbqUH0YiCtQiZyCs9y";
	private final static String CONSUMER_KEY_SECRET = "CbET7Emw5gdNT70nuUpB5Zezp623Y4zgNhu4bitVNCwqP2PJYb";

	public void tweet() throws TwitterException, IOException {
	
	Twitter twitter = new TwitterFactory().getInstance();
	twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
	String accessToken = getSavedAccessToken();
	String accessTokenSecret = getSavedAccessTokenSecret();
	AccessToken oathAccessToken = new AccessToken(accessToken,accessTokenSecret);
	
	twitter.setOAuthAccessToken(oathAccessToken);
	
	 StatusUpdate status = new StatusUpdate("Testing:");
	 File image = new File("pattern_0.png");
	 status.setMedia(image);
	 twitter.updateStatus(status);

	}

    private String getSavedAccessTokenSecret() {
	return "YlE1SOZc6DW6BgqQcb0KJkNHj05V8wVmbQsMIAVuopJMe";
    }

    private String getSavedAccessToken() {
    	return "3094507821-2A8FbZ85pDKNCMsvqnfMN13Re47q2QHnggqimJu";
    }

    public static void main(String[] args) throws Exception {
    	new Tweeter().tweet();
    }

}
