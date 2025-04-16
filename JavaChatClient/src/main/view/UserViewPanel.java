package main.view;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import main.ChatClientMain;
import main.view.item.FriendProfileItem;
import main.view.item.UserProfileItem;

public class UserViewPanel extends BaseViewPanel {

	private static final long serialVersionUID = 1L;
	
	
	private JLabel lblUserList; // "사용자 목록"
	
	private JScrollPane userListScrollPane; // 사용자 리스트 스크롤
	private JPanel userListPanel; // 사용자 리스트 패널
	
	private int userListIdx = 0;
	

	//private Vector<UserProfileItem> UserVec = new Vector<UserProfileItem>();
	

	/**
	 * Create the panel.
	 */
	public UserViewPanel(ChatClientMain parent, String name) {
		super(parent, name);
		// this.setBackground(null);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(0, 0, 335, 510);
		setLayout(null);
		setVisible(false);
		setBackground(resources.Colors.MAIN_BG_COLOR);
		

		// "사용자 목록"
		lblUserList = new JLabel(resources.Strings.USER_LIST);
		lblUserList.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblUserList.setFont(resources.Fonts.MAIN_BOLD_16);
		lblUserList.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		lblUserList.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		lblUserList.setOpaque(true);
		lblUserList.setBounds(10, 10, 330, 25);
		add(lblUserList);


		// 스크롤 화면 설정
		userListPanel = new JPanel(new GridBagLayout());
		userListPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 0; // 1
		JPanel p = new JPanel();
		p.setBackground(resources.Colors.MAIN_BG_COLOR);
		userListPanel.add(p, gbc);
		
	
		userListScrollPane = new JScrollPane(userListPanel);
		userListScrollPane.setBounds(10, 50, 325, 460);
		userListScrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		// userListScrollPane.setPreferredSize(new Dimension(300, 330));
		add(userListScrollPane);

		//UserViewPanel.this.parent.SendObject(new ChatMsg(userName, "600", "all"));
		//UserViewPanel.this.parent.SendObject(new ChatMsg(userName, "700", "all"));
		
	}
	
public void addUser(ChatMsg cm) {
		
		UserProfileItem user = new UserProfileItem(this, cm);
		//UserVec.add(user);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		//gbc.weightx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		//friendListPanel.add(((JPanel)friend), gbc, friendListIdx);
		
		
		userListPanel.add(((JPanel)user), gbc, userListIdx);
		userListIdx++;
		
		userListPanel.revalidate();
		userListPanel.repaint();
	}
	
	public void removeUser(UserProfileItem user) {
		
		userListPanel.remove(user); // idx--??
		userListPanel.revalidate();
		userListPanel.repaint();
//		
//		for(UserProfileItem u : UserVec) {
//			if(u.getUserName().equals(user.getUserName()));
//			UserVec.remove(u);
//		}
	}
	
	public void initUserList() { // 나중에 백터로 대체
//		friendListPanel = new JPanel(new GridBagLayout());
//		friendListPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridwidth = GridBagConstraints.REMAINDER;
//		gbc.weightx = 1;
//		gbc.weighty = 1;
//		JPanel p = new JPanel();
//		p.setBackground(resources.Colors.MAIN_BG_COLOR);
//		friendListPanel.add(p, gbc);
//		friendListScrollPane.setViewportView(friendListPanel);

		userListPanel.removeAll();
		
		// 이거 두 개 필요한가?
		userListPanel.revalidate();
		userListPanel.repaint();
		
		userListIdx = 0;
	}
	
	public String getUserName() {
		return this.userName;
	}
	


	

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

}
