/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facematcher;

import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.parser.ParseException;
import java.lang.*;

/**
 *
 * @author borja
 */
public class FaceMatcher {


    public static void main(String[] args) throws IOException, MalformedURLException, ParseException {
        
        String photo_url = "https://timebusinessblog.files.wordpress.com/2012/02/600_biz_zuckerberg_02011.jpg";
        String groupId = "bugdestroyers10";
        String person_name = "Marc Zuckerberk";
        
        String[] detection = Utils.detect(photo_url);
        String faceId = detection[0];
        String rectangle = detection[1];
        System.out.println("FaceId: " + faceId);
        System.out.println("Rectangle: " + rectangle);
        //Utils.createGroup(groupId);
        String personId = Utils.createPerson(person_name, groupId);
        System.out.println("PersonId: " + personId);
        String persistedFaceId = Utils.addFace(groupId, personId, rectangle, photo_url);
        System.out.println("PersistedFaceId: " + persistedFaceId);
        Utils.trainGroup(groupId);
        String[] train_status = Utils.getTrainingStatus(groupId);
        System.out.println("Train status: " + train_status[0]);
        
    }
    
}
