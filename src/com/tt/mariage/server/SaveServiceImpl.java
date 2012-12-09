package com.tt.mariage.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.services.SaveData;
import com.tt.mariage.client.services.SaveData.Status;
import com.tt.mariage.client.services.SaveService;
import com.tt.mariage.client.services.UserData;
import com.tt.mariage.server.proto.UserDataProtos;
import com.tt.mariage.server.proto.UserDataProtos.Blob.Builder;

public class SaveServiceImpl extends RemoteServiceServlet implements
    SaveService {

	private static final long serialVersionUID = 405169472121812429L;
	
	private Connection connection = null;
	
	private CommonService commonService = new CommonService();

	@Override
	public SaveData save(UserData userData) {
		SaveData saveData = new SaveData();
		saveData.setStatus(Status.Undef);
		saveData.setMessage("");
		
		//saveData.setStatus(Status.InternalError);
		//saveData.setMessage(debug(userData));
		
		
		Builder newBuilder = UserDataProtos.Blob.newBuilder();
		fillFromUserData(userData,newBuilder);
		
		String home = System.getenv("MARIAGETT_HOME");
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy_MM_dd_HH_mm_ss_SSS");
		
		String dataFile = home + "/" + userData.getMail() + "_" + dateFormat.format(now) + ".sav";
		
		// Sava data file
		try {
			FileOutputStream output = new FileOutputStream(dataFile);
			newBuilder.build().writeTo(output);
			output.close();
			saveData.setStatus(Status.SaveOK);
		} catch (FileNotFoundException e) {
	    	saveData.setStatus(Status.InternalError);
	    	saveData.setMessage(commonService.convertException(e));
		} catch (IOException e) {
	    	saveData.setStatus(Status.InternalError);
	    	saveData.setMessage(commonService.convertException(e));
		}
		
		//Update DB
		try {
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:"+home+"/mariagett.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);
			
			//check if user exit
			ResultSet rs = statement.executeQuery("select count(*) from USERS where MAIL=\""+userData.getMail()+"\"");
			int count = 0;
			while(rs.next())
			{
				count = rs.getInt("count(*)");
			}
			rs.close();
			
			if(count == 0){
				saveData.setStatus(Status.InternalError);
				saveData.setMessage("User "+userData.getMail()+" doesn't exist in database");
			}
			else if(count == 1){
				//exists => update password
		        PreparedStatement updateStatement = connection.prepareStatement("update USERS set DATAFILE=? where MAIL=?");
		        updateStatement.setQueryTimeout(10);
		        updateStatement.setString(2, userData.getMail());
		        updateStatement.setString(1, dataFile);
		        updateStatement.executeUpdate();
		        updateStatement.close();
			}
			else{
				saveData.setStatus(Status.InternalError);
				saveData.setMessage("Several users found for "+userData.getMail());				
			}

		} catch (ClassNotFoundException e) {
			saveData.setStatus(Status.InternalError);
			saveData.setMessage(commonService.convertException(e));
		} catch (SQLException e) {
			saveData.setStatus(Status.InternalError);
			saveData.setMessage(commonService.convertException(e));
		}
	    finally
	    {
			try
			{
				if(connection != null){
					connection.close();
					connection = null;
				}
			}
			catch(SQLException e)
			{
				saveData.setMessage(commonService.convertException(e));
			}
	    }
		
		return saveData;
	}

	private void fillFromUserData(UserData userData, Builder newBuilder) {
		newBuilder.setMail(userData.getMail());
		
		Iterator<Person> iterator = userData.getPersonList().iterator();
		while(iterator.hasNext()){
			Person person = iterator.next();
			com.tt.mariage.server.proto.UserDataProtos.Blob.Person.Builder builder = UserDataProtos.Blob.Person.newBuilder();
			builder.setLastName(person.getName());
			builder.setFirstName(person.getFirstname());
			builder.setIsInfant(person.isInfant());
			if(person.getMenu() != null)
				builder.setMenu(person.getMenu());
			newBuilder.addPerson(builder);
		}
		
		newBuilder.setIsBookHotel(userData.isWantHotelBooking());
		if(userData.getArrivalDate() != null && !"".equals(userData.getArrivalDate()))
			newBuilder.setArrivalDate(userData.getArrivalDate());
		if(userData.getDepartureDate() != null && !"".equals(userData.getDepartureDate()))
			newBuilder.setDepartureDate(userData.getDepartureDate());
		
		newBuilder.setHasCar(userData.isHasCar());
		if(userData.getFreePlaces() != null && !"".equals(userData.getFreePlaces()))
			newBuilder.setFreePlaces(userData.getFreePlaces());
		
		if(userData.getPhoneNumber() != null && !"".equals(userData.getPhoneNumber()))
			newBuilder.setPhoneNumber(userData.getPhoneNumber());
		
		if(userData.getOtherInfo() != null && !"".equals(userData.getOtherInfo()))
			newBuilder.setOtherInfo(userData.getOtherInfo());
	}

	/*private String debug(UserData userData) {
		String string = "mail : " + userData.getMail() + "<br/>";

		Iterator<Person> iterator = userData.getPersonList().iterator();
		while(iterator.hasNext()){
			Person person = iterator.next();
			string += "[" + person.getName() + " , " + person.getFirstname() + " , " + (person.isInfant() ? "true" : "false") + " , " + person.getMenu() + "]<br/>";
		}
		
		string += "hotel booking : " + (userData.isWantHotelBooking() ? "true" : "false") + "<br/>";
		string += "arrival date : " + userData.getArrivalDate().toString() + "<br/>";		
		string += "departure date : " + userData.getDepartureDate().toString() + "<br/>";		
		string += "has car : " + (userData.isHasCar() ? "true" : "false") + "<br/>";
		string += "free places : " + userData.getFreePlaces() + "<br/>";
		string += "phone number : " + userData.getPhoneNumber() + "<br/>";
		string += "other info : " + userData.getOtherInfo() + "<br/>";
		
		return string;
	}*/
}