package main.view;

import data.ChatMsg;
import main.ChatClientMain;

public class SettingViewPanel extends BaseViewPanel {

	private static final long serialVersionUID = 1L;

	public SettingViewPanel(ChatClientMain parent, String name) {
		super(parent, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void SendObject(ChatMsg cm) {
		// TODO Auto-generated method stub
		parent.SendObject(cm);
		
	}

}
