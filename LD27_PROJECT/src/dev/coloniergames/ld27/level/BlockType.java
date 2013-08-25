package dev.coloniergames.ld27.level;

import dev.coloniergames.ld27.util.TextureData;

public enum BlockType {

	BLOCK_GRASS(0, 0, false, 100), BLOCK_WALL(1, 0, true, 200), BLOCK_TREE(0, 1, true, 30),
	BLOCK_HOUSE_WALL(2, 0, true, 230), BLOCK_FLOOR(3, 0, false, 150), BLOCK_WOOD(4, 0, false, 20),
	BLOCK_SPACE_FLOOR(0, 2, false, 60), BLOCK_SPACE_WINDOW(1, 2, true, 70);
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
