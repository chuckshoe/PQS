package edu.nyu.cs.pqs1;

/**
 * This is a driver class that is just used to test the AddressBook library.
 * @author Chakshu Sardana
 */
public class Driver {
  public static void main(String[] args) {
    Contact contact = new Contact.Builder("Jon Snow").phoneNumber("9090909090")
        .postalAddress("North of the Wall").build();    
    Contact contact2 = new Contact.Builder("Cersei").phoneNumber("1010101010")
        .postalAddress("King's Landing").build();
    AddressBook book = new AddressBook();
    book.addContact(contact);
    book.addContact(contact2);
  }
}
