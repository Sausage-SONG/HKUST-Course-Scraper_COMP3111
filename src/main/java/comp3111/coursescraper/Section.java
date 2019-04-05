package comp3111.coursescraper;

public class Section {
	private static final int DEFAULT_MAX_SLOT = 2;
	
	private String sectionTitle;     // e.g. L1 (1038)
	private Slot [] slots;           // array of slots 
	private int numSlots;            // # of slots
	
	// Constructor
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; ++i) slots[i] = null;
		numSlots = 0;
		sectionTitle = "";
	}
	
	// section self clone
	public Section clone() {
		Section s = new Section();
		s.setSectionTitle(sectionTitle);
		s.setNumSlots(numSlots);
		for (int i = 0; i < numSlots; ++i) {
			s.slots[i] = slots[i].clone(); 
		}
		return s;
	}
	
	// access and modify section title
	public void setSectionTitle(String title) { sectionTitle = title; }
	public String getSectionTitle() { return sectionTitle; }
	
	// access and modify numSlots
	public int getNumSlots() { return numSlots; }
	public void setNumSlots(int num) { numSlots = num; }
	
	// get a slot by index
	public Slot getSlot(int i) { return (i >= 0 && i < numSlots) ? slots[i] : null; }
	// add a slot to the array
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT) 
			return;
		slots[numSlots++] = s.clone();
	}
	
	// whether a section has slots in AM and in PM
	public boolean hasAM() {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isAM())
				return true;
		}
		return false;
	}
	public boolean hasPM() {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isPM())
				return true;
		}
		return false;
	}
	
	// whether a section has slots on some day, day from 0 to 6
	public boolean hasDay(int day) {
		for (int i = 0; i < numSlots; ++i) {
			if (slots[i].isOn(day))
				return true;
		}
		return false;
	}
	
	// whether a section is a lab or a tutorial
	public boolean isLabOrTuto() {
		if (sectionTitle.charAt(0) == 'T') {
			return true;
		} else if (sectionTitle.charAt(0) == 'L' && sectionTitle.charAt(1) == 'A') {
			return true;
		}
		return false;
	}
}
