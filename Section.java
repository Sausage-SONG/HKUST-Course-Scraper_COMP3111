package comp3111.coursescraper;

public class Section {
	private static final int DEFAULT_MAX_SLOT = 10;
	
	private Course parent;
	private String sectionTitle;
	private Slot [] slots;
	private int numSlots;
	
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; ++i) slots[i] = null;
		numSlots = 0;
		parent = null;
		sectionTitle = "";
	}
	
	public void setSectionCode(String code) { sectionTitle = code; }
	public String getSectionCode() { return sectionTitle; }
	
	public int getNumSlots() { return numSlots; }
	public void addSlot(Slot s) { slots[numSlots++] = s; }
	public Slot getSlot(int i) { return (i >= 0 && i < numSlots) ? slots[i] : null; }
	
	public Course getParent() { return parent; }
	public void setParent(Course c) { parent = c; }
}
