package com.wavemaker.runtime.ws.salesforce.operations;

import java.util.List;

import com.wavemaker.runtime.ws.salesforce.LoginObject;
import com.wavemaker.runtime.ws.salesforce.gen.SearchRecordType;
import com.wavemaker.runtime.ws.salesforce.gen.SearchResponse;
import com.wavemaker.runtime.ws.salesforce.gen.SessionHeader;
import com.wavemaker.runtime.ws.salesforce.gen.SforceService;

public class Search {

	public static List<SearchRecordType> search(String searchString, SessionHeader sessionHeader, SforceService service) throws Exception {

		com.wavemaker.runtime.ws.salesforce.gen.Search parameters = new com.wavemaker.runtime.ws.salesforce.gen.Search();

		parameters.setSearchString(searchString);

		SearchResponse response = service.search(parameters, sessionHeader, null, null);
		return response.getResult().getSearchRecords();
	}
	
	public static void main(String[] args) throws Exception {
		LoginObject login = new LoginObject();
		login.logIn("kvinod.kakarla@gmail.com", "Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G");
		SessionHeader sessionHeader = login.getSessionHeader();
		SforceService service = login.getSforceService();
		System.out.println(search("FIND {*} IN Name FIELDS RETURNING  Contact(Id, Phone, FirstName, LastName), Lead(Id, Phone, FirstName, LastName), Account(Id, Phone, Name)" ,sessionHeader, service));
		
	}
	
}
