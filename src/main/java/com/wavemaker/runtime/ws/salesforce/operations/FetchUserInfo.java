package com.wavemaker.runtime.ws.salesforce.operations;

import com.wavemaker.runtime.ws.salesforce.LoginObject;
import com.wavemaker.runtime.ws.salesforce.gen.GetUserInfo;
import com.wavemaker.runtime.ws.salesforce.gen.GetUserInfoResponse;
import com.wavemaker.runtime.ws.salesforce.gen.GetUserInfoResultType;
import com.wavemaker.runtime.ws.salesforce.gen.SessionHeader;
import com.wavemaker.runtime.ws.salesforce.gen.SforceService;

public class FetchUserInfo {

	public static String getUserInfo(SessionHeader sessionHeader, SforceService service) throws Exception {
		GetUserInfo userInfo = new GetUserInfo();
		GetUserInfoResponse response = service.getUserInfo(userInfo, sessionHeader, null);
		GetUserInfoResultType resultType = response.getResult();
		return resultType.getUserFullName() + " | " + resultType.getUserEmail();
	}
	
	public static void main(String[] args) throws Exception {
		LoginObject login = new LoginObject();
		login.logIn("kvinod.kakarla@gmail.com", "Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G");
		SessionHeader sessionHeader = login.getSessionHeader();
		SforceService service = login.getSforceService();
		System.out.println(getUserInfo(sessionHeader, service));
		
	}

}
