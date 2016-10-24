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
public class Server {
    
    public static void main(String[] args){
        
        int opt = -1;
        String nom = null;
        String id = null;
        String[] urls = null;
        String url = null;
        
        //Anem llegint infinitament opció
        if(opt == Utils.login){
            //LLegim en ordre un String (nom de la persona) i un String[] 
            //amb les url de les fotos que la persona té
            String personId = PersonData.LogIn(nom, urls);
            //retornem l'ID de la persona per a guardarla com a id-nomac
        } else if(opt == Utils.addFace){
            //LLegim en ordre un String (id-nomac) i un String[]
            //amb les url de les fotos que la persona s'ha fet noves
            PersonData.AddFace(id, urls);
        } else if(opt == Utils.faceMatcher){
            //Llegim un String (url de la foto que ha pres la càmera) 
            String matchId = FaceMatcher.faceMatcher(url);
            //retornem la id-nomac de la persona amb qui s'ha fet el match
            
        }
        
        
    }
    
}
