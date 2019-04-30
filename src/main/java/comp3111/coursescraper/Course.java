package comp3111.coursescraper;

/**
 *  a course has an array of sections. ( Course - Section - Slot)<br>
 *  <br>
 *  Attributes:<br>
 *  String title: full course title (e.g. "COMP 1022P - Introduction to Computing with Java (3 units)")<br>
 *  String exclusion: course codes (e.g. "COMP 1021, COMP 1022Q,  COMP 2011, ISOM 3320")<br>
 *  String commonCore: CC info (e.g. "Common Core (QR) for 4Y programs")<br>
 *  Section[] sections: an array of sections<br>
 *  int numSections: number of sections in the array<br>
 */
public class Course {
	private static final int DEFAULT_MAX_SECTION = 80;
	
	private String title; 
	private String exclusion;
	private String commonCore;
	private Section [] sections;
	private int numSections;
	
	/**
	 *  Default Constructor
	 */
	public Course() {
		sections = new Section[DEFAULT_MAX_SECTION];
		for (int i = 0; i < DEFAULT_MAX_SECTION; i++) sections[i] = null;
		numSections = 0;
		commonCore = "";
		exclusion = "";
		title = "";
	}
	
	
	/*
	 *  Sections
	 */
	/**
	 * get the number of sections
	 * @return the number of sections
	 */
	public int getNumSections() {
		return numSections;
	}
	/**
	 * set the number of sections
	 * @param numSections the number of sections to set
	 */
	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
	/**
	 * add a section
	 * @param s the section to add
	 */
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_SECTION)
			return;
		sections[numSections++] = s;
		s.setParent(this);
	}	
	/**
	 * get a section by index
	 * @param i the index of the section
	 * @return the section specified by the index
	 */
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}
	/**
	 * get the total number of slots
	 * @return the total number of slots
	 */
	public int getNumSlots() {
		int result = 0;
		for (int i = 0; i < numSections; ++i) {
			result += sections[i].getNumSlots();
		}
		return result;
	}
	/**
	 * test whether a course is valid (has valid section(s))
	 * @return whether a course is valid
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
	 * set the common core information
	 * @param c the common core information to set
	 */
	public void setCommonCore(String c) { commonCore = c; }
	/**
	 * get the common core information
	 * @return the common core information
	 */
	public String getCommonCore() { return commonCore; }
	/**
	 * test whether a course is 4Y Common Core
	 * @return whether a course is 4Y Common Core
	 */
	public boolean is4YCC() { return commonCore.contains("4Y") ? true : false; }
	
	
	/*
	 *  Exclusion
	 */
	/**
	 * get the exclusion
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}
	/**
	 * set the exclusion
	 * @param exclusion the exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}
	/**
	 * test whether a course has exclusion(s)
	 * @return whether a course has exclusion(s)
	 */
	public boolean hasExclusion() { 
		return (exclusion == "null" ? false : true);
	}
	
	
	/*
	 *  Section Type Boolean Test
	 */
	/**
	 * test whether a course has lab or tutorial section(s)
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
	 * get the course title
	 * @return the course title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * get the simplified cousre title
	 * @return the simplified cousre title (i.e. Subject + Code) (e.g. "COMP1021")
	 */
	public String getSimplifiedTitle() {
		String [] arr = title.split(" ");
		return arr[0] + arr[1];
	}
	/**
	 * set the course title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
