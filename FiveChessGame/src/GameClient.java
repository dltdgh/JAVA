import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class GameClient extends JFrame {
	
	JPanel panel = null;
	JButton loginButton = null;
	MyLabel labels[] = new MyLabel[400];
	Random random = new Random();
	Integer clientID = 1;
	String serverAddress = "192.168.4.84";
	final Integer serverPort = 55555;
	boolean postState = false;
	int map[][] = new int[20][20];
	RecieveThread thread = null;
	
	private static final long serialVersionUID = 1L;
	public GameClient() {
		// TODO Auto-generated constructor stub
		String temp = JOptionPane.showInputDialog("请输入服务器地址");
		if(temp != null && temp != ""){
			serverAddress = temp;
		}
		
		temp = JOptionPane.showInputDialog("请输入id(1或2),1号先手");
		if(temp != null && temp != ""){
			clientID = Integer.parseInt(temp);
		}
		
		if (clientID == 1) {
			postState = true;
		}
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(20, 20));
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				MyLabel label = new MyLabel();
				labels[i*20+j] = label;
				label.pos = i*20+j;
				label.addMouseListener(new LabelListener(this));
				label.setOpaque(true);
				label.setBackground(Color.white);
				label.setBorder(new LineBorder(Color.yellow));
				panel.add(label);
				map[i][j] = 0;
			}
		}
		add(panel, BorderLayout.CENTER);
		loginButton = new JButton("准备");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				postData(new GameData(-1, clientID, false));
				JButton button = (JButton)e.getSource();
				button.setEnabled(false);
			}
		});
		add(loginButton, BorderLayout.SOUTH);
		init();
		setBounds(0, 0, 500, 500);
		setResizable(false);
		setVisible(true);
		setTitle("五子棋");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void init() {
		
		loginButton.setEnabled(true);
		panel.setVisible(false);
		if (clientID == 1) {
			postState = true;
		}
		
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++)
			{
				labels[i*20+j].setBackground(Color.white);
				map[i][j] = 0;
			}
		}
		RecieveThread thread = new RecieveThread(this);
		thread.start();
	}
	
	class LabelListener implements MouseListener{
		GameClient client = null;
		LabelListener(GameClient client){
			this.client = client;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			MyLabel label = (MyLabel)e.getSource();
			if(map[label.pos%20][label.pos/20] == 0){
				if(postData(new GameData(label.pos, clientID, true)))
				{
					map[label.pos%20][label.pos/20] = clientID;
					label.setBackground(Color.black);
					client.postState = false;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public boolean postData(GameData data){
		Socket socket = null;
		try {
			if(postState == true || !data.isPos()){
				socket = null;
				socket = new Socket(serverAddress, 55555);
				data.setLocalAddress(socket.getLocalAddress().toString().substring(1));
				ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
				oout.writeObject(data);
				oout.flush();
				ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
				data = (GameData)oin.readObject();
				System.out.println(data.isChecked());
				oout.close();
				oin.close();
				socket.close();
				return true;
			}
			else {
				JOptionPane.showMessageDialog(this, "未到你的回合！");
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public GameData getData(){
		Socket socket = null;
		GameData data = null;
		try {
			ServerSocket server = new ServerSocket(44444);
			socket = server.accept();
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			data = (GameData)oin.readObject();
			data.setChecked(true);
			oout.writeObject(data);
			oout.flush();
			oin.close();
			oout.close();
			socket.close();
			server.close();	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	class MyLabel extends JLabel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int pos;
		
	}
	
	class RecieveThread extends Thread{
		GameClient client = null;
		RecieveThread(GameClient client){
			this.client = client;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				GameData data = getData();
				Integer ans = data.getAns();
				if(ans == 1){
					int temp = JOptionPane.showConfirmDialog(client, "你赢了，是否重新开始", "结果", JOptionPane.YES_NO_OPTION);
					if(temp == JOptionPane.YES_OPTION){
						init();
					}
					else {
						System.out.println(temp);
						System.exit(0);
					}
					break;
				}
				else if(ans == 2){
					int temp = JOptionPane.showConfirmDialog(client, "你输了，是否重新开始", "结果", JOptionPane.YES_NO_OPTION);
					if(temp == JOptionPane.YES_OPTION){
						init();
					}
					else {
						System.out.println(temp);
						System.exit(0);
					}
				}
				else if(ans == 3){
					client.panel.setVisible(true);
				}
				else {
					if(data.isPos()){
						map[data.getPos()%20][data.getPos()/20] = data.getId();
						System.out.println("shoudao");
						labels[data.getPos()].setBackground(Color.gray);
						this.client.postState = true;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new GameClient();
	}
	
}
