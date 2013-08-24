package dev.coloniergames.ld27.level;

import dev.coloniergames.ld27.util.TextureData;

public enum BlockType {

	BLOCK_GRASS(0, 0, false, 100), BLOCK_WALL(1, 0, true, 200), BLOCK_HOUSE_WALL(2, 0, true, 230), BLOCK_FLOOR(3, 0, false, 150);
	public int textureX, textureY, texture, r;
	public boolean isSolid;
	BlockType(int textureX, int textureY, boolean solid, int r) {
		
		this.textureX = textureX;
		this.textureY = textureY;
		this.r = r;
		this.texture = TextureData.blockTextures[textureX][textureY];
		this.isSolid = solid;
		
	}
}
