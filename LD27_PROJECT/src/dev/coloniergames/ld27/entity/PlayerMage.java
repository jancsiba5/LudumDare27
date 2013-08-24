package dev.coloniergames.ld27.entity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.lwjgl.input.Mouse;

import dev.coloniergames.ld27.util.TextureData;
import dev.coloniergames.ld27.weapon.Projectile;
import dev.coloniergames.ld27.weapon.ProjectileType;

public class PlayerMage extends PlayerClass {

	Random random = new Random();

	public boolean flashR = true;
	public PlayerMage(Player p) {
		super(p);
		s1R = true;
		s2R = true;
		s3R = true;
		s4R = true;

		countdownTimer = new Timer();

		this.textureRow = 1;
	}

	boolean passiveReady = false;
	@Override
	public void attack() {

	}

	@Override
	public void act(int delta, int gameTimer) {


	}

	@Override
	public void jump() {

		//  ("JUMP MAGE");

		if(flashR) {
			float mX = Mouse.getX();
			float mY = h - Mouse.getY();

			player.moveTo(-player.playerCamera.position.x + mX, -player.playerCamera.position.y + mY);

			countdownTimer.schedule(new FlashCDTask(), 5000);
			flashR = false;
		}
	}

	@Override
	public void passive() {



	}

	@Override
	public void spell1() {

		if(s1R) {
			player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.FIREBALL));
			countdownTimer.schedule(new Spell1CDTask(), 2000);
			s1R = false;
		}

	}

	@Override
	public void spell2() {

		if(s2R) {
			player.health += 2;
			if(player.health >= 10) player.health = 10;
			countdownTimer.schedule(new Spell2CDTask(), 3000);
			s2R = false;
		}

	}

	@Override
	public void spell3() {

		if(s3R) {
			player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.LIGHTNING_BALL));
			countdownTimer.schedule(new Spell3CDTask(), 3000);
			s3R = false;
		}
		
	}

	@Override
	public void spell4() {


	}

	private class Spell1CDTask extends TimerTask {
		public void run() {
			s1R = true;
		}
	}

	private class Spell2CDTask extends TimerTask {
		public void run() {
			s2R = true;
		}
	}

	private class Spell3CDTask extends TimerTask {
		public void run() {
			s3R = true;
		}
	}

	private class Spell4CDTask extends TimerTask {
		public void run() {
			s4R = true;
		}
	}

	private class FlashCDTask extends TimerTask {
		public void run() {
			flashR = true;
		}
	}
}
