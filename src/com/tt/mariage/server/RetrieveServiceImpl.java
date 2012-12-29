package com.tt.mariage.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.data.Person.Menu;
import com.tt.mariage.client.services.RetrieveData;
import com.tt.mariage.client.services.RetrieveData.Status;
import com.tt.mariage.client.services.RetrieveService;
import com.tt.mariage.client.services.UserData;
import com.tt.mariage.server.proto.UserDataProtos;
import com.tt.mariage.server.proto.UserDataProtos.Blob.Builder;
import com.tt.mariage.server.proto.UserDataProtos.Blob.Person;

public class RetrieveServiceImpl extends RemoteServiceServlet implements
    RetrieveService {

	private static final long serialVersionUID = 405169472121812429L;
	
	private Connection connection = null;
	
	private CommonService commonService = new CommonService();

	@Override
	public RetrieveData retrieve(String mail) {
		if(mail != null){
			mail = mail.toLowerCase().trim();	
		}
		
		RetrieveData retrieveData = new RetrieveData();
		retrieveData.setStatus(Status.Undef);
		retrieveData.setMessage("");

		UserData userData = new UserData();
		userData.setMail(mail);
		retrieveData.setUserData(userData);
		
		String home = System.getenv("MARIAGETT_HOME");
		String datafile = null;
		
		//Update DB
		try {
			Class.forName("org.sqlite.JDBC");
			
			connection = DriverManager.getConnection("jdbc:sqlite:"+home+"/mariagett.sqlite");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(10);
			
			//check if user exit
			ResultSet rs = statement.executeQuery("select DATAFILE from USERS where MAIL=\""+mail+"\"");
			while(rs.next())
			{
				datafile = rs.getString("DATAFILE");
			}
			rs.close();
			
		} catch (ClassNotFoundException e) {
			retrieveData.setStatus(Status.InternalError);
			retrieveData.setMessage(commonService.convertException(e));
		} catch (SQLException e) {
			retrieveData.setStatus(Status.InternalError);
			retrieveData.setMessage(commonService.convertException(e));
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
				retrieveData.setMessage(commonService.convertException(e));
			}
	    }
		
		if(datafile != null && !"".equals(datafile)){
			// save file => retrieve data
			Builder newBuilder = UserDataProtos.Blob.newBuilder();
			
			try {
				newBuilder.mergeFrom(new FileInputStream(datafile));
				
				fillFromDataFile(newBuilder,userData);
				
				retrieveData.setStatus(Status.RetrieveOK);

			} catch (FileNotFoundException e) {
				retrieveData.setStatus(Status.InternalError);
				retrieveData.setMessage(commonService.convertException(e));
			} catch (IOException e) {
				retrieveData.setStatus(Status.InternalError);
				retrieveData.setMessage(commonService.convertException(e));
			} catch (ParseException e) {
				retrieveData.setStatus(Status.InternalError);
				retrieveData.setMessage(commonService.convertException(e));
			}
		}
		else{
			//no save file => set default data
			userData.setMail(mail);
			userData.setPersonList(new ArrayList<com.tt.mariage.client.data.Person>());
			userData.setWantHotelBooking(false);
			userData.setHasCar(false);
			retrieveData.setStatus(Status.RetrieveOK);
		}

	    
	    return retrieveData;
	}

	private void fillFromDataFile(Builder newBuilder, UserData userData) throws ParseException {
		userData.setMail(newBuilder.getMail());
		
		ArrayList<com.tt.mariage.client.data.Person> personList = new ArrayList<com.tt.mariage.client.data.Person>();
		Iterator<Person> iterator = newBuilder.getPersonList().iterator();
		while(iterator.hasNext()){
			Person next = iterator.next();
			com.tt.mariage.client.data.Person person = new com.tt.mariage.client.data.Person();
			person.setName(next.getLastName());
			person.setFirstname(next.getFirstName());
			person.setInfant(next.getIsInfant());
			person.setMenu(Menu.values()[next.getMenu().ordinal()]);
			personList.add(person);
		}
		userData.setPersonList(personList);
		
		userData.setWantHotelBooking(newBuilder.getIsBookHotel());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");

		if(newBuilder.hasArrivalDate()){
			userData.setArrivalDate(dateFormat.parse(newBuilder.getArrivalDate()));
		}
		if(newBuilder.hasDepartureDate()){
			userData.setDepartureDate(dateFormat.parse(newBuilder.getDepartureDate()));
		}
		
		userData.setHasCar(newBuilder.getHasCar());
		if(newBuilder.hasFreePlaces())
			userData.setFreePlaces(newBuilder.getFreePlaces());
		if(newBuilder.hasPhoneNumber())
			userData.setPhoneNumber(newBuilder.getPhoneNumber());
		if(newBuilder.hasOtherInfo())
			userData.setOtherInfo(newBuilder.getOtherInfo());
	}
}