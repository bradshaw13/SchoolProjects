package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Pump extends GameObject {
	// lil pump
	private int pumpLevel;
	private Hero hero;
	private int counter = 3;
	private char lookDirection;
	private Rectangle2D.Double boundingBox;

	public Pump(Block[][] blocks, Hero hero) {
		super(blocks);
		this.hero = hero;
		this.pumpLevel = 0;
		this.boundingBox = new Rectangle2D.Double(this.x, this.y, this.x + this.blockSize, this.y + this.blockSize);
	}

	public int getPumpLevel() {
		return this.pumpLevel;
	}

	@Override
	public void update() {
		this.lookDirection = this.hero.getLookDirection();
		setDrawPoint(this.hero.getDrawPoint(), this.lookDirection);
		setIAndJ();
		//System.out.println("Pump Location: " + this.x + ", " + this.y);
	}

	public void extendPump() {
		//System.out.println("Pump Level:" + this.pumpLevel);
		if (this.counter < 0) {
			if (this.pumpLevel < 3) {
				this.pumpLevel++;
				this.counter = 3;
			}
		} else {
			this.counter--;
		}
	}

	public void retractPump() {
		this.pumpLevel = 0;
	}

	public void setDrawPoint(Point2D heroStart, char c) {
		if (c == 'n') {
			this.y = heroStart.getY() - this.blockSize;
			this.x = heroStart.getX();
		} else if (c == 's') {
			this.y = heroStart.getY() + this.blockSize;
			this.x = heroStart.getX();
		} else if (c == 'e') {
			this.x = heroStart.getX() + this.blockSize;
			this.y = heroStart.getY();
		} else if (c == 'w') {
			this.x = heroStart.getX() - this.blockSize;
			this.y = heroStart.getY();
		} else {
			this.x = heroStart.getX();
			this.y = heroStart.getY();
		}
		setIAndJ();
		if (this.pumpLevel == 0) {
			this.boundingBox = new Rectangle2D.Double(this.x, this.y, this.blockSize, this.blockSize);
		}
	}

	public Rectangle2D.Double getBoundingBox() {
		return this.boundingBox;
	}

	@Override
	public void drawOn(Graphics2D g) {
		// //System.out.println("Pump Level at Draw: "+this.pumpLevel);
		int multiplyerx = 1;
		int multiplyery = 1;
		// //System.out.println("Look Direction: "+this.lookDirection);

		if (this.pumpLevel > 0) {
			BufferedImage img;
			try {
				//System.out.println("Look Direction in Draw: " + this.lookDirection);
				//System.out.println(this.pumpLevel);
				String fileName = "Sprites/Hero/Pump/Pump" + this.pumpLevel + this.lookDirection + ".png";
				// //System.out.println(fileName);
				img = ImageIO.read(new File(fileName));
				// //System.out.println("Draw Inputs: "+(int) this.x+" "+ (int)
				// this.y+" "+ this.blockSize*multiplyerx+" "+
				// this.blockSize*multiplyery);
				int width,height;
				if (this.lookDirection == 'n') {
					width = this.blockSize;
					height = this.blockSize*this.pumpLevel;
					int drawy = (int) this.y-this.blockSize*(this.pumpLevel-1);
					g.drawImage(img, (int) this.x, drawy ,width,height, null);
					this.boundingBox = new Rectangle2D.Double(this.x,drawy,width,height);
				}
				else if (this.lookDirection == 's') {
					width = this.blockSize;
					height = this.blockSize*this.pumpLevel;
					g.drawImage(img, (int) this.x, (int) this.y ,width,height, null);
					this.boundingBox = new Rectangle2D.Double(this.x,this.y,width,height);
				}
				else if (this.lookDirection == 'e') {
					width = this.blockSize*this.pumpLevel;
					height = this.blockSize;
					g.drawImage(img, (int) this.x, (int) this.y ,width,height, null);
					this.boundingBox = new Rectangle2D.Double(this.x,this.y,width,height);
				} 
				else if (this.lookDirection == 'w') {
					width = this.blockSize*this.pumpLevel;
					height = this.blockSize;
					int drawx = (int) this.x-this.blockSize*(this.pumpLevel-1);
					g.drawImage(img, drawx, (int) this.y ,width,height, null);
					this.boundingBox = new Rectangle2D.Double(drawx,this.y,width,height);
					//System.out.println("Hero :"+this.hero.getDrawPoint().toString());
					//System.out.println("Width: "+width+"\nHeight: "+height+"\nBoundingBox:"+this.boundingBox);
				} 
				else if (this.lookDirection != '?') {
					System.err.println("Error! Invalid look direction given to pump");
				}

			} catch (IOException e) {
				System.err.println("IO Exception caught in Pump");
			}
			g.draw(this.boundingBox);
		}
	}

	public void collidesWith(Monster m, ArrayList<Monster> monsterList) {
		if (m.getBoundingBox().intersects(getBoundingBox()) && this.pumpLevel > 0 && !m.isGhost()) {
			m.setIsInflated(true); 
			//System.out.println("Monster BoundBox: "+m.getBoundingBox().toString());
			//System.out.println("Pump BoundBox: "+this.getBoundingBox().toString());
		} else if (m.getIsInflated()) {
			m.setIsInflated(false);
		}

	}

	@Override
	public void reset() {
		retractPump();
		this.lookDirection = this.hero.getLookDirection();
		setDrawPoint(this.hero.getDrawPoint(), this.lookDirection);
		setIAndJ();
	}

	@Override
	public int getPointValue() {
		return 0;// pump never removed
	}

	@Override
	public void resetFileName() {
		this.fileName = "Pump1e.png";
	}

}
