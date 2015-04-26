package Images;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Image {
	
	private Pattern pattern;
	private String primaryColor;
	private BufferedImage image;
	
	public Image(String color) {
		primaryColor = color;
		pattern =  new Pattern(color);
		image = layerImageFiles();
	}
	
	public void createImage(String createFile){
		try{
			ImageIO.write(image, "png", new File(createFile));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
    
	//this method combines all the image layers into one final image
    private  BufferedImage layerImageFiles(){
    	Vector<BufferedImage> layers = pattern.getImages();
    	BufferedImage layred = new BufferedImage(pattern.width(), pattern.height(), BufferedImage.TYPE_INT_ARGB);
    	int layerCount = 0;
    	Graphics imageGraphics = layred.getGraphics();
    	for(BufferedImage layer : layers){
    		if(layerCount == 0){ // if the layer is the base layer don't offset it
        		imageGraphics.drawImage(layer, 0 , 0, null);
    		}
    		else{ // if the layer isn't the base later do offset it
        		imageGraphics.drawImage(layer, 110, 0, null);
    		}
    		layerCount++;
    	}
    	return layred;
    }
   
}
