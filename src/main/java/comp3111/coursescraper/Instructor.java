package comp3111.coursescraper;

/**
 * An instructor has a name, the number of sections he/she teaches in total, and the sum of the means the instructor gets in every section he/she teaches<br>
 * <br>
 * String name: name of the instructor<br>
 * int numOfSectionsTeached: number of sections taught by the instructor<br>
 * double totalMark: the sum of the means the instructor gets in every section he/she teaches<br>
 */
public class Instructor {

	private String name;
	private int numOfSectionsTeached;
	private double totalMark;
	
	/**
	 * Constructor of Instructor. Construct a new Instructor object 
	 */
	public Instructor() {
		name = null;
		numOfSectionsTeached = 0;
		totalMark = 0;
	}
	
	/**
	 * Constructor of Instructor, use a string(the instructor's name) to construct a new Instructor object
	 * @param s the name of the Instructor object we want to construct
	 */
	public Instructor(String s) {
		name = s;
		numOfSectionsTeached = 0;
		totalMark = 0;
	}
	
	/**
	 * Get name of the instructor
	 * @return name of instructor
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name to the instructor
	 * @param s the name to be set for the instructor 
	 */
	public void setName(String s) {
		name = s;
	}
	
	/**
	 * Get the number of sections taught by the instructor
	 * @return the number of sections taught by the instructor
	 */
	public int getNumOfSectionsTeaches() {
		return numOfSectionsTeached;
	}
	
	/**
	 * Increase number of sections taught by 1
	 */
	public void updateNumOfSectionsTeaches() {
		numOfSectionsTeached++;
	}
	
	/**
	 * Set the number of sections the instructor teaches
	 * @param i number to be set to the number of sections the instructor teaches
	 */
	public void setNumOfSectionsTeaches(int i) {
		numOfSectionsTeached = i;
	}
	
	/**
	 * Get total mark of the instructor
	 * @return the sum of mean which the instructor gets in every section he/she teaches
	 */
	public double getTotalMark() {
		return totalMark;
	}
	
	/**
	 * Add the new mean to total mark of the instructor
	 * @param d mark to add
	 */
	public void updateTotalMark(double d) {
		totalMark += d;
	}
	
	/**
	 * Set total mark of the instructor
	 * @param d the value to be set to the total mark of the instructor
	 */
	public void setTotalMark(double d) {
		totalMark = d;
	}
	
	/**
	 * Get average SFQ score of the instructor
	 * @return average SFQ score of every section the instructor teaches
	 */
	public double getInsSFQ() {
		return (double)totalMark/numOfSectionsTeached;
	}
	
}
