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
public class Test2 {
    
    public static String borja = "0bc90fc0-2ce2-4949-843f-1f2dbc0579b4";
    public static String oleguer = "1c74335f-f8e6-4988-ac19-c7d2e09a65c3";
    public static String david = "dc9656ff-1d50-4a86-af4e-aeb16d8bb8bc";
    public static String isaac = "6a2c67bc-a52d-4b10-bc59-4c224facbfe4";
    public static String louis = "87a26556-929c-4aeb-9b64-2a97060583b9";
    
    
    public static String[] borja_db =     {"https://s25.postimg.org/slz0muhcb/borja_1.jpg",
                            "https://s25.postimg.org/4w9ky5iyz/borja_2.jpg",
                            "https://s25.postimg.org/cqa6jjqrv/borja_3.jpg",
                            "https://s25.postimg.org/s0en4hbnv/borja_4.jpg",
                            "https://s25.postimg.org/hrm5ynnm3/borja_5.jpg"};
    public static String[] david_db =     {"https://s25.postimg.org/ddmveart7/david_1.jpg",
                            "https://s25.postimg.org/uffpge6ob/david_2.jpg",
                            "https://s25.postimg.org/f7zpw1etn/david_3.jpg",
                            "https://s25.postimg.org/wzfxo8jm3/david_4.jpg",
                            "https://s25.postimg.org/3lk78ngwb/david_5.jpg"};
    public static String[] isaac_db =     {"https://s25.postimg.org/n4osi0fnv/isaac_1.jpg",
                            "https://s25.postimg.org/a1t5yqpfv/isaac_2.jpg",
                            "https://s25.postimg.org/4ewt19mx7/isaac_3.jpg",
                            "https://s25.postimg.org/tyz37p8az/isaac_4.jpg",
                            "https://s25.postimg.org/av5rrcvgr/isaac_5.jpg"};
    public static String[] louis_db =     {"https://s25.postimg.org/z06h92frf/louis_1.jpg",
                            "https://s25.postimg.org/z1gf2hhl7/louis_2.jpg",
                            "https://s25.postimg.org/w8n7igh8r/louis_3.jpg",
                            "https://s25.postimg.org/n29i8x1e3/louis_4.jpg",
                            "https://s25.postimg.org/km7ov2jbf/louis_5.jpg"};
    public static String[] oleguer_db =     {"https://s25.postimg.org/3zgxuibm3/oleguer_1.jpg",
                            "https://s25.postimg.org/5fsgcneiz/oleguer_2.jpg",
                            "https://s25.postimg.org/420thcf9n/oleguer_3.jpg",
                            "https://s25.postimg.org/fseqyq823/oleguer_4.jpg",
                            "https://s25.postimg.org/czljep7pn/oleguer_5.jpg"};
    
    public static String[] brad_db = {"http://p.fod4.com/p/channels/hinvf/profile/01ImAXzVSlKtWlZtFE4l_Brad-Pitt-smiling.jpg"};
    
    public static void main(String[] args){
        
        String[] photo_url = louis_db;
        
        String name = PersonData.LogIn("Louis", photo_url);
        
        System.out.println(name);
        
        
    }
    
}
