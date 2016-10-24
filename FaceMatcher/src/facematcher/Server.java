/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facematcher;

import java.util.ArrayList;

/**
 *
 * @author borja
 */
public class Server {
    
    public static void main(String[] args) throws InterruptedException{
        
        String matchId = null;
        String urlImage = null;
        String macId = null;
        String name = null; 
        ArrayList<String> macs = new ArrayList<>();
        
        //Anem llegint infinitament 
        while(true){
            
            urlImage = Utils.getLastImage();
            macs = Utils.getBluetoothAdress();
            
            while(matchId == null || matchId.equals("Match not found")){
                urlImage = Utils.getLastImage();
                System.out.println("The url image is: " + urlImage);
                matchId = FaceMatcher.faceMatcher(urlImage);
                System.out.println("The matchId is: " + matchId);
                Thread.sleep(3500);
            }
           
            macId = Utils.getMacId(matchId);
            
            for (int i = 0; i < macs.size(); ++i){
                if (macId.equals(macs.get(i))){
                    Utils.eraseTravel(macId);
                    break;
                }
            } 
            
        }

    }
    
}
