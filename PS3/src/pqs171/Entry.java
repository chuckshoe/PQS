package pqs171;

/**
 * Used builder pattern to implement this class which as the problem statement
 * states, is used to create an entry object in address book with fields: name,
 * postal address as mandatory-fields and phone number, email address and a note
 * as optional.
 * 
 * @author Iqra
 *
 */
public class Entry {

	// All attributes
	// required attributes
	private String name = " ";
	private String phoneNumber = " ";
	// optional attributes
	private String postalAddress = " ";
	private String emailAddress = " ";
	private String note = " ";

	/**
	 * Constructor to create entry object
	 * 
	 * @param builder
	 */
	private Entry(EntryBuilder builder) {
		this.name = builder.name;
		this.phoneNumber = builder.phoneNumber;
		this.postalAddress = builder.postalAddress;
		this.emailAddress = builder.emailAddress;
		this.note = builder.note;
	}

	// All getters for immutability
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return phonenumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return postaladdress
	 */
	public String getPostalAddress() {
		return postalAddress;
	}

	/**
	 * @return emailaddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @return note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * String printed in this format. If no field then returns " ".
	 * 
	 */
	@Override
	public String toString() {
		return name + ";" + phoneNumber + ";" + postalAddress + ";"
				+ emailAddress + ";" + note;
	}

	/**
	 * This class uses builder pattern to create entry object of Entry class.
	 * 
	 */
	public static class EntryBuilder {
		private String name = " ";
		private String phoneNumber = " ";
		private String postalAddress = " ";
		private String emailAddress = " ";
		private String note = " ";

		/**
		 * @param name
		 * @param phoneNumber
		 */
		public EntryBuilder(String name, String phoneNumber) {
			this.name = name;
			this.phoneNumber = phoneNumber;
		}

		/**
		 * This method sets postal address in the contact entry
		 * 
		 * @param postalAddress
		 * @return object
		 */
		public EntryBuilder postalAddress(String postalAddress) {
			this.postalAddress = postalAddress;
			return this;
		}

		/**
		 * This method sets email address in the contact entry
		 * 
		 * @param emailAddress
		 * @return object
		 */
		public EntryBuilder emailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
			return this;
		}

		/**
		 * This method sets note in the contact entry
		 * 
		 * @param note
		 * @return object
		 */
		public EntryBuilder note(String note) {
			this.note = note;
			return this;
		}

		/**
		 * Return the Entry Object
		 * 
		 * @return
		 */
		public Entry build() {
			Entry entry = new Entry(this);
			return entry;
		}
	}

	/**
	 * Hash code
	 * 
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((postalAddress == null) ? 0 : postalAddress.hashCode());
		return result;
	}

	/**
	 * overridden equals method
	 * 
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entry other = (Entry) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (postalAddress == null) {
			if (other.postalAddress != null)
				return false;
		} else if (!postalAddress.equals(other.postalAddress))
			return false;
		return true;
	}
}
