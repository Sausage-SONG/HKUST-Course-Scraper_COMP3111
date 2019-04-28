package comp3111.coursescraper;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import java.util.List;

/**
 *  a section has an array of slots, and a Course type reference that refers to the course it belongs to. 
 *  ( Course > Section > Slot)<br>
 *  <br>
 *  Attributes:<br>
 *  String sectionTitle: full section title (e.g. "L1 (1038)")<br>
 *  Slots[] slots: an array of slots<br>
 *  int numSlots: number of slots in the array<br>
 *  Course parent: refer to the parent course<br>
 *  Boolean enrolled: whether this section has been enrolled<br>
 */
public class Section {
	private static final int DEFAULT_MAX_SLOT = 4;
	
	private String sectionTitle;
	private Slot [] slots;
	private int numSlots;
	private Course parent;
	private Boolean enrolled;
	
	/**
	 *  Default Constructor
	 */
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; ++i) slots[i] = null;
		numSlots = 0;
		sectionTitle = "";
		parent = null;
		enrolled= false;
	}
	
	
	/*
	 *  Title
	 */
	/**
	 * @param title the title to set
	 */
	public void setSectionTitle(String title) { sectionTitle = title; }
	/**
	 * @return the section title
	 */
	public String getSectionTitle() { return sectionTitle; }
	/**
	 * @return the simplified section title (no absolute id) (i.e. Type + Digit) (e.g. "L1")
	 */
	public String getSimplifiedTitle() {
		String [] arr = sectionTitle.split(" ");
		return arr[0];
	}
	
	
	/*
	 *  Parent
	 */
	/**
	 * @param c the parent course to set
	 */
	public void setParent(Course c) { parent = c; }
	/**
	 * @return the parent course
	 */
	public Course getParent() { return parent; }
	
	
	/*
	 *  Slots
	 */
	/**
	 * @return the number of slots
	 */
	public int getNumSlots() { return numSlots; }
	/**
	 * @param num the number of slots to set
	 */
	public void setNumSlots(int num) { numSlots = num; }
	/**
	 * @param i the index of the slot
	 * @return the slot specified by the index
	 */
	public Slot getSlot(int i) { return (i >= 0 && i < numSlots) ? slots[i] : null; }
	/**
	 * @param s the slot to add
	 */
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT) 
			return;
		slots[numSlots++] = s;
		s.setParent(this);
	}
	
	
	/*
	 *  AM, PM and Weekday Boolean Test
	 */
	/**
	 * @return whether a section has slot(s) in AM
	 */
	public boolean hasAM() {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isAM())
				return true;
		}
		return false;
	}
	/**
	 * @return whether a section has slot(s) in PM
	 */
	public boolean hasPM() {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isPM())
				return true;
		}
		return false;
	}
	/**
	 * @param day the weekday, from 0 to 6
	 * @return whether a section has slot(s) on a specific weekday
	 */
	public boolean hasDay(int day) {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isOn(day))
				return true;
		}
		return false;
	}
	
	
	/*
	 *  Section Type Boolean Test
	 */
	public static final char[] digit = {'0','1','2','3','4','5','6','7','8','9'};
	/**
	 * @param c a character
	 * @return whether the character is a digit
	 */
	public static boolean isDigit(char c) {
		for (int i = 0; i < 10; ++i)
			if (c == digit[i])
				return true;
		return false;
	}
	/**
	 * @return whether a section is a lab or a tutorial
	 */
	public boolean isLabOrTuto() {
		if (sectionTitle.charAt(0) == 'T') {
			return true;
		} else if (sectionTitle.charAt(0) == 'L' && sectionTitle.charAt(1) == 'A') {
			return true;
		}
		return false;
	}
	/**
	 * @return whether the section is valid (valid format: L/T/LA + digit)
	 */
	public boolean isValid() {
		if (sectionTitle.charAt(0) == 'T') {
			return true;
		} else if (sectionTitle.charAt(0) == 'L') {
			if(isDigit(sectionTitle.charAt(1)))
				return true;
			else if (sectionTitle.charAt(1) == 'A' && isDigit(sectionTitle.charAt(2)))
				return true;
		}
		return false;
	}
	

	/*
	 *  Task 3 Special String 
	 */
	/**
	 * @return the course code(simplified title) of a section (e.g. "COMP3111")
	 */

	public String getCourseCode() {
		return this.getParent().getSimplifiedTitle();
	}
	
	/**
	 * @return the course name of a section (e.g. "Introduction to Computing with Java")
	 */
	public String getCourseName() {
		String [] arr = this.getParent().getTitle().split("-",2);
		String s = arr[1];
		String [] arrtwo=s.split("\\(");
		return arrtwo[0].substring(0,arrtwo[0].length()-1);
	}
	
	/**
	 * @return a String of names of instructors teaching this section,  return "N/A" if no slot in this section
	 */
	public String getInstructorList() {
		if (this.numSlots == 0) 
			return "N/A";
		String result = "";
		for (int i = 0; i < numSlots; ++i) {
			List<String> names = this.getSlot(i).getInstName();
			if (names.isEmpty())
				continue;
			for (String name : names)
				if (!result.contains(name))
					result += (", " + name);
		}
		return result.substring(2, result.length());
	}
	
	/*
	 *  Enrolled
	 */
	/**
	 * @return whether a section is enrolled
	 */
	public Boolean getEnrolled() {
		return enrolled;
	}
	/**
	 * @param b a boolean to set, true -- enrolled, false -- not enrolled
	 */
	public void setEnrolled(boolean b) {
		enrolled=b;
	}	
}
