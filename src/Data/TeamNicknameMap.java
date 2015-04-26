package Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.atteo.evo.inflector.English;

public class TeamNicknameMap {
	
	private ColourTable colour_table = new ColourTable("colours.txt");
	private ColourCatalogue colour_catalogue = new ColourCatalogue(colour_table);
	private TeamEndingsMap team_endings_map = new TeamEndingsMap("newTeamEndings.txt");
	static HashMap<String, Vector<Vector<String>>> team_nickname_map = new HashMap<String, Vector<Vector<String>>>();
	
	public TeamNicknameMap(){
		generateNicknames();
	}
	
	private void generateNicknames(){
		HashMap<String, Vector<String>> plural_readymades = colour_catalogue.getreadymadePluralBigrams();
		HashMap<String, Vector<String>> bigram_readymades = colour_catalogue.getreadymadeBigrams();
		HashMap<String, Vector<String>> unigram_readymades = colour_catalogue.getreadymadeUnigrams();

		for (Map.Entry<String, Vector<String>> entry : team_endings_map.getTeamEndingMap().entrySet()){
			Vector<Vector<String>> nicknames = new Vector<Vector<String>>(); 
			for(String ending_colour : entry.getValue()){
				
				Vector<Vector<String>> readymades = new Vector<Vector<String>>();
				double range = 0.02;
				while(readymades.size() < 5){
					 
					readymades.addAll(colour_catalogue.getNearestColours(plural_readymades, ending_colour, range));
					Vector<Vector<String>> temp = new Vector<Vector<String>>();
					temp.addAll(colour_catalogue.getNearestColours(bigram_readymades, ending_colour, range));
					temp.addAll(colour_catalogue.getNearestColours(unigram_readymades, ending_colour, range));
					for(Vector<String> colours : temp){
						colours.add(0, English.plural(colours.elementAt(0)));
						
					}
					
					readymades.addAll(temp);
					removeDuplicates(readymades);
					range += 0.02;
				}
				for(Vector<String> nickname : readymades){
					if(nickname.size() == 3){
						nickname.removeElementAt(1);
					}
					nicknames.add(nickname);
				}
				

			}
			team_nickname_map.put(entry.getKey(), nicknames);
		}

	}
	
	public static void removeDuplicates(Vector v){		// simply removes duplicates in a given vector;
		 for(int i=0;i<v.size();i++){
			 for(int j=0;j<v.size();j++){
				 	if(i != v.size()){
	                    if(i!=j){
//	                    	System.out.println(i+","+j+","+v.size());
	                    	if(v.elementAt(i).equals(v.elementAt(j))){
	                    		v.removeElementAt(j);
	                        }
	                    }
				 	}
	            }
	        }
	    }
	

	
	private void print_to_file(HashMap<String, Vector<Vector<String>>> hmap, String outputFile){
		try{
			File outfile = new File("output\\"+outputFile);
			if (!outfile.exists()) {
				outfile.createNewFile();
			}
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, Vector<Vector<String>>> entry : hmap.entrySet()) {	//loop through every entry in the hashmap
			    bw.write(entry.getKey()+" = [");		//write the key an the value to the specified file
			    for(Vector<String> rgb : entry.getValue()){
			    	bw.write(rgb.toString()+",");
			    }
			    bw.write(" ]");
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMapToFile(){
		print_to_file(team_nickname_map,"teamNicknamesMap.txt");
	}
	
	public HashMap<String, Vector<Vector<String>>> getNicknames(){
		return team_nickname_map;
	}
}
