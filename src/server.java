import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class server {
    public static void main(String[] args)throws Exception{
        try{
        ServerSocket server_socket = new ServerSocket (2000);
        ArrayList<handler>Thread_For_Client = new ArrayList<>();
        System.out.println("Server start... waiting for request");
    
        
        while(true){
            Socket client = server_socket.accept();
            DataInputStream input_data = new DataInputStream(client.getInputStream());
            DataOutputStream output_data = new DataOutputStream(client.getOutputStream());
            System.out.println("Assigning new thread for this client");
            handler thread = new handler(client, input_data, output_data,Thread_For_Client);
            Thread_For_Client.add(thread);
            thread.start();
        }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}


class handler extends Thread{
    final DataInputStream data_input;
    final DataOutputStream data_output;
    final Socket client;
    private ArrayList<handler> thread_List;
    public handler(Socket client, DataInputStream data_input, DataOutputStream data_output,ArrayList<handler>Thread_For_Client) 
    {
        this.client = client;
        this.data_input = data_input;
        this.data_output = data_output;
        this.thread_List=Thread_For_Client;
    }
    @Override
    public synchronized void run(){
        String received;
        
        while(true)
        {
            try{
            
            received = new String ( data_input .readUTF());
            if(received.equals("Sensor start"))
            {
                System. out .println( "Sensor: " + received );
              //  send_to_all_clients("turn on sensors and cameras");
                data_output.writeUTF( "Open sensor" );
                
            }
            
            else if(received.equals("sensor turned on"))
            {
                System. out .println( "Sensor: " + received );
                data_output.writeUTF( "Send data" );
               
            }
            
            else if(received.equals("get photo of streets"))
            {
                System. out .println( "Sensor: " + received );
              //  System. out .println( "All right" );
             
               
            }
            
            else if(received.equals("driver start"))
            {
                System. out .println( "Driver: " + received );
                data_output.writeUTF( "begin" );
              
            }
            
            else if(received.equals("request route"))
            {
                System. out .println( "Driver: " + received );
          
                data_output.writeUTF( "provide the best route" );
                System. out .println( "done" );
            }



            } 
        
            catch(EOFException e) {
                //This isn't problem
                break;
            }
             catch (IOException e) {
             e.printStackTrace();
}
        }
        {
            try
           {
               // closing resources
               client.close();
               this.data_input.close();
               this.data_output.close();
           }
           catch(EOFException e){
               e.printStackTrace();
           }
           catch(IOException e){
               e.printStackTrace();
           }
       }
    }


    private void send_to_all_clients(String outputString)
     {
         for(handler Ch:thread_List)
         {
             try {
                Ch.data_output.writeUTF(outputString);
             } catch (Exception e) {
                 //TODO: handle exception
             }
         }
     }
}