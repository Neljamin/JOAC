import java.io.IOException;
import twitter4j.TwitterException;


public class Main {
	
	public static void main(String[] args) throws TwitterException, IOException {
		ColourTable colout_table = new ColourTable("colours.txt");
		ColourCatalogue colour_catalogue = new ColourCatalogue(colout_table);
		UrlMap url_map = new UrlMap(colour_catalogue);
		Tweeter tweeter = new Tweeter(url_map);
		tweeter.tweet_colour("#154622");
	}

}
