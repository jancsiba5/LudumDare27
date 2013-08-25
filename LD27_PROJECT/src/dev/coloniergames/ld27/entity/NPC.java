package dev.coloniergames.ld27.entity;

import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;

public class NPC extends Entity {

	static Sprite npcSprite = new Sprite(0, 0, TextureData.entityTextures[1][0], BLOCK / 1.5f, BLOCK / 1.5f);
	
	public Sprite uniqueSprite = new Sprite(0, 0, TextureData.entityTextures[1][0], BLOCK / 1.5f, BLOCK / 1.5f);
	public NPC(float x, float y) {
		super(x, y, npcSprite, 10);
		
		setSprite(uniqueSprite);
		
	}

	@Override
	public void act(int delta) {
		// TODO Auto-generated method stub
		
	}

	
}
