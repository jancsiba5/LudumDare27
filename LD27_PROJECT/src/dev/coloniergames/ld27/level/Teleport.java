package dev.coloniergames.ld27.level;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;

public class Teleport implements Constants {

	public int xStart, yStart, xDest, yDest;
	
	public Sprite tSprite, tDestSprite;
	
	public Teleport(int xStart, int yStart, int xDest, int yDest) {
		this.xStart = xStart;
		this.xDest = xDest;
		this.yDest = yDest;
		this.yStart = yStart;
		
		tSprite = new Sprite(xStart * BLOCK, yStart * BLOCK, TextureData.blockTextures[7][7], 0f, 1f, 0f);
		tDestSprite = new Sprite(xDest * BLOCK, yDest * BLOCK, TextureData.blockTextures[7][7], 1f, 0f, 0f);
	}
	
	public void teleport(Entity e) {
		int eX = (int) Math.floor(e.position.x / BLOCK);
		int eY = (int) Math.floor(e.position.y / BLOCK);
		
		if(eX == xStart && eY == yStart) {
			e.position.setPosition(xDest * BLOCK, yDest * BLOCK);
		}
	}
	
	public void draw() {
	
		tSprite.draw();
		tDestSprite.draw();
		
	}
}
