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
import main.view.ImgFrame;

public abstract class BaseProfileItem extends JPanel {

	private static final long serialVersionUID = 1L;
	protected int PROFILE_SIZE = resources.Dimens.PROFILE_IMG_SIZE;
	
	protected ChatMsg user;
	protected EmptyBorder border = new EmptyBorder(5, 5, 5, 5);

	// JPanel profilePanel = new JPanel();
	private JButton btnProfileImg; // 프로필 사진
	private JLabel lbluserName; // 유저 닉네임
	

	public BaseProfileItem(ChatMsg user) {
		//this.parent = parent;
		this.user = user;

		setLayout(new FlowLayout());
		setBackground(resources.Colors.MAIN_BG_COLOR);

		// 유저 프로필 사진 (버튼)
		btnProfileImg = new JButton();
		btnProfileImg.setPreferredSize(new Dimension(PROFILE_SIZE, PROFILE_SIZE));
		btnProfileImg.setIcon(profileImgResized(user.getProfileImg_ori(), PROFILE_SIZE));
		// btnProfileImg.setBackground(null);
		btnProfileImg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // 친구 프로필 사진의 원본을 보여줌
				ImgFrame imgFrame = new ImgFrame(BaseProfileItem.this.user.getProfileImg_ori());
				imgFrame.setVisible(true);
			}

		});
		add(btnProfileImg);

		// 유저 닉네임
		lbluserName = new JLabel(user.getId());
		lbluserName.setBorder(border);
		lbluserName.setFont(resources.Fonts.MAIN_BOLD_16);
		lbluserName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lbluserName.setPreferredSize(new Dimension(115, 30));
		add(lbluserName);

	}

	// 프로필에 적용할 사진 resize
	public ImageIcon profileImgResized(ImageIcon ori_icon, int size) {
		Image img = ori_icon.getImage();
		int width = ori_icon.getIconWidth();
		int height = ori_icon.getIconHeight();

		return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));

	}
	
	public abstract void SendObject(ChatMsg cm);
	
	public String getUserName() {
		return user.getId();
	}

}
