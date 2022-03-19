package TheHelpers;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceLoader {
	private static HashMap<String,Image> resources = new HashMap<String,Image>();
	public static Image loadResource(String filePath){
		
		
		Image img = resources.get(filePath);
		if (img==null) {
			try {
				img = ImageIO.read(new File(filePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resources.put(filePath, img);
		}
		
		return img;
	}
	
	
	public static Image loadResourceOfSize(String filePath,int width, int height) {
		Image img = loadResource(filePath);
		//Load it like normal. All base sizes are created by default
		//Image img = resources.get(filePath + "-w" + width + "h" + height);
		
		
		Image results = resources.get(filePath + "-w" + width + "h" + height);
		
		if (results == null) {
			Image scaledImage = img.getScaledInstance(width, height, 1);
			resources.put(filePath + "-w" + width + "h" + height, img);
			results = scaledImage;
		}
		
		
		
		return results;
	}
}
