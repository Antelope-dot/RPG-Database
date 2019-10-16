package fxRpg;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import rpg.Esine;

/**
 * @author Ilari Massa
 * @version Feb 20, 2019
 *
 */
public class ItemsGUIController implements ModalControllerInterface<Esine> {

    @FXML
    private TextField editEsine;

    @FXML
    private Label labelVirhe;

    @FXML
    private TextField editNotes;

    private Esine esine;
    
    @FXML
    void HandleAccept() {
        if (editEsine.getText().trim().equals("") ) {
            naytaVirhe("item must have name");
            return;
        }
        if (editNotes.getText().trim().equals("") ) {
            naytaVirhe("item must have description");
            return;
        }
        esine.setNimi(editEsine.getText().trim());
        esine.setNotes(editNotes.getText().trim());
        getResult();
        ModalController.closeStage(editEsine);
        
    }

    @FXML
    void HandleCancel() {
        esine = null;
        ModalController.closeStage(editEsine);
    }

    @Override
    public Esine getResult() {
        // TODO Auto-generated method stub
        return esine;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setDefault(Esine oletus) {
        esine = oletus;
        naytaEsine(esine);
        
    }
    
    private void naytaEsine(Esine item) {
        if ( item == null) return;
        editEsine.setText(item.getNimi());
        editNotes.setText(item.getNotes());
    }
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


}
