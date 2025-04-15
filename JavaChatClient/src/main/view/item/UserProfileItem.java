package main.view.item;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		btnUser.setPreferredSize(new Dimension(125, 25));
		btnUser.setFont(resources.Fonts.MAIN_BOLD_10);
		btnUser.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		btnUser.setBackground(resources.Colors.MAIN_BLUE2_COLOR);
		btnUser.setEnabled(false);
		
		if (!user.getId().matches(parent.getUserName())) { // 다른 사용자
			if (user.getData().matches("friend")) { // 친구
				btnUser.setText("친구");
			} else if (user.getData().matches("wait")) { // 친구x, 요청o
				btnUser.setText("친구 요청 보냄");
			} else if (user.getData().matches("none")) { // 친구x, 요청x
				btnUser.setText("친구 요청 보내기");
				btnUser.setEnabled(true);
				btnUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						ChatMsg f = new ChatMsg(user.getId(), "701", user.getId());
						SendObject(f);
						btnUser.setText("친구 요청 보냄");
					}
				});
			}
			
		} else if (user.getId().matches(parent.getUserName())) {
			btnUser.setText("");
		} 
		add(btnUser);

		

	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

	


}
