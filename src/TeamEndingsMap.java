import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;


public class TeamEndingsMap {
	
	ColourTable colours = new ColourTable("colours.txt");
	static HashMap<String, Vector<String>> teamEndingsMap = new HashMap<String, Vector<String>>();
	
	public void getColoursFor(String filename){
		
		try {
			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				String team_ending = line.toLowerCase();
				Vector<String> RGBs = new Vector<String>();
				for(HashMap.Entry<String, String> entry : colours.getTable().entrySet()){
					String[] sterotype = entry.getKey().split("_");
					if(team_ending.contains(sterotype[0].toLowerCase())){
						RGBs.add(entry.getValue());
					}
				}
				teamEndingsMap.put(line, RGBs);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
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
			    bw.write(entry.getKey()+" ");		//write the key an the value to the specified file
			    for(String rgb : entry.getValue()){
			    	bw.write(rgb+",");
			    }
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
