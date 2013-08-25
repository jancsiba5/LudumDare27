package dev.coloniergames.ld27.entity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import dev.coloniergames.ld27.sfx.SoundPlayer;
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

		SoundPlayer.playSound("res/sound/javelin_shoot.wav", 0, 0, 0, 0, 0.65f, 2f, false);
		
		player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.ARROW, player));
		
	}

	@Override
	public void act(int delta, int gameTimer) {


	}

	@Override
	public void jump() {

		//  ("JUMP MAGE");

	}

	@Override
	public void passive() {



	}

	@Override
	public void spell1() {

		if(s1R) {
			player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.FIREBALL, player));
			countdownTimer.schedule(new Spell1CDTask(), 1000);
			s1R = false;
		}

	}

	@Override
	public void spell2() {

		if(s2R) {
			player.health += 2;
			if(player.health >= 10) player.health = 10;
			countdownTimer.schedule(new Spell2CDTask(), 2000);
			s2R = false;
		}

	}

	@Override
	public void spell3() {

		if(s3R) {
			player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.LIGHTNING_BALL, player));
			countdownTimer.schedule(new Spell3CDTask(), 2000);
			s3R = false;
		}

	}

	@Override
	public void spell4() {

		if(s4R) {
			player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.WATER_BALL, player));
			countdownTimer.schedule(new Spell4CDTask(), 3000);
			s4R = false;
		}


	}

	private class Spell1CDTask extends TimerTask {
		public void run() {
			s1R = true;


			SoundPlayer.playSound("res/sound/cdover.wav", 0, 0, 0, 0, false);
		}
	}

	private class Spell2CDTask extends TimerTask {
		public void run() {
			s2R = true;SoundPlayer.playSound("res/sound/cdover.wav", 0, 0, 0, 0, false);
		}
	}

	private class Spell3CDTask extends TimerTask {
		public void run() {
			s3R = true;
			SoundPlayer.playSound("res/sound/cdover.wav", 0, 0, 0, 0, false);
		}
	}

	private class Spell4CDTask extends TimerTask {
		public void run() {
			s4R = true;
			SoundPlayer.playSound("res/sound/cdover.wav", 0, 0, 0, 0, false);
		}
	}

	private class FlashCDTask extends TimerTask {
		public void run() {
			flashR = true;
			SoundPlayer.playSound("res/sound/cdover.wav", 0, 0, 0, 0, false);
		}
	}
}
