/* make sure the folder containing the patterns are in the following format:
 *  
 *   folder(patterns) --> folder(pattern_0)   --> 	file(layer_0)
 *   							.						.
 *   							.						.
 *   							.					file(layer_n)
 *   							.
 *   					 folder(pattern_n) --> file(layer_0)
 *   												.
 *   												.
 *   											file(layer_n)		
 */

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;


public class Pattern {
	
	private static final String PATTERNS_DIRECTORY = "patterns";  // path to folder containing the patterns 
	private static final int PATTERN_NUMBER = 0; //TODO temporary constant used for testing
	/* if there are multiple folders in the pattern folder set this constant to what number folder 
	 * you want to test. What ever sequence the folders in the directory are in will correspond to what 
	 * number to use.
	 * */
	
	private ColourShader shader;
	private Vector<BufferedImage> images;  // contains all image files that makes up the pattern
	private int width;		// this will be the width of the bottom layer i.e. the canvas
	private int height;		// this will be the height of the bottom layer i.e. the canvas
	
	public Pattern(String colour) {
		shader = new ColourShader();
		String patternDir = randomPattern();  	//selects a random pattern folder
		System.out.println("Selected patternDir: "+patternDir);
		Vector<String> files = getFiles(patternDir);	// gets the file names from the pattern directory
		images = loadImages(files);						// loads the images in the pattern directory
		BufferedImage baseLayer = images.get(0);		// gets the bottom layer i'e' the canvas
		height = baseLayer.getHeight();
		width = baseLayer.getWidth();
		colurImages(colour);		//colours the the image layers
		System.out.println("found file:"+files);
	}
	
	// returns the loaded images that make up the pattern
	public Vector<BufferedImage> getImages(){
		return images;
	}
	
	// returns the base height
	public int height(){
		return height;
	}
	
	// returns the base width
	public int width(){
		return width;
	}
	
	// this method loads the images from the given directory and returns them in a vector
	private Vector<BufferedImage> loadImages(Vector<String> filePaths){
		Vector<BufferedImage> imageLayers = new Vector<BufferedImage>();
		try{
			for(String path : filePaths){
				BufferedImage img = ImageIO.read(new File(path));
				imageLayers.add(img);
			}
		} catch(IOException exception){
			exception.printStackTrace();
		}
		
		return imageLayers;
	}
	
	// this method selects a random pattern and returns the directory for that pattern
	private String randomPattern(){
		Vector<String> patterns = new Vector<String>();
		File patternsDir = new File(PATTERNS_DIRECTORY);
		for(File current : patternsDir.listFiles()){
			if(current.isDirectory()){
				patterns.add(current.getPath()); // load all the pattern paths into a vector
			}
			else{
				System.out.println("Non folder found in the directory: "+PATTERNS_DIRECTORY);
				continue;
			}
		}
		
		Random generator = new Random();
		int randIndex = generator.nextInt(patterns.size());
		return patterns.get(PATTERN_NUMBER);  // will return random pattern when complete
	}
	
	// this method returns all file names in the given directory
    private  Vector<String> getFiles(String patternDir){
    	Vector<String> files = new Vector<String>();
    	File f = new File(patternDir);
    	for(File current : f.listFiles()){
    		if(current.isDirectory()){
    			System.out.println("Folder found in directory: "+patternDir);
    		}
    		else{
    			files.add(current.getPath());
    		}
    	}
    	return files;
    }
    
    // this method colours each image layer to a shade of the given colour
    private void colurImages(String colour) {
    	
    	int[][] colours = shader.getColourBands(colour, images.size());
    	
    	int counter = 0;
    	
    	for(BufferedImage currentImage : images){ //loop through layers
    		int width = currentImage.getWidth();
    		int height = currentImage.getHeight();
    		WritableRaster raster = currentImage.getRaster();
    		//change the colour of every pixel in the image but retain the transParency
    		for(int x = 0; x < width; x++){
    			for(int y = 0; y < height; y++){
    				int[] pixels = raster.getPixel(x, y, (int[]) null);
    				pixels[0] = colours[counter][0];
    				pixels[1] = colours[counter][1];
    				pixels[2] = colours[counter][2];
    				raster.setPixel(x, y, pixels);
    			}
    		}
    		counter++;
    	}
    }
}
