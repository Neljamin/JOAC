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
	
	public BufferedImage getImage(){
		return image;
	}
    
    private  BufferedImage layerImageFiles(){
    	Vector<BufferedImage> layers = pattern.getImages();
    	BufferedImage layred = new BufferedImage(pattern.width(), pattern.height(), BufferedImage.TYPE_INT_ARGB);
    	
    	Graphics imageGraphics = layred.getGraphics();
    	for(BufferedImage layer : layers){
    		imageGraphics.drawImage(layer, 0 , 0, null);
    	}
    	return layred;
    }
    
    
    //MAIN METHOD
    
    public static void main(String[] args) throws IOException {
    	Image img  = new Image("");
    	ImageIO.write(img.getImage(), "png", new File("Test.png"));// this will create an image file
    }	
    
}
