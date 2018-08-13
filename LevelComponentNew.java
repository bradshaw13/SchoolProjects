package NewPackageSameStuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class LevelComponentNew extends JComponent {

	private Block[][] blocks;
	public Hero hero;
	public ArrayList<Monster> monsterList;
	public ArrayList<Rock> rockList;
	public JFrame frame;
	public boolean isFruitDropped;
	public Fruit fruit;
	public int numRocksFallen;
	public JLabel points;
	public Timer time;
	public boolean isGameOver;

	public static final int BLOCKCOUNT = 20;
	private int level = 1;
	public static final int MAXLEVELS = 10;

	public LevelComponentNew(JFrame frame) {
		this.monsterList = new ArrayList<>();
		this.rockList = new ArrayList<>();
		this.blocks = new Block[BLOCKCOUNT][BLOCKCOUNT];
		this.frame = frame;
		this.numRocksFallen = 0;
		this.isGameOver=false;
		
				
		this.points = new JLabel("Score: 0");
		this.points.setSize(new Dimension(this.frame.getWidth(), (int) (this.frame.getHeight()*0.1)));
		this.frame.add(this.points,BorderLayout.NORTH);
		this.frame.repaint();

		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[0].length; j++) {
				this.blocks[i][j] = new Block(Block.BLOCKSIZE * i, Block.BLOCKSIZE * j, Color.BLACK, this.level);

			}
		}

		this.hero = new Hero(this.blocks,this);

		buildBaseLevel();

	}

	public void updateState() {
		ArrayList<Rock> shouldRemoveRock = new ArrayList<>(); // TODO change
																// later
		ArrayList<Monster> shouldRemoveMonster = new ArrayList<>();
		this.hero.update();
		handleCollision();

		for (Monster m : this.monsterList) {

			m.update();

		}

		// System.out.println(this.rockList.toString());
		// System.out.println("Rocks before check shouldRemove: " +
		// this.rockList.size());
		for (Rock r : this.rockList) {
			if (r.getShouldRemove()) {
				// System.out.println("Rock " + r.toString() + " should be
				// removed");
				shouldRemoveRock.add(r);
				this.numRocksFallen++;

			} else {
				r.update();
			}
		}
		for (Rock r : shouldRemoveRock) { // TODO also can be expanded to a
											// larger
											// conglomerate of obj

			this.rockList.remove(r);

		}
		for (Monster m : this.monsterList) {
			if (m.getShouldRemove()) {
				shouldRemoveMonster.add(m);
			} else {
				m.update();
			}
		}
		for (Monster m : shouldRemoveMonster) {
			this.monsterList.remove(m);
			addPoints(m.getPointValue());
		}
		if (this.fruit != null) {
			if(this.fruit.getShouldRemove()) {
				this.fruit = null; //fruit has been eaten so remove it
			}
		}

		// System.out.println("Rocks REMAINING AFTER shouldRemove: " +
		// this.rockList.size());

		if (this.numRocksFallen >= 2 && this.isFruitDropped == false) {

			this.fruit = new Fruit(this.blocks, this.hero);
			this.isFruitDropped = true;
		}

		if (this.monsterList.size() == 0) {
			levelUp(this.frame);
		}
		this.repaint();
	}

	public void levelUp(JFrame frame) {
		if (this.level < MAXLEVELS) {
			this.rockList.clear();
			this.monsterList.clear();
			this.level++;
			this.isFruitDropped = false;
			buildBaseLevel();
			frame.setTitle("Dig Dug: Level " + this.level);
		} else {
			System.err.println("At max level, cant go any further up!");
		}
	}

	public void levelDown(JFrame frame) {
		if (this.level > 1) {
			this.rockList.clear();
			this.monsterList.clear();
			this.level--;
			this.isFruitDropped = false;
			buildBaseLevel();
			frame.setTitle("Dig Dug: Level " + this.level);
		} else {
			System.err.println("At base level, cannot go any further down!");
		}
	}

	public void buildBaseLevel() {

		Scanner s;

		try {

			FileReader file = new FileReader("Levels/Level" + this.level + ".txt");
			s = new Scanner(file);

			/*
			 * Each level is 20x20 so we'll use a double-for loop to get each
			 * value and build the level
			 * 
			 * @TODO For this test level... -Make everything blocks -Each of the
			 * different block types just need to be a different color -From
			 * there, it'll be easy to just add nice designs later
			 */
			for (int j = 0; j < BLOCKCOUNT; j++) {
				String line = s.nextLine();
				for (int i = 0; i < BLOCKCOUNT; i++) {
					this.blocks[i][j].setBlockType(line.charAt(i));
					this.blocks[i][j].setLevel(this.level);

					if (this.blocks[i][j].getBlockType() == '2') {
						this.blocks[i][j].setBlockType('o'); // overwrite the
																// block type
																// because its
																// technically
																// open
						Fygar newFygar = new Fygar(this.blocks, this.hero);
						newFygar.setStartPoint(this.blocks[i][j].getDrawPoint());
						newFygar.setIAndJ();
						this.monsterList.add(newFygar);
					}
					if (this.blocks[i][j].getBlockType() == '3') {
						this.blocks[i][j].setBlockType('o'); // overwrite the
																// block type
																// because its
																// technically
																// open
						Pooka newPooka = new Pooka(this.blocks, this.hero);
						newPooka.setStartPoint(this.blocks[i][j].getDrawPoint());
						newPooka.setIAndJ();
						this.monsterList.add(newPooka);
					}
					if (this.blocks[i][j].getBlockType() == 'r') {
						this.rockList.add(new Rock(this.blocks));
						this.rockList.get(this.rockList.size() - 1).setStartPoint(this.blocks[i][j].getDrawPoint());
						this.rockList.get(this.rockList.size() - 1).setIAndJ();
					}

					if (line.charAt(i) == '1') {
						this.hero.setStartPoint((this.blocks[i][j].getDrawPoint()));
						this.hero.setIAndJ();

						this.hero.resetFileName();
					}

				}
			}
			s.close();
		} catch (FileNotFoundException exception) {
			System.err.println("\t\tFile name, Level" + this.level + " not found :(");
			/*
			 * ^^This catch (along with the main function) will need to be
			 * changed to throw to the game most likely
			 */
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < this.blocks.length; i++) {
			for (int j = 0; j < this.blocks[0].length; j++) {
				this.blocks[i][j].drawOn(g2);
			}
		}

		this.hero.drawOn(g2);

		for (Rock index : this.rockList) {
			index.drawOn(g2);
		}
		for (Monster m : this.monsterList) {
			m.drawOn(g2);
		}
		if (this.fruit != null) {
			 this.fruit.drawOn(g2);
		}

	}

	private void handleCollision() {
		for (Monster m : this.monsterList) {
			collidesWith(m, this.monsterList);
			this.hero.getPump().collidesWith(m, this.monsterList);
			for (Rock r : this.rockList) {
				m.collidesWith(r);
			}
		}
		for (Rock r : this.rockList) {
			collidesWith(r, this.monsterList);
		}
		if (this.fruit != null) {
			collidesWith(this.fruit);
		}

	}
	//If the hero collides with the rock....
	public void collidesWith(Rock r, ArrayList<Monster> monsterList) {
		if (this.hero.getBoundingBox().intersects(r.getBoundingBox()) && r.getIsFalling()) {
			this.hero.loseLife();
			
			//System.out.println("HERO "+this.playerLives);
			if (this.hero.getPlayerLives() > 0) {

				
				this.hero.reset();
				this.hero.getPump().reset();
				for (Monster monster : monsterList) {
					monster.reset();
				}

			} else {
				//System.out.println("GAME IS OVER");
				if(this.isGameOver==false){
					
					this.frame.setVisible(false);
					JOptionPane.showMessageDialog(this.frame,"Game Over \nPoints: "+this.points.getText().substring(7));
					
					
					this.isGameOver=true;
					}
				
				
			}
		}

	}
	//If the hero collidesWith the monster.....
	public void collidesWith(Monster m, ArrayList<Monster> monsterList) {
		if (this.hero.getBoundingBox().intersects(m.getBoundingBox())) {
			this.hero.loseLife();
			
			if (this.hero.getPlayerLives() > 0) {
				
				//System.out.println("HERO DIES");

				this.hero.reset();
				this.hero.getPump().reset();
				for (Monster monster : monsterList) {
					monster.reset();
				}

			} else {
				//System.out.println("GAME OVER!");
				if(this.isGameOver==false){
				
				this.frame.setVisible(false);
				JOptionPane.showMessageDialog(this.frame,"Game Over \nPoints: "+this.points.getText().substring(7));
				
				
				this.isGameOver=true;
				}
				}
				
				
				
				

			}
		}

	
	
	//If the fruit is eaten.....
	public void collidesWith(Fruit fruit) {
		if(this.hero.getBoundingBox().intersects(fruit.getBoundingBox())){
			//Add score for picking up fruit
			fruit.removeFruit();
			addPoints(fruit.getPointValue());
						
		}
		
	}
	
	public void addPoints(int pointsToAdd) {
		int currentPoints = Integer.parseInt( this.points.getText().substring(7) );
		currentPoints += pointsToAdd;
		this.points.setText("Score: "+currentPoints);
	}

}



/*
 * To Save for Later!
 * 
 * if (blockType == 'x') { int green = 255-(30*(j/5))-(125/this.level); int red
 * = 75*(this.level/4); //int blue = (255/2)*(this.level/4);
 * this.blocks[i][j].setColor(new Color(red, green, 0));
 * 
 * } // this means the object is dirt
 * 
 * else if (blockType == 'o') { this.blocks[i][j].setBlockType(blockType);
 * this.blocks[i][j].setColor(Color.DARK_GRAY); } // this means the object is
 * open air else if (blockType == 'r') {
 * this.blocks[i][j].setBlockType(blockType);
 * this.blocks[i][j].setColor(Color.RED); } // this means the object is a rock
 * else if (blockType == '1') { this.blocks[i][j].setBlockType('o'); //its open,
 * just has the hero spot
 * this.hero.setHeroStart(this.blocks[i][j].getDrawPoint()); } // this means the
 * object is the HERO else if (blockType == '2') {
 * this.blocks[i][j].setBlockType(blockType);
 * this.blocks[i][j].setColor(Color.ORANGE); } // this means the object is a
 * Pooka else if (blockType == '3') { this.blocks[i][j].setBlockType(blockType);
 * this.blocks[i][j].setColor(Color.YELLOW); } // this means the object is a
 * Fygar else { throw new UnsupportedEncodingException(); }
 */