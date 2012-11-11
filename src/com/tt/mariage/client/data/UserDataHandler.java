package com.tt.mariage.client.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tt.mariage.client.services.UserData;

public class UserDataHandler {
	private UserData userData;
	private List<LoadHandler> loadHandlerList;
	
	public UserDataHandler(UserData userData) {
		this.userData = userData;
		loadHandlerList = new ArrayList<LoadHandler>();
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
	public void addLoadHandler(LoadHandler loadHandler){
		loadHandlerList.add(loadHandler);
	}
	
	public void load(){
		Iterator<LoadHandler> iterator = loadHandlerList.iterator();
		while(iterator.hasNext()){
			LoadHandler loadHandler = iterator.next();
			loadHandler.load(userData);
		}
	}
}
