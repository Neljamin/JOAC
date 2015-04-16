import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class TeamNamer {
	
	private String rgbCode;
	private TeamEndingsMap teamEndingMap;
	private TeamNicknameMap teamNicknameMap;
	private PlaceInventory placeInventory;
	private ColourTable colourTable;
	private HashMap<String, Vector<String>> teamEndings;
	private HashMap<String, Vector<String>> teamNicknames;
	private Vector<String> places;
	
	public TeamNamer(String rgb){
		rgbCode = rgb;
		teamEndingMap = new TeamEndingsMap("newTeamEndings.txt");
		teamNicknameMap = new TeamNicknameMap();
		placeInventory = new PlaceInventory();
		colourTable = new ColourTable("colours.txt");
		teamEndings = teamEndingMap.getTeamEndingMap();
		teamNicknames = teamNicknameMap.getNicknames();
		places = placeInventory.getPlaces();
	}
	
	public String generateName(){
		String place = placeInventory.getRandomPlace();
		System.out.println(place);
		String ending = generateEnding();
		System.out.println(ending);
		String nickname = generateNickname(ending);
		
		return "The " + place + " " + ending + " aka the " + nickname;
	}
	
	private String generateEnding(){
		String ending = "ERROR";
		double shortestDistance = 1000.0;
		double distance;
		String next;
		
		Iterator i = teamEndings.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, Vector<String>> pairs = (Map.Entry<String, Vector<String>>)i.next();
			next = pairs.getKey();
//			System.out.println(next);
			Iterator<String> j = pairs.getValue().iterator();
			while(j.hasNext()){
				distance = colourTable.get_distance(rgbCode, j.next());
//				System.out.println(shortestDistance);
				if(distance < shortestDistance){
					shortestDistance = distance;
					ending = pairs.getKey();
//					System.out.println(ending);
				}
			}
		}
		return ending;
	}
	
	private String generateNickname(String teamEnding){
		Vector<String> potentialNicknames = teamNicknames.get(teamEnding);
		int rand = (int) Math.random() * potentialNicknames.size();
		return potentialNicknames.elementAt(rand);
	}
	
	public static void main(String args[]){
		TeamNamer namer = new TeamNamer("#B31BA1");
		System.out.println(namer.generateName());
	}
}
