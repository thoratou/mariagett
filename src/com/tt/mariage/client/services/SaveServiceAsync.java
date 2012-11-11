package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SaveServiceAsync {
  public void save(UserData userData, AsyncCallback<SaveData> async);
}
