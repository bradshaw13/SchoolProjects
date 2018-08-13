package NewPackageSameStuff;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Dimension2D;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
import javax.swing.Timer;

public class GameNew {
	public JFrame frame;
	private LevelComponentNew myComponent;
	public static final int FRAMESIZE = 600;
	public static final int FRAMESIZE_X = FRAMESIZE + 15;// 15 and 45 need to be removed, but it fits like this, we dont know why yet
	public static final int FRAMESIZE_Y = FRAMESIZE + 45;
	public static final int DELAY = 50;
	public Timer time;
	protected boolean isPaused = false;
	private TitlePage titlePage;

	public GameNew() {

		this.frame = new JFrame("Dig Dug: Level 1");
		this.frame.setSize(FRAMESIZE_X, FRAMESIZE_Y);
		
		runTitlePage();
		
		
		
	}

	private void runTitlePage() {
		TitlePage tPage = new TitlePage(this.frame);
		this.frame.add(tPage);
		this.frame.setVisible(true);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		TitleGameListener tgListener = new TitleGameListener(this.frame,tPage);
		
		this.frame.addKeyListener(tgListener);
		
		
	}

	public class TitleGameListener implements KeyListener {
		
		private JFrame frame;
		private boolean neverRunAgain;
		private TitlePage tPage;

		public TitleGameListener(JFrame frame, TitlePage tPage) {
			this.frame = frame;
			this.tPage = tPage;
			this.neverRunAgain = true;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub.
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_ENTER && this.neverRunAgain)		{
				//System.out.println("Enter is pressed!");
				this.neverRunAgain = false;
				this.frame.remove(this.tPage);
				this.frame.repaint();
				startGame();
			}
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub.
			
		}
		
	}
	public void startGame() {
		
		this.myComponent = new LevelComponentNew(this.frame);
		this.frame.add(this.myComponent);
		this.frame.repaint();
		

		GameAdvanceListener advanceListener = new GameAdvanceListener(this.myComponent);
		
		this.time = new Timer(DELAY, advanceListener);
		this.time.start();
		
		this.frame.addKeyListener(new KeyListener() { // we want to remove this
													// from anon, and make
													// subclass
		


			@Override
			public void keyTyped(KeyEvent e) {
				char ch = e.getKeyChar();
				//System.out.println("Key typed: "+ch);
				if ((ch == 'p') && (!GameNew.this.isPaused)) {
					System.out.println("SYSTEM PAUSED");
					GameNew.this.time.stop();
					GameNew.this.isPaused = true;
				} else if ((ch == 'p') && (GameNew.this.isPaused)) {
					System.out.println("SYSTEM RESUMED");
					GameNew.this.time.start();
					GameNew.this.isPaused = false;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				char ch = e.getKeyChar();
				//System.out.println("Key pressed: "+ch);
				
				
				if (ch == 'u') {
					System.out.println("The level is going Up");
					GameNew.this.myComponent.levelUp(GameNew.this.frame);
					GameNew.this.frame.repaint();
				} else if (ch == 'd') {
					GameNew.this.myComponent.levelDown(GameNew.this.frame);
					System.out.println("The level is going Down");
					GameNew.this.frame.repaint();
				} 
				
				if (e.getKeyCode()==KeyEvent.VK_SPACE){
					//System.out.println("PUMP DEACTIVATED");
					GameNew.this.myComponent.hero.deactivatePump();
					GameNew.this.frame.repaint();
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {// Do Nothing
				
				try {
					if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
						//System.out.println("The hero is going Right");
						GameNew.this.myComponent.hero.setDirection('e');
						GameNew.this.frame.repaint();
					} else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
						//System.out.println("The hero is going Left");
						GameNew.this.myComponent.hero.setDirection('w');
						GameNew.this.frame.repaint();
					} 
					else if (e.getKeyCode()==KeyEvent.VK_UP){
						//System.out.println("The hero is moving Up");
						GameNew.this.myComponent.hero.setDirection('n');
						GameNew.this.frame.repaint();
					}
					else if (e.getKeyCode()==KeyEvent.VK_DOWN){
						//System.out.println("The hero is moving Down");
						GameNew.this.myComponent.hero.setDirection('s');
						GameNew.this.frame.repaint();
					}
				
					
					if (e.getKeyCode()==KeyEvent.VK_SPACE){
						//System.out.println("PUMP ACTIVATED");
						GameNew.this.myComponent.hero.activatePump();
						GameNew.this.frame.repaint();
					}					
					else {
						throw new UnsupportedEncodingException();
					}
				} catch (UnsupportedEncodingException exception) {
					//System.out.println("The key does not have a function at this time.");
				}
			}
		});
		
	}

	/*
	 * Don't know if we will be able to use these methods or not...
	 */

	public JFrame getFrame() {
		return this.frame;
	}
	
	public Timer getTime(){
		return this.time;
	}

	public Dimension2D getFrameSize() {
		return new Dimension(this.frame.getWidth(), this.frame.getHeight());
	}
	

}
