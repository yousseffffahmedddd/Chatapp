import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {
  
    private Socket socket;
    private BufferedReader bufferedReader;
    private  BufferedWriter bufferedWriter;
    public Client(Socket socket) throws IOException
    {
        try{
        this.socket=socket;
        this.bufferedReader=new BufferedReader( new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("can't connect to client");
            closeAll(bufferedReader, bufferedWriter, socket);

        }
    }

    public void closeAll(BufferedReader bufferedReader,BufferedWriter bufferedWriter,Socket socket) 
    {
        try{
        if(bufferedReader!=null)
        {
            bufferedReader.close();
        }
        if(bufferedWriter!=null)
        {
            bufferedWriter.close();
        }
        if(socket!=null)
        {
            socket.close();
        }
    }catch(IOException e)
    {
        System.out.println("Error creating client");
        e.printStackTrace();

    }
    
   


}

public void sendMsgToServer(String msgtoserver)
{
    try{
        bufferedWriter.write(msgtoserver);
        bufferedWriter.newLine();
        bufferedWriter.flush();

    }
    catch(IOException e)
    {
        e.printStackTrace();
        System.out.println("error sending msg to client");
        closeAll(bufferedReader, bufferedWriter, socket);
    }
    
}
public void recieveMsgFromServer(VBox vBox)
{
  
  
        new Thread( new Runnable() 
        {
            @Override
            public void run()
            {
                while(socket.isConnected())
                {
                    try{
                    String msgFromserver = bufferedReader.readLine();
                    ClientController.addLabel(msgFromserver,vBox);
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                        System.out.println("can't recieve from client");
                     closeAll(bufferedReader, bufferedWriter, socket);
                      
                        break;
                    }


                    
                }
            }
        }
        
        
        
        ).start();
       
    }
}
