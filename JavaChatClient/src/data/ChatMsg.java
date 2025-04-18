package data;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;

public class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String status;
	private String code;
	private String data;
	private ChatRoom roomData;
	private Vector<ChatRoom> roomVec = new Vector<ChatRoom>();
	
	// 프로필 사진 ---------------------
	private ImageIcon profileImg_ori;
	private ImageIcon profileImg_resized;

	// 채팅 사진
	//private ImageIcon chatImg;
	//private int roomId = -1;
	
	
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
	
	// 프로필 사진 ------------------------------------------
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


	// 채팅방 ---------------------------------------------
	public void setRoomData(ChatRoom cr) {
		this.roomData = cr;
	}
	
	public ChatRoom getRoomData() {
		return this.roomData;
	}


	public void addChatRoom(ChatRoom cr) {
		this.roomVec.addElement(cr);
	}
	
	public void setRoomVec(Vector<ChatRoom> vec) {
		this.roomVec = vec;
	}
	
	public Vector<ChatRoom> getRoomVec() {
		return this.roomVec;
	}
	
//	public void setChatImg(ImageIcon img) {
//		this.chatImg = img;
//	}
//	
//	public ImageIcon getChatImg() {
//		return chatImg;
//	}
//	
//	public void setRoomId(int id) {
//		this.roomId = id;
//	}
//	
//	public int getRoomId() {
//		return roomId;
//	}

}