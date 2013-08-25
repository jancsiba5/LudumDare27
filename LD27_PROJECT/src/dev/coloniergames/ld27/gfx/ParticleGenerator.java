package dev.coloniergames.ld27.gfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.util.Vector;

public class ParticleGenerator {

	public Vector position;
	int particleCount = 0, maxParticles;
	public int ticksAlive;
	public int ticksMax;
	
	float GRAVITY = 1f;
	
	float r, g, b;
	
	List<Particle> particles = new ArrayList<Particle>();
	List<Particle> particlesToRemove = new ArrayList<Particle>();
	
	Random random = new Random();
	
	public ParticleGenerator(float x, float y, float r, float g, float b, int ticksMax, int maxParticles) {
		
		this.position = new Vector(x, y);
		
		this.r = r;
		this.g = g;
		this.b = b;
		
		this.ticksMax = ticksMax;
		this.maxParticles = maxParticles;
		
	}
	
	public void tick() {
		
		particlesToRemove.clear();
		
		if(particleCount < maxParticles) {
			particles.add(new Particle(position.x, position.y, r, g, b, random.nextFloat() * (float) Math.toRadians(360), random.nextInt(10), random.nextInt(5000)));

			// particleCount = particles.size();
		}
		
		for(Particle p : particles) {
			p.vX -= p.vX / 100f;
			p.vY -= p.vY / 100f;
			
			p.update();
			
			if(p.ticksAlive >= p.ticksMax) particlesToRemove.add(p);
		}
		
		particles.removeAll(particlesToRemove);
		
		particleCount = particles.size();
		
		System.out.println("Particle count: " + particleCount);
		
		ticksAlive++;
	}
	
	public void draw() {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		for(Particle p : particles) {
			GL11.glPointSize(p.size);
			GL11.glBegin(GL11.GL_POINTS);
			p.draw();
			GL11.glEnd();
		}
		
		GL11.glPointSize(1);
	}
	
	public class Particle {
		
		public int ticksAlive, ticksMax;
		public float x, y, vX, vY, r, g, b, size, angle;
		
		public Particle(float x, float y, float r, float g, float b, float angle, float size, int ticksMax) {
			this.x = x;
			this.y = y;
			
			this.vX = (float) -Math.sin(angle) * GRAVITY;
			this.vY = (float) Math.cos(angle) * GRAVITY;
			
			this.r = r;
			this.g = g;
			this.b = b;
			this.angle = angle;
			this.size = size;
			
			this.ticksMax = ticksMax;
		}
		
		public void update() {
			
			this.x += vX;
			this.y += vY;
			
			// System.out.println(vX + "  " + vY + "   |   " + x + "  " + y);
			
			ticksAlive++;
		}
		
		public void draw() {
			
			GL11.glColor3f(r, g, b);
			GL11.glVertex2f(x, y);
			
		}
	}
}
