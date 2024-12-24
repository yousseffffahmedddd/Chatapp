 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private  BufferedWriter bufferedWriter;
    public Server(ServerSocket serverSocket) throws IOException
    {
        try{
        this.serverSocket=serverSocket;
        this.socket=serverSocket.accept();
        this.bufferedReader=new BufferedReader( new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("can't connect to server");
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
    }
    
    catch(IOException e)
    {
        e.printStackTrace();
        System.out.println("can't close all streams and sockets");

    }
    }
    public void sendMsgToClient(String msg) 
    {
        try{
            bufferedWriter.write(msg);
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
    
    public void recieveMsgFromClient(VBox vbox){
        new Thread( new Runnable() 
        {
            @Override
            public void run()
            {
                while(socket.isConnected())
                {
                    try{
                    String msgFromClient = bufferedReader.readLine(); 
                    ControllerServer.addLabel(msgFromClient,vbox);
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