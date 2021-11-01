import java.util.ArrayList;
import java.net.*;
import java.io.*;


public class intermediate {
    public static void main(String[] args)throws Exception{
        try{
        
        ServerSocket server_socket = new ServerSocket (1000);
        Socket client_socket = new Socket("localhost",2000);
        ArrayList<Intermediate_handler>Thread_For_Client = new ArrayList<>();
        while(true){
            Socket client = null;
             client = server_socket.accept();
            System.out.println("Connected");
            DataInputStream data_input_client = new DataInputStream(client.getInputStream());
            DataOutputStream data_output_client = new DataOutputStream(client.getOutputStream());
           
            Intermediate_handler thread = new Intermediate_handler(client, client_socket, Thread_For_Client,data_input_client,data_output_client);
            Thread_For_Client.add(thread);
            thread.start();
        }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}

class Intermediate_handler extends Thread{
    final Socket s = null;
    private Socket client;
    private Socket server_socket;
    private ArrayList<Intermediate_handler> Thread_For_Client;
    DataInputStream data_input_server, data_input_client;
    DataOutputStream data_output_server, data_output_client;
    
    public Intermediate_handler(Socket client,Socket server_socket,ArrayList<Intermediate_handler>Thread_For_Client, DataInputStream data_input_client, DataOutputStream data_output_client) 
    {
        this.client = client;
        this.server_socket = server_socket;
        this.Thread_For_Client = Thread_For_Client;
        this.data_input_client = data_input_client;
        this.data_output_client = data_output_client;
        
    }
    @Override
    public synchronized void run(){
        String server_message = null;
        String client_message = null;
        try{
         data_input_server = new DataInputStream(client.getInputStream());
         data_output_server = new DataOutputStream(client.getOutputStream());
        }
         catch(Exception e){}
        while(true)
        {
            try {
                try{
                    sleep(3000); 
                    }
                    catch(Exception e){}
                client_message = read_from_all_clients();
                if (client_message != null)
                    data_output_server.writeUTF(client_message);
                    System. out .println( client_message);
                server_message=data_input_server.readUTF();
                if (server_message != null)
                    send_to_all_clients(server_message);
                    System. out .println( server_message );

            } catch (IOException e) {
                break;
               //e.printStackTrace();
            }
        }
        try
        {
            // closing resources
            s.close();
            this.data_input_client.close();
            this.data_output_client.close();
        }
        catch(EOFException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void send_to_all_clients(String message)
    {
        for(Intermediate_handler Ch:Thread_For_Client)
        {
            try{
                Ch.data_output_client.writeUTF(message);
            }
            catch(Exception e)
            {}
        }
    }
    private String read_from_all_clients()
    {
       String message=null;
        for(Intermediate_handler Ch:Thread_For_Client)
        {
            try{
                if(Ch.data_input_client.available()>0)
                {
              message=Ch.data_input_client.readUTF();
              break;
                }
            }
            catch(Exception e)
            {}
        }
        return message;
    }
}
