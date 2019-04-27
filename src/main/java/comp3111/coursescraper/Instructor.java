package comp3111.coursescraper;

public class Instructor {

	private String name;
	private int numOfSectionsTeached;
	private double totalMark;
	
	public Instructor() {
		name = null;
		numOfSectionsTeached = 0;
		totalMark = 0;
	}
	
	public Instructor(String s) {
		name = s;
		numOfSectionsTeached = 0;
		totalMark = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public int getNumOfSectionsTeached() {
		return numOfSectionsTeached;
	}
	
	public void updateNumOfSectionsTeached() {
		numOfSectionsTeached++;
	}
	
	public void setNumOfSectionsTeached(int i) {
		numOfSectionsTeached = i;
	}
	
	public double getTotalMark() {
		return totalMark;
	}
	
	public void updateTotalMark(double d) {
		totalMark += d;
	}
	
	public void setTotalMark(double d) {
		totalMark = d;
	}
	
	public double getInsSFQ() {
		return (double)totalMark/(double)numOfSectionsTeached;
	}
	
}
