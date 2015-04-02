import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;


public class UrlMap {

	private static HashMap<String, String> url_map = new HashMap<>();
	private static HashMap<String, String> readymade_urls = new HashMap<>();
	private ColourCatalogue colour_catalogue;
	
	public UrlMap(ColourCatalogue colourCatalogue){
		colour_catalogue = colourCatalogue;
		get_urls("twitter_urls.txt");
	}
	
	public void get_urls(String filename){
		try {

			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line, rgb_code,url;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				String[] parts = line.split("\t");
				rgb_code = "#" + parts[0].substring(2);
				url = parts[1];
				if(!(url_map.containsKey(rgb_code))){
					url_map.put(rgb_code, url);															//put the rgb and its url in a hashmap 
					readymade_urls.put(url, colour_catalogue.get_nearest_colour(rgb_code)[0]);			//put the url and the name closest to it in a hashmap
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String[] get_tweet_data(String rgb_code){
		if(url_map.containsKey(rgb_code)){																//if the code is in the map
			String url = url_map.get(rgb_code).toString();												//get the url
			String readymade = readymade_urls.get(url);													//get the name associated with the url
			String[] url_readymade_pairing = {url,readymade};											//store them in an array
			return url_readymade_pairing;																// return the array	
		}
		return null;
	}
	
	private void print_to_file(HashMap<String, String> hmap, String outputFile){
		try{
			File outfile = new File("output\\"+outputFile);
			if (!outfile.exists()) {
				outfile.createNewFile();
			}
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (HashMap.Entry<String, String> entry : hmap.entrySet()) {	//loop through every entry in the hashmap
			    bw.write(entry.getKey() + " = " + entry.getValue());		//write the key an the value to the specified file
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void printMaps(){
		print_to_file(url_map, "url_map.txt");
		print_to_file(readymade_urls, "readymade_urls.txt");
	}
	


}
