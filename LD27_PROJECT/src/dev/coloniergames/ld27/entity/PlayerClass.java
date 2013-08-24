package dev.coloniergames.ld27.entity;

import java.util.Timer;

import dev.coloniergames.ld27.Constants;

public abstract class PlayerClass implements Constants {
	
	public boolean s1R, s2R, s3R, s4R;
	public Timer countdownTimer;
	public int textureRow;

	public abstract void attack();
	public abstract void act(int delta, int gameTimer);
	public abstract void jump();
	public abstract void passive();
	public abstract void spell1();
	public abstract void spell2();
	public abstract void spell3();
	public abstract void spell4();
	
	Player player;
	
	public PlayerClass(Player p) {
		this.player = p;
	}
	
}
