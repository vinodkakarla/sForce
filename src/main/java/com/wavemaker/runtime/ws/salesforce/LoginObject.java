/*
 * Copyright (C) 2012-2013 CloudJee, Inc. All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.wavemaker.runtime.ws.salesforce;

import java.util.Arrays;

import org.w3c.dom.Element;

import com.wavemaker.runtime.ws.BindingProperties;
import com.wavemaker.runtime.ws.salesforce.gen.Create;
import com.wavemaker.runtime.ws.salesforce.gen.CreateResponse;
import com.wavemaker.runtime.ws.salesforce.gen.Login;
import com.wavemaker.runtime.ws.salesforce.gen.LoginResponse;
import com.wavemaker.runtime.ws.salesforce.gen.LoginScopeHeader;
import com.wavemaker.runtime.ws.salesforce.gen.Logout;
import com.wavemaker.runtime.ws.salesforce.gen.SObjectType;
import com.wavemaker.runtime.ws.salesforce.gen.Search;
import com.wavemaker.runtime.ws.salesforce.gen.SearchResponse;
import com.wavemaker.runtime.ws.salesforce.gen.SessionHeader;
import com.wavemaker.runtime.ws.salesforce.gen.SforceService;

public class LoginObject {

	private SforceService service;

	private SessionHeader sessionHeader = null;

	public String logIn(String userName, String password) throws Exception {
		if (this.sessionHeader != null) {
			return "You are already logged in";
		}

		Login parameters = new Login();
		parameters.setUsername(userName);
		parameters.setPassword(password);
		LoginResponse response = this.service.login(parameters,
				new LoginScopeHeader(), null);

		BindingProperties bindingProperties = new BindingProperties();
		bindingProperties.setEndpointAddress(response.getResult()
				.getServerUrl());
		this.service.setBindingProperties(bindingProperties);
		String sessionId = response.getResult().getSessionId();

		this.sessionHeader = new SessionHeader();
		this.sessionHeader.setSessionId(sessionId);

		return "Logged in successfully";
	}

	public String logOut() throws Exception {
		Logout parameters = new Logout();
		if (this.sessionHeader != null) {
			this.service.logout(parameters, this.sessionHeader, null);
			this.sessionHeader = null;
		} else {
			return "You are not logged in yet";
		}

		return "Logged out";
	}

	public SessionHeader getSessionHeader() throws Exception {
		if (this.sessionHeader == null) {
			throw new Exception("You must log in first");
		}
		return this.sessionHeader;
	}

	public SforceService getSforceService() throws Exception {
		if (this.service == null) {
			throw new Exception("You must log in first");
		}
		return this.service;
	}

	public void setSforceService(SforceService svc) {
		this.service = svc;
	}
	
	private String search() throws Exception {
		Search parameters = new Search();

		parameters.setSearchString("FIND {DummyAccount} IN Name FIELDS RETURNING  Contact(Id, Phone, FirstName, LastName), Lead(Id, Phone, FirstName, LastName), Account(Id, Phone, Name)");

		SearchResponse response = this.service.search(parameters,
				sessionHeader, null, null);
		System.out.println(response.getResult().getSforceReserved());
		return "Done";
	

	}

	String createDummyObject() throws Exception {
		Create parameters = new Create();
		SObjectType contact = new SObjectType();
		contact.setType("Contact");
		contact.setFieldsToNulls(Arrays.asList(new String[]{"LastName"}));
//		Element element = new 
		// contact.setField("FirstName", "Otto");
		// contact.setField("LastName", "Jespersen");
		// contact.setField("Salutation", "Professor");
		// contact.setField("Phone", "(999) 555-1234");
		// contact.setField("Title", "Philologist");

		// Add this sObject to an array
		SObjectType[] contacts = new SObjectType[1];
		contacts[0] = contact;

		parameters.setSObjects(Arrays.asList(contacts));

		CreateResponse response = this.service.create(parameters,
				sessionHeader, null, null, null, null, null, null, null, null);
		System.out.println(response.getResults());
		return "Done";
	}

	public static void main(String[] args) throws Exception {
		LoginObject obj = new LoginObject();
		obj.setSforceService(new SforceService());
		System.out.println(obj.logIn("kvinod.kakarla@gmail.com",
				"Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G"));
		System.out.println(obj.logIn("kvinod.kakarla@gmail.com",
				"Dummy@123NKw0a23ZoI3oVtf7KrmX3mz6G"));
		System.out.println(obj.search());
		System.out.println(obj.logOut());
	}

}
