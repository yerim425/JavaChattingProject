package data;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ChatRoom {
	
	private int roomId = -1;
	private String userList;
	private String[] userNameList;
	private String roomName;
	private JPanel roomImg;
	private String lastMsg;
	private String lastTime;
	private Vector<ChatMsg> chatMsgs = new Vector<ChatMsg>();
	
	private ImageIcon emoticon;
	
	
	private ImageIcon chatImg_ori;
	private ImageIcon chatImg_resized;
	
	public ChatRoom(int id, String nameList){
		roomId = id;
		userList = nameList;
		userNameList = nameList.split(" ");
		roomName = userList.replaceAll(" ", ", ");
	}
	
	public int getRoomId() {
		return roomId;
	}
	
	public String getUserList() {
		return userList;
	}
	
	public void addUser(String user) {
		userList = userList + " user";
	}
	
	public void exitUser(String user) {
		//userList = 
	}
	public String[] getUserNameList() {
		return userNameList;
	}

	public void setRoomImgIcon(JPanel img) {
		roomImg = img;
	}
	public JPanel getRoomImg() {
		return roomImg;
	}
	
	public void setRoomName(String name) {
		roomName = name;
	}
	
	public String getRoomName() {
		return roomName;
	}
	
	public void setLastMsg(String msg) {
		lastMsg = msg;
	}
	
	public String getLastMsg() {
		return lastMsg;
	}
	
	public void setLastTime(String time) {
		lastTime = time;
	}
	
	public String getLastTime() {
		return lastTime;
	}
	
	public void addChatMsg(ChatMsg cm) {
		chatMsgs.add(cm);
	}
	
	public Vector<ChatMsg> getChatMsgs(){
		return chatMsgs;
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
	public void setRoomId(int id) {
		this.roomId = id;
	}
	
	
	public void setUserList(String list) {
		userList = list;
	}
	
	public void setEmoticon(ImageIcon icon) {
		this.emoticon = icon;
	}
	
	public ImageIcon getEmoticon() {
		return emoticon;
	}
	

}
