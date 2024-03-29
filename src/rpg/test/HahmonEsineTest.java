package rpg.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import rpg.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2019.04.05 13:06:18 // Generated by ComTest
 *
 */
@SuppressWarnings("all")
public class HahmonEsineTest {



  // Generated by ComTest BEGIN
  /** testGetTunnusNro55 */
  @Test
  public void testGetTunnusNro55() {    // HahmonEsine: 55
    HahmonEsine  esine = new HahmonEsine(1,1); 
    assertEquals("From: HahmonEsine line: 57", 1, esine.getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testToString113 */
  @Test
  public void testToString113() {    // HahmonEsine: 113
    HahmonEsine esine = new HahmonEsine(); 
    esine.parse("1"); 
    assertEquals("From: HahmonEsine line: 116", true, esine.toString().startsWith("1|")); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testParse132 */
  @Test
  public void testParse132() {    // HahmonEsine: 132
    HahmonEsine esine = new HahmonEsine(); 
    esine.parse("   1   "); 
    assertEquals("From: HahmonEsine line: 135", 1, esine.getTunnusNro()); 
    assertEquals("From: HahmonEsine line: 136", true, esine.toString().startsWith("1|")); 
  } // Generated by ComTest END
}