package Levels;

import Templates.GameInfo;
import Templates.GameStateManager;

public class Room extends Level {
	
	public Room[] side = new Room[4];

	public Room(GameStateManager gm, GameInfo in) {
		super(gm, in);
		init();
	}

	public void init(){
		
	}
	
	public void setsides(Room left, Room up, Room right, Room down) {
		side[0] = left;
		side[1] = up;
		side[2] = right;
		side[3] = down;
	}
}
