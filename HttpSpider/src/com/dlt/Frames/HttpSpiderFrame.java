package com.dlt.Frames;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.dlt.utils.HttpSpider;


public class HttpSpiderFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JLabel startUrlLabel = null;
	JLabel baseUrlLabel = null;
	JLabel infoExLabel = null;
	JLabel searchExLabel = null;
	JLabel filePathLabel = null;
	JLabel resultLabel = null;
	JLabel downloadPathLabel = null;
	
	JTextField startUrlField = null;
	JTextField baseUrlField = null;
	JTextField infoExField = null;
	JTextField searchExField = null;
	JTextField filePathField = null;
	JTextField downloadPathField = null;
	
	JTextArea resultArea = null;
	
	JButton searchButton = null;
	JButton outputButton = null;
	JButton resetButton = null;
	JButton downloadButton = null;
	
	private HttpSpider spider = null;
	
	public HttpSpiderFrame(){
		spider = new HttpSpider();
		init();
		setTitle("��������");
		setSize(300, 500);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void init() {
		setLayout(null);
		startUrlLabel = new JLabel("��ʼ��ַ:");
		baseUrlLabel = new JLabel("����ַ:");
		infoExLabel = new JLabel("��Ϣ���ʽ:");
		searchExLabel = new JLabel("�������ʽ:");
		filePathLabel = new JLabel("����·��:");
		filePathLabel.setToolTipText("�������ǰ��д(�ļ��б����Ѵ���)");
		downloadPathLabel = new JLabel("����·��:");
		downloadPathLabel.setToolTipText("�������ǰ��д(�ļ��б����Ѵ��ڣ�ĩβ��'/'����)");
		
		startUrlField = new JTextField();
		baseUrlField = new JTextField();
		infoExField = new JTextField();
		searchExField = new JTextField();
		filePathField = new JTextField();
		downloadPathField = new JTextField();
		
		startUrlLabel.setBounds(10, 10, 70, 30);
		add(startUrlLabel);
		startUrlField.setBounds(80, 10, 200, 30);
		add(startUrlField);
		baseUrlLabel.setBounds(10, 40, 70, 30);
		add(baseUrlLabel);
		baseUrlField.setBounds(80, 40, 200, 30);
		add(baseUrlField);
		infoExLabel.setBounds(10, 70, 70, 30);
		add(infoExLabel);
		infoExField.setBounds(80, 70, 200, 30);
		add(infoExField);
		searchExLabel.setBounds(10, 100, 70, 30);
		add(searchExLabel);
		searchExField.setBounds(80, 100, 200, 30);
		add(searchExField);
		filePathLabel.setBounds(10, 130, 70, 30);
		add(filePathLabel);
		filePathField.setBounds(80, 130, 200, 30);
		add(filePathField);
		downloadPathLabel.setBounds(10, 160, 70, 30);
		add(downloadPathLabel);
		downloadPathField.setBounds(80, 160, 200, 30);
		add(downloadPathField);
		
		resultLabel = new JLabel("�������:");
		resultLabel.setBounds(10, 190, 70, 30);
		add(resultLabel);
		resultArea = new JTextArea();
		resultArea.setLineWrap(true);
		resultArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(resultArea);
		scrollPane.setBounds(10, 220, 270, 200);
		add(scrollPane);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		ButtonListener buttonListener = new ButtonListener();
		searchButton = new JButton("����");
		searchButton.addActionListener(buttonListener);
		resetButton = new JButton("����");
		resetButton.addActionListener(buttonListener);
		downloadButton = new JButton("����");
		downloadButton.addActionListener(buttonListener);
		outputButton = new JButton("����");
		outputButton.addActionListener(buttonListener);
		panel.add(searchButton);
		panel.add(resetButton);
		panel.add(downloadButton);
		panel.add(outputButton);
		panel.setBounds(10, 425, 270, 40);
		add(panel);
	}
	
	public void reset() {
		
		startUrlField.setText("");
		baseUrlField.setText("");
		infoExField.setText("");
		searchExField.setText("");
		filePathField.setText("");
		downloadPathField.setText("");
		resultArea.setText("");
	}
	
	private class ButtonListener implements ActionListener {

		List<String> results = null;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(searchButton)) {
				System.out.println("searchButton clicked!");
				resultArea.setText("");
				String startUrl = startUrlField.getText();
				String baseUrl = baseUrlField.getText();
				String infoRules = infoExField.getText();
				String searchRules = searchExField.getText();
				spider.prepare(startUrl, baseUrl, infoRules, searchRules);
				spider.Grab();
				results = spider.getResults();
				for (String result : results) {
					resultArea.append(result+"\n");
				}
			}
			else if (e.getSource().equals(outputButton)) {
				System.out.println("outputButton clicked!");
				if (results != null) {
					String filePath = filePathField.getText();
					/*FileService fileService = new FileService(filePath);
					String content = "";
					for (String result : results) {
						content = content + result + "\n";
					}
					fileService.writeToFile(content);*/
					spider.writeResultsToFile(filePath);
				}
			}
			else if (e.getSource().equals(downloadButton)) {
				System.out.println("downloadButton clicked!");
				String downloadPath = downloadPathField.getText();
				spider.downloadFiles(downloadPath);
			}
			else if (e.getSource().equals(resetButton)) {
				System.out.println("resetButton clicked!");
				reset();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new HttpSpiderFrame();
	}
}
