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
                System.out.println(EntityUtils.toString(entity));
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
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
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
            builder.setParameter("targetFace", rectangle);
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
                    System.err.println("Error status: " + response.getStatusLine().getStatusCode());
                    System.err.println("Error detail: " + response.getStatusLine().getReasonPhrase());
                }
            }
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return id;
    }
    
    public static void trainGroup(String groupId){
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            String url = "https://api.projectoxford.ai/face/v1.0/persongroups/";
            url += groupId;
            url += "train";
            URIBuilder builder = new URIBuilder(url);


            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", "{subscription key}");


            // Request body
            StringEntity reqEntity = new StringEntity("{body}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) 
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
   
    
    

    
}
