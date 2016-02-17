import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.text.spi.BreakIteratorProvider;
import java.util.*;

public class Tank {
	
	TankClient tc;
	private int x, y;                                //坦克位置
	public static final int width = 30, height = 30;              //坦克大小
	private int xSpeed = 30, ySpeed = 30;             //坦克速度
	
	private boolean flag = true;
	
	//boolean Alive = true;
	
	int life = 3;
	
	enum Direction {L, R, U, D};                       //坦克方向
	private Direction dir = Direction.R;               //初始方向
	
	Color color = null;                                 //坦克颜色

	static Random random = new Random();
	
	int step = 0, tkey;
	int superFireNum = 0;
	
	public Tank(int x, int y, boolean flag, TankClient tc){                     //构造方法
		
		this.x = x;
		this.y = y;
		
		if(flag){
			life = 3;
			superFireNum = 3;
		}
		else{
			life = Math.abs(random.nextInt())%3+1;
		}
		
		tc.mark[x/width][y/height] = true;
		
		this.flag = flag;
		this.tc = tc;
		int temp = Math.abs(random.nextInt()%4);
		
		switch (temp) {
		case 0:
			dir = Direction.U;
			break;
		case 1:
			dir = Direction.D;
			break;
		case 2:
			dir = Direction.L;
			break;
		case 3:
			dir = Direction.R;
			break;
		default:
			break;
		}
		
		if(flag){
			color = Color.RED;
		}
		else {
			color = Color.BLUE;
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean getFlag(){
		return this.flag;
	}
	
	public void draw(Graphics g){
		
	//	g.drawString("There are "+missiles.size()+" missiles", 10, 15);
		if(this.life > 0){
			Color c = g.getColor();
			if(flag){
				if(life >= 3){
					g.setColor(TankWarColor.ourboard3);
				}
				else if(life == 2){
					g.setColor(TankWarColor.ourboard2);
				}
				else{
					g.setColor(TankWarColor.ourboard1);
				}
			}
			else{
				if(life == 3){
					g.setColor(TankWarColor.enemyboard3);
				}
				else if(life == 2){
					g.setColor(TankWarColor.enemyboard2);
				}
				else{
					g.setColor(TankWarColor.enemyboard1);
				}
			}
			g.fillRect(x, y, width, height);
			if(flag){
				g.setColor(TankWarColor.ourpaotong);
			}
			else{
				g.setColor(TankWarColor.enemypaotong);
			}
			switch (dir) {
			case R:
				g.fillRect(x+width/2, y+height/2-height/6, width/2, height/3);
				break;
			case L:
				g.fillRect(x, y+height/2-height/6, width/2, height/3);
				break;
			case U:
				g.fillRect(x+width/2-width/6, y, width/3, height/2);
				break;
			case D:
				g.fillRect(x+width/2-width/6, y+height/2, width/3, height/2);
				break;
			default:
				break;
			}
			if(flag){
				g.setColor(TankWarColor.ourpaotai);
			}
			else{
				g.setColor(TankWarColor.enemypaotai);
			}
			g.fillOval(x+width/4, y+height/4, width/2, height/2);
			g.setColor(c);
		}
		else {
			if(!flag){
				tc.mark[x/width][y/height] = false;
				tc.score++;
				tc.tanks.remove(this);
			}
		}
	}
	
	public void keyPressed(int key){
		int tx = x, ty = y;
		if(key == KeyEvent.VK_RIGHT){
			if(dir == Direction.R && x+xSpeed+5 < tc.mapWidth){
				tx += xSpeed;
			}
			else {
				dir = Direction.R;
			}
		}
		else if(key == KeyEvent.VK_LEFT){
			if(dir == Direction.L && x > 0){
				tx -= xSpeed;
			}
			else {
				dir = Direction.L;
			}
		}
		else if(key == KeyEvent.VK_DOWN){
			if(dir == Direction.D && y+ySpeed+5 < tc.mapHeight){
				ty += ySpeed;
			}
			else{
				dir = Direction.D;
			}
		}
		else if(key == KeyEvent.VK_UP){
			if(dir == Direction.U && y > 0){
				ty -= ySpeed;
			}
			else {
				dir = Direction.U;
			}
		}
		else if(key == KeyEvent.VK_SPACE){
			tc.missiles.add(fire(dir));
		}
		else if(key == KeyEvent.VK_CONTROL){
			if(superFireNum > 0){
				superFireNum--;
				superFire();
			}
		}
		
		if(!tc.mark[tx/width][ty/height]){
			tc.mark[x/width][y/height] = false;
			tc.mark[tx/width][ty/height] = true;
			x = tx;
			y = ty;
		}
	}
	
	private Missile fire(Direction direction) {
		// TODO Auto-generated method stub
		return new Missile(x+width/2-Missile.radius/2, y+height/2-Missile.radius/2, direction, flag, tc);
	}
	
	private void superFire(){
		
		tc.missiles.add(fire(Direction.R));
		tc.missiles.add(fire(Direction.L));
		tc.missiles.add(fire(Direction.U));
		tc.missiles.add(fire(Direction.D));
		
	}
	
	public Rectangle getRect() {
		return new Rectangle( x, y, width, height);
	}
	
	public void autoMove() {
	/*	if(flag){
			return;
		}
		*/
		if(step > 0){
			keyPressed(tkey);
			step--;
		}
		else {
			step = Math.abs(random.nextInt())%5;
		//	step = 0;
			int temp = Math.abs(random.nextInt())%5;
			switch (temp) {
			case 0:
				keyPressed(KeyEvent.VK_UP);
				tkey = KeyEvent.VK_UP;
				break;
			case 1:
				keyPressed(KeyEvent.VK_DOWN);
				tkey = KeyEvent.VK_DOWN;
				break;
			case 2:
				keyPressed(KeyEvent.VK_LEFT);
				tkey = KeyEvent.VK_LEFT;
				break;
			case 3:
				keyPressed(KeyEvent.VK_RIGHT);
				tkey = KeyEvent.VK_RIGHT;
				break;
			case 4:
				keyPressed(KeyEvent.VK_SPACE);
				tkey = KeyEvent.VK_SPACE;
				break;
			default:
				break;
			}
		}
	}
	
	public void transmit(){	
		while(true){
			int tx = Math.abs(random.nextInt())%tc.xNum;
			int ty = Math.abs(random.nextInt())%tc.yNum;
//			System.out.println(tx+" "+ty);
			if(!tc.mark[tx][ty]){
				tc.mark[x/30][y/30] = false;
				tc.mark[tx][ty] = true;
				x = tx*width;
				y = ty*height;
				break;
			}
		}
	}
}
