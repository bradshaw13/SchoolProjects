package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fruit extends GameObject{
	
	private boolean shouldRemove;
	public static final int FRUIT_POINT_VALUE = 150;

	public Fruit(Block[][] blocks, Hero hero) {
		super(blocks);
		System.out.println("Hero start point (from fruit): "+hero.getStartPoint());
		setStartPoint(hero.getStartPoint());
		this.shouldRemove = false;
		this.fileName =  "Sprites/pineapple.png";
	}

	@Override
	public void drawOn(Graphics2D g) {
		BufferedImage img;
		try {
			img = ImageIO.read(new File(this.fileName));
			g.drawImage(img, (int)this.x, (int)this.y, this.blockSize, this.blockSize, null);
		} catch (IOException e) {
			System.err.println("IO Exception caught in Fruit");
		}
	}

	@Override
	public int getI() {
		return (int) this.x / this.blockSize;
	}

	@Override
	public int getJ() {
		return (int) this.y / this.blockSize;
	}

	public boolean getShouldRemove() {
		return this.shouldRemove;
	}

	public void removeFruit() {
		this.shouldRemove = true;

	}

	@Override
	public int getPointValue() {
		return FRUIT_POINT_VALUE;
	}

	@Override
	public void resetFileName() {
		//filename is always the same
		
	}

	@Override
	public void update() {
		// fruit never updates?
	}

	@Override
	public Double getBoundingBox() {
		return new Rectangle2D.Double(this.x,this.y,this.blockSize,this.blockSize);
	}

}
