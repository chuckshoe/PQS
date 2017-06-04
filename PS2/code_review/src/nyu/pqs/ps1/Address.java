package nyu.pqs.ps1;

/**
 * Container for an address entry.
 * Holds a name, address, phone number, and a note in each entry.
 */
public class Address {
  
  // Contains the name of the person to whom this address belongs
  private String name;
  
  // Address of the person
  private String postalAddress;
  
  // Phone number of the person
  private String phoneNumber;
  
  // Additional info pertaining to this entry
  private String note;
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Getters
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Provides the name of the person to whom this address belongs.
   * @return A string containing the name of the person 
   * to whom this address belongs.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Provides the address in this entry.
   * @return A string containing the address in this entry.
   */
  public String getPostalAddress() {
    return postalAddress;
  }
  
  /**
   * Provides the phone number of the person.
   * @return A string containing the phone number of the person.
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  /**
   * Provides any stored note accompanying the address.
   * @return A string containing any stored note accompanying the address.
   */
  public String getNote() {
    return note;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Setters
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Sets the name of the person to whom this address belongs.
   * @param A string containing the name of the person 
   * to whom this address belongs.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Sets the postal address.
   * @param postalAddress A string containing the postal address.
   */
  public void setPostalAddress(String postalAddress) {
    this.postalAddress = postalAddress;
  }
  
  /**
   * Sets the phone number of the person.
   * @param phoneNumber A string containing the phone number of the person.
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  /**
   * Sets any additional info to be noted, about this entry.
   * @param note A string containing any additional info to be noted.
   */
  public void setNote(String note) {
    this.note = note;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Initializes an empty Address object.
   */
  public Address() {
    
    this.name = null;
    this.postalAddress = null;
    this.phoneNumber = null;
    this.note = null;
  }
  
  /**
   * Initializes an Address entry with a name, postal address, phone number, and a note.
   * @param name The name of the person to whom this address belongs.
   * @param postalAddress The postal address.
   * @param phoneNumber The phone number of the person.
   * @param note Additional info pertaining to this entry.
   */
  public Address(String name, String postalAddress, String phoneNumber, String note) {
    
    this.name = name;
    this.postalAddress = postalAddress;
    this.phoneNumber = phoneNumber;
    this.note = note;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Equals
  /////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Compares two Address objects by string comparing all their fields.
   * @param address Address to compare with.
   * @return True if all the fields for both the objects are equivalent, False otherwise.
   */
  public boolean equals(Address address) {
    
    if (this.name.equals(address.name) && 
        this.postalAddress.equals(address.postalAddress) &&
        this.phoneNumber.equals(address.phoneNumber) &&
        this.note.equals(address.note)) {
      return true;
    }
    
    return false;
  }
}
