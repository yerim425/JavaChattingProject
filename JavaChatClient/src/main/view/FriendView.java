package main.view;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

public class FriendView extends BaseView {

	private static final long serialVersionUID = 1L;
	
	
	private JLabel lblMeTxt; // "나"
	private JLabel lblFriendTxt; // "친구"
	private JButton btnProfileImg; // 내 프로필 이미지 버튼
	private JLabel lblUserName; // 내 닉네임
	
	private JScrollPane friendListScrollPane; // 친구 리스트 스크롤
	private JPanel friendListPanel; // 친구 리스트 패널
	
	private int friendListIdx = 0;
	
	//private Vector<FriendProfileItem> FriendVec = new Vector<FriendProfileItem>();

	/**
	 * Create the panel.
	 */
	public FriendView(ChatClientMain parent, String name) {
		super(parent, name);
		// this.setBackground(null);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(0, 0, 335, 510);
		setLayout(null);
		setVisible(true);
		setBackground(resources.Colors.MAIN_BG_COLOR);

		// "나"
		lblMeTxt = new JLabel(resources.Strings.ME);
		lblMeTxt.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblMeTxt.setFont(resources.Fonts.MAIN_BOLD_16);
		lblMeTxt.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		lblMeTxt.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		lblMeTxt.setOpaque(true);
		lblMeTxt.setBounds(10, 10, 330, 25);
		add(lblMeTxt);

		// 나의 프로필 사진, 버튼에 이미지를 삽입
		btnProfileImg = new JButton();
		btnProfileImg.setBounds(10, 45, 50, 50); // 38
		btnProfileImg.setPreferredSize(new Dimension(50, 50));
		btnProfileImg.setIcon(imageResized(this.profileImg, resources.Dimens.PROFILE_IMG_SIZE));
		add(btnProfileImg);

		btnProfileImg.addActionListener(new ActionListener() { // 버튼 리스너
			public void actionPerformed(ActionEvent e) {
				Frame frame = new Frame("프로필 사진 설정"); // 프레임을 하나 만들고
				FileDialog fd = new FileDialog(frame, resources.Strings.SELECT_IMAGE, FileDialog.LOAD); // 이미지 파일 선택하면
				fd.setVisible(true);

				ImageIcon selectImg = new ImageIcon(fd.getDirectory() + fd.getFile());
				FriendView.this.profileImg = selectImg;
				// UserProfileImg_resized = profileImgResize(UserProfileImg_ori, 50);
				FriendView.this.profileImg_resized = imageResized(selectImg, resources.Dimens.PROFILE_IMG_SIZE);
				btnProfileImg.setIcon(FriendView.this.profileImg_resized);

				ChatMsg obcm = new ChatMsg(FriendView.this.userName, "500", "change profile img"); // 프로필 사진 변경
				obcm.setProfileImg_ori(FriendView.this.profileImg);
				obcm.setProfileImg_resized(FriendView.this.profileImg_resized);
				FriendView.this.parent.SendObject(obcm);
			}
		});
		
		// 내 닉네임
		lblUserName = new JLabel(userName);
		lblUserName.setFont(resources.Fonts.MAIN_BOLD_16);
		lblUserName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lblUserName.setBounds(75, 55, 100, 30);
		add(lblUserName);

		// "친구"
		lblFriendTxt = new JLabel(resources.Strings.FRIEND);
		lblFriendTxt.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblFriendTxt.setFont(resources.Fonts.MAIN_BOLD_16);
		lblFriendTxt.setForeground(resources.Colors.MAIN_WHITE_COLOR);
		lblFriendTxt.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		lblFriendTxt.setOpaque(true);
		lblFriendTxt.setBounds(10, 105, 330, 25);
		add(lblFriendTxt);

		
		//initFriendList();
		friendListPanel = new JPanel(new GridBagLayout());
		friendListPanel.setBackground(resources.Colors.MAIN_BG_COLOR);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.weighty = 0;
		JPanel p = new JPanel();
		p.setBackground(resources.Colors.MAIN_BG_COLOR);
		friendListPanel.add(p, gbc);
		
		

		friendListScrollPane = new JScrollPane(friendListPanel);
		friendListScrollPane.setBounds(10, 140, 325, 370);
		friendListScrollPane.setBackground(resources.Colors.MAIN_BG_COLOR);
		// userListScrollPane.setPreferredSize(new Dimension(300, 330));
		add(friendListScrollPane);

		//FriendViewPanel.this.parent.SendObject(new ChatMsg(userName, "600", "all"));//??
		//FriendViewPanel.this.parent.SendObject(new ChatMsg(userName, "700", "all"));
		
	}
	
//	public void setFriendList(ArrayList<ChatMsg> friends) {
//		
//	}
	
	public void addFriend(ChatMsg cm) {
		
		FriendProfileItem friend = new FriendProfileItem(this, cm);
//		FriendVec.add(friend);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		friendListPanel.add(((JPanel)friend), gbc, friendListIdx);
		friendListIdx++;
		
		
		//friendListPanel.add(((JPanel)friend), gbc, FriendVec.size()-1);

		
		friendListPanel.revalidate();
		friendListPanel.repaint();
	}
	
	public void removeFriend(FriendProfileItem friend) {
		
		friendListPanel.remove(friend); // idx--??
		friendListPanel.revalidate();
		friendListPanel.repaint();
//		
//		for(FriendProfileItem f : FriendVec) {
//			if(f.getUserName().equals(friend.getUserName()));
//			FriendVec.remove(f);
//		}
	}
	
	public void initFriendList() { // 목록 재세팅을 위해 전부 삭제
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

		friendListPanel.removeAll();
		
		// 이거 두개 필요한가?
		friendListPanel.revalidate();
		friendListPanel.repaint();
		
		friendListIdx = 0;
	}
	
	// 내 프로필 setting
	public void setMyProfile(String myName, ImageIcon myImg) {
		this.userName = myName;
		this.btnProfileImg.setIcon(imageResized(myImg, PROFILE_SIZE));
	}

	

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

}
