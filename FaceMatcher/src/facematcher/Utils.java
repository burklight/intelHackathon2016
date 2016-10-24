/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facematcher;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author borja
 */
public class Utils {
    
    public static final String key_video_api = "28991674eeeb476ba42f0a25e27845d8";
    public static final String key_face_api = "2243ff3a06c0445790978dae85862bfe";
    public static final String key_vision_api = "a16ab0814a5c440a995ad8fb378f7eea";
    public static final String USER_AGENT = "Mozilla/5.0";
    public static final String maxNumberOfCandidates = "1";
    public static final double confidenceThreshold = 0.8;
    public static String groupId = "thedreamteam3";
    
    public static String[] detect(String photo_url){
        HttpClient httpclient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        String id= null;
        String rectangle = null;

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/detect");

            builder.setParameter("returnFaceId", "true");
            builder.setParameter("returnFaceLandmarks", "false");

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);


            // Request body
            String photo_url_json = "{\"url\":\""+photo_url+"\"}";
            StringEntity reqEntity = new StringEntity(photo_url_json);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                String entity_string = EntityUtils.toString(entity);
                JSONArray entity_json_array = (JSONArray) parser.parse(entity_string);
                JSONObject entity_json = (JSONObject) entity_json_array.get(0);
                id = (String) entity_json.get("faceId");
                JSONObject rectangle_json = (JSONObject) entity_json.get("faceRectangle");
                rectangle = (String) String.valueOf(rectangle_json.get("left"));
                rectangle += "," + (String) String.valueOf(rectangle_json.get("top"));
                rectangle += "," + (String) String.valueOf(rectangle_json.get("width"));
                rectangle += "," + (String) String.valueOf(rectangle_json.get("height"));
                if (response.getStatusLine().getStatusCode() != 200){
                   System.err.println("detect error status: " + response.getStatusLine().getStatusCode());
                   System.err.println("detect error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        return new String[] {id, rectangle};
    }
    
    public static void createGroup(String groupId){
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            String url_for_request = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url_for_request += groupId;
            URIBuilder builder = new URIBuilder(url_for_request);

            URI uri = builder.build();
            HttpPut request = new HttpPut(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);

            // Request body
            String body = "{\"name\":\""+groupId+"\", \"userData\": \"\"}";
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                if (response.getStatusLine().getStatusCode() != 200){
                   System.err.println("createGroup error status: " + response.getStatusLine().getStatusCode());
                   System.err.println("createGroup error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public static String createPerson(String personId, String groupId){
        HttpClient httpclient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        String id = null;

        try
        {
            String url_for_request = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url_for_request += groupId;
            url_for_request += "/persons";
            URIBuilder builder = new URIBuilder(url_for_request);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);


            // Request body
            String body = "{\"name\":\""+personId+"\", \"userData\": \"\"}";
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                String entity_string = EntityUtils.toString(entity);
                JSONObject entity_json = (JSONObject) parser.parse(entity_string);
                id = (String) entity_json.get("personId");
                if (response.getStatusLine().getStatusCode() != 200){
                   System.err.println("createPerson error status: " + response.getStatusLine().getStatusCode());
                   System.err.println("createPerson error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Id de la persona: " + id);
        
        return id;
    }
    
    public static String addFace(String groupId, String personId, String rectangle, String photo_url){
        HttpClient httpclient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        String id = null;

        try
        {
            String url_for_request = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url_for_request += groupId;
            url_for_request += "/persons/";
            url_for_request += personId;
            url_for_request += "/persistedFaces";
            
            URIBuilder builder = new URIBuilder(url_for_request);
            if(rectangle != null){
                builder.setParameter("targetFace", rectangle);
            }
            URI uri = builder.build();
            

            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);

            // Request body
            String photo_url_json = "{\"url\":\""+photo_url+"\"}";
            StringEntity reqEntity = new StringEntity(photo_url_json);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                String entity_string = EntityUtils.toString(entity);
                JSONObject entity_json = (JSONObject) parser.parse(entity_string);
                id = (String) entity_json.get("persistedFaceId");
                if (response.getStatusLine().getStatusCode() != 200){
                    System.err.println("addFace error status: " + response.getStatusLine().getStatusCode());
                    System.err.println("addFace error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("He afegit una cara amb id: " + id);
        
        return id;
    }
    
    public static void trainGroup(String groupId){
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            String url = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url += groupId;
            url += "/train";
            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);


            // Request body
            String body = "{}";
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                if (response.getStatusLine().getStatusCode() != 202){
                    System.err.println("trainGroup error status: " + response.getStatusLine().getStatusCode());
                    System.err.println("trainGroup error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("He començat a entrenar-me");
    }
    
    public static String[] getTrainingStatus(String groupId){
        HttpClient httpclient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        String train_status = null;
        String train_createdDateTime = null;
        String train_lastActionTime = null;
        String train_message = null;

        try
        {
            String url = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url += groupId;    
            url += "/training";
            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
               String entity_string = EntityUtils.toString(entity);
               JSONObject entity_json = (JSONObject) parser.parse(entity_string);
               train_status = (String) entity_json.get("status");
               train_createdDateTime = (String) entity_json.get("createdDateTime");
               train_lastActionTime = (String) entity_json.get("lastActionTime");
               train_message = (String) entity_json.get("message");
               if (response.getStatusLine().getStatusCode() != 200){
                   System.err.println("getTrainingStatus error status: " + response.getStatusLine().getStatusCode());
                   System.err.println("getTrainingStatus error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return new String[] {train_status, train_createdDateTime, train_lastActionTime, train_message};
    }
    
    public static String[] identify(String groupId, String[] faceIds_array){
        HttpClient httpclient = HttpClients.createDefault();
        JSONParser parser = new JSONParser();
        String[] matches = new String[faceIds_array.length];

        try
        {
            URIBuilder builder = new URIBuilder("https://api.projectoxford.ai/face/v1.0/identify");


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", Utils.key_face_api);


            // Request body
            String body = "{\"personGroupId\":\"" + groupId;
            body += "\",\"faceIds\":[";
            for(int i = 0; i < faceIds_array.length; ++i){
                body += "\"";
                body += faceIds_array[i];
                body += "\",";
            }
            body += "],\"maxNumOfCandidatesReturned\":" + Utils.maxNumberOfCandidates;
            body += ",\"conficenceThreshold\": " + String.valueOf(Utils.confidenceThreshold);
            body += "}";
            StringEntity reqEntity = new StringEntity(body);
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                String entity_string = EntityUtils.toString(entity);
                JSONArray entity_json_array = (JSONArray) parser.parse(entity_string);
                for(int i = 0; i < faceIds_array.length; i++){
                    JSONObject entity_json = (JSONObject) entity_json_array.get(i);
                    JSONArray candidates_array = (JSONArray) entity_json.get("candidates");
                    JSONObject candidates = (JSONObject) candidates_array.get(0);
                    String id_candidate = (String) candidates.get("personId");
                    double confidence = (double) candidates.get("confidence");
                    if(confidence >= Utils.confidenceThreshold){
                        matches[i] = id_candidate;
                    } else{
                        matches[i] = null; 
                    }                    
                }
                if (response.getStatusLine().getStatusCode() != 200){
                   System.err.println("identify error status: " + response.getStatusLine().getStatusCode());
                   System.err.println("identify error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return matches;
    }
   
    
    

    
}
