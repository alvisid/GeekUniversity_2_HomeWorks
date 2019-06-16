package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.ImageView.*;


public class Controller {

    @ImageView
    ImageView imageView;

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    public void sendMsg() {
        textArea.appendText(textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();


    }

}