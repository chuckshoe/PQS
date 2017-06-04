package edu.nyu.cs.pqs1;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class is used to create an Address Book.
 * Users of this library can :
 * <ul>
 * <li>Create an address book.</li>
 * <li>Add a contact entry. An entry consists of name (mandatory), postal address,
 * phone number, email address and note.</li>
 * <li>Remove a contact entry.</li>
 * <li>Search for an entry by any of the contact properties.</li>
 * <li>Save the address book to a file.</li>
 * <li>Read the address book from a file.</li>
 * </ul>
 * @author Chakshu Sardana
 */
public class AddressBook {
  private List<Contact> contacts;

  /**
   * Creates a new AddressBook object.
   */
  public AddressBook() {
    contacts = new ArrayList<Contact>();  
  }

  /**
   * Adds a {@link Contact} to address book.
   * @param contact
   *          the contact to add.
   * @return true, if contact successfully added, otherwise false.
   */
  public boolean addContact(Contact contact) {
    if (contact == null) {
      return false;
    }
    return contacts.add(contact);
  }

  /**
   * Removes a {@link Contact} given its unique ID.
   * @param id
   *          unique ID (see {@link Contact#getId()}) of contact to be removed.
   * @return true, if contact successfully removed, otherwise false.
   */
  public boolean removeContact(UUID id) {
    for (int i = 0; i < contacts.size(); i++) {
      if (contacts.get(i).getId().equals(id)) {
        contacts.remove(i);
        return true;
      }
    }
    return false;
  }

  /**
   * Given a string, search for it by any contact properties in the {@link AddressBook} 
   * contact entries. Search is case insensitive.
   * @param searchString
   *          the string to be searched.
   * @return list of matching {@link Contact} values.
   */
  public List<Contact> searchContacts(String searchString) {
    List<Contact> matchedContacts = new ArrayList<Contact>();
    searchString = searchString.toLowerCase();
    for (Contact c : contacts) {
      if (c.getContactString().toLowerCase().contains(searchString)) {
        matchedContacts.add(c);
      }
    }
    return matchedContacts;
  }

  /**
   * Write the contact entries of the address book to the file location specified by the
   * parameter, path.
   * If the file is not present, it will be created; otherwise it will be overwritten.
   * @param path
   *          location of file where address book contacts will be exported.
   * @return true, if address book entries successfully exported, otherwise false.
   */
  public boolean exportContactsToFile(String path) {
    Gson gson = new Gson();
    try {
      FileWriter writer = new FileWriter(path);
      for (Contact c : contacts) {
        gson.toJson(c, writer);
        writer.write("\n");
      }
      writer.close();
    } catch (IOException FileAccessFailure) {
      return false;
    }
    return true;
  }

  /**
   * Read the address book from the specified location parameter, path.
   * @param path
   *          the location of file from where the address book is to be read.
   * @return true, if address book entries successfully imported, otherwise false.
   */
  public boolean importContactsFromFile(String path) {
    Gson gson = new Gson();
    try {
      BufferedReader br = new BufferedReader(new FileReader(path));
      String line;
      while ((line = br.readLine()) != null) {
        Contact c = gson.fromJson(line, Contact.class);
        contacts.add(c);
      }
      br.close();
    } catch (IOException FileAccessFailure) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "AddressBook [contacts=" + contacts + "]";
  } 
}