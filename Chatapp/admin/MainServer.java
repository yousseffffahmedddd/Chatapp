

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainServer extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
    FXMLLoader loader    = new FXMLLoader(getClass().getResource("guiadmin.fxml"));
    ControllerServer controllerServer=new ControllerServer();
   loader.setController(controllerServer);
    Parent root=loader.load();
     Scene scene=new Scene(root);
        stage.setTitle("Admin");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String []args)
    {
        MainServer.launch(args);
    }
    
}