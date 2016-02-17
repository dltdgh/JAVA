import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



public class TankClient extends JFrame{
	
	Tank myTank = null;
	public static final int pixelWidth = 640, pixelHeight = 480;
	public static int mapWidth, mapHeight;
	JPanel panel;
	Image offScreenImage = null;
	boolean mark[][] = null;
	int xNum, yNum;
	int TankNum = 5;
	int WallNum = 50;
	int giftNum = 1;
	int score = 0;
	static Random random = new Random();
	
	Thread timeThread = null;
	
	boolean isPause = false;
	
	ArrayList<Tank> tanks = new ArrayList<Tank>();
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	ArrayList<Explode> explodes = new ArrayList<Explode>();
	ArrayList<Wall> walls = new ArrayList<Wall>();
	ArrayList<Gift> gifts = new ArrayList<Gift>();
	
	public void launchFrame(){
		
		panel = new JPanel(){
			
			@Override
			public void paint(Graphics g) {
				// TODO Auto-generated method stub 
				/*˫���建����˸����*/
				if(offScreenImage == null){
					offScreenImage = this.createImage(this.getWidth(), this.getHeight());
				}
				Graphics gOffScreen = offScreenImage.getGraphics();
				Color c = gOffScreen.getColor();
				gOffScreen.setColor(TankWarColor.grassColor);
				gOffScreen.fillRect(0, 0, this.getWidth(), this.getHeight());
				gOffScreen.setColor(c);
				drawOffScreenImage(gOffScreen);
				g.drawImage(offScreenImage, 0, 0, this);
			}

			private void drawOffScreenImage(Graphics g) {
				// TODO Auto-generated method stub
				if(!isPause){	
					for(int i = 0; i < missiles.size(); i++){
						
						for(int j = 0; j < tanks.size(); j++){
							missiles.get(i).hitTank(tanks.get(j));
						}
					
						for(int j = 0; j < walls.size(); j++){
							missiles.get(i).hitWall(walls.get(j));
						}
					
						missiles.get(i).hitTank(myTank);
						missiles.get(i).draw(g);
					}			
				
					for(int i = tanks.size(); i < TankNum; i++){
						tanks.add(createTank(false));
					}
				
					for(int i = 0; i < walls.size(); i++){
						walls.get(i).draw(g);
					}
				
					for(int i = 0; i < tanks.size(); i++){
						tanks.get(i).draw(g);
					}
				
					for(int i = 0; i < explodes.size(); i++){
						explodes.get(i).draw(g);
					}
				
					for(int i = 0; i < gifts.size(); i++){
						gifts.get(i).beEaten(myTank);
						gifts.get(i).draw(g);
					}
				
					myTank.draw(g);
				}
				drawText(g);	
				
			}
		};
		
		add(panel);
		this.addKeyListener(new KeyListener());
		
		this.setLocation(0, 0);
		this.setSize(pixelWidth, pixelHeight);
		this.setResizable(false);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		mapWidth = panel.getWidth();
		mapHeight = panel.getHeight();
		
		this.setBackground(Color.GREEN);
		this.setTitle("̹�˴�ս");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		xNum = mapWidth/Tank.width;
		yNum = mapHeight/Tank.height;
		
		gameInit();
	}
	
	public static void main(String[] args) {
		TankClient tc = new TankClient();
		tc.launchFrame();
	}
	
	private class PaintThread implements Runnable{
		int count = 0;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true)
			{
				panel.repaint();
				
				if(isPause){
			//		System.out.println("pause state: "+isPause);
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					continue;
				}
			//	System.out.println("hello~~~");
				count = (count+1)%2000;
				if(count % 5 == 0){
					for(int i = 0; i < tanks.size(); i++){
						tanks.get(i).autoMove();
					}
				}
				if(count % 500 == 400){
					for(int i = gifts.size(); i < giftNum; i++){
						gifts.add(createGift());
					}
				}
				if(count % 500 == 499){
					for(int i = 0; i < gifts.size(); i++){
						gifts.remove(i);
					}
				}
				if(count == 1999){
					if(TankNum < 30){
						TankNum++;
					}
				}
				if(myTank.life == 0){
					break;
				}
				try {
					Thread.sleep(100);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		//	JOptionPane.showMessageDialog();
		//	System.exit(0);
			int temp = JOptionPane.showConfirmDialog(panel, "You Died!\nScore: "+score+"\nRestart?");
			if (temp == JOptionPane.OK_OPTION) {
				gameInit();
			}
			else{
				System.exit(0);
			}
		}
	}
	
	private class KeyListener extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_ESCAPE){
				if(isPause){
					isPause = false;
				}
				else{
					isPause = true;
				}
			}
			else{
				if(!isPause){
					myTank.keyPressed(key);
				}
			}	
		}
	}
	
	public Tank createTank(boolean flag){
		while(true){
			int tx = Math.abs(random.nextInt())%xNum;
			int ty = Math.abs(random.nextInt())%yNum;
//			System.out.println(tx+" "+ty);
			if(!mark[tx][ty]){
				Tank tank = new Tank(tx*Tank.width, ty*Tank.height, flag, this);
				return tank;
			}
		}
	}
	
	public Wall createWall(){
		while(true){
			int tx = Math.abs(random.nextInt())%xNum;
			int ty = Math.abs(random.nextInt())%yNum;
//			System.out.println(tx+" "+ty);
			if(!mark[tx][ty]){
	//			System.out.println(tx+" "+ty);
				Wall wall = new Wall(tx*Tank.width, ty*Tank.height, this);
				return wall;
			}
		}
	}
	
	public Gift createGift(){
		while(true){
			int tx = Math.abs(random.nextInt())%xNum;
			int ty = Math.abs(random.nextInt())%yNum;
//			System.out.println(tx+" "+ty);
			if(!mark[tx][ty]){
	//			System.out.println(tx+" "+ty);
				Gift gift = new Gift(tx*Tank.width, ty*Tank.height, this);
				return gift;
			}
		}
	}
	
	public void drawText(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("̹�������� "+tanks.size(), 10, 20);
		g.drawString("�ӵ������� "+missiles.size(), 10, 35);
		g.drawString("��ը������ "+explodes.size(), 10, 50);
		g.drawString("ǽ������ "+walls.size(), 10, 65);
		g.drawString("���������� "+myTank.superFireNum, 10, 80);
		g.drawString("ʣ�������� "+myTank.life, 10, 95);
		g.drawString("��÷����� "+score, 10, 110);
		g.drawString("��ͣ״̬�� "+isPause, 10, 125);
		g.setColor(c);
	}
	
	public void gameInit(){
		
		TankNum = 5;
		WallNum = 50;
		giftNum = 1;
		score = 0;
		
		tanks.clear();
		missiles.clear();
		explodes.clear();
		walls.clear();
		gifts.clear();
		mark = new boolean[xNum][yNum];
		
		myTank = createTank(true);
		
		for (int i = 0; i < WallNum; i++) {
			walls.add(createWall());
		}
		
//		System.out.println(xNum+" "+yNum);
		for(int i = 0; i < TankNum; i++){
			tanks.add(createTank(false));
		}
		
		timeThread = new Thread(new PaintThread());
		timeThread.start();
	}
}