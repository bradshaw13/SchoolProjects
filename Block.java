package NewPackageSameStuff;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.UnsupportedEncodingException;

public class Block {

	private double x, y;
	public static final int BLOCKSIZE = GameNew.FRAMESIZE / LevelComponentNew.BLOCKCOUNT;
	public static final int NUMGRADIENTCOLORSFORDIRT = 4;
	private Color color;
	private char blockType;
	private int level;
	public static final Color[] dirtColors = new Color[14];

	public Block(int xpoint, int ypoint, Color color, int level) {
		this.color = color;
		this.x = xpoint; // these points should be fixed to the frame bc theyre
							// "pixels"
		this.y = ypoint;
		this.level = level;
		
		populateDirtColors();

	}

	public void setBlockType(char blockType) {
		this.blockType = blockType;
	}

	public char getBlockType() {
		return this.blockType;
	}

	public void setColor(Color newColor) {
		this.color = newColor;
	}

	public Point2D getDrawPoint() {
		return new Point2D.Double(this.x, this.y);
	}

	public void drawOn(Graphics2D g) {
		try {
			updateColorFromBlockType();
		} catch (UnsupportedEncodingException exception) {
			System.err.println("Error! An unsupported character type exists in the Level");
		}
		g.setColor(this.color);
		// if (this.color == Color.GREEN) {
		// draw(g);
		// } else {
		Rectangle2D block = new Rectangle2D.Double(this.x, this.y, BLOCKSIZE, BLOCKSIZE);
		g.fill(block);
		// }
	}

	// public void draw(Graphics2D g) {
	// int levelImage =
	// (this.level-1)+((((int)(this.y))/(GameNew.FRAMESIZE_Y/NUMGRADIENTCOLORSFORDIRT)));
	// String fileName = "DirtBlocks/" + levelImage +".jpg";
	// BufferedImage img;
	// try {
	// img = ImageIO.read(new File(fileName));
	// g.drawImage(img, (int)this.x, (int)this.y,BLOCKSIZE, BLOCKSIZE, null);
	// } catch (IOException e) {}
	// }

	public void updateColorFromBlockType() throws UnsupportedEncodingException {
		if (this.blockType == 'x') {
			int levelColor = (int)((645-this.y)/(645/3))+this.level;
			//System.out.println("Dirt Level Color: "+levelColor);
			setColor(dirtColors[levelColor]);

		}
		// this means the object is dirt

		else if (this.blockType == 'o') {
			setColor(Color.DARK_GRAY);
		}
		// this means the object is open air
		else if (this.blockType == 'r') {
			setColor(Color.DARK_GRAY);  //TODO add this to open instead
		} // this means the object is a rock
		else if (this.blockType == '1') {
			setColor(Color.DARK_GRAY);  //TODO add this to open instead
		} // this means the object is the HERO
		else if (this.blockType == '2') {
			setColor(Color.DARK_GRAY);
		} // this means the object is a Pooka
		else if (this.blockType == '3') {
			setColor(Color.DARK_GRAY);
		} else {// this means the object is a Fygar
			throw new UnsupportedEncodingException();
		}
	}
	
	public static void populateDirtColors() {
		dirtColors[0] = new Color(45,27,4);
		dirtColors[1] = new Color(75,49,16);
		dirtColors[2] = new Color(124,49,16);
		dirtColors[3] = new Color(181,49,16);
		dirtColors[4] = new Color(198,0,0);
		dirtColors[5] = new Color(221,51,0);
		dirtColors[6] = new Color(255,77,0);
		dirtColors[7] = new Color(255,185,0);
		dirtColors[8] = new Color(232,255,4);
		dirtColors[9] = new Color(134,255,0);
		dirtColors[10] = new Color(45,202,0);
		dirtColors[11] = new Color(36,190,67);
		dirtColors[12] = new Color(36,124,67);
		dirtColors[13] = new Color(45,80,156);
	}

	public void setLevel(int level2) {
		this.level = level2;
	}

}
