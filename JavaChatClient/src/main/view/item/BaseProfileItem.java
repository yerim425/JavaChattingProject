package main.view.item;

import java.awt.BorderLayout;
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
	//protected EmptyBorder border = new EmptyBorder(5, 5, 5, 5);

	// JPanel profilePanel = new JPanel();
	private JButton btnProfileImg; // 프로필 사진
	private JLabel lbluserName; // 유저 닉네임
	
	protected String userName; // user.getData() 문자열을 "/"으로 자른 0번째 데이터
	protected String dataMsg; // .. 1번째 데이터
	protected JPanel contentPane;
	

	public BaseProfileItem(ChatMsg user) {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(325, 60));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
		setOpaque(false);
		setAlignmentX(LEFT_ALIGNMENT); 
		
		contentPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    contentPane.setBorder(new EmptyBorder(5,5,5,5));
	    contentPane.setBackground(resources.Colors.MAIN_BLUE_COLOR);
	    contentPane.setOpaque(true);
		//this.parent = parent;
		this.user = user;
		
		String[] str = user.getData().split("/");
		userName = str[0]; 
		dataMsg = str[1];

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
		contentPane.add(btnProfileImg);

		// 유저 닉네임
		lbluserName = new JLabel(userName);
		lbluserName.setBorder(new EmptyBorder(5, 5, 5, 5));
		lbluserName.setFont(resources.Fonts.MAIN_BOLD_16);
		lbluserName.setForeground(resources.Colors.MAIN_DARK_BLUE_COLOR);
		lbluserName.setPreferredSize(new Dimension(115, 30));
		contentPane.add(lbluserName);
		
		add(contentPane, BorderLayout.CENTER);
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
		return this.userName;
	}
	
}
