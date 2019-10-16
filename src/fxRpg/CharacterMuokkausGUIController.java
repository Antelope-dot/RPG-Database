package fxRpg;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rpg.Hahmo;

/**
 * @author Ilari Massa
 * @version 28.2.2019
 *
 */
public class CharacterMuokkausGUIController implements ModalControllerInterface<Hahmo>, Initializable {
    @FXML
    private Label labelVirhe;
    @FXML
    private TextField editName;

    @FXML
    private TextArea editNotes;

    @FXML
    private TextField editClass;

    @FXML
    private TextField editRace;

    @FXML
    private TextField editAge;

    @FXML
    private TextField editOrigin;

    @FXML
    private TextField editAllignment;
    
    private String kirjaimet = "abcdefghijklmnopqrstuvwxyzöäå";
    
    @FXML
    private void HandleAddCharacter() {
        if (hahmoKohdalla != null && hahmoKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Cant leave name empty");
            return;
        }
        if (editAge != null) {
            if (tarkistus(editAge.getText())) {
                naytaVirhe("Must use numbers");
                return;
            }
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML
    private void HandleCancel() {
        hahmoKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }


    
    //=================================================
    private Hahmo hahmoKohdalla;
    
    private TextField edits[];
    private TextArea editArea;
    private TextField editRotu;
    private static String rotu;
    
    /**
     * @param edits muokkaukkset
     * @param editArea muokattava teksti alue
     */
    public static void tyhjenna(TextField[] edits, TextArea editArea) {
        //
        for (TextField edit: edits) {
            edit.setText("");
        }
        editArea.setText("");
    }
    
    @Override
    public Hahmo getResult() {
        // TODO Auto-generated method stub
        return hahmoKohdalla;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        editName.requestFocus();
        
    }

    @Override
    public void setDefault(Hahmo oletus) {
        hahmoKohdalla = oletus;
        naytaHahmo(edits, hahmoKohdalla, editArea, editRotu);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle bundle) {
        // TODO Auto-generated method stub
        alusta();
    }
    
    /**
     * tekee tarvittavat muut alustukset
     */
    protected void alusta() {
        //
        edits = new TextField[] {editName, editClass, editAge,editOrigin, editAllignment};
        editArea = editNotes;
        editRotu = editRace;
        int i = 0;
        for (TextField edit: edits){
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosHahmoon(k, (TextField) (e.getSource())));
            }
        editArea.setOnKeyReleased(e -> hahmoKohdalla.setNotes(editArea.getText()));
        editRotu.setOnKeyReleased(e -> rotu = editRotu.getText()); 
    }
    
    private void kasitteleMuutosHahmoon(int k, TextField edit) {
        if (hahmoKohdalla == null) return;
        String s = edit.getText();

        String virhe = null;
        switch (k) {
        case 1 : virhe = hahmoKohdalla.setNimi(s); break;
        case 2 : virhe = hahmoKohdalla.setLuokka(s); break;
        case 3 : 
            if (s.matches("[0-9]+") == false) {
                virhe = "must be a number";
                break;
            }
            if (s.contentEquals("")) {
                virhe = "";
                break;
            }
            virhe = String.valueOf(hahmoKohdalla.setAge(Integer.parseInt(s)));
                    break;
            
        case 4 : virhe = hahmoKohdalla.setOrigin(s); break;
        case 5 : virhe = hahmoKohdalla.setAllignment(s); break;
        default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        

        }
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
    
    /**
     * @param hahmo asetetaan muokkaus ikkunan hahmon tiedot
     * @param edits taulukko jossa tekstikentät
     * @param editArea teksti alue
     * @param editRotu lol
     */
    public static void naytaHahmo(TextField[] edits, Hahmo hahmo, TextArea editArea, TextField editRotu) {
        if (hahmo == null) return;
        edits[0].setText(hahmo.getNimi());
        edits[1].setText(hahmo.getLuokka());
        edits[2].setText(String.valueOf(hahmo.getAge()));
        edits[3].setText(hahmo.getOrigin());
        edits[4].setText(hahmo.getAllignment());
        editArea.setText(hahmo.getNotes());
        editRotu.setText(rotu);
        
    }
    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painetaan cancel, muuten täytetty true
     */
    public static Hahmo kysyHahmo(Stage modalityStage, Hahmo oletus) {
        return ModalController.<Hahmo, CharacterMuokkausGUIController>showModal(CharacterMuokkausGUIController.class.getResource("CharacterMuokkausGUIView.fxml"), 
                "RPG", 
                modalityStage, oletus, null);
    }
    
    /**
     * @param text teksti joka tarkistetaan
     * @return true tai false
     */
    public boolean tarkistus(String text) {
        for(int i = 0; i < text.length(); i++ ) {
            for(int j = 0; j < kirjaimet.length(); j++) {
                if (text.charAt(i) == (kirjaimet.charAt(j))) return true;
            }
        }
        return false;
    }
    
}