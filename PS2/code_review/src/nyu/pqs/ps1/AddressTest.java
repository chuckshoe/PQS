package nyu.pqs.ps1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the Address class.
 */
public class AddressTest {

  /**
   * Tests an empty address book.
   */
  @Test
  public void testEmptyAddress() {
    
    Address address = new Address();
    
    assertTrue(address.getName() == null);
    assertTrue(address.getPostalAddress() == null);
    assertTrue(address.getPhoneNumber() == null);
    assertTrue(address.getNote() == null);
  }
  
  /**
   * Tests adding an address using the constructor.
   */
  @Test
  public void testAddressConstructor() {
    
    Address address = new Address("Batman", "Gotham City", "100", "Dark Knight");
    
    assertTrue(address.getName().equals("Batman"));
    assertTrue(address.getPostalAddress().equals("Gotham City"));
    assertTrue(address.getPhoneNumber().equals("100"));
    assertTrue(address.getNote().equals("Dark Knight"));
  }
  
  /**
   * Tests adding an address by creating an empty object and then using the setters to set data.
   */
  @Test
  public void testAddressSetters() {
    
    Address address = new Address();
    
    address.setName("Superman");
    address.setNote("Son of Krypton");
    
    assertTrue(address.getName().equals("Superman"));
    assertTrue(address.getPhoneNumber() == null);
    assertTrue(address.getPostalAddress() == null);
    assertTrue(address.getNote().equals("Son of Krypton"));
  }
}
