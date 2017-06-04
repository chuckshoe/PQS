package pqs171;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;

public class AddressBookTest {

  private AddressBook addressBook;
  private Entry entry1;

  @Before
  public void setUp() {
    addressBook = AddressBook.createAddressBook();
    entry1 = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
  }
    
  @Test
  public void testAddEntry() {
    addressBook.addEntry(entry1);
    assertEquals(1, addressBook.searchByAnyProperty("TestName").size());
    addressBook.removeEntry(entry1);
  }
  
  @Test
  public void testSearchByAnyProperty_NameMatched() {
    addressBook.addEntry(entry1);  
    assertEquals(1, addressBook.searchByAnyProperty("TestName").size());
    addressBook.removeEntry(entry1);
  }

  @Test
  public void testSearchByAnyProperty_PhoneNumberMatched() {
    addressBook.addEntry(entry1);  
    assertEquals(1, addressBook.searchByAnyProperty("+19990009999").size());
    addressBook.removeEntry(entry1);
  }

  @Test
  public void testSearchByAnyProperty_PostalAddressMatched() {
    addressBook.addEntry(entry1);  
    assertEquals(1, addressBook.searchByAnyProperty("TestAddress").size());
    addressBook.removeEntry(entry1);
  }
  
  @Test
  public void testSearchByAnyProperty_EmailAddressMatched() {
    addressBook.addEntry(entry1);  
    assertEquals(1, addressBook.searchByAnyProperty("abc@xyz.com").size());
    addressBook.removeEntry(entry1);
  }
  
  @Test
  public void testSearchByAnyProperty_NoteMatched() {
    addressBook.addEntry(entry1);
    assertEquals(1, addressBook.searchByAnyProperty("TestNote").size());
    addressBook.removeEntry(entry1);
  }

  @Test
  public void testSearchByAnyProperty_NoPropertyMatched() {
    addressBook.addEntry(entry1);
    assertEquals(0, addressBook.searchByAnyProperty("xyz@abc.com").size());
    addressBook.removeEntry(entry1);
  }
  
  @Test
  public void testSearchByAnyProperty_PartialMatch() {
    addressBook.addEntry(entry1);
    assertEquals(0, addressBook.searchByAnyProperty("abc@pqr.com").size());
    addressBook.removeEntry(entry1);
  }   
  
  @Test
  public void testRemoveEntry_entryNotPresent() {
    addressBook.addEntry(entry1);  
    Entry testEntry = new Entry.EntryBuilder("abc", "+11112223333").build();
    assertFalse(addressBook.removeEntry(testEntry));
  }
  
  @Test
  public void testRemoveEntry_entryPresent() {
    Entry testEntry = new Entry.EntryBuilder("TestName", "+19900009999").build();
    addressBook.addEntry(testEntry);  
    assertTrue(addressBook.removeEntry(testEntry));
  }
  
  @Test
  public void testRemoveEntry_fromEmptyAddressBook() {
    addressBook.addEntry(entry1);  
    assertTrue(addressBook.removeEntry(entry1));
    Entry entry2 = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();    
    assertFalse(addressBook.removeEntry(entry2));
  }
    
  @Test
  public void testSaveBookToFile() throws IOException {
    File addressBookFile = new File("TestFile");
    addressBook.addEntry(entry1);
    addressBook.saveBookToFile("TestFile");
    String fileContents = new String(Files.readAllBytes(Paths.get("TestFile")));
    assertTrue(addressBookFile.exists());
    assertTrue(fileContents.contains("TestName"));
    assertTrue(fileContents.contains("+19990009999"));
    assertTrue(fileContents.contains("TestAddress"));
    assertTrue(fileContents.contains("abc@xyz.com"));
    assertTrue(fileContents.contains("TestNote"));
  }
  
  @Test
  public void testSaveBookToFile_emptyAddressBook() throws IOException {
    AddressBook testAddressBook = AddressBook.createAddressBook();
    testAddressBook.saveBookToFile("TestFile");
  }
  
  @Test
  public void testReadBookFromFile_emptyAddressBook() throws IOException {
    AddressBook testAddressBook1 = AddressBook.createAddressBook();
    testAddressBook1.saveBookToFile("TestFile");
    AddressBook testAddressBook2 = AddressBook.createAddressBook();
    testAddressBook2.readBookFromFile("TestFile");
    assertEquals(testAddressBook1, testAddressBook2);    
  }
  // BUG in the implementation
  @Test
  public void testReadBookFromFile() throws IOException {
    addressBook.addEntry(entry1);
    addressBook.saveBookToFile("TestFile2");
    AddressBook testAddressBook = AddressBook.createAddressBook();
    testAddressBook.readBookFromFile("TestFile2");
    String addressBookContent = "";
    String testAddressBookContent = "";
    for (Entry e : addressBook.searchByAnyProperty("TestName") ) {
        addressBookContent = addressBookContent + e.toString();
    }
    for (Entry e : testAddressBook.searchByAnyProperty("TestName") ) {
        testAddressBookContent = testAddressBookContent + e.toString();
    }
    assertTrue(addressBookContent.equals(testAddressBookContent));
  }
  
  @Test(expected=NullPointerException.class)
  public void testReadBookFromFile_null() throws IOException {  
    AddressBook testAddressBook = AddressBook.createAddressBook();
    testAddressBook.saveBookToFile(null);
  } 
}
