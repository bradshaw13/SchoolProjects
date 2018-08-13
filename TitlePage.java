package NewPackageSameStuff;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TitlePage extends JComponent{
	public String fileName;
	private JFrame frame;
	
	public TitlePage(JFrame frame2) {
		this.fileName = "Sprites/TitlePage.png";
		this.frame = frame2;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(this.fileName));
			g.drawImage(img, 0, 0, this.frame.getWidth(), this.frame.getHeight(), null);
		} catch (IOException e) {
			System.err.println("IO Exception caught in TitlePage");
			System.err.println("Filename that caused issue: "+this.fileName);
		}
	}
	

}
