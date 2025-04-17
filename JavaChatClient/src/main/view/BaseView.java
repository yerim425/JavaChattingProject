package main.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.ChatMsg;
import main.ChatClientMain;

public abstract class BaseView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected int PROFILE_SIZE = resources.Dimens.PROFILE_IMG_SIZE;
	

	protected ChatClientMain parent;
	protected String userName;
	protected ImageIcon profileImg;
	protected ImageIcon profileImg_resized;

	public abstract void SendObject(ChatMsg cm);
	
	public BaseView(ChatClientMain parent, String name) {
		this.parent = parent;
		this.userName = name;
		this.profileImg = new ImageIcon("src/images/profile_default.png");
	}

	
	// 프로필에 적용할 사진 resize
	public ImageIcon imageResized(ImageIcon ori_icon, int size) {
		Image img = ori_icon.getImage();
		int width = ori_icon.getIconWidth();
		int height = ori_icon.getIconHeight();

		return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));

	}

}
