import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Missile {
	private int x, y;
	public static final int radius = 15;
	int xSpeed = 30, ySpeed = 30;
	Tank.Direction dir;
	boolean visiable = true;
	boolean flag = true;
	TankClient tc = null;
	
	public Missile(int x, int y, Tank.Direction dir, boolean flag, TankClient tc) {
		super(); 
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.flag = flag;
		this.tc = tc;
	}
	
	public void draw(Graphics g){
		if(visiable){
			Color c = g.getColor();
			g.setColor(TankWarColor.missileColor);
			g.fillOval(x, y, radius, radius);
			g.setColor(c);
			move();
		}
		else{
			tc.missiles.remove(this);
		}
	}
	
	private void move(){
		
		switch(dir){
		case L:
			x -= xSpeed;
			break;
		case R:
			x += xSpeed;
			break;
		case U:
			y -= ySpeed;
			break;
		case D:
			y += ySpeed;
			break;
		}
		
		if(x < 0 || x > tc.mapWidth || y < 0 || y > tc.mapHeight){
			visiable = false;
		}
	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y, radius, radius);
	}
	
	public void hitTank(Tank t){
		if(t.life > 0 && (this.flag != t.getFlag())){
			if(this.getRect().intersects(t.getRect())){
				t.life--;
				this.visiable = false;
				if(t.life == 0){
					tc.explodes.add(new Explode(t.getX(), t.getY(), tc, t));
				}
			}
		}
	}
	
	public void hitWall(Wall w) {
		if(w.life > 0){
			if(this.getRect().intersects(w.getRect())){
				this.visiable = false;
				w.life--;
			}
		}
	}
}
