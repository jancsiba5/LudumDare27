package dev.coloniergames.ld27;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.game.Game;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;

public class Main implements Constants {

	Game game;
	
	public Main() {
		
		setUp();
		
		init();
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			
			pollInput();
			
			tick();
			
			renderGL();
		
			// Display.sync(1);
			
			Display.update();
			
		}
		
		Display.destroy();
		
		System.exit(0);
		
	}
	
	public void setUp() {
		
		try {
			
			Display.setDisplayMode(new DisplayMode(w, h));
			Display.setTitle(TITLE);
			Display.create();
			
		} catch (LWJGLException e) {
			
			System.out.println("SOMETHING WENT WRONG DURING LWJGL INIT!!");
			
			e.printStackTrace();
			
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, w, h, 0, -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
	}
	
	public void init() {

		game = new Game();

		System.out.println("DONE INIT");
		
	}
	
	public void pollInput() {
		
		game.pollInput();
		
	}
	
	public void tick() {
		
		game.tick();
		
	}
	
	public void renderGL() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		game.renderGL();
		
	}
	
	public static void main(String[] args) {
		
		String path = "";
		
		if(System.getProperty("os.name").startsWith("Win") || System.getProperty("os.name").startsWith("win")) path = "windows";
		System.setProperty("org.lwjgl.librarypath", new File("lwjgl/native").getAbsolutePath() + "/" + path);
		
		new Main();
	}
}
