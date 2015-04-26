package Images;
import java.util.Arrays;

public class ColourShader {
	
	private double BACKGROUND_LIGHTER = 0.333; 

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
		
		colourBands[0] = lighten(colourBands[0], BACKGROUND_LIGHTER);
		
		return colourBands;
	}
	
	private int[] darken(int[] colour, double fraction){
	
		
		int red = (int) Math.round(Math.max(0, colour[0] - 255 * fraction));
		int green = (int) Math.round(Math.max(0, colour[1] - 255 * fraction));
		int blue = (int) Math.round(Math.max(0, colour[2] - 255 * fraction));
	    
	    if (red < 0 | green < 0 | blue < 0){
	    	red += 255;
	    	green += 255;
	    	blue += 255;
	    	red %=255;
	    	green %=255;
	    	blue%=255;
	    }
		
		return new int[] {red, green, blue};
	}
	
	private int[] lighten(int[] colour, double fraction){
		
	    
		int red = (int) Math.round(Math.min( colour[0] + 255 * fraction, 255));
		int green = (int) Math.round(Math.min( colour[1] + 255 * fraction, 255));
		int blue = (int) Math.round(Math.min( colour[2] + 255 * fraction ,255));
		
		return new int[] {red, green, blue};
	}
	
	
}
