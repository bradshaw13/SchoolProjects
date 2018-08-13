package NewPackageSameStuff;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class GameObject {
	
	
	protected Point2D startPoint;
	protected double x,y; //location on the frame
	protected int i,j; //block location
	protected int blockSize = Block.BLOCKSIZE;
	protected String fileName;
	protected Block[][] blocks;

	public abstract int getPointValue();
	public abstract void drawOn(Graphics2D g);
	public abstract void resetFileName();
	public abstract void update();
	public abstract Rectangle2D.Double getBoundingBox();
	
	public GameObject(Block[][] blocks) {
		this.blocks = blocks;
	}
	
	public Point2D getStartPoint() {
		return this.startPoint;
	}
	
	public Point2D getXAndY() {
		return new Point2D.Double(this.x,this.y);
	}
	
	public void setStartPoint(Point2D startPoint) {
		this.x = startPoint.getX();
		this.y = startPoint.getY();
		this.startPoint = startPoint;
	}
	
	public void setIAndJ() {
		this.i = (int) (this.x / this.blockSize);
		this.j = (int) (this.y / this.blockSize);
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public void reset() {
		setStartPoint(getStartPoint());
		setIAndJ();
		resetFileName();
	}
	

}
