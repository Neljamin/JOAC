package Data;
import java.io.BufferedReader;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;


public class TeamEndingsMap {
	
	
	
	private ColourTable colours = new ColourTable("colours.txt");
	private static HashMap<String, Vector<String>> teamEndingsMap = new HashMap<String, Vector<String>>();
	
	public TeamEndingsMap(String filename){
		readTeamEndings(filename);
	}
	
	public HashMap<String, Vector<String>> getTeamEndingMap(){
		return teamEndingsMap;
	}
	public void readTeamEndings(String filename){   //Reads in the team endings and their colours from the file
		try {
			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				String[] parts = line.split(" ");
				Vector<String> RGBs = new Vector<String>();
				String team_ending = "";
				for(int i=0;i<parts.length;i++){
					if(parts[i].startsWith("#")){
						RGBs.add(parts[i].trim());
					}
					else{
						team_ending = team_ending + parts[i]+" ";
					}
				}
				teamEndingsMap.put(team_ending.trim(),RGBs);
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void print_to_file(HashMap<String, Vector<String>> hmap, String outputFile){ //prints the map to a file
		try{
			File outfile = new File("output\\"+outputFile);
			if (!outfile.exists()) {
				outfile.createNewFile();
			}
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, Vector<String>> entry : hmap.entrySet()) {	//loop through every entry in the hashmap
			    bw.write(entry.getKey()+" ");		//write the key an the value to the specified file
			    for(String rgb : entry.getValue()){
			    	bw.write(rgb+" ");
			    }
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMapToFile(){
		print_to_file(teamEndingsMap,"teamEndingsMap.txt");
	}
}
