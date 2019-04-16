package comp3111.coursescraper;


public class Course {
	private static final int DEFAULT_MAX_SECTION = 80;
	
	private String title; 
	private String description;
	private String exclusion;
	private String commonCore;
	private Section [] sections;
	private int numSections;
	
	public Course() {
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSections = 0;
		commonCore = "";
	}
	
	
	/*
	 *  Sections
	 */
	/**
	 * @return the number of sections
	 */
	public int getNumSections() {
		return numSections;
	}
	/**
	 * @param numSlots the number of sections to set
	 */
	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
	/**
	 * @param s the section to add
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;
		sections[numSections++] = s;
		s.setParent(this);
	}	
	/**
	 * @param i the index of the section
	 * @return the section specified by the index
	 */
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	/**
	 * @return the total number of slots of a course
	 */
	public int getNumSlots() {
		int result = 0;
		for (int i = 0; i < numSections; ++i) {
			result += sections[i].getNumSlots();
		}
		return result;
	}
	/**
	 * @return whether a course has valid sections
	 */
	public boolean isValid() {
		for (int i = 0; i < numSections; ++i) {
			if (this.getSection(i).isValid())
				return true;
		}
		return false;
	}
	
	
	/*
	 *  Common Core
	 */
	/**
	 * @param c the common core information to set
	 */
	public void setCommonCore(String c) { commonCore = c; }
	/**
	 * @return the common core information
	 */
	public String getCommonCore() { return commonCore; }
	/**
	 * @return whether a course is 4Y Common Core
	 */
	public boolean is4YCC() { return commonCore.contains("4Y") ? true : false; }
	
	
	/*
	 *  Exclusion
	 */
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
	 * @return whether a course has exclusion(s)
	 */
	public boolean hasExclusion() { 
		return (exclusion == "null" ? false : true);
	}
	
	
	/*
	 *  Section Type Boolean Test
	 */
	/**
	 * @return whether a course has lab or tutorial section(s)
	 */
	public boolean hasLabOrTuto() {
		for (int i = 0; i < numSections; ++i) {
			if (sections[i].isLabOrTuto())
				return true;
		}
		return false;
	}

	
	/*
	 *  Title
	 */
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @return the simplified cousre title (i.e. Subject + Code) (e.g. "COMP1021")
	 */
	public String getSimplifiedTitle() {
		String [] arr = title.split(" ");
		return arr[0] + arr[1];
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
