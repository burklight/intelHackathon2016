/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facematcher;

/**
 *
 * @author borja
 */
public class PersonData {
    
    public static String LogIn(String person_name, String[] photo_url){
        
        String personId = Utils.createPerson(person_name, Utils.groupId);
                
        for (int i = 0; i < photo_url.length; ++i){
            String[] detection = Utils.detect(photo_url[0]);
            String faceId = detection[0];
            String rectangle = detection[1];
            String persistentFaceId = Utils.addFace(Utils.groupId, personId, rectangle, photo_url[0]);  
        }
        
        Utils.trainGroup(Utils.groupId);
        
        return personId;
        
    }
    
    public static void AddFace(String personId, String[] photo_url){
        for (int i = 0; i < photo_url.length; ++i){
            String[] detection = Utils.detect(photo_url[0]);
            String faceId = detection[0];
            String rectangle = detection[1];
            String persistentFaceId = Utils.addFace(Utils.groupId, personId, rectangle, photo_url[0]);  
        }
        
        Utils.trainGroup(Utils.groupId);
    }
    
}
