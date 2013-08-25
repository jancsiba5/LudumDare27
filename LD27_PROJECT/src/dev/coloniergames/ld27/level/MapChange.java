package dev.coloniergames.ld27.level;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Entity;
import dev.coloniergames.ld27.game.Game;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;

public class MapChange implements Constants {

	String newMap;
	
	int x, y;
	
	public Sprite mcSprite;
	public MapChange(int x, int y, String location) {
		
		this.x = x;
		this.y = y;
		this.newMap = location;
		
		mcSprite = new Sprite(x * BLOCK, y * BLOCK, TextureData.blockTextures[6][7], 1f, 0f, 0f);
		
	}
	
	public void changeMap(Entity e) {
		
		int eX = (int) Math.floor(e.position.x / BLOCK);
		int eY = (int) Math.floor(e.position.y / BLOCK);
		
		if(eX == x && eY == y) {
			System.out.println("SHOULD CHANGE MAP!!");
			Game.requireChange(newMap);
		}
		
	}
	
	public void draw() {
		
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
		mcSprite.draw();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
	}
}
