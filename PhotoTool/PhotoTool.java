import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.spi.ImageWriterSpi;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.Option;


public class PhotoTool extends JFrame{
	
	String yzmString = null;
	JLabel photoLabel = null;
	String cookieString = null;
	JButton preButton = null;
	JButton nextButton = null;
	long number = 440007;
	
	final String YZM_URL = "http://jw.qdu.edu.cn/academic/getCaptcha.do";
	final String lOGIN_URL = "http://jw.qdu.edu.cn/academic/j_acegi_security_check";
	
	
	PhotoTool() throws Exception{
		logIn();
		
		photoLabel = new JLabel();
		preButton = new JButton("上一张");
		preButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				number -= 4;
				LoadImg();
			}
		});
		nextButton = new JButton("下一张");
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				number += 4;
				LoadImg();
			}
		});
		
		add(photoLabel, BorderLayout.CENTER);
		add(preButton, BorderLayout.WEST);
		add(nextButton, BorderLayout.EAST);
		setBounds(0, 0, 385, 345);
		setTitle("教务照片查看器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void LoadImg() {
		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					if(new File("E:/photo/"+number+".jpg").exists()){
						ImageIcon imgIcon = new ImageIcon("E:/photo/"+number+".jpg");
						imgIcon.setImage(imgIcon.getImage().getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_DEFAULT));
						photoLabel.setIcon(imgIcon);
					}
					else {
						getImage("http://jw.qdu.edu.cn/academic/student/studentinfo/loadphoto_added.jsdo?primary=userid&kind=student&userid="+number, "E:/photo/"+number+".jpg");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public static void main(String[] args) throws Exception {
		new PhotoTool();
	}
	
	public void logIn(){	
		try {
			
			yzmString = (String)JOptionPane.showInputDialog(this, "请输入验证码", "验证码", JOptionPane.OK_CANCEL_OPTION, new ImageIcon(getYzm()), null, null);
			System.out.println(cookieString);
			URL url = new URL(lOGIN_URL);
			HttpURLConnection loginConnection = (HttpURLConnection)url.openConnection();
			loginConnection.setDoOutput(true);
			loginConnection.setRequestMethod("GET");
			loginConnection.setRequestProperty("Cookie", cookieString);
			OutputStreamWriter out = new OutputStreamWriter(loginConnection.getOutputStream());
			out.write("groupId=&j_username=201240703006&j_password=d2092l1377t&j_captcha="+yzmString);
			out.flush();
			out.close();
			InputStream in = loginConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while ((n = in.read(buffer)) > 0) {
				System.out.println(new String(buffer, 0, n));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String getYzm(){
		try {
			URL url = new URL(YZM_URL);
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
			File yzmFile = new File("yzm.jpg");
			FileOutputStream fout = new FileOutputStream(yzmFile);
			InputStream in = httpURLConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int n = 0;
			while((n = in.read(buffer)) > 0){
				fout.write(buffer, 0, n);
			}
			cookieString = httpURLConnection.getHeaderField("Set-Cookie");
			fout.close();
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "yzm.jpg";
	}
	
	public void getImage(String urlString, String fileNameString) throws Exception{
		
		Image img = null;
		URL url = new URL(urlString);
		HttpURLConnection huc = (HttpURLConnection)url.openConnection();
		huc.setRequestProperty("Cookie", cookieString);
		
		huc.connect();
		
		File file = new File(fileNameString);
		if(!file.exists()){
			file.createNewFile();
		}
		FileOutputStream fout = new FileOutputStream(file);
		InputStream in = huc.getInputStream();
		byte[] buffer = new byte[1024];
		int n = 0;
		
		while((n = in.read(buffer)) > 0) {
			fout.write(buffer, 0, n);
		}
		fout.close();
		huc.disconnect();
		ImageIcon imgIcon = new ImageIcon(fileNameString);
		imgIcon.setImage(imgIcon.getImage().getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_DEFAULT));
		photoLabel.setIcon(imgIcon);
	}

}
