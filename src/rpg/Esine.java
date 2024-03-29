package rpg;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Esine jota hahmo käyttää
 * 
 * @author Ilari Massa
 * @version Mar 20, 2019
 *
 */
public class Esine {
    private int iid;
    private String esine = "";
    private String notes = "";
     
    private static int seuraavaNro = 1;
    
    /**
     * Esineen muodostaja ilman paarametrejä
     */
    public Esine(){
        // vielä ei mitään
    }

    /**
     * @param iid esineen id
     */
    public Esine(int iid) {
        this.iid = iid;
    }
    
    /**
     * @return esineen nimi
     */
    public String getNimi() {
        return esine;
    }
    
    /**
     * @return esineen tiedot
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * @param esine jonka nimi asetetaan
     */
    public void setEsine(String esine) {
        this.esine = esine;
    }
    
    /**
     * Tekee miekan
     */
    public void taytaMiekka() {
        esine = "Miekka";
        notes = "+1 attack damage";
    }
    
    /**
     * @param out tietovirta mihin tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(iid + " " + esine + " " + notes);
    }
    
    /**
     * @param os tietovirta mihin tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa esineelle rekisterinumeron
     * @return esineen uusi tunnus
     * @example
     * <pre name ="test">
     *  Esine miekka = new Esine();
     *  miekka.getIDnumero() === 0;
     *  miekka.rekisteroi();
     *  Esine kirves = new Esine();
     *  kirves.rekisteroi();
     *  int n1 = miekka.getIDnumero();
     *  int n2 = kirves.getIDnumero();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        iid = seuraavaNro;
        seuraavaNro++;
        return iid;
    }
    
    /**
     * @return esineen id numero
     */
    public int getIDnumero() {
        return iid;
    }
    
    /**
     * @return esineen nimi
     */
    public String getEsine() {
        return esine;
    }
    
    /**
     * @param id on esineen id
     */
    public void setEsine(int id) {
        iid = id;
        if (iid >= seuraavaNro) seuraavaNro = iid + 1;
    }
    
    /**
     * @param nimi joka asetetaan
     */
    public void setNimi(String nimi) {
        esine = nimi;
    }
    
    /**
     * @param tiedot jotka asetetaan
     */
    public void setNotes(String tiedot) {
        notes = tiedot;
    }
    
    /**
     * Palauttaa esineen tiedot merkkijonona jonka voi tallentaa tiedostoon
     * @return hahmon tolppaeroteltuna merkkijononna
     * @example
     * <pre name="test">
     *  Esine esine = new Esine();
     *  esine.parse("2|Ankka Aku");
     *  esine.toString().startsWith("2|Ankka Aku|") === true;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getIDnumero() + "|" +
                esine + "|" +
                notes;
    }
    
    /**
     * Selvittää esineen tiedot | erotellusta merkistä
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta hahmon tiedot saadaan
     * 
     * @example
     * <pre name="test">
     *  Esine esine = new Esine();
     *  esine.parse("   1   | Kirves ");
     *  esine.getIDnumero() === 1;
     *  esine.toString().startsWith("1|Kirves|") === true;
     *  
     *  esine.rekisteroi();
     *  int n = esine.getIDnumero();
     *  esine.parse(""+(n+20));
     *  esine.rekisteroi();
     *  esine.getIDnumero() === n+20+1;
     *  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setEsine(Mjonot.erota(sb, '|', getIDnumero()));
        esine = Mjonot.erota(sb, '|', esine);
        notes = Mjonot.erota(sb, '|', notes);
    }
    
    @Override
    public boolean equals(Object esine1) {
        if ( esine1 == null) return false;
        return this.toString().equals(esine1.toString());  
    }
    
    @Override
    public int hashCode() {
        return iid;
    }
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Esine esine = new Esine();
        esine.taytaMiekka();
        esine.tulosta(System.out);
        Esine itemi = new Esine();
        itemi.parse("2|Ankka Aku|antaa statteja");
        itemi.tulosta(System.out); 
    }
    
    
    
    
    
}
