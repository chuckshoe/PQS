package edu.nyu.cs.pqs1;

import java.util.UUID;

/**
 * This class represents a contact entry of the Address Book. It has the following information:
 * <ul>
 * <li>Name (required)</li>
 * <li>Unique Id (automatically generated and is unique for an object)</li>
 * <li>Phone number (optional; empty if not provided)</li>
 * <li>Postal address (optional; empty if not provided)</li>
 * <li>Email (optional; empty if not provided)</li>
 * <li>Note (optional; empty if not provided)</li>
 *</ul>
 * @author Chakshu Sardana
 */
public class Contact {
  private final String name;
  private final UUID id;
  private final String phoneNumber;
  private final String postalAddress;
  private final String email;
  private final String note;
  
  /**
   * This class is used to build an object of {@link Contact} class using the Builder pattern.
   * @author Chakshu Sardana
   */
  public static class Builder {
    private final UUID id;    
    // Required parameters
    private final String name;
    // Optional parameters
    private String phoneNumber = "";
    private String postalAddress = "";
    private String email = "";
    private String note = "";
    
    /**
     * Creates a new Builder object.
     * @param name
     *          name for the contact entry in the address book.
     * @throws IllegalArgumentException if the argument is null.
     */
    public Builder(String name) {
      if (name == null) {
        throw new IllegalArgumentException("Name cannot be null");
      } else {
        this.name = name;
        this.id = UUID.randomUUID();
      }
    }
    
    /**
     * Sets the parameter, phone number. Any hyphen, space, parentheses is removed from the 
     * number. The number is stored as a string of decimal digits. No additional checks 
     * (except checking for null) are performed to verify the correctness of number.
     * @param phoneNumber
     *          the phone number that is to be set.
     * @return updated {@link Builder} object.
     * @throws IllegalArgumentException if the argument is null.
     */
    public Builder phoneNumber(String phoneNumber) {
      if (phoneNumber == null) {
        throw new IllegalArgumentException("Phone Number cannot be null");
      } else {
        this.phoneNumber = phoneNumber.replaceAll("[\\s-()]", "");
        return this;
      }
    }
    
    /**
     * Sets the parameter, postal address.
     * @param postalAddress
     *          the postal address that is to be set.
     * @return updated {@link Builder} object.
     * @throws IllegalArgumentException if the argument is null.
     */
    public Builder postalAddress(String postalAddress) {
      if (postalAddress == null) {
        throw new IllegalArgumentException("Postal Address cannot be null");
      } else {
        this.postalAddress = postalAddress;
      }
      return this;
    }
    
    /**
     * Sets the parameter, email address. No checks (except checking for null) 
     * are performed to verify correctness of email address.
     * @param email
     *          the email address that is to be set.
     * @return updated {@link Builder} object.
     * @throws IllegalArgumentException if the argument is null.
     */
    public Builder email(String email) {
      if (email == null) {
        throw new IllegalArgumentException("Email Address cannot be null");
      } else {
        this.email = email;
        return this;
      }
    }
        
    /**
     * Sets the parameter, note. 
     * @param note
     *          the note that is to set.
     * @return updated {@link Contact} object.
     * @throws IllegalArgumentException if the argument is null.
     */
    public Builder note(String note) {
      if (note == null) {
        throw new IllegalArgumentException("Note cannot be null");
      } else {
        this.note = note;
        return this;
      }
    }
    
    /**
     * Builds a new {@link Contact} object from the Builder object.
     * @return a {@link Contact} object.
     */
    public Contact build() {
      return new Contact(this);
    }
  }
  
  /**
   * Creates a new instance of Contact class from Builder object.
   * @param builder
   *          {@link Builder} object from which new instance of Contact class is created.
   */
  private Contact(Builder builder) {
    name = builder.name;
    id = builder.id;
    phoneNumber = builder.phoneNumber;
    postalAddress = builder.postalAddress;
    email = builder.email;
    note = builder.note;
  }
    
  /**
   * @return name of the Contact.
   */
  public String getName() {
    return name;
  }
        
  /**
   * @return unique Id of Contact.
   */
  public UUID getId() {
    return id; 
  }
        
  /**
   * @return postal address of Contact.
   */
  public String getpostalAddress() {
    return postalAddress;
  }

  /**
   * @return phone number of Contact.
   */
  public String getphoneNumber() {
    return phoneNumber;
  }

  /**
   * @return email address of Contact.
   */
  public String getEmail() {
    return email;
  }

  /**
   * @return note of Contact.
   */
  public String getNote() {
    return note;
  }
    
  /**
   * @return raw String of the Contact.
   */
  public String getContactString() {
    return name + " " + postalAddress + " " + phoneNumber + " " + email + " " + note;
  }
  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || (getClass() != o.getClass()) ) {
      return false;
    }
    Contact other = (Contact) o;
    // Check if each field value matches for both the objects
    if (!name.equals(other.getName())) {
        return false;
    }
    if (!phoneNumber.equals(other.getphoneNumber())) {
        return false;
    }
    if (!email.equals(other.getEmail())) {
      return false;
    }
    if (!note.equals(other.getNote())) {
      return false;
    }
    if (!postalAddress.equals(other.getpostalAddress())) {
      return false;
    }
    return true;
  }
    
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;    
    result = prime * result + name.hashCode();
    result = prime * result + phoneNumber.hashCode();
    result = prime * result + postalAddress.hashCode();
    result = prime * result + email.hashCode();
    result = prime * result + note.hashCode();
    return result;
  }
  
  @Override
  public String toString() {
    return "Contact [name=" + name + ", id=" + id + ", phoneNumber=" +
        phoneNumber + ", postalAddress=" + postalAddress + ", email=" + email
        + ", note=" + note + "]";
  }
}