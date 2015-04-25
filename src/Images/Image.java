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
    
    private  BufferedImage layerImageFiles(){
    	Vector<BufferedImage> layers = pattern.getImages();
    	BufferedImage layred = new BufferedImage(pattern.width(), pattern.height(), BufferedImage.TYPE_INT_ARGB);
    	int layerCount = 0;
    	Graphics imageGraphics = layred.getGraphics();
    	for(BufferedImage layer : layers){
    		if(layerCount == 0){
        		imageGraphics.drawImage(layer, 0 , 0, null);
    		}
    		else{
        		imageGraphics.drawImage(layer, 110, 0, null);
    		}
    		layerCount++;
    	}
    	return layred;
    }
    
    
    //MAIN METHOD
    
    public static void main(String[] args) throws IOException {
    	Image img  = new Image("abc44c");
    	img.createImage("jersey1.png");
    }	
    
}
