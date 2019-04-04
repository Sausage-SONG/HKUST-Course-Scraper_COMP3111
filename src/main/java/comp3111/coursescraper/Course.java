package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_SECTION = 10;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private Section [] sections;
	private int numSections;
	
	public Course() {
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSections = 0;
	}
	
	// add a section to the array
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;
		sections[numSections++] = s.clone();
	}
	
	// get a section by index
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	
	// get total # of slots
	public int getNumSlots() {
		int result = 0;
		for (int i = 0; i < numSections; ++i) {
			result += sections[i].getNumSlots();
		}
		return result;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	/**
	 * @return the numSlots
	 */
	public int getNumSections() {
		return numSections;
	}

	/**
	 * @param numSlots the numSlots to set
	 */
	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
}
