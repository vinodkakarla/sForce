package com.wavemaker.runtime.ws.salesforce.operations;

import java.util.List;

import com.wavemaker.runtime.ws.salesforce.LoginObject;
import com.wavemaker.runtime.ws.salesforce.gen.ChildRelationshipType;
import com.wavemaker.runtime.ws.salesforce.gen.DescribeSObject;
import com.wavemaker.runtime.ws.salesforce.gen.DescribeSObjectResponse;
import com.wavemaker.runtime.ws.salesforce.gen.DescribeSObjectResultType;
import com.wavemaker.runtime.ws.salesforce.gen.SessionHeader;
import com.wavemaker.runtime.ws.salesforce.gen.SforceService;

public class FetchDescSObjects {

	public static List<ChildRelationshipType> getSObjectDescription(String sObjectType, SessionHeader sessionHeader, SforceService service) throws Exception {
		DescribeSObject parameters = new DescribeSObject();
		parameters.setSObjectType(sObjectType);
		DescribeSObjectResponse response = service.describeSObject(parameters, sessionHeader, null, null, null);
		DescribeSObjectResultType resultType = response.getResult();
		return resultType.getChildRelationships();
	}
	
	public static void main(String[] args) throws Exception {
		LoginObject login = new LoginObject();
		login.logIn("kvinod.kakarla@gmail.com", "Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G");
		SessionHeader sessionHeader = login.getSessionHeader();
		SforceService service = login.getSforceService();
		System.out.println(getSObjectDescription("Account" ,sessionHeader, service));
		
	}
	
}
