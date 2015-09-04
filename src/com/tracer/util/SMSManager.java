/**
 * @author Jp
 *
 */
package com.tracer.util;

import static com.tracer.common.Constants.SMS_API_STATUS;

/*import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;*/

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.tracer.dao.model.SMSResponseDetails;

public class SMSManager {
  protected transient final Log logger = LogFactory.getLog(SMSManager.class);

  private static SMSManager instance = null;
  
  // ========================================================================

  private SMSManager() {
  }

  // ========================================================================

  public static synchronized SMSManager getInstance() {
    if (instance == null) {
      instance = new SMSManager();
    }
    return instance;
  }

  // ========================================================================

  /*public void sendSMS(String mobileNumbers, String smsMessage) {
    logger.info("START of the method sendSMS, smsMessage");
    StringBuffer smsApiUrlBuffer = null;
    URL url = null;
    HttpURLConnection connection = null;
    int responseCode = 0;
    
    try {
      smsApiUrlBuffer = new StringBuffer("http://login.smsindiahub.in/API/WebSMS/Http/v1.0a/index.php");
      smsApiUrlBuffer.append("?username=corpone");
      smsApiUrlBuffer.append("&password=corpone1234");
      smsApiUrlBuffer.append("&sender=TRACER");
      smsApiUrlBuffer.append("&to="+mobileNumbers);
      smsApiUrlBuffer.append("&message="+smsMessage);
      
      if("on".equals(SMS_API_STATUS)) {
        url = new URL(smsApiUrlBuffer.toString());
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        responseCode = connection.getResponseCode();
        logger.info("responseCode : "+responseCode);
        
        if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
          logger.info("connected");
          BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          String response = "";
          String current;
            
          while((current = in.readLine()) != null) {
            response += current;
          }
          logger.info(response);
        } else if (responseCode >= 500) {
          logger.error("server error happened  ");
        } else if (responseCode >= 400) {
          logger.error("Error forbidden");
        }
        
        if(connection != null) {
          connection.disconnect();
        }
      } else {
        logger.info("Unable to send the sms, as the api status is off");
      }
    } catch (Exception e) {
      logger.error("PROBLEM in the method sendSMS");
    }
    logger.info("END of the method sendSMS");
  }*/

  //========================================================================
  
  public SMSResponseDetails sendSMS(String mobileNumbers, String smsMessage) {
    logger.info("START of the method sendSMS");
    ClientResource clientResource = null;
    Representation representation = null;
    SMSResponseDetails smsResponseDetails = null;
    StringBuffer smsApiUrlBuffer = null;
    JSONObject smsApiRespJSON = null;
    
    try {
      if("on".equals(SMS_API_STATUS)) {
        smsApiUrlBuffer = new StringBuffer("http://cloud.smsindiahub.in/vendorsms/pushsms.aspx");
        smsApiUrlBuffer.append("?user=corpone");
        smsApiUrlBuffer.append("&password=corpone123");
        smsApiUrlBuffer.append("&sid=TRACER");
        smsApiUrlBuffer.append("&msisdn="+mobileNumbers);
        smsApiUrlBuffer.append("&msg="+smsMessage);
        smsApiUrlBuffer.append("&fl=0");
        smsApiUrlBuffer.append("&gwid=2");
        clientResource = new ClientResource(smsApiUrlBuffer.toString());
        representation = clientResource.get(MediaType.APPLICATION_JSON);
        //smsResponseDetails = (new JacksonRepresentation<SMSResponseDetails>(representation, SMSResponseDetails.class)).getObject();
        smsApiRespJSON = new JSONObject(representation.getText());
        smsResponseDetails = new SMSResponseDetails();
        smsResponseDetails.setErrorCode(smsApiRespJSON.getString("ErrorCode"));
        smsResponseDetails.setErrorMessage(smsApiRespJSON.getString("ErrorMessage"));
        smsResponseDetails.setJobId(smsApiRespJSON.get("JobId").toString());
        logger.info("Error Code:"+smsResponseDetails.getErrorCode());
        logger.info("Error Message:"+smsResponseDetails.getErrorMessage());
        /*JSONArray messageDataJSONArray = smsApiRespJSON.getJSONArray("MessageData");
        SMSResponseDetails.MessageData messageDataArray[] = null;
      
        if(messageDataJSONArray != null && messageDataJSONArray.length() > 0) {
          messageDataArray = new SMSResponseDetails.MessageData[messageDataJSONArray.length()];
          for(int i = 0; i < messageDataJSONArray.length(); i++) {
            SMSResponseDetails.MessageData messageData = new SMSResponseDetails.MessageData();
            JSONObject messageDataJSON = messageDataJSONArray.getJSONObject(i);
            messageData.setNumber(messageDataJSON.getString("Number"));
            JSONArray messagePartsJSONArray = messageDataJSON.getJSONArray("MessageParts");
          
            if(messagePartsJSONArray != null && messagePartsJSONArray.length() > 0) {
              SMSResponseDetails.MessageData.MessageParts messagePartsArray[] = new SMSResponseDetails.MessageData.MessageParts[messagePartsJSONArray.length()];
            
              for(int j = 0; j < messagePartsJSONArray.length(); j++) {
                JSONObject messagePartsJSON = messagePartsJSONArray.getJSONObject(j);
                SMSResponseDetails.MessageData.MessageParts messageParts = new SMSResponseDetails.MessageData.MessageParts();
                messageParts.setMsgId(messagePartsJSON.getString("MsgId"));
                messageParts.setPartId(messagePartsJSON.getInt("PartId"));
                messageParts.setText(messagePartsJSON.getString("Text"));
                messagePartsArray[j] = messageParts;
              }
              messageData.setMessageParts(messagePartsArray);
            }
            messageDataArray[i] = messageData;
          }
        }
        smsResponseDetails.setMessageData(messageDataArray);*/
      }
    } catch (Exception e) {
      logger.error("PROBLEM in the method sendSMS");
      e.printStackTrace();
    }
    logger.info("END of the method sendSMS");
    return smsResponseDetails;
  }
  
  // ========================================================================
  
  public static void main(String args[]) {
    //SMSManager.getInstance().sendSMS("9000143256", "Credentials to access TraceR app are, Username: 8099717796 and Password: Welcome1");
    SMSResponseDetails smsResponseDetails = SMSManager.getInstance().sendSMS("9000143256", "Credentials to access TraceR app are, Username: jpmanne and Password: Welcome1");
    System.out.println("ErrorCode:"+smsResponseDetails.getErrorCode());
    
    if(smsResponseDetails.getMessageData() != null && smsResponseDetails.getMessageData().length > 0) {
      System.out.println("Number : "+smsResponseDetails.getMessageData()[0].getNumber());
      System.out.println("Text : "+smsResponseDetails.getMessageData()[0].getMessageParts()[0].getText());
    }
  }
  
  //========================================================================
}