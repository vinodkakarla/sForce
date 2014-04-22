package com.wavemaker.runtime.ws.salesforce.operations;

import java.util.List;

import com.wavemaker.runtime.ws.salesforce.LoginObject;
import com.wavemaker.runtime.ws.salesforce.gen.SearchRecordType;
import com.wavemaker.runtime.ws.salesforce.gen.SessionHeader;
import com.wavemaker.runtime.ws.salesforce.gen.SforceService;

public class SearchLeads {

	public static List<SearchRecordType> getSearchLeads(String sObjectType,
			SessionHeader sessionHeader, SforceService service)
			throws Exception {
		String leadSearchString = "FIND {%s*} IN Name FIELDS RETURNING  Lead(Id, Phone, Name, LastName)";

		return Search.search(String.format(leadSearchString, sObjectType),
				sessionHeader, service);
	}

	public static void main(String[] args) throws Exception {
		LoginObject login = new LoginObject();
		login.logIn("kvinod.kakarla@gmail.com",
				"Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G");
		SessionHeader sessionHeader = login.getSessionHeader();
		SforceService service = login.getSforceService();
		List<SearchRecordType> list = getSearchLeads("Vinod", sessionHeader, service);
		System.out.println(list.get(0).getRecord().getAnies().get(1).getFirstChild().getNodeValue());

	}

}
