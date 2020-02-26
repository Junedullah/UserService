/**SmartSoftware User - Service */
/**
 * Description: CLXSMSUtility
 * Name of Project: SmartSoftware
 * Created on: Feb 10, 2020
 * Modified on: Feb 10, 2020 4:19:38 PM
 * @author Juned
 * Version: 
 */

package com.ss.util;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@SuppressWarnings("deprecation")
@Component
public class CLXSMSUtility {

	private static final Logger LOGGER = Logger.getLogger(CLXSMSUtility.class);
	
	/**
	 * @param from
	 * @param messageBody
	 * @param numberList
	 */
	@Async
	public void sendSMS(String from, String messageBody, List<String> numberList){
		
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost("https://api.clxcommunications.com/xms/v1/btitd12/batches");
            JSONObject messageJSON = new JSONObject();
            messageJSON.put("from", from);	// from
            messageJSON.put("body", messageBody);	// message body
            messageJSON.put("to", new JSONArray(numberList));	//cell numbers 
            StringEntity requestEntity = new StringEntity(messageJSON.toString(), HTTP.UTF_8);
            request.addHeader("Content-Type", "application/json;charset=utf-8");
            request.addHeader("Authorization", "Bearer 3f14b1bb396746aab9ed9cf7fd58ef84");
            request.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            LOGGER.info("Response Body --->>"+responseBody);
        }catch (Exception ex) {
        	LOGGER.info("Error occured due to ::"+ex.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
	}
	

}
