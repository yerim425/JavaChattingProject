package main.view;

import data.ChatMsg;
import main.ChatClientMain;

public class SettingView extends BaseView {

	private static final long serialVersionUID = 1L;

	public SettingView(ChatClientMain parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

	public int calculatePanelHeight() {
		return 0;
	}

}
