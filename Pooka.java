package NewPackageSameStuff;

public class Pooka extends Monster {
	
	public static final int POOKA_POINT_VALUE = 200;

	public Pooka(Block[][] blocks, Hero hero) {
		super(blocks, hero);
		this.ghostChance = this.ghostChance/2; //pooka will go ghost 2 times more likely
	}

	@Override
	public void Attack() {
		// TODO Pooka doesn't attack
	}

	@Override
	public int getPointValue() {
		return POOKA_POINT_VALUE;
	}

	

	

}
