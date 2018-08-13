package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fygar extends Monster {
	private int fireLevel=0;
	private int fireTimer=0;
	private boolean canAttack = false;
	public static final int FYGAR_POINT_VALUE = 300; 

	public Fygar(Block[][] blocks, Hero hero) {
		super(blocks, hero);
	}

	public void canAttack() {
		if (((int)(Math.random()*100))==1 && !this.canAttack && !this.ghost && this.sizeLevel==0) {
			//System.out.println("PREPARE FOR ATTACK");
			this.fireTimer = 10;
			this.canAttack = true;
		}
	}
	
	@Override
	public void update() {
		canAttack();
		if (this.fireTimer < 0 && this.canAttack && this.fireLevel==3) {
			//System.out.println("Fire is complete!");
			this.fireTimer = 0;
			this.fireLevel = 0;
			this.canAttack = false;
			return;
		}
		if (this.fireTimer >= 0 && this.canAttack) {
			//System.out.println("Counting down..time: "+this.fireTimer);
			this.fireTimer--;
			return;
		} else if (this.fireTimer < 0 && this.canAttack) {
			//System.out.println("Emmitting fire!!! Level: "+this.fireLevel);
			this.fireLevel++;
			Attack();
			this.fireTimer=10;
			return;
		}
		
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
				if ((int) (Math.random() * this.ghostChance) == 4){
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

	@Override
	public int getPointValue() {
		return FYGAR_POINT_VALUE;
	}

	@Override
	public void Attack() {
		//System.out.println("Attack has been called");
		this.fileName = "Sprites/"+this.getClass().getSimpleName()+"/"+this.getClass().getSimpleName()+"Fire"+this.fireLevel+".png";
		//System.out.println("Filename: "+this.fileName);
	}
	
	@Override
	public void drawOn(Graphics2D g) {
		
		if (!this.ghost && !this.isAttack && !this.isInflated && fireLevel==0 && this.sizeLevel==0) {
			resetFileName();
		}
		BufferedImage img;
		try {
			if (fireLevel!=0) {
				img = ImageIO.read(new File(this.fileName));
				g.drawImage(img, (int) this.x, (int) this.y, this.blockSize * (this.fireLevel+1),
						this.blockSize, null);
			} else {
			img = ImageIO.read(new File(this.fileName));
			g.drawImage(img, (int) this.x, (int) this.y, (int) (this.blockSize * this.size),
					(int) (this.blockSize * this.size), null);
			}
		} catch (IOException e) {
			System.err.println("IO Exception caught in " + this.getClass().getSimpleName());
			System.err.println("Filename that caused issue: "+this.fileName);
		}
	}
	
	@Override
	public Rectangle2D.Double getBoundingBox() {
		if (this.fireLevel!=0) {
			Rectangle2D rect = new Rectangle2D.Double((int) this.x, (int) this.y, this.blockSize * (this.fireLevel+1),
					this.blockSize);
			//System.out.println("Fire is active!\nHere's the boundingBox: "+rect.toString());
			return (Double) rect;
		}
		return new Rectangle2D.Double(this.x, this.y, this.blockSize, this.blockSize);
	}
	

	

	

	

	

}
