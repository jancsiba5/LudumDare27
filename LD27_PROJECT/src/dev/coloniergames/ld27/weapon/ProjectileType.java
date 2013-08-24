package dev.coloniergames.ld27.weapon;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.TextureData;

public enum ProjectileType implements Constants {

	FIREBALL(0, 2, 1000, 0.1f, BLOCK / 2, BLOCK / 2), LIGHTNING_BALL(1, 3, 1000, 1f, BLOCK / 2, BLOCK / 2);
	public int row, damage, maxTicks;
	public int[] textures;
	public float speed, width, height;
	
	ProjectileType(int tX, int damage, int maxTicks, float speed, float width, float height) {
		this.row = tX;
		this.damage = damage;
		this.maxTicks = maxTicks;
		
		this.width = width;
		this.height = height;
		
		this.speed = speed;
		
		this.textures = TextureData.projectileTextures[row];
	}
}
