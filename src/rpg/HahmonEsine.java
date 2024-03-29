package rpg;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Toimii liitetiedostona
 * @author Ilari Massa
 * @version Mar 20, 2019
 *
 */
public class HahmonEsine {
    
    private int tunnusNro;
    private int iid;
    
    private static int seuraavaNro = 1;
    
    /**
     * HahmonEsineen muodostaja
     */
    public HahmonEsine() {
        //Ei vielä mitään
    }
    
    
    /**
     * @param tunnusNro Hahmon id
     * @param iid esineen id
     */
    public HahmonEsine(int tunnusNro, int iid) {
        this.tunnusNro = tunnusNro;
        this.iid = iid;
    }
    
    /**
     * @param tunnusNro hahmon tunnusNro
     */
    public HahmonEsine(int tunnusNro) {
        this.tunnusNro = tunnusNro;
    }
    
    /**
     * hakee esineen id:n
     * @return esineen id
     */
    public int getIID() {
        return iid;
    }
    
    /**
     * @return hakee Hahmon id:n
     * @example
     * <pre name="test">
     *   HahmonEsine  esine = new HahmonEsine(1,1);
     *   esine.getTunnusNro() === 1;
     * </pre> 
     */
    public int getTunnusNro() {
        return tunnusNro;
    }

    /**
     * Tulostetaan hahmonesine
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        //out.println("  " + tyyli);
        out.println(String.format("tunnusNro: %03d" + "\n" + "iid: %03d" + "\n", tunnusNro, iid));
    }
    
    
    /**
     * Tulostetaan hahmon esineiden tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * rekisteroi esineen id
     * @return esineen id
     */
    public int rekisteroi() {
        //if (iid!=0) return iid;
        iid = seuraavaNro;
        seuraavaNro++;
        return iid;
    }
    
    /**
     * @param id on esineen id
     */
    public void setTunnusNro(int id) {
        this.tunnusNro = id;
    }
    
    /**
     * @param id on esineen id
     */
    public void setIID(int id) {
        this.iid = id;
    }
    
    /**
     * Palauttaa hahmojenesineiden id ja tunnusNRO tiedot merkkijonona jonka voi tallentaa tiedostoon
     * @return hahmon tolppaeroteltuna merkkijononna
     * @example
     * <pre name="test">
     *  HahmonEsine esine = new HahmonEsine();
     *  esine.parse("1");
     *  esine.toString().startsWith("1|") === true;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                getIID();
    }
    
    /**
     * Selvittää hahmon tiedot | erotellusta merkistä
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta hahmon tiedot saadaan
     * 
     * @example
     * <pre name="test">
     *  HahmonEsine esine = new HahmonEsine();
     *  esine.parse("   1   ");
     *  esine.getTunnusNro() === 1;
     *  esine.toString().startsWith("1|") === true;  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        setIID(Mjonot.erota(sb, '|', getIID()));
    }
    
    @Override
    public boolean equals(Object esine) {
        if ( esine == null) return false;
        return this.toString().equals(esine.toString());  
    }
    
    @Override
    public int hashCode() {
        return iid;
    }    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        HahmonEsine t = new HahmonEsine();
        t.rekisteroi();
        t.tulosta(System.out);
    }
    
}
