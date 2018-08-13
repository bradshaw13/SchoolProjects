package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hero extends GameObject{

	private char moveDirection, lookDirection;

	private int playerLives;
	private Pump pump;

	private LevelComponentNew component;

	/*
	 * for now, our "Hero will be a dark grey rectangle" bear with us yeah
	 */

	public Hero(Block[][] blocks, LevelComponentNew levelComponentNew) {
		super(blocks);
		this.moveDirection = '?';
		this.blocks = blocks;
		this.lookDirection = '?';

		this.playerLives = 3;

		this.fileName = "Sprites/Hero/LookMiddle.png";

		this.pump = new Pump(blocks, this);
		this.component = levelComponentNew;
	}
	
	public int getI(){
		return this.i;
	}
	public int getJ(){
		return this.j;
	}
	
	
	public int getPlayerLives(){
		return this.playerLives;
	}
	
	public void loseLife(){
		this.playerLives--;
	}
	@Override
	public void update() {
		
//		if (this.lookDirection == '?') // TODO NEED TO WORK ON
//
		//System.out.println("HERO KEY PRESSED: " + this.moveDirection); 
	
		if (this.moveDirection == 'n') {

			moveUp();

		} else if (this.moveDirection == 'w') {

			moveLeft();

		} else if (this.moveDirection == 's') {

			moveDown();

		} else if (this.moveDirection == 'e') {

			moveRight();
		}
		this.moveDirection = '!';
		//System.out.println("Hero Location: "+getDrawPoint().toString());
		this.pump.update();
	}
	
	

	public void moveRight() {
		/*
		 * This section of code will determine how the sprite should look
		 */           
		this.fileName = "Sprites/Hero/LookRight.png";
		/*
		 * This section of code will actually move the sprite
		 */
		//System.out.println("IS Moving Right");
		if (this.x != (LevelComponentNew.BLOCKCOUNT - 1) * this.blockSize) {
			if (this.blocks[this.i + 1][this.j].getBlockType() == 'r') {
				return;
			}
			this.x = this.x + this.blockSize;
			if (this.i != 19) {
				if (this.blocks[this.i + 1][this.j].getBlockType() == 'x') {
					this.blocks[this.i + 1][this.j].setBlockType('o');
					this.component.addPoints(10);
				}
			}
			setIAndJ();

		}
	}

	public void moveLeft() {
		/*
		 * This section of code will determine how the sprite should look
		 */
		this.fileName = "Sprites/Hero/LookLeft.png";
		/*
		 * This section of code will actually move the sprite
		 */
		if (this.x != 0) {
			if (this.blocks[this.i - 1][this.j].getBlockType() == 'r') {
				return;
			}
			this.x = this.x - this.blockSize;
			if (this.i != 0) {
				if (this.blocks[this.i - 1][this.j].getBlockType() == 'x') {
					this.blocks[this.i - 1][this.j].setBlockType('o');
					this.component.addPoints(10);
				}
			}
			setIAndJ();
		}
	}

	public void moveUp() {
		/*
		 * This section of code will determine how the sprite should look
		 */
		// //System.out.println("Look Direction: " + this.lookDirection +
		// "\nMoveUpCalled");
		String currentDirection = this.fileName.substring(17);
		//System.out.println(currentDirection);
		if (currentDirection.equals("Right.png")) {
			this.fileName = "Sprites/Hero/LookRightUp.png";
		} else if (currentDirection.equals("Left.png")) {
			this.fileName = "Sprites/Hero/LookLeftUp.png";
			// we need to find which side his feet were on
		} else if (this.fileName.substring(17).equals("RightDn.png")) {
			this.fileName = "Sprites/Hero/LookLeftUp.png";
		} else if (this.fileName.substring(17).equals("LeftDn.png")) {
			this.fileName = "Sprites/Hero/LookRightUp.png";
		} else if (currentDirection.equals("Middle.png")) {
			this.fileName = "Sprites/Hero/LookRightUp.png";
		} else {
			// System.err.println("Error in filename! Inner'if' statement of
			// MoveUp");

		}
		/*
		 * This section of code will actually move the sprite
		 */
		if (this.y != 0) {
			if (this.blocks[this.i][this.j - 1].getBlockType() == 'r') {
				return;
			}
			this.y = this.y - this.blockSize;
			if (this.j != 0) {
				if (this.blocks[this.i][this.j - 1].getBlockType() == 'x') {
					this.blocks[this.i][this.j - 1].setBlockType('o');
					this.component.addPoints(10);
				}
			}
			setIAndJ();
		}
	}

	public void moveDown() {
		/*
		 * This section of code will determine how the sprite should look
		 */
		// //System.out.println("Look Direction: " + this.lookDirection +
		// "\nMoveUpCalled");
		String currentDirection = this.fileName.substring(17);
		//System.out.println(currentDirection);
		if (currentDirection.equals("Right.png")) {
			this.fileName = "Sprites/Hero/LookRightDn.png";
		} else if (currentDirection.equals("Left.png")) {
			this.fileName = "Sprites/Hero/LookLeftDn.png";
			// we need to find which side his feet were on
		} else if (currentDirection.equals("RightUp.png")) {
			this.fileName = "Sprites/Hero/LookLeftDn.png";
		} else if (currentDirection.equals("LeftUp.png")) {
			this.fileName = "Sprites/Hero/LookRightDn.png";
		} else if (currentDirection.equals("Middle.png")) {
			this.fileName = "Sprites/Hero/LookLeftDn.png";
		} else {
			// System.err.println("Error in filename! Inner'if' statement of
			// MoveUp");
			// testing for errors
		}
		/*
		 * This section of code will actually move the sprite
		 */
		if (this.y != (LevelComponentNew.BLOCKCOUNT - 1) * this.blockSize) {
			if (this.blocks[this.i][this.j + 1].getBlockType() == 'r') {
				return;
			}
			this.y = this.y + this.blockSize;
			if (this.j != 19) {
				if (this.blocks[this.i][this.j + 1].getBlockType() == 'x') {
					this.blocks[this.i][this.j + 1].setBlockType('o');
					this.component.addPoints(10);
				}
			}
			setIAndJ();
		}

	}

	public void activatePump() {
		boolean canActivate = false;
		//need to see if pump can activate (is the space in front of me dirt?)
		if (this.lookDirection == 'n') {
			if (this.blocks[this.getI()][this.getJ()-1].getBlockType() != 'x' && this.blocks[this.getI()][this.getJ()-1].getBlockType() != 'r') {
				canActivate = true;
			}
		} else if (this.lookDirection == 's') {
			if (this.blocks[this.getI()][this.getJ()+1].getBlockType() != 'x' && this.blocks[this.getI()][this.getJ()+1].getBlockType() != 'r') {
				canActivate = true;
			}
		} else if (this.lookDirection == 'e') {
			if (this.blocks[this.getI()+1][this.getJ()].getBlockType() != 'x' && this.blocks[this.getI()+1][this.getJ()].getBlockType() != 'r') {
				canActivate = true;
			}
		} else if (this.lookDirection == 'w') {
			if (this.blocks[this.getI()-1][this.getJ()].getBlockType() != 'x' && this.blocks[this.getI()-1][this.getJ()].getBlockType() != 'r') {
				canActivate = true;
			}
		} 
		if (canActivate) {
		if (!this.fileName.contains("Pump")) {
			this.fileName = this.fileName.substring(0, this.fileName.length() - 4); // remove
																				// ".png"
			//System.out.println(this.fileName);
			this.fileName += "Pump.png";
			// //System.out.println(this.fileName);
		} else {
			this.pump.extendPump();
		}
		}
	}
	

	public void deactivatePump() {
		this.fileName = this.fileName.substring(0, this.fileName.length() - 8);
		// //System.out.println(this.fileName);
		this.fileName += ".png";
		// //System.out.println(this.fileName);
		this.pump.retractPump();
		this.pump.update();
	}

	@Override
	public void drawOn(Graphics2D g) {
		this.pump.drawOn(g);
		BufferedImage img;
		try {
			img = ImageIO.read(new File(this.fileName));
			g.drawImage(img, (int) this.x, (int) this.y, this.blockSize, this.blockSize, null);
		} catch (IOException e) {
//			System.err.println("IO Exception caught in Hero");
			resetFileName();
			drawOn(g);
		}
	}

	public void setDirection(char direction2) {
		this.moveDirection = direction2;
		this.lookDirection = direction2;
	}

	
	
	
	public Pump getPump() {
		return this.pump;
	}

	

	public char getLookDirection() {
		return this.lookDirection;
	}

	public Point2D getDrawPoint() {
		return new Point2D.Double(this.x, this.y);
	}

	

	@Override
	public int getPointValue() {
		// Hero never does points
		return 0;
	}

	@Override
	public void resetFileName() {
		this.fileName="Sprites/Hero/LookMiddle.png";
		this.lookDirection = '?';
		this.moveDirection = '?';
	}

	@Override
	public Double getBoundingBox() {
		return new Rectangle2D.Double(this.x,this.y,this.blockSize,this.blockSize);
	}

}
