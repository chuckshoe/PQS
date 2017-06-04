package nyu.pqs.ps1;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Class to store and manage addresses.
 */
public class AddressBook {
  
  // List of addresses
  private List<Address> addresses;
  
  // Search maps
  private Map<String, List<Address>> nameMap;
  private Map<String, List<Address>> postalAddressMap;
  private Map<String, List<Address>> phoneNumberMap;
  private Map<String, List<Address>> noteMap;

  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Initializes an empty address book.
   */
  public AddressBook() {
    
    // Initialize addresses
    addresses = new ArrayList<>();
    
    // Initialize maps
    nameMap = new HashMap<>();
    postalAddressMap = new HashMap<>();
    phoneNumberMap = new HashMap<>();
    noteMap = new HashMap<>();
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Private methods
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Adds an address entry to maps to enable faster searching across fields.
   * @param address The address object to be added into the map.
   */
  private void addAddressToMap(Address address) {
    
    // Add an empty list to the corresponding key if entering for the
    // first time.
    if (nameMap.containsKey(address.getName()) == false) {
      nameMap.put(address.getName(), new ArrayList<>());
    }
    
    if (postalAddressMap.containsKey(address.getPostalAddress()) == false) {
      postalAddressMap.put(address.getPostalAddress(), new ArrayList<>());
    }

    if (phoneNumberMap.containsKey(address.getPhoneNumber()) == false) {
      phoneNumberMap.put(address.getPhoneNumber(), new ArrayList<>());
    }
    
    if (noteMap.containsKey(address.getNote()) == false) {
      noteMap.put(address.getNote(), new ArrayList<>());
    }
    
    // Add the address entry to the list created.
    nameMap.get(address.getName()).add(address);
    postalAddressMap.get(address.getPostalAddress()).add(address);
    phoneNumberMap.get(address.getPhoneNumber()).add(address);
    noteMap.get(address.getNote()).add(address);
  }
  
  /**
   * Removes an address entry from the local maps.
   * @param address The address object to be removed from the map.
   */
  private void removeAddressFromMap(Address address) {
    
    // Search and remove entry from local maps
    if (nameMap.containsKey(address.getName())) {
      nameMap.get(address.getName()).remove(address);
      
      if (nameMap.get(address.getName()).size() == 0) {
        nameMap.remove(address.getName());
      }
    }
    
    if (postalAddressMap.containsKey(address.getPostalAddress())) {
      postalAddressMap.get(address.getPostalAddress()).remove(address);
      
      if (postalAddressMap.get(address.getPostalAddress()).size() == 0) {
        postalAddressMap.remove(address.getPostalAddress());
      }
    }
    
    if (phoneNumberMap.containsKey(address.getPhoneNumber())) {
      phoneNumberMap.get(address.getPhoneNumber()).remove(address);
      
      if (phoneNumberMap.get(address.getPhoneNumber()).size() == 0) {
        phoneNumberMap.remove(address.getPhoneNumber());
      }
    }
    
    if (noteMap.containsKey(address.getNote())) {
      noteMap.get(address.getNote()).remove(address);
      
      if (noteMap.get(address.getNote()).size() == 0) {
        noteMap.remove(address.getNote());
      }
    }
  }
  
  /**
   * Rebuilds the local maps using data from the addresses variable. 
   */
  private void rebuildMaps() {

    // Clear maps
    nameMap.clear();
    postalAddressMap.clear();
    phoneNumberMap.clear();
    noteMap.clear();

    for (Address address : addresses) {

      addAddressToMap(address);
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Public methods
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Provides the total number of addresses stored in this AddressBook object.
   * @return Returns the total number of addresses stored in this AddressBook object.
   */
  public int getTotalAddressesStored() {

  return addresses.size();
  }
  
  /**
   * Adds an address into the address book.
   * @param address An Address object.
   */
  public void addAddress(Address address) {
    
    this.addresses.add(address);
    
    // Update local maps for supporting searches
    addAddressToMap(address);
  }
  
  /**
   * Adds an address into the address book.
   * @param name The name of the person to whom this address belongs.
   * @param postalAddress The postal address.
   * @param phoneNumber The phone number of the person.
   * @param note Additional info pertaining to this entry.
   */
  public void addAddress(String name, String postalAddress, String phoneNumber, String note) {
    
    addAddress(new Address(name, postalAddress, phoneNumber, note));
  }
  
  /**
   * Removes an entry from the address book.
   * @param address Address object to be removed.
   * @return True if the object passed was found and removed, False otherwise.
   */
  public boolean removeAddress(Address address) {
    
    // Remove if the address object is found
    if (this.addresses.contains(address)) {

      // Remove entry
      this.addresses.remove(address);
      
      // Update local maps
      removeAddressFromMap(address);
      
      // Return true as the object was successfully removed
      return true;
    }
    
    // Return false as the object was not found
    return false;
  }
  
  /**
   * Provides an address entry after searching for it by name in the address book.
   * @param name The name to be searched for in the address book.
   * @return A list of Address objects containing all the entries matching with the search query. 
   * Null if no entries are found.
   */
  public List<Address> getAddressByName(String name) {
    
    // Return the address object if found in map, else, return 
    if (nameMap.containsKey(name)) {
      return nameMap.get(name);
    }
    
    return null;
  }
  
  /**
   * Provides an address entry after searching for it by postal address in the address book.
   * @param postalAddress The postal address to be searched for in the address book.
   * @return A list of Address objects containing all the entries matching with the search query. 
   * Null if no entries are found.
   */
  public List<Address> getAddressByPostalAddress(String postalAddress) {
    
    // Return the address object if found in map, else, return 
    if (postalAddressMap.containsKey(postalAddress)) {
      return postalAddressMap.get(postalAddress);
    }
    
    return null;
  }
  
  /**
   * Provides an address entry after searching for it by phone number in the address book.
   * @param phoneNumber The phone number to be searched for in the address book.
   * @return A list of Address objects containing all the entries matching with the search query. 
   * Null if no entries are found.
   */
  public List<Address> getAddressByPhoneNumber(String phoneNumber) {
    
    // Return the address object if found in map, else, return 
    if (phoneNumberMap.containsKey(phoneNumber)) {
      return phoneNumberMap.get(phoneNumber);
    }
    
    return null;
  }
  
  /**
   * Provides an address entry after searching for it by note in the address book.
   * @param note The note to be searched for in the address book.
   * @return A list of Address objects containing all the entries matching with the search query. 
   * Null if no entries are found.
   */
  public List<Address> getAddressByNote(String note) {
    
    // Return the address object if found in map, else, return 
    if (noteMap.containsKey(note)) {
      return noteMap.get(note);
    }
    
    return null;
  }
  
  /////////////////////////////////////////////////////////////////////////////////////////////////
  // Serializers
  /////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * Serializes the addresses into the specified path in a JSON format.
   * @param filePath The path to the file to which the addresses have to be serialized. 
   * @throws IOException Thrown if the target file path is not accessible. 
   * @throws JsonGenerationException 
   * @throws JsonMappingException
   */
  public void serialize(String filePath) throws IOException {
    
    ObjectMapper mapper = new ObjectMapper();
    
    // Enable serializing private variables, enable pretty print and disable serializing nulls.
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.setSerializationInclusion(Include.NON_NULL);
    
    mapper.writeValue(new File(filePath), addresses);
  }
  
  /**
   * Deserializes addresses from a file into this address book instance.
   * @param filePath The path to the file which contains the serialized addresses.
   * @throws IOException Thrown if the target file doesn't exist or is inaccessible.
   */
  public void deserialize(String filePath) throws IOException {
    
    ObjectMapper mapper = new ObjectMapper();
    
    TypeReference<List<Address>> addressesTypeReference = 
        new TypeReference<List<Address>>() {
    };
    
    addresses = mapper.readValue(new File(filePath), addressesTypeReference);
    
    // Rebuild local maps
    rebuildMaps();
  }
}
