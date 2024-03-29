package rpg;

import java.io.File;
import java.util.Collection;
/**
 * Rpg-luokka joka huolehtii jäsenistöstä. Pääosin kaikki metodit
 * 
 * @author Ilari Massa
 * @version Feb 28, 2019
 *
 */
public class Rpg {
    private Hahmot hahmot = new Hahmot();
    private Esineet esine = new Esineet();
    private HahmojenEsineet esineet = new HahmojenEsineet();
    private Rodut alkiot = new Rodut();
    

    
    /**
     * Palauttaa rpg:n jäsenmäärän
     * @return hahmo määrä
     */
    public int getHahmoja() {
        return hahmot.getLkm();
    }
    
    /**
     * @param k esinen nimi
     * @return esineen id
     */
    public int loydaEsineID(String k) {
        return esine.annaEsine(k);
    }
    
    /**
     * Poistaa Rpg:sta ne joillo on nro. Kesken
     * @param hahmo joka poistetaan
     * @return montako hahmoa poistetaan
     */
    public int poista(Hahmo hahmo) { 
             if ( hahmo == null ) return 0; 
             int ret = hahmot.poista(hahmo.getTunnusNro());  
             esineet.poistaHahmonEsineet(hahmo.getTunnusNro());  
             return ret;  
         } 
    
    /**
     * hahmon esineen poisto
     * @param id hahmon id
     * @param iid esineen id
     * @return onnistuiko poisto
     */
    public boolean poistaEsine(int id, int iid) {
        return esineet.poista(id, iid);
    }
    
    /**
     * Lisää rpg:hen uuden jäsenen
     * @param hahmo lisättävä hahmo
     * @throws SailoException jos lisäystä ei voi tehdä
     * @example
     * <pre name ="test">
     *  #THROWS SailoException
     *  Rpg rpg = new rpg();
     *  Hahmo aku1 = new Hahmo(), Hahmo aku2 = new Hahmo();
     *  aku1.rekisteroi(), aku2.rekisteroi();
     *  rpg.getHahmoja() === 0;
     *  rpg.lisaa(aku1); rpg.getHahmoja() === 1;
     *  rpg.lisaa(aku2); rpg.getHahmoja() === 2;
     *  rpg.lisaa(aku1); rpg.getHahmoja() === 3;
     *  rpg.annaHahmo(0) === aku1;
     *  rpg.annaHahmo(1) === aku2;
     *  rpg.annaHahmo(2) === aku1;
     *  rpg.annaHahmo(1) == aku1 === false;
     *  rpg.anna(3) === aku1; #THROWS IndexOutOfBoundsException
     *  rpg.lisaa(aku1); rpg.getLkm() === 4;
     *  rpg.lisaa(aku1); rpg.getLkm() === 5;
     *  rpg.lisaa(aku1); #THROWS SailoException
     * </pre>
     */
    public void lisaa(Hahmo hahmo) throws SailoException {
        hahmot.lisaa(hahmo);
    }
    
    /**
     * @param esine1 esine
     * @throws SailoException jos liika
     */
    public void lisaa(Esine esine1) throws SailoException {
        esine.lisaa(esine1);
    }

    /**
     * @param rotu on rotu
     * @throws SailoException jos liikaa
     */
    public void lisaa(Rotu rotu) throws SailoException {
        alkiot.lisaa(rotu);
    }
    
    /**
     * @param esine1 Hahmone esine
     * @throws SailoException jos lisäys ei onnistu
     */
    public void lisaa(HahmonEsine esine1) throws SailoException {
        esineet.lisaa(esine1);
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Hahmo> etsi(String hakuehto, int k) throws SailoException { 
        return hahmot.etsi(hakuehto, k); 
    } 
    /**
     * @param hahmo hahmo jonka esineet otetaan
     * @return hahmon esineet
     * @throws SailoException jos 
     */
    public Collection<HahmonEsine> annaEsineet(Hahmo hahmo) throws SailoException {
        return esineet.annaHahmonEsine(hahmo.getTunnusNro());
    }
    
    /**
     * Palautta i:n jäsenen
     * @param i monesko jäsen palautetaan
     * @return viite i:teen jäseneen
     */
    public Hahmo annaHahmo(int i)  {
        return hahmot.anna(i);
    }
    
    /**
     * @param hahmonesine esine joka annetaan
     * @return hahmon esine
     * @throws SailoException jos
     */
    public Collection<Esine> annaEsine(HahmonEsine hahmonesine) throws SailoException {
        return esine.annaEsine(hahmonesine.getIID());
    }
    
    /**
     * antaa tietyn rodun 
     * @param id rodun id
     * @return tietty rotu
     */
    public Collection<Rotu> annaRotu(int id) {
        return alkiot.annaRotu(id);
    }
    
    /**
     * antaa kaikki rodut
     * @return rodut
     */
    public Collection<Rotu> annaRodut() {
        return alkiot.annaRodut();
    }
    
    /**
     * antaa kaikki esineet
     * @return kaikki esineet
     */
    public Collection<Esine> annaEsineet() {
        return esine.annaEsineet();
    }

    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        hahmot.setTiedostonPerusNimi(hakemistonNimi + "nimet");
        esine.setTiedostonPerusNimi(hakemistonNimi + "esineet");
        esineet.setTiedostonPerusNimi(hakemistonNimi + "hahmojen_esineet");
        alkiot.setTiedostonPerusNimi(hakemistonNimi + "rodut");
    }
    
    /**
     * Lukee Rpg:een tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen ei onnistu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     * 
     *  Rpg kerho = new Rpg();
     *  
     *  Hahmo aku1 = new Hahmo(); aku1.taytaAkuAnkka(); aku1.rekisteroi();
     *  Hahmo aku2 = new Hahmo(); aku2.taytaAkuAnkka(); aku2.rekisteroi();
     *  
     *  Esine esine1 = new Esine(); esine1.taytaMiekka(); esine1.rekisteroi();
     *  Esine esine2 = new Esine(); esine2.taytaMiekka(); esine2.rekisteroi();
     *  
     *  Rotu rotu1 = new Rotu(); rotu1.taytaRotu(1);
     *  Rotu rotu2 = new Rotu(); rotu2.taytaRotu(2);
     *  
     *  HahmonEsine he1 = new HahmonEsine(1,1);
     *  HahmonEsine he2 = new HahmonEsine(2,2); 
     *  
     *  String hakemisto = "testikelmit";
     *  File dir = new File(hakemisto);
     *  File ftied  = new File(hakemisto+"/nimet.dat");
     *  File ftied1  = new File(hakemisto+"/esineet.dat");
     *  File ftied2  = new File(hakemisto+"/hahmojen_esineet.dat");
     *  File ftied3  = new File(hakemisto+"/rodut.dat");
     *  dir.mkdir();  
     *  ftied.delete();
     *  ftied1.delete();
     *  ftied2.delete();
     *  ftied3.delete();
     *  kerho.lueTiedostosta(hakemisto); #THROWS SailoException
     *  kerho.lisaa(aku1);
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(esine1);
     *  kerho.lisaa(esine2);
     *  kerho.lisaa(rotu1);
     *  kerho.lisaa(rotu2);
     *  kerho.lisaa(he1);
     *  kerho.lisaa(he2);
     *  kerho.talleta();
     *  Rpg rpg = new Rpg();
     *  rpg.lueTiedostosta(hakemisto);
     *  Collection<Hahmo> kaikki = kerho.etsi("",-1); 
     *  Iterator<Hahmo> it = kaikki.iterator();
     *  it.next() === aku1;
     *  it.next() === aku2;
     *  it.hasNext() === false;
     *  kerho.lisaa(aku2);
     *  kerho.lisaa(esine2);
     *  kerho.lisaa(rotu2);
     *  kerho.lisaa(he2);
     *  kerho.talleta();
     *  ftied.delete() === true;
     *  ftied1.delete() === true;
     *  ftied2.delete() === true;
     *  ftied3.delete() === true;
     *  File fbak = new File(hakemisto+"/nimet.bak");
     *  File fbak1 = new File(hakemisto+"/esineet.bak");
     *  File fbak2 = new File(hakemisto+"/hahmojen_esineet.bak");
     *  File fbak3 = new File(hakemisto+"/rodut.bak");
     *  fbak.delete() === true;
     *  fbak1.delete() === true;
     *  fbak2.delete() === true;
     *  fbak3.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        hahmot = new Hahmot(); // jos luetaan olemassa olevaan niin helpoin tyhjentää näin
        esine = new Esineet();
        esineet = new HahmojenEsineet();
        alkiot = new Rodut();

        setTiedosto(nimi);
        
        hahmot.lueTiedostosta();
        esine.lueTiedostosta();
        esineet.lueTiedostosta();
        alkiot.lueTiedostosta();
    }
    
    /**
     * Tallettaa rpg:en tiedot tiedostoon
     * @throws SailoException jos ei voi tallettaa
     */
    public void talleta() throws SailoException {
        String virhe = "";
        try {
            hahmot.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            esine.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            esineet.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        try {
            alkiot.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }        
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
        }
    
    /**
     * Korvaa hahmon tietorakenteessa, Ottaa Hahmon omistukseensa
     * Etsitään samalla tunnusnumerolla oleva hahmo. Jos ei löydy,
     * niin lisätään uutena hahmona
     * 
     * @param hahmo lisättävän hahmon viite. 
     * @throws SailoException jos tietokanta on täynnä
     */
    public void korvaaTaiLisaa(Hahmo hahmo) throws SailoException {
        hahmot.korvaaTaiLisaa(hahmo);
    }
    /**
     * Testiohjelma Rpg:sta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Rpg rpg = new Rpg();
    
        try {
            
            Hahmo aku1 = new Hahmo(), aku2 = new Hahmo();
            aku1.rekisteroi();
            aku1.taytaAkuAnkka();
            aku2.rekisteroi();
            aku2.taytaAkuAnkka();
            
            rpg.lisaa(aku1);
            rpg.lisaa(aku2);
            
            System.out.println("========== RPG:N testi ===========");
            
            for (int i = 0; i < rpg.getHahmoja(); i++) {
                Hahmo hahmo = rpg.annaHahmo(i);
                System.out. println("Hahmo paikassa: " + i);
                hahmo.tulosta(System.out);
            }
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    
    }
    
    

}
