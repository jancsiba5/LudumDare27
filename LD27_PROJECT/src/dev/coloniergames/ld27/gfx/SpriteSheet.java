package dev.coloniergames.ld27.gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	BufferedImage image;
	public int rows;
	public int cols;
	int width;
	int height;
	
	public SpriteSheet(String src, int width, int height, int rows, int cols) {
		try {
			image = ImageIO.read(new File(src));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = width;
		this.height = height;
		this.rows = rows;
		this.cols = cols;
	}
	
	public BufferedImage getImage(int row, int col) {
		BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = i.createGraphics();
		g.drawImage(image.getSubimage(row * width, col * height, width, height), 0, 0, null);
		return i;
	}
	
}
