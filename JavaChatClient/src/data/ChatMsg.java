// ChatMsg.java ä�� �޽��� ObjectStream ��.
package data;

import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String code; // 100:�α���, 400:�α׾ƿ�, 200:ä�ø޽���, 300:Image
	private String data;
	private ImageIcon chatImg_ori;
	private ImageIcon chatImg_resized;
	private ImageIcon profileImg_ori;
	private ImageIcon profileImg_resized;
	private int roomId = 0;
	private String userList;
	private JPanel roomImg;
	private ImageIcon emoticon;
	private String sendTime;
	
	public ChatMsg(String id, String code, String msg) {
		this.id = id;
		this.code = code;
		this.data = msg;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	public String getData() {
		return data;
	}
	
	public void setChatImg(ImageIcon img) {
		this.chatImg_resized = img;
	}
	
	public ImageIcon getChatImg() {
		return chatImg_resized;
	}
	
	public void setChatImg_ori(ImageIcon img) {
		this.chatImg_ori = img;
	}
	
	public ImageIcon getChatImg_ori() {
		return chatImg_ori;
	}
	
	public void setProfileImg_ori(ImageIcon img) {
		this.profileImg_ori = img;
	}
	
	public ImageIcon getProfileImg_ori() {
		return profileImg_ori;
	}
	public void setProfileImg_resized(ImageIcon img) {
		this.profileImg_resized = img;
	}
	
	public ImageIcon getProfileImg_resized() {
		return profileImg_resized;
	}
	
	public void setRoomId(int id) {
		this.roomId = id;
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public void setUserList(String list) {
		userList = list;
	}
	
	public String getUserList() {
		return userList;
	}
	
	public JPanel getRoomImg() {
		return roomImg;
	}
	public void setEmoticon(ImageIcon icon) {
		this.emoticon = icon;
	}
	
	public ImageIcon getEmoticon() {
		return emoticon;
	}
	
	public void setTime(String time) {
		this.sendTime = time;
	}
	
	public String getTime() {
		return sendTime;
	}

}