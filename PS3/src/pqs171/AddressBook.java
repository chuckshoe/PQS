package pqs171;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import pqs171.Entry.EntryBuilder;

/**
 * This class implemented to create empty address book, add entry, remove entry,
 * search for entry by property, save book to file and read book from file.
 * 
 * @author Iqra
 *
 */
public class AddressBook {

	//private static ArrayList<Entry> entries = new ArrayList<Entry>();
	private ArrayList<Entry> entries = new ArrayList<Entry>(); //changed for testing

	/**
	 * Constructor
	 */
	private AddressBook() {
	}

	/**
	 * This method creates empty address book
	 * 
	 * @return address book
	 */
	public static AddressBook createAddressBook() {
		return new AddressBook();
	}

	/**
	 * This method adds one given contact entry into book
	 * 
	 * @param entry
	 *            object of Entry class
	 */
	public void addEntry(Entry entry) {
		entries.add(entry);
	}

	/**
	 * This method removes one given contact entry from book
	 * 
	 * @param entry
	 *            object of Entry class
	 * @return boolean
	 */
	public boolean removeEntry(Entry entry) {
		if (entries.size() > 0) {
			if (!entries.contains(entry)) {
				return false;
			}
			entries.remove(entry);
			return true;
		} else
			return false;
	}

	/**
	 * This method searches for a contact by any of its fields; name, phone
	 * number, postal address, email address or note.
	 * 
	 * @param propertyValue
	 *            to search by
	 * @return arraylist of corresponding entries
	 */
	public ArrayList<Entry> searchByAnyProperty(String propertyValue) {
		ArrayList<Entry> resultantEntry = new ArrayList<Entry>();
		for (Entry each : entries) {
			if (each.getName().equals(propertyValue)
					|| each.getPhoneNumber().equals(propertyValue)
					|| each.getPostalAddress().equals(propertyValue)
					|| each.getEmailAddress().equals(propertyValue)
					|| each.getNote().equals(propertyValue)) {
				resultantEntry.add(each);
			}
		}
		return resultantEntry;
	}

	/**
	 * This method saves the address book into a file
	 * 
	 * @param pathToFile
	 *            ; creates files to save the entries or updates it
	 * @throws IOException
	 *             when error
	 */
	public void saveBookToFile(String pathToFile) throws IOException {
		File file = new File(pathToFile);
		Writer writer = null;
		BufferedWriter bWriter = null;

		try {
			writer = new FileWriter(file);
			bWriter = new BufferedWriter(writer);

			for (Entry str : entries) {
				bWriter.write(str.toString());
				bWriter.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bWriter != null && writer != null) {
				try {
					bWriter.close();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * This method reads entries from a file on delimiter(";") and loads into
	 * address book object
	 * 
	 * @param pathFromFile
	 *            ; to read entries from file
	 * @throws IOException
	 *             when error
	 */
	public ArrayList<Entry> readBookFromFile(String pathFromFile)
			throws IOException {
		ArrayList<Entry> resultantEntry = new ArrayList<Entry>();
		BufferedReader input = null;
		String[] lineToken;
		try {
			input = new BufferedReader(new FileReader(pathFromFile));
			String str;
			while ((str = input.readLine()) != null) {
				lineToken = str.split(";");
				EntryBuilder entry = new EntryBuilder(lineToken[0],
						lineToken[1]);
				entry.postalAddress(lineToken[2]);
				entry.emailAddress(lineToken[3]);
				entry.note(lineToken[4]);
				Entry contact = entry.build();
				resultantEntry.add(contact);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return resultantEntry;
	}

	/**
	 * Override toString() for printing addressbook
	 */
	@Override
	public String toString() {
		String resultantEntry = "";
		for (Entry e : entries) {
			resultantEntry = resultantEntry + e.toString() + "\n";
		}
		return resultantEntry;
	}

}
