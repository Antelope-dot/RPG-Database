package fxRpg;

import java.awt.Desktop;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.io.IOException;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import rpg.Esine;
import rpg.Hahmo;
import rpg.HahmonEsine;
import rpg.Rotu;
import rpg.Rpg;
import rpg.SailoException;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;


/**
 * Luokka rpg käyttöliittymän tapahtumien hoitamiseksi
 * 
 * @author Ilari Massa
 * @version Feb 7, 2019
 *
 */
public class RpgGUIController implements Initializable {

    @FXML private TextField hakuehto;
    @FXML private ScrollPane panelHahmo;
    @FXML private ListChooser<Hahmo> chooserHahmot;
    @FXML private Label labelVirhe;
    @FXML private StringGrid<HahmonEsine> StringGridInventory;
    @FXML private TextField editStr;
    @FXML private TextField editDex;
    @FXML private TextField editConst;
    @FXML private TextField editLuck;
    @FXML private TextField editEXP;
    @FXML private TextField editWis;
    @FXML private TextField editCha;
    @FXML private TextField editInt;
    @FXML private TextField editGold;
    @FXML private TextField editLvl;
    @FXML private TextField editName;
    @FXML private TextField editClass;
    @FXML private TextField editRace;
    @FXML private TextField editAge;
    @FXML private TextField editOrigin;
    @FXML private TextField editAllignment;
    @FXML private TextArea editNotes;
    @FXML private TextField textHaku;

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();        
    }
    @FXML
    void HandleAbout() {
        Dialogs.showMessageDialog("Keep track of characters");
    }
    
    @FXML
    void HandleClose() {
        tallenna();
        Platform.exit();
    }

    @FXML
    void HandleDelete() {
        poistaHahmo();
    }

    @FXML
    void HandleHelp() {
        apua();
    }


    @FXML
    private void HandleAddCharacter() {
        uusiHahmo();
    }

    @FXML
    private void HandleAddItem() {
        uusiEsine();
    }

    @FXML
    private void HandleEdit() {
        muokkaa();
    }

    @FXML
    private void HandleFIle() {
        Dialogs.showMessageDialog("Vielä ei osata handleFile");
    }

    @FXML
    private void HandleModifyCharacter() {
        muokkaa();    
        }
    
    @FXML
    private void HandleModifyStats() {
        muokkaaStatteja();
    }

    @FXML
    private void HandleRemoveCharacter() {
        poistaHahmo();
    }

    @FXML
    private void HandleRemoveItem() {
         poistaEsine();
    }
    
    @FXML
    void handleHaku() {
        haku();
    }


    
    //=========================================================================================================

    private Rpg rpg;
    private Hahmo hahmoKohdalla;
    private TextField edits[];
    private TextField editss[];
    private TextArea editArea;
    private TextField editRotu;
    
    /*
     * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot
     * Alustetaan myös jäsen listan kuuntelija
     */
    private void alusta() {
        chooserHahmot.clear();
        chooserHahmot.addSelectionListener(e -> naytaHahmo());
        edits = new TextField[] {editName, editClass, editAge,editOrigin, editAllignment};
        editss =  new TextField[] {editStr, editDex, editConst, editLuck, editEXP, editWis, editCha, editInt, editGold, editLvl};
        editArea = editNotes;
        editRotu = editRace;
        
        StringGridInventory.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
        StringGridInventory.setEditable(false); 
        StringGridInventory.setPlaceholder(new Label("Ei vielä Esineita")); 
    }
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     * @return null jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto(String nimi) {
        try {
            rpg.lueTiedostosta(nimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
     }
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        
        String uusinimi = "RPG";
        lueTiedosto(uusinimi);
        return true;
    }


    
    /**
     * Näyttää llistasta valitun jäsenen tiedot, tilapäisesti yhteen isoon edit, kenttään
     */
    protected void naytaHahmo() {
        hahmoKohdalla = chooserHahmot.getSelectedObject();
        if (hahmoKohdalla == null) return;
        CharacterMuokkausGUIController.naytaHahmo(edits, hahmoKohdalla, editArea, editRotu);
        StatsGUIController.naytaHahmo(editss, hahmoKohdalla);
        naytaEsineet(hahmoKohdalla);
        naytaRotu(hahmoKohdalla);
    }
    
    private void naytaRotu(Hahmo hahmo) {
        if (hahmo == null) return;
        
        Collection<Rotu> loytyneet = rpg.annaRotu(hahmo.getHahmonrotu());
        for(Rotu rotu : loytyneet) {
            naytaRotu(rotu);
        }
    }
    
    private void naytaRotu(Rotu rotu) {
        editRotu.setText(rotu.getRotu());
    }
    
    private void naytaEsineet(Hahmo hahmo) {
        StringGridInventory.clear();
        if (hahmo == null)  return;
      
        try {
        Collection<HahmonEsine> loytyneet = rpg.annaEsineet(hahmo);
        Iterator<HahmonEsine> iterator = loytyneet.iterator();
        while(iterator.hasNext()) {
            HahmonEsine esine = iterator.next();
            Collection<Esine> esineita = rpg.annaEsine(esine);
            for (Esine esineeet : esineita) {
                naytaEsine(esineeet);
            }
        }
        } catch (SailoException e) {
            // naytaVirhe
        }
    }
    
    private void naytaEsine(Esine he) {
        String[] rivi = he.toString().split("\\|");
        StringGridInventory.add(rivi[1],rivi[2]);
    }
    

    /*
     * Luo uuden hahmon jota aletaan editoimaan
     */
    private void uusiHahmo() {
        try {
            Hahmo hahmo = new Hahmo();
            hahmo = CharacterMuokkausGUIController.kysyHahmo(null, hahmo);
            if ( hahmo == null ) return;
            if ( hahmo.getNimi().equals("")) return;
            hahmo.rekisteroi();

            rpg.lisaa(hahmo);
            hae(hahmo.getTunnusNro());
            Rotu rotu = new Rotu(editRotu.getText());
            Iterator<Rotu> rodut = rpg.annaRodut().iterator();
            while (rodut.hasNext()) {
                Rotu loydetty = rodut.next();
                if (loydetty.getRotu().toLowerCase().equals(rotu.getRotu().toLowerCase())) {
                    hahmo.setHahmonrotu(loydetty.getRotuID());
                    return;
                }
                continue;
            }
            rotu.rekisteroi();
            hahmo.setHahmonrotu(rotu.getRotuID());
            rpg.lisaa(rotu);
            
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden hahmon luomisessa" + e.getMessage());
            return;
        }
    }
    
    private void uusiEsine() {
        if (hahmoKohdalla == null) return;
        int id = hahmoKohdalla.getTunnusNro();
        
        Esine esine = new Esine();
        esine.rekisteroi();
        int iid = esine.getIDnumero();
        esine = ModalController.showModal(RpgGUIController.class.getResource("ItemsGUIView.fxml"), "New Item", null, esine);
        
        if (esine == null) return;
        Iterator<Esine> esineet = rpg.annaEsineet().iterator();
        while (esineet.hasNext()) {
            Esine loydetty = esineet.next();
            if(loydetty.getEsine().toLowerCase().equals(esine.getEsine().toLowerCase())) {
                HahmonEsine uusi = new HahmonEsine(id, loydetty.getIDnumero());
                try {
                rpg.lisaa(uusi);
                } catch (SailoException e) {
                    Dialogs.showMessageDialog("Ongelmia uuden luomiessa" + e.getMessage());
                    return;
                }
                hae(hahmoKohdalla.getTunnusNro());
                return;
            }
            
        }


        HahmonEsine hahmonesine = new HahmonEsine(id , iid);        
        try {
            rpg.lisaa(esine);
            rpg.lisaa(hahmonesine);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }   
        
        hae(hahmoKohdalla.getTunnusNro());
    }
    
    private void poistaEsine() {
        int rivi = StringGridInventory.getRowNr(); 
        if ( rivi < 0 ) return; 
        String k = StringGridInventory.get(rivi, 0);
        int iid = loydaEsineID(k);
        int id = hahmoKohdalla.getTunnusNro();
        
        rpg.poistaEsine(id, iid); 
        naytaEsineet(hahmoKohdalla); 
        int esineita = StringGridInventory.getItems().size();
        if ( rivi >= esineita ) rivi = esineita -1;
        StringGridInventory.getFocusModel().focus(rivi);
        StringGridInventory.getSelectionModel().select(rivi);
        hae(hahmoKohdalla.getTunnusNro());
    }
    
    /**
     * @param rpg Rpg jota käytetään täsää käyttöliittymässä
     */
    public void setRpg(Rpg rpg) {
        this.rpg = rpg;
        naytaHahmo();
    }
    
    /**
     * @param esine esine jota haetaan
     * @return esineen id
     */
    public int loydaEsineID(String esine) {
        return rpg.loydaEsineID(esine);
    }
    
    /*
     * Hakee jäsenten tiedot listalle
     */
    private void hae(int jnro) {
        chooserHahmot.clear();
        
        int index = 0;
        for (int i = 0; i < rpg.getHahmoja(); i++) {
            Hahmo hahmo = rpg.annaHahmo(i);
            if( hahmo.getTunnusNro() == jnro) index = i;
            chooserHahmot.add(hahmo.getNimi(), hahmo);
        }
        
        chooserHahmot.getSelectionModel().select(index);
        
    }
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    /*
     * tietojen talletus, muuten virhe tekstinä
     */
    private String tallenna() {
        try {
            rpg.talleta();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    /**
     * kun käyttäjä painaa help, niin metodi vie käyttäjän projektin tim sivulle
     */
    private void apua() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI oURL = new URI ("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/ilahilma");
            desktop.browse(oURL);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
    
    /**
     * Tulostaa adminin tiedot
     * @param os tietovirta johon tulostetaan
     * @param hahmo tulostettava hahmo
     * @throws SailoException jos ei onnistu
     */
    public void tulosta(PrintStream os, final Hahmo hahmo) throws SailoException  {
        os.println("----------------------------------------------");
        hahmo.tulosta(os);
        os.println("----------------------------------------------");
        Collection<HahmonEsine> loytyneet = rpg.annaEsineet(hahmo);
        Iterator<HahmonEsine> iterator = loytyneet.iterator();

        while(iterator.hasNext()) {
            HahmonEsine esine = iterator.next();
            Collection<Esine> esineita = rpg.annaEsine(esine);
            for (Esine esineeet : esineita) {
                esineeet.tulosta(os);
            }
        }
    }
   
    /**
     * Tulostaa listassa olevat jäsenet tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
            os.println("Tulostetaan kaikki jäsenet");
            Collection<Hahmo> hahmot = rpg.etsi("", -1); 
            for (Hahmo hahmo:hahmot) { 
                tulosta(os, hahmo);
                os.println("\n\n");
            }
        } catch (SailoException ex) { 
            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage()); 
        }
    }
    
    /**
     * muokaa hahmoa
     */
    public void muokkaa() {
        if ( hahmoKohdalla == null) return;
        try {
            Hahmo hahmo;
            hahmo = CharacterMuokkausGUIController.kysyHahmo(null, hahmoKohdalla.clone());
            if ( hahmo == null ) return;
            if ( hahmoKohdalla.equals2(hahmo)) return;
            rpg.korvaaTaiLisaa(hahmo);
            hae(hahmo.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    /**
     * Muokkaa statteja
     */
    public void muokkaaStatteja() {
        if ( hahmoKohdalla == null) return;
        try {
            Hahmo hahmo;
            hahmo = StatsGUIController.kysyHahmo(null, hahmoKohdalla.clone());
            if ( hahmo == null ) return;
            if ( hahmoKohdalla.equals2(hahmo)) return;
            rpg.korvaaTaiLisaa(hahmo);
            hae(hahmo.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    private void poistaHahmo() {
        Hahmo hahmo = hahmoKohdalla;
        if(hahmo == null) return;
        if ( !Dialogs.showQuestionDialog("Delete", "Delete Character: " + hahmo.getNimi(), "Yes", "No") )
            return;
        rpg.poista(hahmo);
        int index = chooserHahmot.getSelectedIndex(); 
        hae(0); 
        chooserHahmot.setSelectedIndex(index);
        naytaEsineet(hahmoKohdalla);
    }
    
    /**
     * hakee hahmon search bariin
     */
    public void haku() {
        String haku = textHaku.getText().toLowerCase();
        ArrayList<Integer> jnumerot = new ArrayList<Integer>();
        for (int i = 0; i < rpg.getHahmoja(); i++) {
            if (rpg.annaHahmo(i).getNimi().toLowerCase().startsWith(haku)) jnumerot.add(rpg.annaHahmo(i).getTunnusNro());
        }
        chooserHahmot.clear();
        
        int index = 0;
        for (int i = 0; i < rpg.getHahmoja(); i++) {
            Hahmo hahmo = rpg.annaHahmo(i);
            for (int j = 0; j < jnumerot.size(); j++) {
                if( hahmo.getTunnusNro() == jnumerot.get(j)) {
                    index = i;
                    chooserHahmot.add(hahmo.getNimi(), hahmo);
                }
            }
        }
        
        chooserHahmot.getSelectionModel().select(index);
        
    }
    
}
