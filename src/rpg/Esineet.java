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
public class Esineet implements Iterable<Esine> {
   
    private String           tiedostonNimi= "";
   
   private final Collection<Esine> alkiot = new ArrayList<Esine>();
   
   private boolean muutettu = false;


   /**
   * Esineiden muodostaja vielä tyhjä
   */
   public Esineet() {
       // Attribuuttien oma alustus riittää
   }
   
    /**
    * @param esine joka lisätään
    * @example
    */
   public void lisaa(Esine esine) {
       alkiot.add(esine);
       muutettu = true;
   }

   /**
   * @param tunnusNro esineen tunnusNro
   * @return loydetyt esineet
    */
   public List<Esine> annaEsine(int tunnusNro) {
       List<Esine> loydetyt = new ArrayList<Esine>();
       for (Esine esine : alkiot) 
           if (esine.getIDnumero() == tunnusNro) loydetyt.add(esine);

       return loydetyt;
   }
   
  /**
  * antaa esineen ID:n jonka nimi on parametri
  * @param nimi jota haetaan
  * @return esineen ID
  */
   public int annaEsine(String nimi) {
       for (Esine esine : alkiot) { 
           String k = esine.getNimi();
           if (k.equals(nimi)) return esine.getIDnumero();
       }
       return -1;
   }
   
  /**
  * antaa listan esineista
  * @return loydetyt esineet
  */
   public List<Esine> annaEsineet() {
       List<Esine> loydetyt = new ArrayList<Esine>();
       for (Esine esine : alkiot) {
           loydetyt.add(esine);
       }
       
       return loydetyt;
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
     *  Esineet harrasteet = new Esineet();
     *  Esine miekka = new Esine(); miekka.taytaMiekka(); 
     *  Esine kirves = new Esine(); kirves.taytaMiekka();
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  harrasteet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  harrasteet.lisaa(kirves);
     *  harrasteet.lisaa(miekka);
     *  harrasteet.tallenna();
     *  harrasteet = new Esineet();
     *  harrasteet.lueTiedostosta(tiedNimi);
     *  Iterator<Esine> i = harrasteet.iterator();
     *  i.next().toString() === miekka.toString();
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
               Esine esine = new Esine();
               esine.parse(rivi); // voisi olla virhekäsittely
               lisaa(esine);
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
    * Tiedoston muoto:
    * <pre>
    * Kelmien kerho
    * 20
    * ; kommenttirivi
    * 2|Ankka Aku|121103-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
    * 3|Ankka Tupu|121153-706Y|Paratiisitie 13|12345|ANKKALINNA|12-1234|||1996|50.0|30.0|Velkaa Roopelle
    * </pre>
    * @throws SailoException jos talletus epäonnistuu
    */
   public void tallenna() throws SailoException {
       if ( !muutettu ) return;

       File fbak = new File(getBakNimi());
       File ftied = new File(getTiedostonNimi());
       fbak.delete(); // if .. System.err.println("Ei voi tuhota");
       ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

       try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
           for (Esine esine : this) {
               fo.println(esine.toString());
           }
           //} catch ( IOException e ) { // ei heitä poikkeusta
           //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
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
    * Palauttaa Esineiden lukumÃ¤Ã¤rÃ¤n
    * @return adminien tyylikategorioiden lukumÃ¤Ã¤rÃ¤
    */
   public int getLkm() {
       return alkiot.size();
   }   
   
 
   /**
    * Iteraattori kaikkien harrastusten läpikäymiseen
    * @return harrastusiteraattori
    */
   @Override
   public Iterator<Esine> iterator() {
       return alkiot.iterator();
   }
   
   /**
    * Testiohjelma Esineiden nimille
    * @param args ei kÃ¤ytÃ¶ssÃ¤
    */
   public static void main(String[] args) {
       Esineet esineet = new Esineet();
       Esine rotu1 = new Esine();
       rotu1.taytaMiekka();
       Esine rotu2 = new Esine();
       rotu2.taytaMiekka();
       Esine rotu3 = new Esine();
       rotu3.taytaMiekka();
       Esine rotu4 = new Esine();
       rotu4.taytaMiekka();

       esineet.lisaa(rotu1);
       esineet.lisaa(rotu2);
       esineet.lisaa(rotu3);
       esineet.lisaa(rotu2);
       esineet.lisaa(rotu4);

       System.out.println("============= Esineet testi =================");

       List<Esine> rodut2 = esineet.annaEsine(0);
       

       for (Esine rotu : rodut2) {
           rotu.tulosta(System.out);
       }
   }
   
   
}
