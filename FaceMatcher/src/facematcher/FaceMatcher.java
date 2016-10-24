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


    public static String faceMatcher(String photo_url){
        
        String[] detection = Utils.detect(photo_url);
        String[] faceIds = {detection[0]};
        String[] matches = Utils.identify(Utils.groupId, faceIds);
        String match = Matcher.getMatch(matches);
        
        if(match != null){
            return match;
        } else{
            return "Match not found";
        }
        
    }
}
