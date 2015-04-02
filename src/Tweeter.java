import java.io.IOException;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class Tweeter {
	
	private final static int COLOUR_URL = 0;
	private final static int COLOUR_NAME = 1;
    private final static String CONSUMER_KEY = "ZSwbiJMuakwItiVk6lHXvIZkl";
    private final static String CONSUMER_KEY_SECRET = "KgsCwuOWe1TkXE1Kcogr5f3vYPgGXCVkPMK2KRRPRYu0i80Psi";
    private UrlMap url_map;

    public Tweeter(UrlMap urlMap){
    	url_map = urlMap;
    }
    
    public void tweet_colour(String rgb_code) throws TwitterException, IOException {
    
	     url_map.get_urls("twitter_urls.txt");
		 String[] tweet_data = url_map.get_tweet_data(rgb_code);
		 if(tweet_data == null){
			 System.out.println("No colour swatch for "+rgb_code+" in Url Map");
		 }
		 else{
			 Twitter twitter = new TwitterFactory().getInstance();
			 twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
			
			 String accessToken = getSavedAccessToken();
			 String accessTokenSecret = getSavedAccessTokenSecret();
			 AccessToken oathAccessToken = new AccessToken(accessToken,accessTokenSecret);
			 twitter.setOAuthAccessToken(oathAccessToken);
			
			 twitter.updateStatus("The name of this colour is "+tweet_data[COLOUR_NAME]+"\n"+tweet_data[COLOUR_URL]);
			
		 }
    }
	
	private String getSavedAccessTokenSecret() {
		return "W2d6mOfR2rcv3i6ROi9CvK66nED9ekB1as3uE03gkUIy6";
	}
	
	private String getSavedAccessToken() {
		return "260475724-zJHa0U6WVzuCdeLBFieEVNFaSj3433FaUP0yqqWM";
	}
}