
import java.io.File;
import java.io.IOException;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class Tweeter {
 
	private final static String CONSUMER_KEY = "Ml6x6F1EbqUH0YiCtQiZyCs9y";
	private final static String CONSUMER_KEY_SECRET = "CbET7Emw5gdNT70nuUpB5Zezp623Y4zgNhu4bitVNCwqP2PJYb";
	
	//private TwitterStream twitterStream;


	
	
	public void tweet() throws TwitterException, IOException {
	
		final Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		String accessToken = getSavedAccessToken();
		String accessTokenSecret = getSavedAccessTokenSecret();
		AccessToken oathAccessToken = new AccessToken(accessToken,accessTokenSecret);
		
		twitter.setOAuthAccessToken(oathAccessToken);
	    TwitterStream twitterStream = new TwitterStreamFactory(new ConfigurationBuilder().setJSONStoreEnabled(true).build()).getInstance();

	    twitterStream.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
	    AccessToken token = new AccessToken(accessToken, accessTokenSecret);
	    twitterStream.setOAuthAccessToken(token);

	    StatusListener listener = new StatusListener() {
	        public void onStatus(Status status) {
	        	String colour = status.getText().substring(2, 8); 
				System.out.println("Tweeting: " + colour);
				
				StatusUpdate statusUpdate = new StatusUpdate(colour);
				
				File image = new File("jersey.png");
				statusUpdate.setMedia(image);
				try {
					twitter.updateStatus(statusUpdate);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
	        }
			public void onException(Exception arg0) {}
			public void onDeletionNotice(StatusDeletionNotice arg0) {}
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning arg0) {}
			public void onTrackLimitationNotice(int arg0) {}
	    };

	    twitterStream.addListener(listener);
	    
	    long everycolorbotId = 1909219404l;

	    FilterQuery query = new FilterQuery();
	    query.follow(new long[] { everycolorbotId });
	    twitterStream.filter(query);

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
