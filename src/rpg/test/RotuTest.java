package rpg.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import rpg.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.05.03 22:55:42 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class RotuTest {



  // Generated by ComTest BEGIN
  /** testToString109 */
  @Test
  public void testToString109() {    // Rotu: 109
    Rotu rotu = new Rotu(); 
    rotu.parse("2"); 
    assertEquals("From: Rotu line: 112", true, rotu.toString().startsWith("2")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse128 */
  @Test
  public void testParse128() {    // Rotu: 128
    Rotu esine = new Rotu(); 
    esine.parse("   1   "); 
    assertEquals("From: Rotu line: 131", 1, esine.getRotuID()); 
    assertEquals("From: Rotu line: 132", true, esine.toString().startsWith("1|")); 
    esine.rekisteroi(); 
    int n = esine.getRotuID(); 
    esine.parse(""+(n+20)); 
    esine.rekisteroi(); 
    assertEquals("From: Rotu line: 138", n+20+1, esine.getRotuID()); 
  } // Generated by ComTest END
}