import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;


public class TeamNamer {
	
	private String rgbCode, tweetString;
	private TeamEndingsMap teamEndingMap;
	private TeamNicknameMap teamNicknameMap;
	private PlaceInventory placeInventory;
	private ColourTable colourTable;
	private HashMap<String, Vector<String>> teamEndings;
	private HashMap<String, Vector<String>> teamNicknames;
	
	private class NameChecker{
		private int score;
		private String name, targetName;
		
		public NameChecker(String inputName1, String inputName2){
			name = inputName1;
			targetName = inputName2;
			score = 0;
			generateScore();
		}
		
		public int getScore(){
			return score;
		}
		
		private void generateScore(){
			String temp;
			
			if(name.charAt(0) == targetName.charAt(0)){ //check for alliteration
				score += 10;
			}
			
			if(Character.isLowerCase(name.charAt(0))){  
				if(name.contains("_")){ //if the we're checking a nickname
					temp = name.substring(0, name.indexOf("_"));
				}
				
				else{
					temp = name;
				}
				
				if(targetName.contains(temp)){
					score += 10;
				}
			}
		}
	}
	
	public TeamNamer(String rgb){
		rgbCode = rgb;
		teamEndingMap = new TeamEndingsMap("newTeamEndings.txt");
		teamNicknameMap = new TeamNicknameMap();
		placeInventory = new PlaceInventory();
		colourTable = new ColourTable("colours.txt");
		teamEndings = teamEndingMap.getTeamEndingMap();
		teamNicknames = teamNicknameMap.getNicknames();
	}
	
	public String generateName(){
		String place = placeInventory.getRandomPlace();
		System.out.println(place);
		String ending = generateEnding(place);
		System.out.println(ending);
		String nickname = generateNickname(ending);
		tweetString = "The " + place + " " + ending + " aka the " + nickname;
		
		return tweetString.replace('_', ' ');
	}
	
	private String generateEnding(String place){
		String ending = "ERROR";
		String placeName = place;
		Vector<String> endings = new Vector<String>();
		double distance;
		int score = 0, highestScore = 0;
		
		Iterator i = teamEndings.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry<String, Vector<String>> pairs = (Map.Entry<String, Vector<String>>)i.next();
			Iterator<String> j = pairs.getValue().iterator();
			while(j.hasNext()){
				distance = colourTable.get_distance(rgbCode, j.next());
				//LOOK FOR ANY COLOUR WITHIN 0.2 RANGE AND SEND IT TO THE SCORE CHECKER
				if(distance < 0.2){
					System.out.println("Adding " + pairs.getKey());
					endings.add(pairs.getKey());
				}
			}
		}
		
		for(int j = 0; j < endings.size(); j++){
			NameChecker nameChecker = new NameChecker(endings.elementAt(j), placeName);
			score = nameChecker.getScore();
			System.out.println("Score for " + endings.elementAt(j) + " = " + score);
			if(highestScore < score){
				ending = endings.elementAt(j);
				highestScore = score;
			}
		}
		
		if(highestScore == 0){
			ending = endings.elementAt(0);
		}
		
		return ending;
	}
	
	private String generateNickname(String teamEnding){
		int score = 0, highestScore = 0;
		String nickname = "ERROR";
		Vector<String> potentialNicknames = teamNicknames.get(teamEnding);
		for(int i = 0; i < potentialNicknames.size(); i++){
			NameChecker nameChecker = new NameChecker(potentialNicknames.elementAt(i), teamEnding);
			score = nameChecker.getScore();
			if(highestScore < score){
				nickname = potentialNicknames.elementAt(i);
				highestScore = score;
			}
		}
		
		if(highestScore == 0){
			nickname = potentialNicknames.elementAt(0);
		}
		
		System.out.println(nickname);
		return nickname;
	}
	
	public static void main(String args[]){
		TeamNamer namer = new TeamNamer("#E8BA23");
		System.out.println("Name = " + namer.generateName());
	}
}
