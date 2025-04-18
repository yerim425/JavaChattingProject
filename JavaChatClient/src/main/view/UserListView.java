package main.view;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import main.ChatClientMain;
import main.view.item.ChatRoomItem;
import main.view.item.FriendProfileItem;
import main.view.item.UserProfileItem;

public class UserListView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	
	private JLabel lblUserList; // "사용자 목록"
	
	private JScrollPane userListScrollPane; // 사용자 리스트 스크롤
	private JPanel userListPanel; // 사용자 리스트 패널
	
	private int userListIdx = 0;
	

	//private Vector<UserProfileItem> UserVec = new Vector<UserProfileItem>();
	

	/**
	 * Create the panel.
	 */
	public UserListView(ChatClientMain parent, String name) {
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
		userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));
		userListPanel.setAlignmentY(TOP_ALIGNMENT);
		userListPanel.setPreferredSize(new Dimension(325, calculatePanelHeight()));
		userListPanel.setOpaque(true);
		
	
		userListScrollPane = new JScrollPane(userListPanel);
		userListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		userListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		userListScrollPane.setBounds(10, 50, 325, 460);
		userListScrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		// userListScrollPane.setPreferredSize(new Dimension(300, 330));
		userListScrollPane.getViewport().setOpaque(false);
		userListScrollPane.setOpaque(false);
		userListScrollPane.setBorder(null);
		add(userListScrollPane);

		//UserViewPanel.this.parent.SendObject(new ChatMsg(userName, "600", "all"));
		//UserViewPanel.this.parent.SendObject(new ChatMsg(userName, "700", "all"));
		
	}
	
public void addUser(ChatMsg cm) {
		
		UserProfileItem user = new UserProfileItem(this, cm);
		userListPanel.add(user);
		userListPanel.add(Box.createVerticalStrut(10));
		
		userListPanel.setPreferredSize(new Dimension(325, calculatePanelHeight()));
		
		userListScrollPane.revalidate();
		userListScrollPane.repaint();
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
		
		userListPanel.revalidate();
		userListPanel.repaint();
		
	}
	
	public String getUserName() {
		return this.userName;
	}
	


	

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}
	
	public int calculatePanelHeight() {
		int itemCount = 0;
		
	    for (int i = 0; i < userListPanel.getComponentCount(); i++) {
	        if (userListPanel.getComponent(i) instanceof UserProfileItem) {
	            itemCount++;
	        }
	    }
	    int itemHeight = 60; 
	    int spacing = 10;
	    return itemCount * (itemHeight + spacing);
	}

}
