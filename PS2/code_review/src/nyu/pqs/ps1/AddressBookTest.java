package nyu.pqs.ps1;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Unit tests for the AddressBook class.
 */
public class AddressBookTest {

  /**
   * Tests creating an empty address book. 
   */
  @Test
  public void testEmptyAddressBook() {
    
    AddressBook addressBook = new AddressBook();
    
    assertTrue(addressBook.getAddressByName("Batman") == null);
  }
  
  /**
   * Tests adding and removing of entries in the address book.
   */
  @Test
  public void testAddAndRemove() {
    
    AddressBook addressBook = new AddressBook();
    
    // Add one address
    addressBook.addAddress("Batman", "Gotham City", "100", "Dark Knight");
    
    assertTrue(addressBook.getAddressByName("Batman").size() == 1);
    assertTrue(addressBook.getAddressByPostalAddress("Gotham City").size() == 1);
    assertTrue(addressBook.getAddressByPhoneNumber("100").size() == 1);
    assertTrue(addressBook.getAddressByNote("Dark Knight").size() == 1);
    
    assertTrue(addressBook.getAddressByNote("Dark Knight").equals(
        addressBook.getAddressByName("Batman")));
    
    // Add another address with the same postal address
    addressBook.addAddress("Bruce Wayne", "Gotham City", "100", null);
    
    // Search by postal address and check size
    assertTrue(addressBook.getAddressByPostalAddress("Gotham City").size() == 2);
    
    // Remove first address
    Address batmanAddress = addressBook.getAddressByName("Batman").get(0);
    addressBook.removeAddress(batmanAddress);
    
    // Search again by postal address and check size
    assertTrue(addressBook.getAddressByPostalAddress("Gotham City").size() == 1);
  }

  /**
   * Tests serialization and deserialization of the address book.
   * @throws IOException Thrown when the specified path to serialize is inaccessible.
   */
  @Test
  public void testSerialization() throws IOException {
    
    AddressBook addressBook = new AddressBook();
    
    Address batman = new Address("Batman", "Gotham City", "100", "Dark Knight");
    Address bruceWayne = new Address("Bruce Wayne", "Gotham City", "100", null);
    Address superman = new Address("Superman", null, null, "Son of Krypton");
    
    String targetFilePath = "AddressBookTest.testSerialization.json";
    
    // Add addresses
    addressBook.addAddress(batman);
    addressBook.addAddress(bruceWayne);
    addressBook.addAddress(superman);
    
    // Check add
    assertTrue(addressBook.getTotalAddressesStored() == 3);
    assertTrue(addressBook.getAddressByName("Batman").get(0) == batman);
    
    // Serialize
    addressBook.serialize(targetFilePath);
    
    // Remove all addresses from the address book
    addressBook.removeAddress(batman);
    addressBook.removeAddress(superman);
    addressBook.removeAddress(bruceWayne);
    
    // Check remove
    assertTrue(addressBook.getTotalAddressesStored() == 0);
    assertTrue(addressBook.getAddressByName("Superman") == null);
    
    // Deserialize
    addressBook.deserialize(targetFilePath);
    
    // Check add again (for deserialization)
    assertTrue(addressBook.getTotalAddressesStored() == 3);
    assertTrue(addressBook.getAddressByName("Batman").size() == 1);
    assertTrue(addressBook.getAddressByNote("Son of Krypton").size() == 1);
    assertTrue(addressBook.getAddressByPostalAddress("Gotham City").size() == 2);
    
    assertTrue(addressBook.getAddressByNote("Dark Knight").get(0).equals(batman));
    
    // Clean up created JSON file
    File targetFile = new File(targetFilePath);
    targetFile.delete();
  }
}
