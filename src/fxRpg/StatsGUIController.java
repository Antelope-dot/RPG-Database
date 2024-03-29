package fxRpg;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rpg.Hahmo;

/**
 * @author Ilari Massa
 * @version Feb 14, 2019
 *
 */
public class StatsGUIController implements ModalControllerInterface<Hahmo>, Initializable {

    @FXML
    private Label labelVirhe;
    
    @FXML
    private TextField editStr;

    @FXML
    private TextField editDex;

    @FXML
    private TextField editConst;

    @FXML
    private TextField editLuck;

    @FXML
    private TextField editExp;

    @FXML
    private TextField editWis;

    @FXML
    private TextField editCha;

    @FXML
    private TextField editInt;

    @FXML
    private TextField editGold;

    @FXML
    private TextField editLvl;
    @FXML
    void handleSAccept() {
        for (int i = 0; i < edits.length; i++) {
            if(tarkistus(edits[i].getText())) {
                naytaVirhe("Must use numbers");
                return;
            }
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML
    void handleSCancel() {
        hahmoKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
//============================================================================================
    private Hahmo hahmoKohdalla;
    private String kirjaimet = "abcdefghijklmnopqrstuvwxyzöäå";

    
    private TextField edits[];
    
    /**
     * asetaa kaikille stateille ""
     */
    public void tyhjenna() {
        for (TextField edit: edits) {
            edit.setText("");
        }
    }
    
    @Override
    public Hahmo getResult() {
        // TODO Auto-generated method stub
        return hahmoKohdalla;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        editStr.requestFocus();
        
    }

    @Override
    public void setDefault(Hahmo oletus) {
        hahmoKohdalla = oletus;
        naytaHahmo(edits, hahmoKohdalla);

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
        edits = new TextField[] {editStr, editDex, editConst, editLuck, editExp, editWis, editCha, editInt, editGold, editLvl};
        int i = 0;
        for (TextField edit: edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosHahmoon(k, (TextField) (e.getSource())));
        }
    }
    
    private void kasitteleMuutosHahmoon(int k, TextField edit) {
        if (hahmoKohdalla == null) return;
        String s = edit.getText();
        
        if (edit.getText().contentEquals(""))
            s = "0";
        
        if (edit.getText().matches("[0-9]+")) {
            
               
        String virhe = null;
        switch (k) {
        case 1 : virhe = String.valueOf(hahmoKohdalla.setStr(Integer.parseInt(s) | 0)); break;
        case 2 : virhe = String.valueOf(hahmoKohdalla.setDex(Integer.parseInt(s))); break;
        case 3 : virhe = String.valueOf(hahmoKohdalla.setConst(Integer.parseInt(s))); break;
        case 4 : virhe = String.valueOf(hahmoKohdalla.setLuck(Integer.parseInt(s))); break;
        case 5 : virhe = String.valueOf(hahmoKohdalla.setExp(Integer.parseInt(s))); break;
        case 6 : virhe = String.valueOf(hahmoKohdalla.setWis(Integer.parseInt(s))); break;
        case 7 : virhe = String.valueOf(hahmoKohdalla.setCha(Integer.parseInt(s))); break;
        case 8 : virhe = String.valueOf(hahmoKohdalla.setInt(Integer.parseInt(s))); break;
        case 9 : virhe = String.valueOf(hahmoKohdalla.setGold(Integer.parseInt(s))); break;
        case 10 : virhe = String.valueOf(hahmoKohdalla.setLvl(Integer.parseInt(s))); break;
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
        else
            s = "0";
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
     * @param edits muokkaukset
     * @param hahmo joka naytetaan
     */
    public static void naytaHahmo(TextField[] edits, Hahmo hahmo) {
        if (hahmo == null) return;
        edits[0].setText(String.valueOf(hahmo.getStr()));
        edits[1].setText(String.valueOf(hahmo.getDex()));
        edits[2].setText(String.valueOf(hahmo.getConst()));
        edits[3].setText(String.valueOf(hahmo.getLuck()));
        edits[4].setText(String.valueOf(hahmo.getExp()));
        edits[5].setText(String.valueOf(hahmo.getWis()));
        edits[6].setText(String.valueOf(hahmo.getCha()));
        edits[7].setText(String.valueOf(hahmo.getInt()));
        edits[8].setText(String.valueOf(hahmo.getGold()));
        edits[9].setText(String.valueOf(hahmo.getLvl()));
    }
    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä dataa näytetään oletuksena
     * @return null jos painetaan cancel, muuten täytetty true
     */
    public static Hahmo kysyHahmo(Stage modalityStage, Hahmo oletus) {
        return ModalController.<Hahmo, StatsGUIController>showModal(StatsGUIController.class.getResource("StatsGUIView.fxml"), 
                "RPG", 
                modalityStage, oletus, null);
    }
    
    /**
     * @param text joka tarkistetaan
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
