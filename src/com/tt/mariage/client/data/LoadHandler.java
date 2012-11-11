package com.tt.mariage.client.data;

import com.tt.mariage.client.services.UserData;

public abstract class LoadHandler {
	public LoadHandler() {
		
	}
	
	public abstract void load(UserData userData);
}
