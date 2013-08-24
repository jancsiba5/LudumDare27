package dev.coloniergames.ld27.util;

import dev.coloniergames.ld27.gfx.SpriteSheet;

public class TextureData {

	public static int[][] blockTextures = new int[8][8];
	public static int[][] projectileTextures = new int[8][8];
	public static int[][] entityTextures = new int[8][8];
	private static SpriteSheet blockSheet = new SpriteSheet("res/texture/blockSheet.png", 32, 32, 8, 8);
	private static SpriteSheet projectileSheet = new SpriteSheet("res/texture/projSheet.png", 32, 32, 8, 8);
	private static SpriteSheet entitySheet = new SpriteSheet("res/texture/entitySheet.png", 32, 32, 8, 8);
	static {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				blockTextures[i][j] = TextureLoader.loadTexture(blockSheet, i, j);
				projectileTextures[i][j] = TextureLoader.loadTexture(projectileSheet, i, j);
				entityTextures[i][j] = TextureLoader.loadTexture(entitySheet, i, j);
			}
		}
	}
	public static int testTexture = TextureLoader.loadTexture("res/texture/test.png");
	
	public static int healthBar = TextureLoader.loadTexture("res/texture/healthbar.png");
	public static int healthIcon = TextureLoader.loadTexture("res/texture/healthicon.png");
	
	public static int warriorText = TextureLoader.loadTexture("res/texture/warriorText.png");
	public static int mageText = TextureLoader.loadTexture("res/texture/mageText.png");
	
}
