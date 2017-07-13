package com.qait.svm.RestWs;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qait.svm.RestWs.GetWebService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class WSTest{
	
	GetWebService getWebService;
	WebResource webData;
	
	@BeforeTest
	public void loadWebService(){
        Client restClient = Client.create();
        
        webData = restClient.resource("http://10.0.1.86/snl/soap/v1/wsdl");
        getWebService=new GetWebService();
	}
	
	@Test
	public void statusCode_method_should_return_200_if_request_processed(){
             assertEquals(getWebService.statusCode(), 200);

	}
	@Test
	public void generateResponse_method_should_return_length_of_XML_file(){
		 
             assertEquals(getWebService.generateResponse(webData).length(), 11331);

	}
	@Test
	public void getListOfBoardsAsJsonString_method_should_return_list_of_boards_in_json(){
             assertEquals(getWebService.getListOfBoardsAsJsonString().length(), 700);

	}
	@Test
	public void isInvalidPlayer_method_should_check_player_is_Invalid_when_pass_invalid_id(){
             assertEquals(getWebService.isInvalidPlayer(4433), true);

	}
}
    