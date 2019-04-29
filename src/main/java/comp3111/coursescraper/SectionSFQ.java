package comp3111.coursescraper;

/**
 * a SectionSFQ has an array of Instructors and an array of double. (SectionSFQ > Instructor)<br>
 * <br>
 * Attributes:<br>
 * double mean: mean of this SectionSFQ (scraped from the website)
 * int numOfInstructors: number of Instructors in the array
 * Instructor [] instructor: an array of Instructor who teach this section<br>
 * double[] insMeanList: an array of double, the index of an element in this array corresponds to the index of the Instructor in the array Instructor[] instructor<br>
 * static final int MAX_NUM_INSTRUCTORS: the maximum number of instructors in the array, and it is set to 10<br>
 */
public class SectionSFQ {
	private static final int MAX_NUM_INSTRUCTORS = 10;
	
	private double mean;
	private int numOfInstructors;
	private Instructor[] instructor;
	private double[] insMeanList;
	
	/**
	 * Constructor of SectionSFQ, construct a new object of SectionSFQ
	 */
	public SectionSFQ() {
		mean = 0;
		numOfInstructors = 0;
		instructor = new Instructor[MAX_NUM_INSTRUCTORS];
		for(int i = 0; i<MAX_NUM_INSTRUCTORS; i++) {
			instructor[i] = new Instructor();
		}
		insMeanList = new double[MAX_NUM_INSTRUCTORS];
	}
	
	/**
	 * Get the instructors' mean in this section.
	 * @param i the index of the double in the double[] insMeanList
	 * @return the double with index i in double[] insMeanList
	 */
	public double getInsMeanForThisSection(int i) {
		return insMeanList[i];
	}
	
	/**
	 * Set the mean of the instructor with index i in the array.
	 * @param i the index of the double which we want to set value in the double[] insMeanList
	 * @param d the value we want to set to the double of index i
	 */
	public void setInsMeanForThisSection(int i, double d) {
		insMeanList[i] = d;
	}
	
	/**
	 * Get mean of this section.
	 * @return mean of this SectionSFQ (scraped from the website)
	 */
	public double getMean() {
		return mean;
	}
	
	/**
	 * Set mean of this section.
	 * @param num the value we want to set to the mean of this SectionSFQ
	 */
	public void setMean(double num) {
		mean = num;
	}
	
	/**
	 * Get number of instructors in this section.
	 * @return number of instructors
	 */
	public int getNumOfInstructors() {
		return numOfInstructors;
	}
	
	/**
	 * Set number of instructors in this section.
	 * @param num the number of instructors we want to set
	 */
	public void setNumOfInstructors(int num) {
		numOfInstructors = num;
	}
	
	/**
	 * Get the Instructor object with index i in the array.
	 * @param i the index of the Instructor in the array Instructor[] instructor
	 * @return the Instructor of index i in the array Instructor[] instructor
	 */
	public Instructor getInstructor(int i) {
		return instructor[i];
	}
	
	/**
	 * Set name of the Instructor object with index i in the array.
	 * @param s the name we want to set to the Instructor
	 * @param i the index of the Instructor in the array Instructor[] instructor
	 */
	public void setInsByName(String s, int i) {
		instructor[i] = new Instructor(s);
	}
	
	/**
	 * Copy the information of an Instructor object to the Instructor object of index i in the array. 
	 * @param ins the Instructor we want to copy
	 * @param i the index of the Instructor in the array Instructor[] instructor
	 */
	public void setInsByAnotherIns(Instructor ins, int i) { 
		instructor[i].setName(ins.getName());
		instructor[i].setTotalMark(ins.getTotalMark());
		instructor[i].setNumOfSectionsTeaches(ins.getNumOfSectionsTeaches());
	}
	
	/**
	 * Increase number of instructors by 1
	 */
	public void updateNumOfInstructors() {
		numOfInstructors++;
	}
	
}
