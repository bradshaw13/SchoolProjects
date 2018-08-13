package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rock extends GameObject{
	
	private int countdown;
	private boolean isFalling;
	private int toggleVibrate = 2;
	private boolean shouldRemove = false;

	public Rock(Block blocks[][]) {
		super(blocks);
	}

	@Override
	public void update() {
		checkFall();
		
		/*
		 * checkFall will test 4 possible cases (filled/not filled below | falling/notfalling)
		 */
		if ((this.isFalling) && (this.countdown >= 50) && (this.j < LevelComponentNew.BLOCKCOUNT)) {
			//TODO 500 should be a variable later
			this.blocks[this.i][this.j].setBlockType('o');
			this.j +=1;
			this.y += this.blockSize;
		}
		
		if ((this.isFalling) && (this.countdown < 50)) { 
			this.countdown += 1;
			this.x += this.toggleVibrate;
			this.toggleVibrate *= -1;
		}
		
		
//		if (this.blocks[this.i][this.j].getBlockType() != 'o') {
//			this.countdown = 0;
//		}		
//		
//		if (this.blocks[this.i][this.j + 1].getBlockType() == 'o') {
//			this.blocks[this.i][this.j].setBlockType('o');
//			this.blocks[this.i][this.j].setBlockType('r');
//		}

	}
	
	public void checkFall() {
		/* 4 Cases: 
		 * If the block below is open and it isn't currently falling, start the timer
		 * If the block below is open and is currently falling - do nothing, method above will handle
		 * If the block below is filled and is currently falling, reset the timer and isFalling = false
		 * If the block below is filled and isnt currently falling, do nothing
		 */
		
		if (this.j >= LevelComponentNew.BLOCKCOUNT-1) {
			this.isFalling = false;
			this.countdown = 0;
			this.shouldRemove=true;
			this.blocks[this.i][this.j].setBlockType('o');
			return; // you are at the bottom of the level
		}
		if ((this.blocks[this.i][this.j+1].getBlockType() == 'o') && (!this.isFalling)) { 
			this.countdown = 0;
			this.isFalling = true;
		} 
		
		else if ((this.blocks[this.i][this.j+1].getBlockType() == 'x') && (this.isFalling)) {
			this.countdown = 0;
			this.isFalling = false;
			this.shouldRemove = true;
			this.blocks[this.i][this.j].setBlockType('o');
		}
	}
	
	public boolean getIsFalling() {
		return this.isFalling;
	}
	
	public boolean getShouldRemove() {
		return this.shouldRemove;
	}
		
	
	@Override
	public void drawOn(Graphics2D g) {
		String fileName = "Sprites/rock.png";
		BufferedImage img;
		try {
			img = ImageIO.read(new File(fileName));
			g.drawImage(img, (int) this.x, (int) this.y, this.blockSize, this.blockSize, null);
		} catch (IOException e) {
			System.err.println("IO Exception caught in Rock");
		}
	}

	@Override
	public int getPointValue() {
		// TODO Auto-generated method stub.
		return 0;
	}

	@Override
	public void resetFileName() {
		// TODO Auto-generated method stub.
		
	}
	
	@Override
	public Double getBoundingBox() {
		return new Rectangle2D.Double(this.x,this.y,this.blockSize,this.blockSize);
	}

}
