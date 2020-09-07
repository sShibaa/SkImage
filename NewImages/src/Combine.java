import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Combine {
	
	private static final boolean debug = false;
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		//BufferedImage overlay = ImageIO.read(new File(path, "mycfaceskin" + ".png"));
		//MultiImage(path, "01f31ed698e5e2d9ad0ce23c119095d8ad3dd326", "15217747", 0,0);
		//01f31ed698e5e2d9ad0ce23c119095d8ad3dd326		mycfaceskin
		//imageToSkin(imageTWICE, path, null);
		Selector.open();
	}
	
	public static void imageToSkin(File image, File output, JProgressBar bar) throws IOException, URISyntaxException {
		if (debug) {JOptionPane.showMessageDialog(null, "Starting");}
		BufferedImage Wyoming = new BufferedImage(64,64, BufferedImage.TYPE_INT_ARGB);
		Graphics g = Wyoming.getGraphics();
		BufferedImage image1 = ImageIO.read(image);
		if (!(bar == null)) {bar.setValue(5);}
		//g.drawImage(crop(image1, 0, 0, 48, 16), -10, 0, null);
		//g.drawImage(crop(image1, 0, 0, 32, 32), 16, 12, null); //torso
		//g.drawImage(crop(image1, 0, 0, 32, 8), 4, 8, null); //head   		32
		
		
		/*//torso
		g.drawImage(crop(image1, 0, 0, 32, 32), 16, 12, null);
		if (!(bar == null)) {bar.setValue(25);}
		
		//ARMS
		g.drawImage(crop(image1, 12,20, 16,11), 0, 20, null); //TODO left arm
		if (!(bar == null)) {bar.setValue(34);}
		g.drawImage(crop(image1, 16,8, 16,16), 6, 40, null); //TODO right arm
		if (!(bar == null)) {bar.setValue(50);}*/
		
		if (debug) {JOptionPane.showMessageDialog(null, "Drawing heads");}
		//HEADS
		g.drawImage(crop(image1, 12,0, 8,8), 8, 8, null); //TODO head FRONT
		if (!(bar == null)) {bar.setValue(10);}
		g.drawImage(crop(image1, 4,24, 8,8), 0, 8, null); //TODO head LEFT
		if (!(bar == null)) {bar.setValue(15);}
		g.drawImage(crop(image1, 20,0, 8,8), 16, 8, null); //TODO head RIGHT
		if (!(bar == null)) {bar.setValue(20);}
		
		if (debug) {JOptionPane.showMessageDialog(null, "Drawing torso");}
		//TORSO
		g.drawImage(crop(image1, 12,8, 8,12), 20, 20, null); //TODO torso
		if (!(bar == null)) {bar.setValue(25);}
		
		
		if (debug) {JOptionPane.showMessageDialog(null, "Drawing arms");}
		//ARMS
		g.drawImage(crop(image1, 20,8, 8,12),36, 52, null); //TODO right arm
		if (!(bar == null)) {bar.setValue(34);}
		g.drawImage(crop(image1, 4,8, 8,12), 40, 20, null); //TODO left arm
		if (!(bar == null)) {bar.setValue(50);}
		
		if (debug) {JOptionPane.showMessageDialog(null, "Drawing legs");}
		//LEGS
		g.drawImage(crop(image1, 16,20, 4,12),20, 52, null); //TODO right leg
		g.drawImage(crop(image1, 12,20, 4,12),4, 20, null); //TODO left leg
		if (!(bar == null)) {bar.setValue(75);}
		
		
		
		/*g.drawImage(crop(image1, 8, 4, 4, 12), 20, 52, null); //right leg
		g.drawImage(crop(image1, 0, 4, 4, 12), 4, 20, null);*/
		
		//g.drawImage(image1, 0, 10, null);
		int width = Wyoming.getWidth();
		int height = Wyoming.getHeight();
		if (debug) {JOptionPane.showMessageDialog(null, "Grabbed wyoming width and height");}
		int c = new Color(132,0,255).getRGB();
		if (debug) {JOptionPane.showMessageDialog(null, "Made color c");}
		//if (debug) {JOptionPane.showMessageDialog(null, "Preparing purple.png: "+Combine.class.getResource("purple.png").getPath());}
		//File outline = new File(Combine.class.getResource("purple.png").getPath());
		//if (debug) {JOptionPane.showMessageDialog(null, "turned purple into file");}
		BufferedImage template = ImageIO.read(Combine.class.getResourceAsStream("assets/purple.png"));
		if (debug) {JOptionPane.showMessageDialog(null, "turned purple file into bufferedimage");}
		if (!(bar == null)) {bar.setValue(80);}
		//g.drawImage(template, 0, 0, null);
		for (int w = 0; w < width; w++) {
		   for (int h = 0; h < height; h++) {
			   if (template.getRGB(w, h) == c) {
				  // System.out.println("Pixel color grabbed");
		      Wyoming.setRGB(w, h, 0);
		      
			   } else {
				   if (debug) {System.out.println("Pixel color failed");
				   System.out.println(w + "    "+h);}
			   }
		   }}
		if (!(bar == null)) {bar.setValue(85);}
		g.dispose();

		
		if (Selector.replaceFiles) {
		ImageIO.write(Wyoming, "PNG", output);
		 if (debug) {System.out.println("output  "+output.toString());}
		} else {
			ImageIO.write(Wyoming, "PNG", new File(output.getPath().replaceAll(".png", "_skin.png")));
			 if (debug) {System.out.println("output  "+output.getPath().replaceAll(".png", "_skin.png"));}
			 if (!(bar == null)) {bar.setValue(90);}
		}
		// Save as new image
		if (!(bar == null)) {bar.setValue(95);}
		System.out.println("image converted");
		if (!(bar == null)) {bar.setValue(100);}
		JOptionPane.showMessageDialog(null, "Image processed");
		System.exit(200);
	}
	
	public static BufferedImage crop(BufferedImage image, int TposX, int TposY, int BposX, int BposY) {
		BufferedImage img = image.getSubimage(TposX, TposY, BposX, BposY); //fill in the corners of the desired crop location here
		BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		return copyOfImage; //or use it however you want
	}
	
	public static BufferedImage MultiBufferedImage(File path, String image1, String image2,int  x, int y, int sizeX, int sizeY) throws IOException {
		

		// load source images
		BufferedImage image = ImageIO.read(new File(path, image1 + ".png"));
		BufferedImage overlay = ImageIO.read(new File(path, image2 + ".png"));

		// create the new image, canvas size is the max. of both image sizes
		int w, h;
		if (sizeX == 0 && sizeY == 0) {
		 w = Math.max(image.getWidth(), overlay.getWidth());
		 h = Math.max(image.getHeight(), overlay.getHeight());
		} else {
			 w = Math.max(image.getWidth(), sizeX);
			 h = Math.max(image.getHeight(), sizeY);
		}
		BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

		// paint both images, preserving the alpha channels
		Graphics g = combined.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.drawImage(overlay, x, y, null);

		g.dispose();

		// Save as new image
		//ImageIO.write(combined, "PNG", new File(path, "combined.png"));
		return combined;
}
	
	public static void MultiImage(File path, String image1, String image2,int  x, int y, int sizeX, int sizeY) throws IOException {
		

				// load source images
				BufferedImage image = ImageIO.read(new File(path, image1 + ".png"));
				BufferedImage overlay = ImageIO.read(new File(path, image2 + ".png"));

				// create the new image, canvas size is the max. of both image sizes
				int w, h;
				if (sizeX == 0 && sizeY == 0) {
				 w = Math.max(image.getWidth(), overlay.getWidth());
				 h = Math.max(image.getHeight(), overlay.getHeight());
				} else {
					 w = Math.max(image.getWidth(), sizeX);
					 h = Math.max(image.getHeight(), sizeY);
				}
				BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

				// paint both images, preserving the alpha channels
				Graphics g = combined.getGraphics();
				g.drawImage(image, 0, 0, null);
				g.drawImage(overlay, x, y, null);

				g.dispose();

				// Save as new image
				ImageIO.write(combined, "PNG", new File(path, "combined.png"));
	}

}
