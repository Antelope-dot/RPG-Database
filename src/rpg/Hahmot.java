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
 * Rpg:n jäsenistö joka osaa mm. lisätä uuden hahmon
 * 
 * @author Ilari Massa
 * @version Feb 28, 2019
 *
 */
public class Hahmot implements Iterable<Hahmo> {
    private static final int MAX_HAHMOJA  = 15;
    private boolean muutettu = false;
    private int              lkm          = 0;
    private String           kokoNimi     = "";
    private String           tiedostonNimi= "nimet";
    private Hahmo            alkiot[]     = new Hahmo[MAX_HAHMOJA];
    
    /**
     * Oletus muodostaja
     */
    public Hahmot() {
        //
    }
    
    /**
     * @param hahmo joka lisataan tai korvataan
     * @throws SailoException jos tila ei riita
     */
    public void korvaaTaiLisaa (Hahmo hahmo) throws SailoException {
        int id = hahmo.getTunnusNro();
        for (int i = 0; i< lkm; i++) {
            if ( alkiot[i].getTunnusNro() == id) {
                alkiot[i] = hahmo;
                muutettu = true;
                return;
            }
        }
        lisaa(hahmo);
            
    }
    
    /**
     * poistaa hahmon 
     * @param id hahmon id
     * @return 0 jos ei onnistu ja 1 jos onnistuu
     */
    public int poista(int id) {  
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1];  
        alkiot[lkm] = null;
        muutettu = true;
        return 1;  
    }
    
    /**
     * @param id id jota haetaan
     * @return id jota haetaan
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    } 
    
    /**
     * @param id hahmon id
     * @return hahmon id
     */
    public Hahmo annaId(int id) { 
        for (Hahmo hahmo : this) { 
            if (id == hahmo.getTunnusNro()) return hahmo; 
        } 
        return null; 
    } 

    /**
     * Lisaa uuden jasenen tietorakenteeseen. Ottaa jäsenen omistukseensa
     * @param hahmo lisattavan hahmon viite
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     *  Hahmot hahmot = new Hahmot();
     *  Hahmo aku1 = new Hahmo(); Hahmo aku2 = new Hahmo();
     *  hahmot.getLkm() === 0;
     *  hahmot.lisaa(aku1); hahmot.getLkm() === 1;
     *  hahmot.lisaa(aku2); hahmot.getLkm() === 2;
     *  hahmot.lisaa(aku1); hahmot.getLkm() === 3;
     *  Iterator<Hahmo> it = hahmot.iterator();
     *  it.next() === aku1;
     *  it.next() === aku2;
     *  it.next() === aku1;
     *  hahmot.lisaa(aku1); hahmot.getLkm() === 4;
     *  hahmot.lisaa(aku1); hahmot.getLkm() === 5;
     *  hahmot.lisaa(aku1);
     * </pre>
     */
    public void lisaa(Hahmo hahmo) throws SailoException {
        if (lkm >= alkiot.length) {
            Hahmo[] uusi = new Hahmo[lkm*2]; 
            for(int i = 0; i<lkm;i++) {
                uusi[i] = alkiot[i];
            }
            alkiot = uusi;
        }
        alkiot[lkm] = hahmo;
        lkm++;
        muutettu = true;
    }
    
    /**
     * palauttaa viitteen i:teen jäsenen
     * @param i monenko jäsenen viite halutaan
     * @return viite jäseneen, jonko indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole salitulla alueella
     */
    protected Hahmo anna(int i) throws IndexOutOfBoundsException {
        if (i<0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi:" + i);
        return alkiot[i];
    }
    
    /**
     * Lukee jäsenistön tiedostosta. 
     * @param tied tiedoston perusnimi
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Hahmot jasenet = new Hahmot();
     *  Hahmo aku1 = new Hahmo(), aku2 = new Hahmo();
     *  aku1.taytaAkuAnkka();
     *  aku2.taytaAkuAnkka();
     *  String hakemisto = "testikelmit";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  jasenet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  jasenet.lisaa(aku1);
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  jasenet = new Hahmot();            // Poistetaan vanhat luomalla uusi
     *  jasenet.lueTiedostosta(tiedNimi);  // johon ladataan tiedot tiedostosta.
     *  Iterator<Hahmo> i = jasenet.iterator();
     *  i.next() === aku1;
     *  i.next() === aku2;
     *  i.hasNext() === false;
     *  jasenet.lisaa(aku2);
     *  jasenet.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     *  dir.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            String rivi = fi.readLine();
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Hahmo hahmo = new Hahmo();
                hahmo.parse(rivi); // voisi olla virhekäsittely
                lisaa(hahmo);
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
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); // if .. System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); // if .. System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println(getKokoNimi());
            fo.println(alkiot.length);
            for (Hahmo hahmo : this) {
                fo.println(hahmo.toString());
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
     * Palauttaa Kerhon koko nimen
     * @return Kerhon koko nimi merkkijononna
     */
    public String getKokoNimi() {
        return kokoNimi;
    }    

    /**
     * Palauttaa Rpg:n hahmojen lukumäärän;
     * @return jäsenten lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Luokka hahmojen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Hahmot hahmot = new Hahmot();
     * Hahmo aku1 = new Hahmo(), aku2 = new Hahmo();
     * aku1.rekisteroi(); aku2.rekisteroi();
     *
     * hahmot.lisaa(aku1); 
     * hahmot.lisaa(aku2); 
     * hahmot.lisaa(aku1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Hahmo hahmo:hahmot)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+hahmo.getTunnusNro());           
     * 
     * String tulos = " " + aku1.getTunnusNro() + " " + aku2.getTunnusNro() + " " + aku1.getTunnusNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Hahmo>  i=hahmot.iterator(); i.hasNext(); ) { // ja iteraattorin toimintaa
     *   Hahmo hahmo = i.next();
     *   ids.append(" "+hahmo.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Hahmo>  i=hahmot.iterator();
     * i.next() == aku1  === true;
     * i.next() == aku2  === true;
     * i.next() == aku1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class HahmotIterator implements Iterator<Hahmo> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava jäsen
         * @return seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Hahmo next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }
    
    @Override
    public Iterator<Hahmo> iterator() {
        return new HahmotIterator();
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Hahmot hahmot = new Hahmot(); 
     *   Hahmo jasen1 = new Hahmo(); jasen1.parse("1|Ankka Aku|030201-115H|Paratiisitie 13|"); 
     *   Hahmo jasen2 = new Hahmo(); jasen2.parse("2|Ankka Tupu||030552-123B|"); 
     *   Hahmo jasen3 = new Hahmo(); jasen3.parse("3|Susi Sepe|121237-121V||131313|Perämetsä"); 
     *   Hahmo jasen4 = new Hahmo(); jasen4.parse("4|Ankka Iines|030245-115V|Ankkakuja 9"); 
     *   Hahmo jasen5 = new Hahmo(); jasen5.parse("5|Ankka Roope|091007-408U|Ankkakuja 12"); 
     *   hahmot.lisaa(jasen1); hahmot.lisaa(jasen2); hahmot.lisaa(jasen3); hahmot.lisaa(jasen4); hahmot.lisaa(jasen5);
     *   // TODO: toistaiseksi palauttaa kaikki hahmot 
     * </pre> 
     */ 
    @SuppressWarnings("unused")
    public Collection<Hahmo> etsi(String hakuehto, int k) { 
        Collection<Hahmo> loytyneet = new ArrayList<Hahmo>(); 
        for (Hahmo hahmo : this) { 
            loytyneet.add(hahmo);  
        } 
        return loytyneet; 
    }
 
    /**
     * Testiohjelmajäsenistölle
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Hahmot hahmot = new Hahmot();
        
        Hahmo aku = new Hahmo(), aku2 = new Hahmo();
        aku.rekisteroi();
        aku.taytaAkuAnkka();
        aku2.rekisteroi();
        aku2.taytaAkuAnkka();
        
        try {
            hahmot.lisaa(aku);
            hahmot.lisaa(aku2);
            
            System.out.println("============ Jäsenet testi ============");
            
            for ( int i = 0; i < hahmot.getLkm(); i++) {
                Hahmo hahmo = hahmot.anna(i);
            System.out.println("Jäsen nro: " + i);
            hahmo.tulosta(System.out);
            }
        
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
                
        }
        
    }


}


