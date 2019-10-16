package rpg;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Rpg hahmo joka osaa mm. itsehuolehtia tunnusNro:sta
 * 
 * @author Ilari Massa
 * @version Feb 20, 2019
 *
 */
public class Hahmo implements Cloneable{
    private int tunnusNro;
    private String name = "";
    private String luokka = "";
    private int hahmonrotu = 0;
    private int age = 0;
    private String origin = "";
    private String allignment = "";
    private String notes = "";
    
    private int str = 0;
    private int dex = 0;
    private int consti = 0;
    private int luck = 0;
    private int exp = 0;
    private int wis = 0;
    private int cha = 0;
    private int inte = 0;
    private int gold = 0;
    private int lvl = 0;
    
    private static int seuraavaNro = 1;
    
    /**
     * hahmon konstruktori
     */
    public Hahmo () {
        //
    }
    
    /**
     * hahmon konstruktori rodun id:lla
     * @param rid hahmon rotu numero
     */
    public Hahmo (int rid) {
        this.hahmonrotu = rid;
    }
    
    /**
     * Hakee hahmon nimen
     * @return hahmon nimi
     * @example
     * <pre name="test">
     *  Hahmo aku = new Hahmo();
     *  aku.taytaAkuAnkka();
     *  aku.getNimi().equals("Ankka Aku");
     * </pre>
     */
    public String getNimi() {
        return name;
    }
    
    /**
     * @param n nimi
     * @return nimen
     */
    public String setNimi(String n) {
        name = n;
        return n;
    }
    
    /**
     * @return hahmon luokka
     */
    public String getLuokka() {
        return luokka;
    }
 
    /**
     * @param n nimi
     * @return luokka
     */
    public String setLuokka(String n) {
        luokka = n;
        return n;
    }
    
    /**
     * @return hahmonrotu id
     */
    public int getHahmonrotu() {
        return hahmonrotu;
    }
    
    
    /**
     * @param n rodun id
     * @return esineen id
     */
    public int setHahmonrotu(int n) {
        hahmonrotu = n;
        return n;
    }
    
    /**
     * @return ika
     */
    public int getAge() {
        return age;
    }
    
    
    /**
     * @param n ika
     * @return ika
     */
    public int setAge(int n) {
        age = n;
        return n;
    }
    
    /**
     * @return origin
     */
    public String getOrigin() {
        return origin;
    }
    
    
    /**
     * @param n origin
     * @return origin
     */
    public String setOrigin(String n) {
        origin = n;
        return n;
    }
    
    /**
     * @return allignment
     */
    public String getAllignment() {
        return allignment;
    }
    
    
    /**
     * @param n allignment
     * @return  allignment
     */
    public String setAllignment(String n) {
        allignment = n;
        return n;
    }
    
    /**
     * @return notes
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * @param n notes
     * @return notes
     */
    public String setNotes(String n) {
        notes = n;
        return n;
    }
    
    /**
     * @return dex
     */
    public int getDex() {
        return dex;
    }
    
    /**
     * @param n dex
     * @return dex
     */
    public int setDex(int n) {
        dex = n;
        return n;
    }
    
    /**
     * @return str
     */
    public int getStr() {
        return str;
    }
    
    /**
     * @param n str
     * @return str
     */
    public int setStr(int n) {
        str = n;
        return n;
    }
    
    /**
     * @return luck
     */
    public int getLuck() {
        return luck;
    }
    
    /**
     * @param n luck
     * @return luck
     */
    public int setLuck(int n) {
        luck = n;
        return n;
    }
    
    /**
     * @return exp
     */
    public int getExp() {
        return exp;
    }
    
    /**
     * @param n exp
     * @return exp
     */
    public int setExp(int n) {
        exp = n;
        return n;
    }
    
    /**
     * @return wis
     */
    public int getWis() {
        return wis;
    }
    
    /**
     * @param n wis
     * @return wis
     */
    public int setWis(int n) {
        wis = n;
        return n;
    }    
    
    /**
     * @return const
     */
    public int getConst() {
        return consti;
    }
    
    /**
     * @param n const
     * @return const
     */
    public int setConst(int n) {
        consti = n;
        return n;
    }    
    
    /**
     * @return cha
     */
    public int getCha() {
        return cha;
    }
    
    /**
     * @param n cha
     * @return cha
     */
    public int setCha(int n) {
        cha = n;
        return n;
    }    
    
    /**
     * @return int
     */
    public int getInt() {
        return inte;
    }
    
    /**
     * @param n int
     * @return int
     */
    public int setInt(int n) {
        inte = n;
        return n;
    }    
    
    /**
     * @return gold
     */
    public int getGold() {
        return gold;
    }
    
    /**
     * @param n gold
     * @return gold
     */
    public int setGold(int n) {
        gold = n;
        return n;
    }    
    
    /**
     * @return lvl
     */
    public int getLvl() {
        return lvl;
    }
    
    /**
     * @param n lvl
     * @return lvl
     */
    public int setLvl(int n) {
        lvl = n;
        return n;
    }    
    
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot jäsenille
     * @param rid rotu joka annetaan henkilölle
     */
    public void taytaAkuAnkka(int rid) {
        name = "Ankka Aku";
        luokka = "Soturi";
        age = 18;
        hahmonrotu  = rid;
        origin = "Ankka linna";
        allignment = "True Neutral";
        notes = "Velkaa Roopelle";
        
        str = 5;
        dex = 5;
        consti = 2;
        luck = 0;
        exp = 12;
        wis = 3;
        cha = 4;
        inte = 2;
        gold = 3;
        lvl = 4;
    }
    
    /**
     * Apumetodi jolla saadaan täytettyä testiarvot jäsenille
     * rid arvotaan jotta kahdella jäsenellä ei olisi samoja tietoja
     */
    public void taytaAkuAnkka() {
        Random rand = new Random();
        int rid  = 0 + rand.nextInt(2);
        taytaAkuAnkka(rid);
    }
    
    
    /**
     * Tulostaa henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("Character name and id: " + name + " " + tunnusNro + "\n" + "Luokka: " + luokka + "\n" + "Race: " + hahmonrotu + "\n" + "Age: " + age + "\n" + "Origin: " + origin 
                + "\n" + "Allignment: " + allignment + "\n" + "Additional information: " + notes  );
        
        out.println( name + "'s stats are: " + "\n" + "Str: " +  + str + "\n" + "Dex: " + dex + "\n" + "Const: " + consti + "\n" + "Luck: " + luck + "\n" +
        "Exp: " + exp + "\n" + "Wis: " + wis + "\n" + "Cha: " + cha + "\n" + "Int: " + inte + "\n" + "Gold: " + gold + "\n" + "Level: " + lvl);
        
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Hakee tunnus numeron
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }


    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Antaa hahmolle rekisterinumeron
     * @return hahmon uusi tunnus numero
     * <prename ="test">
     *  Hahmo aku1 = new Hahmo();
     *  aku1.getTunnusNro() === 0;
     *  aku1.rekisteroi();
     *  Hahmo aku2 = new Hahmo();
     *  aku2.rekisteroi();
     *  int n1 = aku1.getTunnusNro();
     *  int n2 = aku2.getTunnusNro();
     *  n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
        
    }
    
    /**
     * Palauttaa hahmon tiedot merkkijonona jonka voi tallentaa tiedostoon
     * @return hahmon tolppaeroteltuna merkkijononna
     * @example
     * <pre name="test">
     *  Hahmo hahmo = new Hahmo();
     *  hahmo.parse("2|Ankka Aku|18");
     *  hahmo.toString().startsWith("2|Ankka Aku|18|") === true;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                name + "|" +
                age + "|" +
                luokka + "|" +
                origin + "|" +
                allignment + "|" +
                notes + "|" +
                hahmonrotu + "|" +
                str + "|" +
                dex + "|" +
                consti + "|" +
                inte + "|" +
                wis + "|" +
                cha + "|" +
                luck + "|" +
                gold + "|" +
                exp + "|" +
                lvl;
    }
    
    /**
     * Selvittää hahmon tiedot | erotellusta merkistä
     * Åitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta hahmon tiedot saadaan
     * 
     * @example
     * <pre name="test">
     *  Hahmo hahmo = new Hahmo();
     *  hahmo.parse("   3   | Ankka Aku | 18");
     *  hahmo.getTunnusNro() === 3;
     *  hahmo.toString().startsWith("3|Ankka Aku|18|") === true;
     *  
     *  hahmo.rekisteroi();
     *  int n = hahmo.getTunnusNro();
     *  hahmo.parse(""+(n+20));
     *  hahmo.rekisteroi();
     *  hahmo.getTunnusNro() === n+20+1;
     *  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        name = Mjonot.erota(sb, '|', name);
        age = Mjonot.erota(sb, '|', age);
        luokka = Mjonot.erota(sb, '|', luokka);
        origin = Mjonot.erota(sb, '|', origin);
        allignment = Mjonot.erota(sb, '|', allignment);
        notes = Mjonot.erota(sb, '|', notes);
        hahmonrotu = Mjonot.erota(sb, '|', hahmonrotu);
        str = Mjonot.erota(sb, '|', str);
        dex = Mjonot.erota(sb, '|', dex);
        consti = Mjonot.erota(sb, '|', consti);
        inte = Mjonot.erota(sb, '|', inte);
        wis = Mjonot.erota(sb, '|', wis);
        cha = Mjonot.erota(sb, '|', cha);
        luck = Mjonot.erota(sb, '|', luck);
        gold = Mjonot.erota(sb, '|', gold);
        exp = Mjonot.erota(sb, '|', exp);
        lvl = Mjonot.erota(sb, '|', lvl);
    }
    
    @Override
    public boolean equals(Object hahmo) {
        if ( hahmo == null) return false;
        return this.toString().equals(hahmo.toString());  
    }
    
    @Override
    public Hahmo clone() throws CloneNotSupportedException {
        Hahmo uusi;
        uusi = (Hahmo) super.clone();
        return uusi;
    }
    
    @Override
    public int hashCode() {
        return tunnusNro;
    }

    /**
     * testi ohjelma hahmolle
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Hahmo aku = new Hahmo(), aku2 = new Hahmo();
        aku.rekisteroi();
        aku2.rekisteroi();
        
        aku.tulosta(System.out);
        aku.taytaAkuAnkka();
        aku.tulosta(System.out);
        
        aku2.taytaAkuAnkka();
        aku2.tulosta(System.out);

        aku2.taytaAkuAnkka();
        aku2.tulosta(System.out);
        
        Hahmo iines = new Hahmo();
        iines.parse("2|  Iines Ankka|18  |   Narnia  |");
        iines.tulosta(System.out);
        
    }
    
    /**
     * @param hahmo johon verrataan
     * @return true tai false
     */
    public boolean equals2(Hahmo hahmo) {  // equals2 method
        if(this.toString().equals(hahmo.toString())) { // if equals() method returns true
            return true; // return true
        }
        return false; // if equals() method returns false, also return false
    }
}