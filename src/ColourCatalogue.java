/*
 * Name: Jonathan Doyle
 * Student NUmber: 12443578
 * 
 * This class takes in all the readymades and assigns colours to them 
 * based on the colours in the colourtable class and stores the results
 * in hash tables. There is a method to decide on what mix to give these colours
 * but there is only a basic check so far. There is also a method to get the nearest colour to a 
 * specified rgb code.There is also a method to print the hash table to files
 * which is mainly to check if things are working correctly. I included some samples 
 * of the files as the full files are too big to submit
 * */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;


public class ColourCatalogue {
	
	private static HashMap<String, Vector<String>> readymade_bigrams = new HashMap<String, Vector<String>>();
	private static HashMap<String, Vector<String>> readymade_unigrams = new HashMap<String, Vector<String>>();
	private static HashMap<String, Vector<String>> readymade_plural_bigrams = new HashMap<String, Vector<String>>();
	private ColourTable colour_table;

	public ColourCatalogue(ColourTable table){
		colour_table = table;
		get_colour_bigrams("bigrams.txt");
		get_unigrams("unigrams.txt");
	}
	
	public HashMap<String, Vector<String>> getreadymadeBigrams(){
		return readymade_bigrams;
	}
	
	public HashMap<String, Vector<String>> getreadymadeUnigrams(){
		return readymade_unigrams;
	}
	
	public HashMap<String, Vector<String>> getreadymadePluralBigrams(){
		return readymade_plural_bigrams;
	}
	
	private void get_colour_bigrams(String filename){
		try {
			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				String[] parts = line.split("\t");
				if(parts.length == 3){											//if its a plural bigram
					if(!(readymade_plural_bigrams.containsKey(parts[0] +"_"+parts[2]))){	//check if key is unique
						readymade_plural_bigrams.put(parts[0] +"_"+parts[1], generate_new_codes(parts[0], parts[2]));
					}
				}
				else{
					if(!(readymade_bigrams.containsKey(parts[0] +"_"+parts[1]))){
						readymade_bigrams.put(parts[0] +"_"+parts[1], generate_new_codes(parts[0], parts[1]));
					}
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void get_unigrams(String filename){
		try {
			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				for(int i =1;i< line.length();i++){
					if(colour_table.contains(line.substring(0, i))){			//check each sub string of the unigram for a colour term
						Vector<String> codes = generate_new_codes(line.substring(0, i), line.substring(i, line.length()));
						if(!(codes.size() == 0)){				
							readymade_unigrams.put(line, codes);
							  break;
						}
					}
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void print_to_file(HashMap<String, Vector<String>> hmap, String outputFile){
		try{
			File outfile = new File("output\\"+outputFile);
			if (!outfile.exists()) {
				outfile.createNewFile();
			}
			FileWriter fw = new FileWriter(outfile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Map.Entry<String, Vector<String>> entry : hmap.entrySet()) {	//loop through every entry in the hashmap
			    bw.write(entry.getKey() + " = " + entry.getValue());		//write the key an the value to the specified file
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Vector<String> generate_new_codes(String part1, String part2){
		Vector<String> modifier = colour_table.get_every(part1.toLowerCase());	//get every rgb for the modifier
		Vector<String> head = colour_table.get_every(part2.toLowerCase());		//get every rgb for the head
		String[] modifiers = new String[modifier.size()];
		String[] heads = new String[head.size()];
		modifier.toArray(modifiers);
		head.toArray(heads);
		
		Vector<String> new_codes = new Vector<String>();
		for(int i =0;i<modifiers.length;i++){
			for(int j=0;j<heads.length;j++){
				int[] blends = get_blend(modifiers[i], heads[j]);										//decide on best blend ratio 
				String new_rgb = colour_table.blend_colours(modifiers[i], heads[j],blends[0],blends[1]); // blend each modifier and head
				if(!(new_codes.contains(new_rgb))){
					new_codes.add(new_rgb);												// add new codes to the nee vector
				}
			}
		}
		 return new_codes;															//return the vector with the new codes
	}
	private int[] get_blend(String mod, String head){
		int[] blends = {40,60};
		if(colour_table.get_distance(mod, "#000000") < 0.1 ){					//if the modifier is dark
			blends[0] = 20;														//only use 20%
			blends[1] = 80;
		}
		return blends;
	}
	
	public String[] get_nearest_colour(String rgb_code){
		String[] nearest_bigram = search(readymade_bigrams,rgb_code);					//search for closest in bigams
		String[] nearest_plural_bigram = search(readymade_plural_bigrams,rgb_code);		//search for closest in plural bigams
		String[] nearest_unigram = search(readymade_unigrams,rgb_code);					//search for closest in unigams
		double bigram_distance = colour_table.get_distance(nearest_bigram[1], rgb_code);
		double pbigram_distance = colour_table.get_distance(nearest_plural_bigram[1], rgb_code);
		double uniigram_distance = colour_table.get_distance(nearest_unigram[1], rgb_code);
		if(bigram_distance < pbigram_distance && bigram_distance < uniigram_distance ){
			return nearest_bigram;
		}
		else if(pbigram_distance < bigram_distance && pbigram_distance < uniigram_distance){	//get the smallest of the three values
			return nearest_plural_bigram;														// return the nearest code
		}
		else{
			return nearest_unigram;												
		}
	}
	
	public Vector<Vector<String>> getNearestColours(HashMap<String, Vector<String>> map, String my_rgb,double range){
		Vector<Vector<String>> nearest_colours = new Vector<Vector<String>>();
		for (Map.Entry<String, Vector<String>> entry : map.entrySet()) {		//go through every entry in the hash table			
			for(String rgb : entry.getValue()){
					double distance = colour_table.get_distance(rgb, my_rgb);	//get the distance from each code
					if(distance < range){				
						Vector<String> nearest_colour = new Vector<String>();
						nearest_colour.add(entry.getKey());
						nearest_colour.add(rgb);
						nearest_colours.add(nearest_colour);
					}
				
			}
		}
		return nearest_colours;
	}
	
	private String[] search(HashMap<String, Vector<String>> map, String rgb_code){
		String[] nearest_colour = new String[2];
		double nearest = 1.0;												//set nearest codes to max distance
		for (Map.Entry<String, Vector<String>> entry : map.entrySet()) {		//go through every entry in the hash table			
			String[] values = new String[entry.getValue().size()];
			entry.getValue().toArray(values);
			if(values.length > 10){
				for(int i=0;i<values.length;i++){
					double distance = colour_table.get_distance(values[i], rgb_code);	//get the distance from each code
					if(distance < 0.01){												//check if its the smallest distance
						nearest_colour[0] = entry.getKey();
						nearest = distance;
						nearest_colour[1] = values[i];
					}
				}
			}
			else{
				for(int i=0;i<values.length;i++){
					
					double distance = colour_table.get_distance(values[i], rgb_code);	//get the distance from each code
										
					if(distance < nearest){												//check if its the smallest distance
						nearest_colour[0] = entry.getKey();
						nearest = distance;
						nearest_colour[1] = values[i];
					}
				}
			}
		}
		return nearest_colour;												//return the nearest colour found
	}
	
	public void print_colours_to_files(){									//prints all the hashmaps to files
		print_to_file(readymade_bigrams, "readymade_bigrams.txt");
		print_to_file(readymade_plural_bigrams, "readymade_plural_bigrams.txt");
		print_to_file(readymade_unigrams, "readymade_unigrams.txt");
	}
	
	public static void main(String[] args) {
		ColourTable c = new ColourTable("colours.txt");
		ColourCatalogue cc = new ColourCatalogue(c);
		cc.print_colours_to_files();
	}
	
	
				
}
