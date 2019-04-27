package comp3111.coursescraper;

public class SectionSFQ {
	private final int MAX_NUM_INSTRUCTORS = 10;
	
	private double mean;
	private int numOfInstructors;
	private Instructor[] instructor;
	private double[] insMeanList;
	
	public SectionSFQ() {
		mean = 0;
		numOfInstructors = 0;
		instructor = new Instructor[MAX_NUM_INSTRUCTORS];
		for(int i = 0; i<MAX_NUM_INSTRUCTORS; i++) {
			instructor[i] = new Instructor();
		}
		insMeanList = new double[MAX_NUM_INSTRUCTORS];
	}
	
	public double getInsMeanForThisSection(int i) {
		return insMeanList[i];
	}
	
	public void setInsMeanForThisSection(int i, double d) {
		insMeanList[i] = d;
	}
	
	public double getMean() {
		return mean;
	}
	
	public void setMean(double num) {
		mean = num;
	}
	
	public int getNumOfInstructors() {
		return numOfInstructors;
	}
	
	public void setNumOfInstructors(int num) {
		numOfInstructors = num;
	}
	
	public Instructor getInstructor(int i) {
		return instructor[i];
	}
	
	public void setInsByName(String s, int i) { //function name changed
		instructor[i] = new Instructor(s);
	}
	
	public void setInsByAnotherIns(Instructor ins, int i) {   //function name changed
		instructor[i].setName(ins.getName());
		instructor[i].setTotalMark(ins.getTotalMark());
		instructor[i].setNumOfSectionsTeached(ins.getNumOfSectionsTeached());
	}
	
	public void updateNumOfInstructors() {
		numOfInstructors++;
	}
	
}
