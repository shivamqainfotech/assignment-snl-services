package com.qait.svm.RestWs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
 /**
  * 
  * @author shivambharadwaj
  *
  */
public class GetWebService {
	
 public ClientResponse response;
 Client restClient = Client.create();
	public String generateResponse(WebResource webData){
       response = webData.accept("application/json").get(ClientResponse.class);
        String outputOfResponse = response.getEntity(String.class);
        
        return outputOfResponse;  
	}
	
	/**
	 * This method returns status code of the response
	 * @return int
	 * @author shivambharadwaj
	 */
	public int statusCode(){
	        if(response.getStatus() == 200){
	        	return response.getStatus();
	        }else{
	            System.err.println("Unable to connect to the server");
	        }
			return 0;
	        
	}
	/**
	 * This method returns String of board in json format
	 * @return string of json 
	 * @author shivambharadwaj
	 */
	public String getListOfBoardsAsJsonString(){
		
        WebResource webData = restClient.resource("http://10.0.1.86/snl/rest/v1/board/new.json");
        String json=webData.accept("application/json").get(ClientResponse.class).getEntity(String.class);
       
	return json;
	}

/**
 * This methos is used to verify the player
 * @author shivambharadwaj
 * @param id
 * @return boolean
 */
	public boolean isInvalidPlayer(int id) {
		WebResource webData = restClient.resource("http://10.0.1.86/snl//rest/v1/board/"+id+".json");
     boolean status=webData.accept("application/json").get(ClientResponse.class).getEntity(String.class).contains("Invalid board id");
		return status;
	}
    
}