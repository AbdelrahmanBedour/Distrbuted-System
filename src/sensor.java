import java.net.*;
import java.io.*;


public class sensor {
    public static void main(String[]args)throws Exception{
        Socket sensor_socket =new Socket("LocalHost", 1000);
        String received;
        DataInputStream input_data = new DataInputStream(sensor_socket.getInputStream());
        DataOutputStream output_data = new DataOutputStream(sensor_socket.getOutputStream());
        output_data.writeUTF( "Sensor start" );
         
        received= new String( input_data .readUTF());
        if(received.equals("Open sensor")){

            System. out .println( "server: " + received );
            output_data .writeUTF( "sensor turned on" );
        }
        received= new String( input_data .readUTF());
        if(received.equals("Send data")){
            System. out .println( "server: " + received );
            output_data .writeUTF( "get photo of streets" );
            System. out .println( "done" );
        }
        
        input_data.close();
        output_data.close();
        sensor_socket.close();
    }
    
}
