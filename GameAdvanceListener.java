package NewPackageSameStuff;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameAdvanceListener implements ActionListener {
	
	private LevelComponentNew levelComponent;
	private boolean isPaused;
	
	public GameAdvanceListener(LevelComponentNew levelComponent) {
		this.levelComponent = levelComponent;
		// TODO Auto-generated constructor stub.
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isPaused)
		advanceOneTick();
		// TODO Auto-generated method stub.

	}
	
	public void advanceOneTick(){
		//System.out.println("Current Time " + System.currentTimeMillis());
		
		
		this.levelComponent.updateState();
		this.levelComponent.revalidate();
		this.levelComponent.repaint();
	}

}
