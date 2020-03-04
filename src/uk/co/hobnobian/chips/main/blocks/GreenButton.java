package uk.co.hobnobian.chips.main.blocks;

import uk.co.hobnobian.chips.main.server.Block;
import uk.co.hobnobian.chips.main.server.Direction;
import uk.co.hobnobian.chips.main.server.Entity;
import uk.co.hobnobian.chips.main.server.GameVariables;

public class GreenButton extends Block {
	private static final long serialVersionUID = -4847454343260538041L;

	@Override
	public boolean onEnter(Entity e, Direction d, GameVariables vars) {
		if (vars.get("greenBlock") == 1) {
			vars.set("greenBlock", 0);
		}
		else {
			vars.set("greenBlock", 1);
		}
		return true;
	}

	@Override
	public boolean onLeave(Entity e, Direction d, GameVariables vars) {
		return true;
	}

	@Override
	public String getImage(GameVariables vars) {
		// TODO Auto-generated method stub
		return "greenButton.png";
	}

}