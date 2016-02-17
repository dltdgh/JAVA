import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class Wall {
	int x, y;
	int width = 30, height = 30;
	int life = 15;
	TankClient tc;
	Random random = new Random();
	
	public Wall(int x, int y, TankClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
		this.life = Math.abs(random.nextInt())%30+1;
		tc.mark[x/Tank.width][y/Tank.height] = true;
	}
	
	public void draw(Graphics g) {
		if(life > 0){
			Color c = g.getColor();
			if(life > 20){
				g.setColor(TankWarColor.wallColor3);
			}
			else if(life > 10){
				g.setColor(TankWarColor.wallColor2);
			}
			else{
				g.setColor(TankWarColor.wallColor1);
			}
		//	System.out.println("Drawed wall");
			g.fillRect(x, y, width, height);
			g.setColor(c);
		}
		else{
			tc.mark[x/Tank.width][y/Tank.height] = false;
			tc.walls.remove(this);
		}
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}
}
