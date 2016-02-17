import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	int x, y;
	private boolean Alive = true;
	
	TankClient tc = null;
	Tank t;
	
	int[] radiuses = {5, 10, 25, 30, 40, 20, 10, 5};
	int step = 0;
	
	public Explode(int x, int y, TankClient tc, Tank t){
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.t = t;
	}
	
	public void draw(Graphics g){
		
		if(!Alive){
			tc.explodes.remove(this);
			return;	
		}
		if(step == radiuses.length){
			Alive = false;
			step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(TankWarColor.explodeColor);
		int tx = x+Tank.width/2-radiuses[step]/2;
		int ty = y+Tank.height/2-radiuses[step]/2;
		g.fillOval(tx, ty, radiuses[step], radiuses[step]);
		g.setColor(c);
		
		step++;
	}
	
	public boolean isAlive(){
		return Alive;
	}
}
