package dev.coloniergames.ld27.entity;

import java.util.TimerTask;

import dev.coloniergames.ld27.sfx.SoundData;
import dev.coloniergames.ld27.sfx.SoundPlayer;
import dev.coloniergames.ld27.weapon.Projectile;
import dev.coloniergames.ld27.weapon.ProjectileType;

public class PlayerWarrior extends PlayerClass {

	public PlayerWarrior(Player p) {
		super(p);
		
		this.textureRow = 0;
	}

	@Override
	public void attack() {
		
	}

	@Override
	public void act(int delta, int gameTimer) {
		
	}

	@Override
	public void jump() {
		
		System.out.println("JUMP WARRIOR");
		
		float distance = 3 * BLOCK;
		
		float rotation = player.rotation;
		
		float vX = (float) -Math.sin(rotation) * distance;
		float vY = (float) Math.cos(rotation) * distance;
		
		player.moveTo(player.position.x - vX, player.position.y - vY);
		
		// SoundData.jumpSound.play();
		
		SoundPlayer.playSound("res/sound/jump.wav", 0, 0, 0, 0, false);
		
	}

	@Override
	public void passive() {
		
	}

	@Override
	public void spell1() {
		
		player.projectiles.add(new Projectile(player.position.x, player.position.y, player.rotation, ProjectileType.JAVELIN, player));
		// SoundData.javelinShootSound.play();
		
		SoundPlayer.playSound("res/sound/javelin_shoot.wav", 0, 0, 0, 0, 0.65f, 2f, false);
		
	}

	@Override
	public void spell2() {
		
	}

	@Override
	public void spell3() {
		
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
	
}
