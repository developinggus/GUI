package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SampleController {

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField balance;

    @FXML
    private TextField month;

    @FXML
    private TextField day;

    @FXML
    private TextField year;

    @FXML
    private Button openAccountButton;

    @FXML
    void openAccount(ActionEvent event) {
    	Profile p = new Profile(fName.getText(), lName.getText());
    }

}
