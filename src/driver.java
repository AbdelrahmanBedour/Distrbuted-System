import java.net.*;
import java.io.*;

public class driver {
    public static void main (String[]args) throws Exception{
        Socket driver_Socket = new Socket("LocalHost", 1000);
        String data ; 
        DataInputStream input_data = new DataInputStream(driver_Socket.getInputStream());
        DataOutputStream output_data = new DataOutputStream(driver_Socket.getOutputStream());
        output_data.writeUTF( "Driver start" );
        data = new String(input_data.readUTF());
        if(data.equals("ready")){
            System. out .println( "server: " + data );
            output_data .writeUTF( "request route" );
            
        }
        data= new String( input_data .readUTF());
        if(data.equals("provide the best route")){
            System. out .println( "server: " + data );
            System. out .println( "done" );
        }
        input_data.close();
        output_data.close();
        driver_Socket.close();




    }
}