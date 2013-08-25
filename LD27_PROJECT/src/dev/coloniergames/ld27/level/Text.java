package dev.coloniergames.ld27.level;

import javax.swing.JOptionPane;

import dev.coloniergames.ld27.Constants;
import dev.coloniergames.ld27.entity.Player;
import dev.coloniergames.ld27.gfx.Sprite;
import dev.coloniergames.ld27.util.TextureData;

public class Text implements Constants {

	String message;
	
	int x, y;
	
	boolean isRead = false;
	
	public Sprite textSprite;
	
	boolean draw;
	public Text(int x, int y, String msg, boolean draw) {
		
		this.x = x;
		this.y = y;
		this.message = msg;
		
		this.draw = draw;
		
		textSprite = new Sprite(x * BLOCK, y * BLOCK, TextureData.blockTextures[5][7], 1, 1, 1);
	}
	
	public void pickUp(Player p) {
		
		int eX = (int) Math.floor(p.position.x / BLOCK);
		int eY = (int) Math.floor(p.position.y / BLOCK);
		
		if(eX == x && eY == y && !isRead) {
			
			p.setVelocity(0, 0);
			
			p.position.setPosition(x * BLOCK, y * BLOCK);
			
			p.mUp = false;
			p.mDown = false;
			p.mLeft = false;
			p.mRight = false;
			p.mNo = true;
			
			JOptionPane.showMessageDialog(null, message);
			
			isRead = true;
			
			textSprite.setTexture(TextureData.blockTextures[4][7]);
		}
	}
	
	public void draw() {
		if(draw) textSprite.draw();
	}
}
