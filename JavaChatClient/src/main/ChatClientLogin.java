// ChatClientLogin.java
// ObjecStream 사용하는 채팅 Client
package main;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChatClientLogin extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JTextField txtIpAddress;
	private JTextField txtPortNumber;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClientLogin frame = new ChatClientLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatClientLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel projectName = new JLabel(resources.Strings.BUGI_TALK);
		projectName.setFont(resources.Fonts.MAIN_BOLD_28);
		projectName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		projectName.setHorizontalAlignment(JLabel.CENTER);
		projectName.setBounds(75, 70, 200, 50);
		contentPane.add(projectName);
		
		JLabel lblbugiIcon = new JLabel(new ImageIcon("src/images/main_logo.png"));
		lblbugiIcon.setVerticalAlignment(JLabel.TOP);
		lblbugiIcon.setBounds(90, 130, 194, 130);
		contentPane.add(lblbugiIcon);
		
		
		JLabel lbluserName = new JLabel(resources.Strings.NICKNAME);
		lbluserName.setFont(resources.Fonts.MAIN_BOLD_16);
		lbluserName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lbluserName.setBounds(70, 280, 50, 30);
		contentPane.add(lbluserName);
		
		txtUserName = new JTextField();
		txtUserName.setFont(resources.Fonts.MAIN_PLAIN_15);
		txtUserName.setHorizontalAlignment(SwingConstants.CENTER);
		txtUserName.setBounds(120, 280, 150, 30);
		txtUserName.setColumns(10);
		contentPane.add(txtUserName);
		
		JButton btnLogin = new JButton(resources.Strings.LOGIN);
		
		btnLogin.setFont(resources.Fonts.MAIN_BOLD_16);
		btnLogin.setBounds(125, 320, 90, 40);
		btnLogin.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		btnLogin.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		btnLogin.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(btnLogin);
		
		
		Myaction action = new Myaction();
		btnLogin.addActionListener(action);
		txtUserName.addActionListener(action);
	}
	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = txtUserName.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			
			ChatClientMain main = new ChatClientMain(username, ip_addr, port_no);
			setVisible(false);
			main.setBounds(getLocation().x, getLocation().y, 350, 600);
			main.setVisible(true);
			 
		}
	}
}


