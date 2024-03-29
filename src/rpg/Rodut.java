package rpg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author Ilari Massa
 * @version Mar 20, 2019
 *
 */
public class Rodut implements Iterable<Rotu> {
    
    private String tiedostonNimi = "";
    
    private final Collection<Rotu> alkiot = new ArrayList<Rotu>();
    private boolean muutettu = false;
    
    /**
     * Rodut muodostaja
     */
    public Rodut() {
        //for(int i = 0; i<3 ; i++) {
        //    Rotu esim1 = new Rotu();
         //   esim1.taytaRotu(i);
          //  lisaa(esim1);
       //}
    }
    /**
     * @param rotu rotu id
     */
    public void lisaa(Rotu rotu) {
        alkiot.add(rotu);
        muutettu = true;
    }
    
    /**
     * Lukee jäsenistön tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
      * @example
      * <pre name="test">
      * #THROWS SailoException 
      * #import java.io.*;
      * #import java.util.*;
      *  Rodut harrasteet = new Rodut();
      *  Rotu miekka = new Rotu(); miekka.taytaRotu(0); 
      *  Rotu kirves = new Rotu(); kirves.taytaRotu(1);
      *  String tiedNimi = "testikelmit";
      *  File ftied = new File(tiedNimi+".dat");
      *  ftied.delete();
      *  harrasteet.lueTiedostosta(tiedNimi); #THROWS SailoException
      *  harrasteet.lisaa(kirves);
      *  harrasteet.lisaa(miekka);
      *  harrasteet.tallenna();
      *  harrasteet = new Rodut();
      *  harrasteet.lueTiedostosta(tiedNimi);
      *  Iterator<Rotu> i = harrasteet.iterator();
      *  i.hasNext() === true;
      *  harrasteet.lisaa(miekka);
      *  harrasteet.lisaa(kirves);
      *  harrasteet.tallenna();
      *  ftied.delete() === true;
      *  File fbak = new File(tiedNimi+".bak");
      *  fbak.delete() === true;
      * </pre>
      */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Rotu rotu = new Rotu();
                rotu.parse(rivi); // voisi olla virhekäsittely
                lisaa(rotu);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }    

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonNimi;
    }
    

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonNimi = nimi;
    }    
    
    /**
     * Tallentaa jäsenistön tiedostoon.  
     * @throws SailoException jos liian iso
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Rotu rotu : this) {
                fo.println(rotu.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonNimi + ".bak";
    }
    
    /**
     * @return hakee taulukon koon
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    @Override
    public Iterator<Rotu> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * @param tunnusNro hahmon tunnusNro
     * @return löydetyt hahmot
     */
    public List<Rotu> annaRotu(int tunnusNro) {
        List<Rotu> loydetyt = new ArrayList<Rotu>();
        for (Rotu rotu : alkiot) 
            if (rotu.getRotuID() == tunnusNro) loydetyt.add(rotu);

        return loydetyt;
    }
    
    /**
     * @return loydetyt rodut
     */
    public List<Rotu> annaRodut() {
        List<Rotu> loydetyt = new ArrayList<Rotu>();
        for (Rotu rotu : alkiot)
            loydetyt.add(rotu);
        
        return loydetyt;
    }
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Rodut rodut = new Rodut();
        Rotu rotu1 = new Rotu();
        rotu1.taytaRotu(2);
        Rotu rotu2 = new Rotu();
        rotu2.taytaRotu(1);
        Rotu rotu3 = new Rotu();
        rotu3.taytaRotu(3);
        Rotu rotu4 = new Rotu();
        rotu4.taytaRotu(4);

        rodut.lisaa(rotu1);
        rodut.lisaa(rotu2);
        rodut.lisaa(rotu3);
        rodut.lisaa(rotu2);
        rodut.lisaa(rotu4);

        System.out.println("============= Rodut testi =================");

        List<Rotu> rodut2 = rodut.annaRotu(0);
        

        for (Rotu rotu : rodut2) {
            System.out.print(rotu.getRotu() + " ");
            rotu.tulosta(System.out);
        }
    }
}
