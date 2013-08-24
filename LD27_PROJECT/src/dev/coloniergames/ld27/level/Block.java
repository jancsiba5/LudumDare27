package dev.coloniergames.ld27.level;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.Vector;

public class Block implements Constants {

	Sprite blockSprite;
	Vector position;
	BlockType type;
	
	public Block(int x, int y, BlockType type) {
		this.blockSprite = new Sprite(x * BLOCK, y * BLOCK, type.texture);
		this.position = new Vector(x * BLOCK, y * BLOCK);
		this.type = type;
	}
	
	public void draw() {
		blockSprite.draw();
	}
	
}
