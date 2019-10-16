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
 * @version Mar 18, 2019
 *
 */
public class HahmojenEsineet implements Iterable<HahmonEsine>{    
    
    private String tiedostonNimi = "";
    private boolean muutettu = false;
    
    private final Collection<HahmonEsine> alkiot = new ArrayList<HahmonEsine>();
    
    
    /**
     * Tyylien alustaminen.
     */
    public HahmojenEsineet() {
        // ei tee toistaiseksi mitään
    }
    
    /**
     * Lisää uuden hahmonesineen tietorakenteeseen. Ottaa hahmonesineet omistukseensa.
     * @param esine lisÃ¤ttÃ¤vÃ¤ tyyli
     * @throws SailoException jos lisÃ¤Ã¤minen epÃ¤onnistuu
     */
    public void lisaa(HahmonEsine esine) throws SailoException {
        alkiot.add(esine);
        muutettu = true;
    }
    
    /**
     * 
     * @param id hahmonTunnusNro
     * @return montako poistettiin
     */
    public int poistaHahmonEsineet(int id) {
        int n = 0; 
        for (Iterator<HahmonEsine> it = alkiot.iterator(); it.hasNext();) { 
            HahmonEsine har = it.next();  
            if ( har.getTunnusNro() == id ) {
                it.remove(); 
                n++; 
            }
        }
        if (n > 0) muutettu = true; 
        return n; 
    }

    /**
     * poistaa hahmon esineen
     * @param id hahmon id
     * @param iid esineen id
     * @return onnistuiko poisto
     */
    public boolean poista(int id, int iid) {
        int n = 0; 
        for (Iterator<HahmonEsine> it = alkiot.iterator(); it.hasNext();) { 
            HahmonEsine har = it.next();  
            if ( har.getTunnusNro() == id && har.getIID() == iid ) {
                it.remove(); 
                n++; 
            }
        }
        if (n > 0) muutettu = true; 
        return true;
    }
    
    /**
     * Iteraattori kaikkien esineiden läpikäyymiseen
     * @return esine-iteraattori
     */
    @Override
    public Iterator<HahmonEsine> iterator() {
        return alkiot.iterator();
    }
 
    /**
     * Palauttaa hahmojen esineiden lukumÃ¤Ã¤rÃ¤n.
     * @return tyylien lukumÃ¤Ã¤rÃ¤
     */
    public int getLkm() {
        return alkiot.size();
    }
    
    

    /**
     * Haetaan hahmon esineet
     * @param tunnusNro hahmon jonka esineitää haetaan
     * @return tietorakenne josta viite löydettän
     */
    public List<HahmonEsine> annaHahmonEsine(int tunnusNro) {
        List<HahmonEsine> loydetyt = new ArrayList<HahmonEsine>();
        for(HahmonEsine esine : alkiot)
            if(esine.getTunnusNro() == tunnusNro) loydetyt.add(esine);
        
        return loydetyt;
    }
    
    
     /**
     * Lukee HahmojenEsineiden tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.*;
     * #import java.util.*;
     *  HahmojenEsineet harrasteet = new HahmojenEsineet();
     *  HahmonEsine miekka = new HahmonEsine(2,2); 
     *  HahmonEsine kirves = new HahmonEsine(2,2);
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  harrasteet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  harrasteet.lisaa(kirves);
     *  harrasteet.lisaa(miekka);
     *  harrasteet.tallenna();
     *  harrasteet = new HahmojenEsineet();
     *  harrasteet.lueTiedostosta(tiedNimi);
     *  Iterator<HahmonEsine> i = harrasteet.iterator();
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
                HahmonEsine esine = new HahmonEsine();
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
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonNimi = nimi;
    }    
    
    /**
     * Tallentaa jäsenistön tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (HahmonEsine esine : this) {
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
     * Testiohjelma HahmonEsineille.
     * @param args ei kÃ¤ytÃ¶ssÃ¤
     */
    public static void main(String[] args) {
        HahmojenEsineet esineet = new HahmojenEsineet();
        Esine miekka = new Esine();
        miekka.rekisteroi();
        miekka.taytaMiekka();
        int id = miekka.getIDnumero();
        
        HahmonEsine esine = new HahmonEsine(id,1);
        try {
            esineet.lisaa(esine);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("============= HahmonEsineet testi =================");

        List<HahmonEsine> esineet2 = esineet.annaHahmonEsine(1);
       
        for (HahmonEsine esin : esineet2) {
            System.out.print(esin.getTunnusNro() + " ");
            esin.tulosta(System.out);
        }
        
    }
    
}
