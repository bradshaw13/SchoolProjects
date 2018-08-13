package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public abstract class Monster extends GameObject{

	private boolean shouldRemove = false;
	protected int toggleVibrate = 1;
	public static final int MOVEDELAY = 20;
	protected int ghostChance = 20;
	protected int moveTimer = MOVEDELAY;
	protected double size = 1;
	protected boolean ghost = false;
	public final double SIZE_INCREMENT = 0.2;
	protected int sizeLevel = 0;
	public final double FINAL_SIZE = this.size+(this.SIZE_INCREMENT*4);

	private Hero hero;
	protected boolean isInflated;
	protected boolean isAttack;

	public Monster(Block blocks[][], Hero hero) {
		super(blocks);
		this.hero = hero;
		resetFileName();
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, this.blockSize, this.blockSize);
	}

	@Override
	public void update() {
		//System.out.println("UPDATING MONSTER "+this.getClass().getSimpleName());
		if ( this.moveTimer < 0) {
			this.moveTimer = MOVEDELAY;
			if (this.isInflated && this.sizeLevel<4) {
				//System.out.println(this.getClass().getSimpleName()+" size: "+this.size);
				this.size += this.SIZE_INCREMENT;
				this.sizeLevel++;
				this.fileName = "Sprites/"+this.getClass().getSimpleName()+"/"+this.getClass().getSimpleName()+"Inflate"+this.sizeLevel+".png";
			} else if (!this.isInflated && this.size > 1) {
				//System.out.println(this.getClass().getSimpleName()+" size: "+this.size);
				this.size -= this.SIZE_INCREMENT;
				this.sizeLevel--;
				this.fileName = "Sprites/"+this.getClass().getSimpleName()+"/"+this.getClass().getSimpleName()+"Inflate"+this.sizeLevel+".png";
			} else if (this.sizeLevel == 4) {
				markShouldRemove();
				
			} else {
				if ((int) (Math.random() * this.ghostChance) == 4 ){
					this.ghost = true;
					this.fileName = "Sprites/"+this.getClass().getSimpleName()+"/"+this.getClass().getSimpleName()+"Ghost.png";
					//System.out.println("goin ghost");
				}
				
				whereToMove();
			}
		}
		this.moveTimer--;
		this.y += this.toggleVibrate;
		this.toggleVibrate *= -1;
		//System.out.println("");
	}

	public void whereToMove() {
		/*
		 * First, lets find which of the four available ways we can move
		 */
		HashMap<Character, Integer> distToGoList = new HashMap<Character, Integer>();

		if (this.ghost == false) {
			// check right
			//System.out.println("checking where to go");
			if (getI() != this.blocks.length - 1) {
				if (this.blocks[getI() + 1][getJ()].getBlockType() != 'r'
						&& this.blocks[getI() + 1][getJ()].getBlockType() != 'x') {
					distToGoList.put('e', getDistFromHero(getI() + 1, getJ()));
					 //System.out.println("Monster -"+this.getClass().getSimpleName()+" can go East");
				}
			} // check left
			if (getI() > 0) {
				if (this.blocks[getI() - 1][getJ()].getBlockType() != 'r'
						&& this.blocks[getI() - 1][getJ()].getBlockType() != 'x') {
					distToGoList.put('w', getDistFromHero(getI() - 1, getJ()));
					 //System.out.println("Monster -"+this.getClass().getSimpleName()+" can go West");
				}
			} // check down
			if (getJ() != this.blocks.length - 1) {
				if (this.blocks[getI()][getJ() + 1].getBlockType() != 'r'
						&& this.blocks[getI()][getJ() + 1].getBlockType() != 'x') {
					distToGoList.put('s', getDistFromHero(getI(), getJ() + 1));
					 //System.out.println("Monster -"+this.getClass().getSimpleName()+" can go South");
				}
			} // check up
			if (getJ() > 0) {
				if (this.blocks[getI()][getJ()-1].getBlockType() != 'r'
						&& this.blocks[getI()][getJ()-1].getBlockType() != 'x') {
					distToGoList.put('n', getDistFromHero(getI(), getJ() - 1));
					 //System.out.println("Monster -"+this.getClass().getSimpleName()+" can go North");
				}
			}
		}

		if (this.ghost == true) {
			this.fileName = "Sprites/"+this.getClass().getSimpleName()+"/"+this.getClass().getSimpleName()+"Ghost.png";
			// check right
			if (getI() != this.blocks.length - 1) {
				distToGoList.put('e', getDistFromHero(getI() + 1, getJ()));
				// System.out.println("Monster -
				// "+this.getClass().getSimpleName()+" can go East");
			} // check left
			if (getI() > 0) {
				distToGoList.put('w', getDistFromHero(getI() - 1, getJ()));
				// System.out.println("Monster -
				// "+this.getClass().getSimpleName()+" can go West");
			} // check down
			if (getJ() != this.blocks.length - 1) {
				distToGoList.put('s', getDistFromHero(getI(), getJ() + 1));
				// System.out.println("Monster -
				// "+this.getClass().getSimpleName()+" can go South");
			} // check up
			if (getJ() > 0) {
				distToGoList.put('n', getDistFromHero(getI(), getJ() - 1));
				// System.out.println("Monster -
				// "+this.getClass().getSimpleName()+" can go North");
			}
		}

		// System.out.println(distToGoList.toString());
		char wayToGo = '?';
		int min = 1000000;
		for (char index : distToGoList.keySet()) {
			if (distToGoList.get(index) < min) {
				min = distToGoList.get(index);
				wayToGo = index;
			}
		}
//		if (this.olddist == 0) {
//			this.olddist = distToGoList.get(wayToGo);
//			this.currentdist = distToGoList.get(wayToGo);
//		} else {
//			this.olddist = this.currentdist;
//			this.currentdist = distToGoList.get(wayToGo);
//			if (this.olddist < this.currentdist) {
//				System.out.println("ghost?");
//				ghost = true;
//				hasbeendirt = false;
//			}
		
		// System.out.println("Monster - "+this.getClass().getSimpleName()+"
		// wants to go "+wayToGo);

		move(wayToGo);
		
		if (this.blocks[getI()][getJ()].getBlockType() == 'o' && this.ghost == true ) {
			this.ghost = false;
			
			//System.out.println("Monster "+this.getClass().getSimpleName()+" was in ghost mode, but hit a tunnel, now out of ghost mode");
		}

		/*
		 * Now, of those ways we can move, see which one is the closest to Hero
		 */

		// Recursive function that checks to see if the blocks around the
		// monster are dirt or air
	}

	public void setIsInflated(boolean b) {
		this.isInflated = b;
	}

	public void move(char wayToGo) {
		if (wayToGo == 'n') {
			this.j -= 1;
			this.y -= this.blockSize;
		} else if (wayToGo == 's') {
			this.j += 1;
			this.y += this.blockSize;
		} else if (wayToGo == 'w') {
			this.i -= 1;
			this.x -= this.blockSize;
		} else if (wayToGo == 'e') {
			this.i += 1;
			this.x += this.blockSize;
		} else {
			System.err.println("Error! Monster recieved unknown move command");
		}

	}

	private int getDistFromHero(int i, int j) {
		return Math.abs(i - this.hero.getI()) + Math.abs(j - this.hero.getJ()); // computes
																				// the
																				// Manhattan
																				// distance
																				// from
																				// hero
	}

	@Override
	public void drawOn(Graphics2D g) {
		
		if (!this.ghost && !this.isAttack && !this.isInflated) {
			resetFileName();
		}
		BufferedImage img;
		try {
			img = ImageIO.read(new File(this.fileName));
			g.drawImage(img, (int) this.x, (int) this.y, (int) (this.blockSize * this.size),
					(int) (this.blockSize * this.size), null);
		} catch (IOException e) {
			System.err.println("IO Exception caught in " + this.getClass().getSimpleName());
			System.err.println("Filename that caused issue: "+this.fileName);
		}
	}

	public abstract void Attack();

	public void markShouldRemove() {

		this.shouldRemove = true;
	}
	
	public boolean getIsInflated() {
		return this.isInflated;
	}

	public boolean getShouldRemove() {
		// TODO Auto-generated method stub.
		return this.shouldRemove;
	}

	public void collidesWith(Rock r) {
		if ((r.getJ()/this.blockSize) != r.getStartPoint().getY() && r.getIsFalling() && r.getI() == getI() && r.getJ() == getJ()) {
			//System.out.println("Monster has been killed by rock");
			markShouldRemove();
		}
	}
	
	@Override
	public void resetFileName() {
		this.fileName="Sprites/" + this.getClass().getSimpleName() + "/"+this.getClass().getSimpleName()+".png";
	}
	
	@Override
	public void reset() {
		super.reset();
		this.size = 1;
	}

	public boolean isGhost() {
		return this.ghost;
	}

}
