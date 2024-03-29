package rpg;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Ilari Massa
 * @version Mar 20, 2019
 *
 */
public class Rotu {
    ///
    private int rid;
    private String rotu;
    
    private static int seuraavaNro = 1;
    
    /**
     * konstruktori rodulle
     */
    public Rotu() {
        // ei mitään
    }
    
    /**
     * konstruktori rodulle jolle annetaan nimi
     * @param nimi rodun nimi
     */
    public Rotu(String nimi) {
        rotu = nimi;
    }
    
    /**
     * @param id rotu id
     */
    public void taytaRotu(int id) {
        Random rand = new Random();
        int i  = 0 + rand.nextInt(3);
        
        rid = id;
        String[] testirodut = {"Vulcan", "Ainur", "derp"};
        rotu = testirodut[i];
    }
    
    /**
     * @return get rotu id
     */
    public int getRotuID() {
        return rid;
    }
    
    /**
     * @return rotu 
     */
    public String getRotu() {
        return rotu;
    }
    
    /**
     * @param out tulostaa tietovirran
     */
    public void tulosta(PrintStream out) {
        out.println(rotu);
    }
    
    /**
     * @param os tulostaa tietovirran
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * @return rodun id
     * @example
     * <pre name ="test">
     *  Rotu miekka = new Rotu();
     *  miekka.getRotuID() === 0;
     *  miekka.rekisteroi();
     *  Rotu kirves = new Rotu();
     *  kirves.rekisteroi();
     *  int n1 = miekka.getRotuID();
     *  int n2 = kirves.getRotuID();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        rid = seuraavaNro;
        seuraavaNro++;
        return rid;
    }
    
    
    /**
     * @param id on esineen id
     */
    public void setRotu(int id) {
        rid = id;
        if (rid >= seuraavaNro) seuraavaNro = rid + 1;
    }
    
    /**
     * Palauttaa hahmon tiedot merkkijonona jonka voi tallentaa tiedostoon
     * @return hahmon tolppaeroteltuna merkkijononna
     * @example
     * <pre name="test">
     *  Rotu rotu = new Rotu();
     *  rotu.parse("2");
     *  rotu.toString().startsWith("2") === true;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getRotuID() + "|" +
                rotu;
    }
    
    /**
     * Selvittää hahmon tiedot | erotellusta merkistä
     * Åitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta hahmon tiedot saadaan
     * 
     * @example
     * <pre name="test">
     *  Rotu esine = new Rotu();
     *  esine.parse("   1   ");
     *  esine.getRotuID() === 1;
     *  esine.toString().startsWith("1|") === true;
     *  
     *  esine.rekisteroi();
     *  int n = esine.getRotuID();
     *  esine.parse(""+(n+20));
     *  esine.rekisteroi();
     *  esine.getRotuID() === n+20+1;
     *  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setRotu(Mjonot.erota(sb, '|', getRotuID()));
        rotu = Mjonot.erota(sb, '|', rotu);
    }
    
    @Override
    public boolean equals(Object rotu1) {
        if ( rotu1 == null) return false;
        return this.toString().equals(rotu1.toString());  
    }
    
    @Override
    public int hashCode() {
        return rid;
    }    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Rotu rotu = new Rotu();
        rotu.taytaRotu(2);
        rotu.tulosta(System.out);
    }

}
