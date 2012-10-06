package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("register")
public interface RegisterService extends RemoteService {
  public RegisterInfo register(String mail);
}
