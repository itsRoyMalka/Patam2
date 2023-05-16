package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class Controller {
    @FXML
    Button JServer,CServer,Join,Back;
    @FXML
    TextField IPBox,PortBox;
    //ViewModel vm;
    @FXML
    Text PortText,IPText;

    Alert alert;
    @FXML
    AnchorPane ac;
    @FXML
    ImageView board;
    void init(){
        //this.vm = vm;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
    }

    //Starting page:
    public void SJoinPressed(){
        JServer.setVisible(false);
        CServer.setVisible(false);
        Join.setVisible(true);
        Back.setVisible(true);
        IPBox.setVisible(true);
        PortBox.setVisible(true);
        IPText.setVisible(true);
        PortText.setVisible(true);
    }

    public void BackPressed(){
        JServer.setVisible(true);
        CServer.setVisible(true);
        Join.setVisible(false);
        Back.setVisible(false);
        IPBox.setVisible(false);
        PortBox.setVisible(false);
        IPText.setVisible(false);
        PortText.setVisible(false);
    }
    public void Create(){
        ac.setVisible(false);
        board.setVisible(true);
        //vm.CreateServer();
    }

    private boolean IsIPValid(){
        String[] nums = IPBox.getCharacters().toString().split("\\.");
        try{
            for(String s : nums){
                int num = Integer.parseInt(s);
                if(num < 0 || num >255)
                    return false;
            }
        }catch (NumberFormatException e){return false;}
        return true;
    }
    private boolean IsPortValid(){
        try {
            int port = Integer.parseInt(PortBox.getCharacters().toString());
            return port <= 7000 && port >= 6000;
        }catch (NumberFormatException e){return false;}
    }
    public void Join(){
        if(IPBox.getCharacters().isEmpty())
        {
            alert.setContentText("IP text box is empty");
            alert.show();
        }
        else if(PortBox.getCharacters().isEmpty()){
            alert.setContentText("Port text box is empty");
            alert.show();
        }
        else if(!IsIPValid()) {
            alert.setContentText("IP is invalid");
            alert.show();
        }
        else if (!IsPortValid()) {
            alert.setContentText("Port is invalid");
            alert.show();
        }
        else{
            board.setVisible(true);
            ac.setVisible(false);
            //check if server exists
            //vm.JoinServer(IPBox.getCharacters().toString(),Integer.parseInt(PortBox.getCharacters().toString()));
        }
    }
}