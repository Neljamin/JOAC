/*	Name: Jonathan Doyle 
 *	Student Number:12443578
 * 	Assignment 1
 * 	Colour Table
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.Iterator;


public class ColourTable {
	HashMap<String, String> colours = new HashMap<>();						//create new hash map named colours
	Vector<String> sterotype = new Vector<String>();
	Vector<String>  solid_colour = new Vector<String>();
	private static final int  RED = 0;
    private static final int  GREEN = 1;
    private static final int  BLUE = 2;
    private static final double  MAX_DISTANCE = 441.6729559300637;
	
	public ColourTable(String filename){
		add_colours(filename);
	}
    public ColourTable(){														//blank constructor
		
	}
    
    public HashMap<String, String> getTable(){
    	return colours;
    }
	
	public void add_colours(String filename){
		try {
			File file = new File("data\\"+filename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {				//read in each line of the file
				
				String[] parts = line.split("\t");								// split the lines of the file for each tab
				sterotype.add(parts[0].toLowerCase());
				solid_colour.add(parts[1].toLowerCase());
				colours.put(parts[0].toLowerCase()+'_'+parts[1].toLowerCase(), parts[2]);					//add the colours to the hash map
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String get_colour(String colour_name){
		return colours.get(colour_name);										// return the rgb code of the corresponding string
	}
	
	public Vector get_every(String colour){
		Vector<String> rgb_codes = new Vector<String>();
		String[] sterotypes = new String[sterotype.size()];
		String[] colour_names = new String[sterotype.size()];
		sterotype.toArray(sterotypes);
		solid_colour.toArray(colour_names);
		for(int i = 0;i<sterotypes.length;i++){
			
			if(sterotypes[i].equals(colour)){
				rgb_codes.add(colours.get(sterotypes[i] +"_"+ colour_names[i]));
			}
			if(colour_names[i].equals(colour)){
				rgb_codes.add(colours.get(sterotypes[i] +"_"+ colour_names[i]));
			}
		}

		return rgb_codes;
	}
	public String[] get_every_to_array(String colour){
		Vector<String> rgb_codes= get_every(colour);
		String[] codes = new String[rgb_codes.size()];
		rgb_codes.toArray(codes);
		return codes;
	}
	public boolean contains(String keyword){
		if(sterotype.contains(keyword) || solid_colour.contains(keyword)){
			return true;
		}
		return false;
	}
	
	public String blend_colours(String colour_a,String colour_b, float amount1, float amount2){
		
		
		int[] colour1 = to_rgb_code(colour_a).clone();							// split the colours into red,green,blue parts
		int[] colour2 = to_rgb_code(colour_b).clone();
		
		int red = (int) (((colour1[RED]*(amount1/100)) + (colour2[RED]*(amount2/100)))) ;			//calculate new red value 
		int green = (int) (((colour1[GREEN]*(amount1/100)) + (colour2[GREEN]*(amount2/100)))) ;		//calculate new green value
		int blue = (int) (((colour1[BLUE]*(amount1/100)) + (colour2[BLUE]*(amount2/100)))) ;		//calculate new blue value
		
		String temp = Integer.toHexString(red);						//put red value to a string
		if(temp.length() < 2){										//if its single digit add a 0 at the start
			temp = "0"+temp;
		}
		String new_colour = '#' + temp;								//add the red value to the new colour string
		
		temp = Integer.toHexString(green);							//put green value to a string
		if(temp.length() < 2){										//if its single digit add a 0 at the start
			temp = "0"+temp;
		}
		new_colour = new_colour + temp;								//add the green value to the new colour string
		
		temp = Integer.toHexString(blue);							//put blue value to a string
		if(temp.length() < 2){										//if its single digit add a 0 at the start
			temp = "0"+temp;
		}
		new_colour = new_colour + temp;								//add the blue value to the new colour string

		return new_colour;											//return the new colour
	}
	
	public String blend_colours(String colour_a,String colour_b){
		
		return blend_colours(colour_a,colour_b,50,50);				// default blend if no % specified
	}
	
	public int[] to_rgb_code(String colour){
		int[] rgb = new int[3];
		colour.substring(1);
		
		rgb[0] = Integer.parseInt(colour.substring(1,3), 16);		//get red parts of the string and change it to an int
		rgb[1] = Integer.parseInt(colour.substring(3,5), 16);		//get green parts of the string and change it to an int
		rgb[2] = Integer.parseInt(colour.substring(5,7), 16);		//get blue parts of the string and change it to an int
		
		return rgb;													//return an array with the red,green blue values as int 
	}
	 public double get_distance(String colour1,String colour2){
		int[] colour_1 = to_rgb_code(colour1).clone();				//split colours into red green blue parts
		int[] colour_2 = to_rgb_code(colour2).clone();
		
		double distance = Math.pow((colour_1[RED] - colour_2[RED]), 2);			//calculate the distance using distance formula
		distance = distance + Math.pow((colour_1[GREEN] - colour_2[GREEN]), 2);
		distance = distance + Math.pow((colour_1[BLUE] - colour_2[BLUE]), 2);
		distance = Math.sqrt(distance);
		 return distance / MAX_DISTANCE;														// return distance value as a double
	 }

}