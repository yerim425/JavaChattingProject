package main.view.item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import data.ChatMsg;
import main.view.UserViewPanel;

public class UserProfileItem extends BaseProfileItem {

	private static final long serialVersionUID = 1L;
	private JButton btnUser;  // 친구 버튼
	private UserViewPanel parent;
	
	
	public UserProfileItem(UserViewPanel parent, ChatMsg user) {
		//super(parent, user);
		super(user);
		this.parent = parent;
		setBorder(border);
		
		
		
		// 친구 버튼 추가
		btnUser  = new JButton(); 
		btnUser.setPreferredSize(new Dimension(125, 30));
		btnUser.setFont(resources.Fonts.MAIN_BOLD_12);
		btnUser.setBackground(resources.Colors.MAIN_BG_COLOR);
		btnUser.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		btnUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnUser.setUI(new BasicButtonUI()); // LookAndFeel 영향 최소화
		

		
		if (!user.getId().equals(userName)) { // 다른 사용자
			if (dataMsg.equals("friend")) { 		// case1) 친구
				btnUser.setText("친구");
				
			} else if (dataMsg.equals("wait")) { // case2) 친구x, 요청o
				btnUser.setText("친구 요청 보냄");
				btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
				
			} else if(dataMsg.equals("recv")) { // 친구x, 친구신청 받음
				btnUser.setText("친구 요청 수락");
				btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
				btnUser.setForeground(resources.Colors.MAIN_WHITE_COLOR);
				//btnUser.setBorder(border);
				
				btnUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// 친구 요청 수락/거절 다이얼로그 창 띄움
						int result = JOptionPane.showConfirmDialog(null, userName + "님의 친구 요청을 승낙하겠습니까?", "Confirm",
								JOptionPane.YES_NO_CANCEL_OPTION);

						if (result == JOptionPane.YES_OPTION) { // 친구 요청 수락
							ChatMsg yesMsg = new ChatMsg(user.getId(), "720", userName);
							SendObject(yesMsg);
							btnUser.setText("친구");
							btnUser.setBackground(resources.Colors.MAIN_BG_COLOR);
							btnUser.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
						} else if (result == JOptionPane.NO_OPTION) { // 친구 요청 거절
							ChatMsg noMsg = new ChatMsg(user.getId(), "730", userName);
							SendObject(noMsg);
							
							btnUser.setText("친구 요청 보내기");
							btnUser.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									ChatMsg f = new ChatMsg(user.getId(), "710", userName);
									SendObject(f);
									btnUser.setText("친구 요청 보냄");
									btnUser.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
									
								}
							});
						}
						
						btnUser.repaint();
					}
				});
			}else if (dataMsg.equals("none")) { // case3) 친구x, 요청x
				btnUser.setText("친구 요청 보내기");
				btnUser.setEnabled(true);
				btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
				btnUser.setForeground(resources.Colors.MAIN_WHITE_COLOR);
				btnUser.setBorder(border);
				
				btnUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChatMsg f = new ChatMsg(user.getId(), "710", userName);
						SendObject(f);
						btnUser.setText("친구 요청 보냄");
						btnUser.setEnabled(false);
						
					}
				});
			}
			
		} else if (user.getId().equals(userName)) { // 나
			btnUser.setText("나");
		} 
		add(btnUser);

		
		
//		if (!user.getId().equals(parent.getUserName())) { // 다른 사용자
//			if (user.getData().equals("friend")) { 		// case1) 친구
//				btnUser.setText("친구");
//				
//				
//			} else if (user.getData().equals("wait")) { // case2) 친구x, 요청o
//				btnUser.setText("친구 요청 보냄");
//				
//				
//			} else if(user.getData().equals("recv")) { // 친구x, 친구신청 받음
//				btnUser.setText("친구 요청 수락");
//				btnUser.setEnabled(true);
//				btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
//				btnUser.setForeground(resources.Colors.MAIN_WHITE_COLOR);
//				//btnUser.setBorder(border);
//				
//				btnUser.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//
//						// 친구 요청 수락/거절 다이얼로그 창 띄움
//						int result = JOptionPane.showConfirmDialog(null, user.getId() + "님의 친구 요청을 승낙하겠습니까?", "Confirm",
//								JOptionPane.YES_NO_CANCEL_OPTION);
//
//						if (result == JOptionPane.YES_OPTION) { // 친구 요청 승낙
//							ChatMsg yesMsg = new ChatMsg(user.getId(), "720", user.getId());
//							SendObject(yesMsg);
//							btnUser.setText("친구");
//							btnUser.setBackground(resources.Colors.MAIN_BG_COLOR);
//							btnUser.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
//						} else if (result == JOptionPane.NO_OPTION) { // 친구 요청 거절
//							ChatMsg noMsg = new ChatMsg(user.getId(), "730", user.getId());
//							SendObject(noMsg);
//							
//							btnUser.setText("친구");
//							btnUser.addActionListener(new ActionListener() {
//								public void actionPerformed(ActionEvent e) {
//									ChatMsg f = new ChatMsg(parent.getUserName(), "710", user.getId());
//									SendObject(f);
//									btnUser.setText("친구 요청 보냄");
//									btnUser.setEnabled(false);
//									
//								}
//							});
//						}
//					}
//				});
//			}else if (user.getData().equals("none")) { // case3) 친구x, 요청x
//				btnUser.setText("친구 요청 보내기");
//				btnUser.setEnabled(true);
//				btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
//				btnUser.setForeground(resources.Colors.MAIN_WHITE_COLOR);
//				btnUser.setBorder(border);
//				
//				btnUser.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						ChatMsg f = new ChatMsg(parent.getUserName(), "710", user.getId());
//						SendObject(f);
//						btnUser.setText("친구 요청 보냄");
//						btnUser.setEnabled(false);
//						
//					}
//				});
//			}
//			
//		} else if (user.getId().matches(parent.getUserName())) { // 나
//			btnUser.setText("test");
//		} 
//		add(btnUser);

		

	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

	


}
