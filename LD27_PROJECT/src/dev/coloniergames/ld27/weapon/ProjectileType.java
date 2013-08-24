package dev.coloniergames.ld27.weapon;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.util.TextureData;

public enum ProjectileType implements Constants {

	FIREBALL(0, 0, 2, 1000, 1f, BLOCK / 2, BLOCK / 2);
	public int textureX, textureY, texture, damage, maxTicks;
	public float speed, width, height;
	
	ProjectileType(int tX, int tY, int damage, int maxTicks, float speed, float width, float height) {
		this.textureX = tX;
		this.textureY = tY;
		this.damage = damage;
		this.maxTicks = maxTicks;
		
		this.width = width;
		this.height = height;
		
		this.speed = speed;
		
		this.texture = TextureData.projectileTextures[tX][tY];
	}
}
