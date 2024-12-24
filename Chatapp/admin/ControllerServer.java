
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Action;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
  



public class ControllerServer implements Initializable {

    @FXML
  private Button send;
  
    @FXML
  private TextField textField;
    @FXML
  private VBox vboxmsg;
    @FXML
  private ScrollPane sp;
  @FXML
      private Server server;
  
      @Override
      public void initialize(URL location,ResourceBundle resources)
  
      {
          try{
               server=new Server( new ServerSocket(8080));
  
          }
          catch(IOException e)
          {
              e.printStackTrace();
              System.out.println("can't create server");
  
  
          }
          vboxmsg.heightProperty().addListener(new ChangeListener<Number>() {
                  @Override
                  public void changed(ObservableValue<? extends  Number> observable,Number old,Number newvalue)
                  {
                      sp.setVvalue((Double) newvalue);
  
  
                  }
                  
                  
              } );
              server.recieveMsgFromClient(vboxmsg);

    
    
    send.setOnAction( new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent e)
        {
             String msgSend =textField.getText();
             if(!msgSend.isEmpty())
             {
                HBox hbox=new HBox();
                hbox.setAlignment(Pos.CENTER_LEFT);
                hbox.setPadding(new Insets(5,5,5,10));
                Text text = new Text(msgSend);
                TextFlow textFlow=new TextFlow(text);
                textFlow.setStyle( "-fx-color: rgb(239,242,255);"+
                "-fx-background-color : rgb(15,125,242);"+"-fx-background-radius: 20px;"
                ); 
                textFlow.setPadding(new Insets(5,5,5,10));
              text.setFill(Color.color(0.934,0.945,0.966));



                hbox.getChildren().add(textFlow);
                vboxmsg.getChildren().add(hbox);

                
                    server.sendMsgToClient(msgSend);
                    textField.clear();
               
               
             

            }
          
    }}
    );


   

}
//msg appear to client;
public static void addLabel(String msgFromClient,VBox vbox)
{
    HBox hbox =new HBox();
    hbox.setAlignment(Pos.CENTER_RIGHT);
    hbox.setPadding(new Insets(5,5,5,10));


    Text text= new Text(msgFromClient);
    TextFlow textFlow= new TextFlow(text);
    textFlow.setStyle(
                "-fx-background-color : rgb(233,233,235);"+"-fx-background-radius: 20px;");
    textFlow.setPadding(new Insets(5,10,5,10));
    hbox.getChildren().add(textFlow);
     
    Platform.runLater(new Runnable (){
    @Override
    public void run()
    {
        vbox.getChildren().add(hbox);
    }
}


    );


}
}