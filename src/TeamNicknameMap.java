import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;


public class TeamNicknameMap {
	
	ColourTable colour_table = new ColourTable("colours.txt");
	ColourCatalogue colour_catalogue = new ColourCatalogue(colour_table);
	TeamEndingsMap team_endings_map = new TeamEndingsMap("team_endings.txt");
	static HashMap<String, Vector<String>> team_nickname_map = new HashMap<String, Vector<String>>();
	
	
	
	public void generateNicknames(){
		HashMap<String, Vector<String>> plural_readymades = colour_catalogue.getreadymadePluralBigrams();
		for (HashMap.Entry<String, Vector<String>> entry : team_endings_map.getTeamEndingMap().entrySet()){
			Vector<String> nicknames = new Vector<String>(); 
			for(String ending_colour : entry.getValue()){
				Vector<Vector<String>> readymades = colour_catalogue.getNearestColours(plural_readymades, ending_colour, 0.03);
				for(Vector<String> nickname : readymades){
					nicknames.add(nickname.firstElement());
				}
			}
			team_nickname_map.put(entry.getKey(), nicknames);
		}

	}
	
	public void print_to_file(HashMap<String, Vector<String>> hmap, String outputFile){
		try{
			File outfile = new File("output\\"+outputFile);
			if (!outfile.exists()) {
				outfile.createNewFile();
			}
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (HashMap.Entry<String, Vector<String>> entry : hmap.entrySet()) {	//loop through every entry in the hashmap
			    bw.write(entry.getKey()+" = [");		//write the key an the value to the specified file
			    for(String rgb : entry.getValue()){
			    	bw.write(rgb+",");
			    }
			    bw.write(" ]");
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TeamNicknameMap tn = new TeamNicknameMap();
		tn.generateNicknames();
		tn.print_to_file(team_nickname_map,"teamNicknameMap.txt" );
			
	}
}
