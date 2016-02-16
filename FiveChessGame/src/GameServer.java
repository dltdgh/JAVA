
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GameServer extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String[] clientAddress = new String[10];
	public int map[][] = new int[20][20];
	public JButton restartButton = null;
	public ServerThread thread = null;
	public JTextArea textArea = null;
	public ServerSocket server = null;
	
	public void updateOther(String remoteAddress, GameData data){
		try {
			Socket socket = null;
			socket = new Socket(remoteAddress, 44444);
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			oout.writeObject(data);
			oout.flush();
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			data = (GameData)oin.readObject();
			System.out.println(data.isChecked());
			oout.close();
			oin.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public GameData recieve(ServerSocket server, int id){
		GameData data = null;
		try {
			Socket socket = null;
			socket = server.accept();
			ObjectInputStream oin = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oout = new ObjectOutputStream(socket.getOutputStream());
			data = (GameData)oin.readObject();
			if(data.getId() == id || id == 3){
				data.setChecked(true);
				if(id == 3){
					data.setPos(false);
				}
			}
			
			System.out.println(data.getId()+" "+data.getLocalAddress()+" "+data.getPos()+" "+data.isPos());
			
			
			oout.writeObject(data);
			oout.flush();
			oin.close();
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean check(GameData data){
		int id = data.getId();
		int x = data.getPos()%20;
		int y = data.getPos()/20;
		map[x][y] = id;
		int cnt = 1, i, j;
		
		//左上右下
		i = x-1;
		j = y-1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i--;
			j--;
		}
		i = x+1;
		j = y+1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i++;
			j++;
		}
		if(cnt >= 5){
			return true;
		}
		//左下右上
		cnt = 1;
		i = x-1;
		j = y+1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i--;
			j++;
		}
		i = x+1;
		j = y-1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i++;
			j--;
		}
		if(cnt >= 5){
			return true;
		}
		
		//上下
		cnt = 1;
		i = x;
		j = y+1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			j++;
		}
		i = x;
		j = y-1;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			j--;
		}
		if(cnt >= 5){
			return true;
		}
		//左右
		cnt = 1;
		i = x+1;
		j = y;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i++;
		}
		i = x-1;
		j = y;
		while(i >= 0 && j >= 0 && i < 20 && j < 20 && map[i][j] == id){
			cnt++;
			i--;
		}
		if(cnt >= 5){
			return true;
		}
		
		return false;
	}
	
	public GameServer(){
		try {
			this.server = new ServerSocket(55555);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println(server.getLocalPort());
		restartButton = new JButton("重启服务器");
		restartButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				init();
			}
		});
		
		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);
		add(restartButton, BorderLayout.SOUTH);
		setBounds(0, 0, 300, 400);
		setVisible(true);
		setTitle("服务器");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init();
	}
	
	public void init() {	
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 20; j++)
			{
				map[i][j] = 0;
			}
		}
		textArea.setText("");
		thread = new ServerThread(this);
		thread.start();
		System.out.println("已启动");
	}
	
	class ServerThread extends Thread{
		GameServer gameServer = null;
		ServerThread(GameServer server){
			this.gameServer = server;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ServerSocket server = gameServer.server;
				
				GameData data = null;
				int cnt = 0;
				while(cnt < 2){
					data = recieve(server, 3);
					if(clientAddress[data.getId()] == null){
						clientAddress[data.getId()] = data.getLocalAddress();
						cnt++;
						if(!data.isPos()){
							textArea.append(data.getId()+"号已准备 ip地址:"+data.getLocalAddress()+"\n");
						}
					}
				}
				System.out.println("验证完成");
				textArea.append("验证完成\n");
				GameData mydata = new GameData(-1, 0, false);
				mydata.setAns(3);
				updateOther(clientAddress[1], mydata);
				updateOther(clientAddress[2], mydata);
				while (true) {
					while((data = recieve(server, 1)).getId() != 1 && data.isPos()){};
					System.out.println("一号落子");
					textArea.append("一号落子: 位置("+(data.getPos()%20)+","+(data.getPos()/20)+")\n");
					if(check(data)){
						data.setAns(1);
						updateOther(clientAddress[1], data);
						data.setAns(2);
						updateOther(clientAddress[2], data);
						break;
					}
					else {
						updateOther(clientAddress[2], data);
					}
					
					while((data = recieve(server, 2)).getId() != 2 && data.isPos()){};
					textArea.append("二号落子: 位置("+(data.getPos()%20)+","+(data.getPos()/20)+")\n");
					if(check(data)){
						data.setAns(1);
						updateOther(clientAddress[2], data);
						data.setAns(2);
						updateOther(clientAddress[1], data);
						break;
					}
					else {
						updateOther(clientAddress[1], data);
					}
				}
				textArea.append("游戏结束");
				
				gameServer.clientAddress[1] = null;
				gameServer.clientAddress[2] = null;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new GameServer();
	}
}
