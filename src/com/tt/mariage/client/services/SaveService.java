package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("save")
public interface SaveService extends RemoteService {
  public SaveData save(UserData userData);
}
