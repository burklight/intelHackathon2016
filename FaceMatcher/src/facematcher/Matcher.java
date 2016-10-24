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
public class Matcher {
    
    public static String getMatch(String[] matches){
        
        String train_status = null;
        
        while((train_status == null) || !(train_status.equals("succeeded"))){
            String[] train_status_array = Utils.getTrainingStatus(Utils.groupId);
            train_status = train_status_array[0];
        }      
        
        String match = null;
        
        for (int i = 0; i < matches.length; ++i){
            if (matches[i] != null){
                match = matches[i];
                break;
            }
        }
        
        return match;              
        
    }
    
}
