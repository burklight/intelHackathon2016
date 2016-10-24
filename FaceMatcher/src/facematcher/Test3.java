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
public class Test3 {
    
    public static String test_borja = "https://s25.postimg.org/qbnxfdphn/borja_test.jpg";
    public static String test_david = "https://s25.postimg.org/qcxv8srbf/david_test.jpg";
    public static String test_oleguer = "https://s25.postimg.org/6jlrg3dxr/oleguer.jpg";
    public static String test_isaac = "https://s25.postimg.org/mloamhdn3/isaac_test.jpg";
    
    public static String borja = "0bc90fc0-2ce2-4949-843f-1f2dbc0579b4";
    public static String oleguer = "1c74335f-f8e6-4988-ac19-c7d2e09a65c3";
    public static String david = "dc9656ff-1d50-4a86-af4e-aeb16d8bb8bc";
    public static String isaac = "6a2c67bc-a52d-4b10-bc59-4c224facbfe4";
    public static String louis = "87a26556-929c-4aeb-9b64-2a97060583b9";
    
    public static void main(String[] args){
        
        String photo_url = test_oleguer;
        
        String[] detection = Utils.detect(photo_url);
        
        String[] faceIds = {detection[0]};
        
        String[] matches = Utils.identify(Utils.groupId, faceIds);
        String match = Matcher.getMatch(matches);
        
        String post = null;
        
        if(match != null){
            if(match.equals(borja)){
                post = "Borja";
            } else if(match.equals(oleguer)){
                post = "Oleguer";
            } else if(match.equals(david)){
                post = "David";
            } else if(match.equals(isaac)){
                post = "Isaac";
            } else if(match.equals(louis)){
                post = "Louis";
            }
            
            System.out.println("He detectat a " + post);
        } else{
            System.out.println("No he trobat cap match");
        }
        
        
        
        
        
    }    
}
