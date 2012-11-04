package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("retrieve")
public interface RetrieveService extends RemoteService {
  public UserData retrieve(String mail);
}
