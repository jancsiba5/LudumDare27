package dev.coloniergames.ld27.collision;

import org.lwjgl.opengl.GL11;

import dev.coloniergames.ld27.util.Vector;

public class AABB
{
	public Vector pos, size;

	public AABB(Vector pos, Vector size)
	{
		this.pos = pos;
		this.size = size;
	}

	public void update(float x, float y) {
		pos.setPosition(x, y);
	}

	public static boolean collides(AABB a, AABB b)
	{
		if(Math.abs(a.pos.x - b.pos.x) < a.size.x + b.size.x)
		{
			if(Math.abs(a.pos.y - b.pos.y) < a.size.y + b.size.y)
			{
				return true;
			}
		}

		return false;
	}

	
	public static boolean inside(AABB a, Vector b)
	{
		if(Math.abs(a.pos.x - b.x) < a.size.x)
		{
			if(Math.abs(a.pos.y - b.y) < a.size.y)
			{
				return true;
			}
		}
		return false;
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glBegin(GL11.GL_LINES);

		GL11.glColor3f(0, 1, 1);
		
		GL11.glVertex2f(pos.x, pos.y);
		GL11.glVertex2f(pos.x + size.x, pos.y);

		GL11.glVertex2f(pos.x + size.x, pos.y);
		GL11.glVertex2f(pos.x + size.x, pos.y + size.y);

		GL11.glVertex2f(pos.x + size.x, pos.y + size.y);
		GL11.glVertex2f(pos.x, pos.y + size.y);

		GL11.glVertex2f(pos.x, pos.y + size.y);
		GL11.glVertex2f(pos.x, pos.y);

		GL11.glEnd();
		
		GL11.glPopMatrix();
	}
}
