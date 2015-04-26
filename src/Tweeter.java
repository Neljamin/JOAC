import Data.TeamNamer;
import Images.Image;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

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
				if(!isReply(status) && !status.isRetweet()){
					System.out.println("Got colour: "+colour);
					Image img = new Image(colour);
					img.createImage("jersey.png");
					String[] parts = nameGenerator.generateName("#"+colour);
					String name = assembleTweet(parts);
					StatusUpdate statusUpdate = new StatusUpdate(name);
					System.out.println("Tweeting: " +name); 
					File image = new File("jersey.png");
					statusUpdate.setMedia(image);
					try {
						twitter.updateStatus(statusUpdate);
					} catch (TwitterException e) {
						e.printStackTrace();
					}
				}
	        }
	        
	        private boolean isReply(Status status){
	        	return status.getInReplyToStatusId() != -1;
	        }
	        
	    	private String assembleTweet(String[] parts){
	    		Vector<String>  opening = new Vector<String>();
	    		Vector<String>  knownAs = new Vector<String>();
	    		
	    		opening.add("Nice colour .@everycolorbot that's the colour of the ");
	    		opening.add("@everycolorbot I recognise that colour! That's the colour of the ");
	    		opening.add("@everycolorbot that's the colour of my favourite team the ");
	    		opening.add("I know that colour @everycolorbot that's the colour of the ");
	    		
	    		Random generator  = new Random();
	    		int random = generator.nextInt(opening.size());
	    		
	    		String tweet = opening.get(random) + parts[0] + " " + parts[1] + " a.k.a the" +parts[2].replace("_", " ");
	    		return tweet;
	    	}
	    	
	        private TeamNamer nameGenerator  = new TeamNamer();
			public void onException(Exception arg0) {}
			public void onDeletionNotice(StatusDeletionNotice arg0) {}
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning arg0) {}
			public void onTrackLimitationNotice(int arg0) {}
	    };

	    twitterStream.addListener(listener);
	    
	    //TODO
	    long everycolorbotId = 258107892;
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
