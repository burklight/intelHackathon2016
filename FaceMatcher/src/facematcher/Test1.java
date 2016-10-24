/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facematcher;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author borja
 */
public class Test1 {
    
    public static void main(String[] args) throws InterruptedException{
        
        String train_status = null;
        
        while((train_status == null) || !(train_status.equals("succeeded"))){
            String[] train_status_array = Utils.getTrainingStatus(Utils.groupId);
            train_status = train_status_array[0];
            System.out.println(train_status);
            TimeUnit.SECONDS.sleep(5);

        }
        
    }
    
}
