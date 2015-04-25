package Images;
import java.util.Arrays;

public class ColourShader {
	
	private double BACKGROUND_LIGHTER = 0.5; 

	private int[] hexToInts(String hex){

		int[] ints  = new int[3];

		if(hex.charAt(0) == '#') {
			hex = hex.substring(1);
		}

		ints[0] = Integer.parseInt(hex.substring(0, 2),16);
		ints[1] = Integer.parseInt(hex.substring(2, 4),16);
		ints[2] = Integer.parseInt(hex.substring(4, 6),16);

		return ints;
	}

	
	public int[][] getColourBands(String hex, int bands){
		
		int[] colour = hexToInts(hex);
		int[][] darkerColourBands = new int[bands/2 + 1][];
		int[][] lighterColourBands = new int[bands/2 + 1][];
		
		for (int i = 0; i < darkerColourBands.length; i++) {
			darkerColourBands[i] = darken(colour, (double) i / (double) bands);
		}
		
		for (int i = 0; i < lighterColourBands.length; i++) {
			lighterColourBands[i] = lighten(colour, (double) i / (double) bands);
		}
		
		int[][] colourBands = new int[lighterColourBands.length + darkerColourBands.length - 1][];
		
		for (int i = lighterColourBands.length-1; i >=0 ; i--) {
			colourBands[i] = lighterColourBands[lighterColourBands.length-(1+i)];
		}
		
		for (int i = 1; i < darkerColourBands.length; i++) {
			colourBands[i + lighterColourBands.length - 1] = darkerColourBands[i];
		}
		
		colourBands[0] = lighten(colour, BACKGROUND_LIGHTER);
		
		return colourBands;
	}
	
	private int[] darken(int[] colour, double fraction){
		
		int red = (int) Math.round(colour[0] - 255 * fraction) % 255;
	    int green = (int) Math.round(colour[1] - 255 * fraction) % 255;
	    int blue = (int) Math.round(colour[2] - 255 * fraction) % 255;
	    
	    red += red < 0 ? 255 : 0;
	    green += green < 0 ? 255 : 0;
	    blue += blue < 0 ? 255 : 0;
		
		return new int[] {red, green, blue};
	}
	
	private int[] lighten(int[] colour, double fraction){
	    
	    int red = (int) Math.round(colour[0] + 255 * fraction) % 255;
	    int green = (int) Math.round(colour[1] + 255 * fraction) % 255;
	    int blue = (int) Math.round(colour[2] + 255 * fraction)% 255;
		
		return new int[] {red, green, blue};
	}
	
	public static void main(String[] args) {
		ColourShader cs = new ColourShader();
		System.out.print(Arrays.deepToString(cs.getColourBands("#000000", 5)));
	}
	
}
