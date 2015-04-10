
public class TeamNamer {
	
	private String rgbCode;
	private TeamEndingsMap teamEndings;
	private TeamNicknameMap teamNicknames;
	private PlaceInventory places;
	
	public TeamNamer(String rgb){
		rgbCode = rgb;
		teamEndings = new TeamEndingsMap("TeamEndingsOutput.txt");
		teamNicknames = new TeamNicknameMap();
		places = new PlaceInventory();
	}
}
