package comp3111.coursescraper;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import java.util.List;

public class CourseSFQ {
	private final int MAX_NUM_SECTIONS = 10;
	
	private String courseName;
	private double courseMean;
	private int numOfSections;
	private SectionSFQ [] sections;
	
	public CourseSFQ() {
		courseName = null;
		courseMean = 0;
		numOfSections = 0;
		sections = new SectionSFQ[MAX_NUM_SECTIONS];
		for(int i = 0; i<MAX_NUM_SECTIONS; i++) {
			sections[i] = new SectionSFQ();
		}
	}
	
	public String getName() {
		return courseName;
	}
	
	public void setName(String name) {
		courseName = name ;
	}
	
	public double getCourseMean() {
		double temp = 0.0;
		for(int i=0; i<numOfSections; i++) {
			temp += sections[i].getMean();
		}
		courseMean = ((double)temp)/((double)numOfSections);
		return courseMean;
	}
	
	public SectionSFQ getOneSection(int i) {
		return sections[i];
	}
	
	public int getNumOfSections() {
		return numOfSections;
	}
	
	public void updateNumOfSections() {
		numOfSections++;
	}
	
	public double getMeanOfOneSection(int i) {
		return sections[i].getMean();
	}
	
	public Instructor getInstructorOfOneSection(int i, int j) {
		return sections[i].getInstructor(j);
	}
	
}
