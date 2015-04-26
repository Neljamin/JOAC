package Data;

import java.util.Arrays;
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
	private HashMap<String, Vector<Vector<String>>> teamNicknames;
	
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
			if(name.charAt(0) == targetName.charAt(0)){ //check for alliteration
				score += 10;
			}
		}
	}
	
	public TeamNamer(){
		System.out.println("Setting up data...");
		teamEndingMap = new TeamEndingsMap("newTeamEndings.txt");
		teamNicknameMap = new TeamNicknameMap();
		placeInventory = new PlaceInventory();
		colourTable = new ColourTable("colours.txt");
		teamEndings = teamEndingMap.getTeamEndingMap();
		teamNicknames = teamNicknameMap.getNicknames();
	}
	
	public String[] generateName(String rgb){
		rgbCode = rgb;
		String place = placeInventory.getRandomPlace();
		String ending = generateEnding(place);
		String nickname = generateNickname(ending);
		nickname = capitalize(nickname);
		String[] nameParts = new String[]{place, ending, nickname};
		
		return nameParts;
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
					endings.add(pairs.getKey());
				}
			}
		}
		
		for(int j = 0; j < endings.size(); j++){
			NameChecker nameChecker = new NameChecker(endings.elementAt(j), placeName);
			score = nameChecker.getScore();
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
		double range = 0.2;
		Vector<String> nextVector = new Vector<String>();
		Vector<String> nicknameFinalists = new Vector<String>();
//		String nextString = "";
		String nickname = "ERROR";
		Vector<Vector<String>> potentialNicknames = teamNicknames.get(teamEnding);
		Iterator<Vector<String>> i = potentialNicknames.iterator();
		while(i.hasNext()){
			nextVector = i.next();
			while(nicknameFinalists.isEmpty()){
				if(colourTable.get_distance(nextVector.elementAt(1), rgbCode) < range){
					nicknameFinalists.add(nextVector.elementAt(0));
				}
				range += 0.1;
			}	
		}
		
		for(int k = 0; k < nicknameFinalists.size(); k++){
			NameChecker nameChecker = new NameChecker(nicknameFinalists.elementAt(k), teamEnding);
			score = nameChecker.getScore();
			if(highestScore < score){
				nickname = nicknameFinalists.elementAt(k);
				highestScore = score;
			}
		}
		
		if(highestScore == 0){ //Default choice if all have a zero score
			nickname = nicknameFinalists.elementAt(0);
		}
		
		return nickname;
	}
	
	private String capitalize(String nickname){
		if(nickname.contains("_")){
			String[] parts = nickname.split("_");
			String newPart = Character.toUpperCase(parts[0].charAt(0)) + parts[0].substring(1);
			String newPart2 = Character.toUpperCase(parts[1].charAt(0)) + parts[1].substring(1);
			return newPart +"_"+newPart2;
		}
		else{
			String nickName = Character.toUpperCase(nickname.charAt(0)) + nickname.substring(1);
			return nickName;
		}
	}
}
