package comp3111.coursescraper;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import java.util.List;
/**
 * A CourseSFQ has an array of SectionSFQ. (CourseSFQ - SectionSFQ - Instructor)<br>
 * <br>
 * Attributes:<br>
 * String courseName: the name of the course scraped (e.g. " COMP 1021 ") The name of the course has spaces.<br>
 * double courseMean: average of the mean of all sections of this course<br>
 * int numOfSections: number of sections of this course<br>
 * SectionSFQ [] sections: an array of SectionsSFQ<br>
 * static final int MAX_NUM_SECTIONS: the maximum number of SectionSFQ in the array, and it is set to 10<br>
 */
public class CourseSFQ {
	private static final int MAX_NUM_SECTIONS = 10;
	
	private String courseName;
	private double courseMean;
	private int numOfSections;
	private SectionSFQ [] sections;
	
	/**
	 * Constructor of CourseSFQ, construct a new object of CourseSFQ
	 */
	public CourseSFQ() {
		courseName = null;
		courseMean = 0;
		numOfSections = 0;
		sections = new SectionSFQ[MAX_NUM_SECTIONS];
		for(int i = 0; i<MAX_NUM_SECTIONS; i++) {
			sections[i] = new SectionSFQ();
		}
	}
	
	/**
	 * Get the name of the course.
	 * @return the name of the course scraped
	 */
	public String getName() {
		return courseName;
	}
	
	/**
	 * Set the name of the course.
	 * @param name the name that will be set to the course
	 */
	public void setName(String name) {
		courseName = name ;
	}
	
	/**
	 * Get the SFQ score of the course.
	 * @return the average of the mean of each SectionSFQ
	 */
	public double getCourseMean() {
		double temp = 0.0;
		for(int i=0; i<numOfSections; i++) {
			temp += sections[i].getMean();
		}
		courseMean = ((double)temp)/((double)numOfSections);
		return courseMean;
	}
	
	/**
	 * Get the SectionSFQ object with index i in the array.
	 * @param i the index of the SectionSFQ we want to get
	 * @return the SectionSFQ specified by index i 
	 */
	public SectionSFQ getOneSection(int i) {
		return sections[i];
	}
	
	/**
	 * Get number of SectionSFQ object in the array.
	 * @return number of SectionSFQ in this course
	 */
	public int getNumOfSections() {
		return numOfSections;
	}
	
	/**
	 * Increase the number of SectionSFQ of this course by 1
	 */
	public void updateNumOfSections() {
		numOfSections++;
	}
	
	/**
	 * Get mean of the SectionSFQ object in the array.
	 * @param i i the index of the SectionSFQ which we want to get its mean
	 * @return the mean of the SectionSFQ with index i
	 */
	public double getMeanOfOneSection(int i) {
		return sections[i].getMean();
	}
	
	/**
	 * Get the Instructor object with index j in the array of the ScetionSFQ object. This ScetionSFQ object is with index i in the array of this CourseSFQ.
	 * @param i the index of SectionSFQ
	 * @param j the index of Instructor in the SectionSFQ with index i
	 * @return the Instructor object with index j in the array of the ScetionSFQ object. This ScetionSFQ object is with index i in the array of this CourseSFQ.
	 */
	public Instructor getInstructorOfOneSection(int i, int j) {
		return sections[i].getInstructor(j);
	}
	
}
