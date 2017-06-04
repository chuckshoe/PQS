package pqs171;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EntryTest {

  private Entry entry;  

  @Before
  public void setUp() {
    entry = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
  }
  
  /**
   * The below test case fails. The java doc mentions that the name field is required;
   * yet it accepts null. For null, it should throw IllegalArgumentException. 
   */
  @Test(expected=IllegalArgumentException.class)
  public void testEntryConstructor_nullName() {
    Entry testEntry = new Entry.EntryBuilder(null, "+19990009999").build();
  }
  
  /**
   * The below test case fails. The java doc mentions that the phone number field
   * is required; yet it accepts null. For null, it should throw IllegalArgumentException.
   */  
  @Test(expected=IllegalArgumentException.class)
  public void testEntryConstructor_nullPhoneNumber() {
    Entry testEntry = new Entry.EntryBuilder("TestName", null).build();
  }
  
  @Test
  public void testGetName() {
    assertEquals("TestName", entry.getName());
  }

  @Test
  public void testGetPhoneNumber() {
    assertEquals("+19990009999", entry.getPhoneNumber());
  }
  
  @Test
  public void testGetPostalAddress() {
    assertEquals("TestAddress", entry.getPostalAddress());
  }
  
  @Test
  public void testGetEmailAddress() {
    assertEquals("abc@xyz.com", entry.getEmailAddress());
  }
  
  @Test
  public void testGetNote() {
    assertEquals("TestNote", entry.getNote());
  }
  
  @Test
  public void testEquals_trueComparison() {
    Entry testEntry = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
    assertTrue(entry.equals(testEntry));
    assertTrue(entry.equals(entry));
  }
  
  @Test
  public void testEquals_nullFields() {
    Entry testEntry1 = new Entry.EntryBuilder(null, "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
    assertFalse(entry.equals(testEntry1));
    assertFalse(testEntry1.equals(entry));
    Entry testEntry2 = new Entry.EntryBuilder("TestName", null)
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
    assertFalse(entry.equals(testEntry2));
    assertFalse(testEntry2.equals(entry));
    Entry testEntry3 = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress(null).emailAddress("abc@xyz.com").note("TestNote").build();
    assertFalse(entry.equals(testEntry3));
    assertFalse(testEntry3.equals(entry));
    Entry testEntry4 = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress(null).note("TestNote").build();
    assertFalse(entry.equals(testEntry4));
    assertFalse(testEntry4.equals(entry));
    Entry testEntry5 = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").emailAddress("abc@xyz.com").note(null).build();
    assertFalse(entry.equals(testEntry5));
    assertFalse(testEntry5.equals(entry));                
  }  
  
  @Test
  public void testEquals_falseComparison() {
    Entry testEntry = new Entry.EntryBuilder("TestName", "+19990009999")
        .postalAddress("TestAddress").note("TestNote").build();
    assertFalse(entry.equals(testEntry));
    assertFalse(entry.equals(null));
    assertFalse(entry.equals(""));
  }
 
   @Test
   public void testHashCode() {
     Entry testEntry1 = new Entry.EntryBuilder("TestName", "+19990009999")
         .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
     assertEquals(entry.hashCode(), testEntry1.hashCode());
     assertEquals(testEntry1.hashCode(), entry.hashCode());
     Entry testEntry2 = new Entry.EntryBuilder(null, "+19990009999")
         .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
     assertNotEquals(entry.hashCode(), testEntry2.hashCode());
     assertNotEquals(testEntry2.hashCode(), entry.hashCode());
     Entry testEntry3 = new Entry.EntryBuilder("TestName", null)
         .postalAddress("TestAddress").emailAddress("abc@xyz.com").note("TestNote").build();
     assertNotEquals(entry.hashCode(), testEntry3.hashCode());
     assertNotEquals(testEntry3.hashCode(), entry.hashCode());
     Entry testEntry4 = new Entry.EntryBuilder("TestName", "+19990009999")
         .postalAddress(null).emailAddress("abc@xyz.com").note("TestNote").build();
     assertNotEquals(entry.hashCode(), testEntry4.hashCode());
     assertNotEquals(testEntry4.hashCode(), entry.hashCode());
     Entry testEntry5 = new Entry.EntryBuilder("TestName", "+19990009999")
         .postalAddress("TestAddress").emailAddress(null).note("TestNote").build();
     assertNotEquals(entry.hashCode(), testEntry5.hashCode());
     assertNotEquals(testEntry5.hashCode(), entry.hashCode());
     Entry testEntry6 = new Entry.EntryBuilder("TestName", "+19990009999")
         .postalAddress("TestAddress").emailAddress(null).note("TestNote").build();
     assertNotEquals(entry.hashCode(), testEntry6.hashCode());
     assertNotEquals(testEntry6.hashCode(), entry.hashCode());
     Entry testEntry7 = new Entry.EntryBuilder("TestName", "+19990009999")
         .postalAddress("TestAddress").emailAddress("abc@xyz.com").note(null).build();
     assertNotEquals(entry.hashCode(), testEntry7.hashCode());
     assertNotEquals(testEntry7.hashCode(), entry.hashCode());
   }
    
}
