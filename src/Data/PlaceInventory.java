package Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;


public class PlaceInventory {
	Vector<String> places;
	public PlaceInventory(){
		places = new Vector<String>();
		loadPlaces();
	}
	
	private void loadPlaces(){
		try {
			FileReader placeFile = new FileReader("data\\placenames.txt");
			@SuppressWarnings("resource")
			Scanner in = new Scanner(placeFile);
			while(in.hasNextLine()){
				places.add(in.nextLine());
			}
			in.close();
			try {
				placeFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Vector<String> getPlaces(){
		return places;
	}
	
	public String getRandomPlace(){
		int rand = (int) (Math.random() * 385);
		return places.elementAt(rand);
	}
	
	public static void main(String args[]){
		PlaceInventory places = new PlaceInventory();
		Iterator<String> place_it = places.getPlaces().iterator();
		while(place_it.hasNext()){
			System.out.println(place_it.next());
		}
	}
}
