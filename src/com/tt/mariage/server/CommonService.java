package com.tt.mariage.server;

public class CommonService {
	public String convertException(Exception exception){
		String ret = exception.getMessage()+"<br/>";
		ret+=convertStackTrace(exception.getStackTrace());
		return ret;
	}
	
	public String convertStackTrace(StackTraceElement[] elements){
		String ret = "";
		for(int i=0; i<elements.length && i<10; ++i){
			ret += "[";
			ret += elements[i].getClassName();
			ret += ".";
			ret += elements[i].getMethodName();
			ret += " / ";
			ret += elements[i].getFileName();
			ret += "#";
			ret += elements[i].getLineNumber();
			ret += "]";
			if(i<elements.length && i<10)
				ret+= "<br/>";
		}
		return ret;
	}
}
