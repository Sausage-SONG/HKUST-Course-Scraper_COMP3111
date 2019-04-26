package comp3111.coursescraper;


import org.junit.Test;

import comp3111.coursescraper.Course;

import static org.junit.Assert.*;


public class ItemTest {
	
	/*
	 *  Test on Course
	 */
	@Test
	public void testCourseSetTitle() {
		Course c = new Course();
		c.setTitle("COMP1021");
		assertEquals(c.getTitle(), "COMP1021");
	}
	
	@Test
	public void testCourseGetSimplifiedTitle() {
		Course c = new Course();
		c.setTitle("COMP 1021 - Introduction to Computer Science (3 units)");
		assertEquals(c.getSimplifiedTitle(), "COMP1021");
	}
	
	@Test
	public void testSetCommonCore() {
		Course c = new Course();
		c.setCommonCore("SSC-SA");
		assertEquals(c.getCommonCore(),"SSC-SA");
	}
	
	@Test
	public void testIs4YCC() {
		Course c = new Course();
		c.setCommonCore("SSC-SA");
		assertEquals(c.is4YCC(),false);
		c.setCommonCore("4Y SSC");
		assertEquals(c.is4YCC(),true);
	}
	
	@Test
	public void testSetExclusion() {
		Course c = new Course();
		c.setExclusion("COMP1021");
		assertEquals(c.getExclusion(),"COMP1021");
	}
	
	@Test
	public void testHasExclusion() {
		Course c = new Course();
		c.setExclusion("null");
		assertEquals(c.hasExclusion(),false);
		c.setExclusion("COMP1021");
		assertEquals(c.hasExclusion(),true);
	}
	
	/*
	 *  Test on Section
	 */
	@Test
	public void testSectionSetTitle() {
		Section s = new Section();
		s.setSectionTitle("L1");
		assertEquals(s.getSectionTitle(),"L1");
	}
	
	@Test
	public void testSectionGetSimplifiedTitle() {
		Section s = new Section();
		s.setSectionTitle("LA1 (7777)");
		assertEquals(s.getSimplifiedTitle(),"LA1");
	}
	
	@Test
	public void testSectionSetParent() {
		Course c = new Course();
		Section s = new Section();
		s.setParent(c);
		assertEquals(s.getParent(),c);
	}
	
	@Test
	public void testIsDigit() {
		assertEquals(Section.isDigit('1'),true);
		assertEquals(Section.isDigit('5'),true);
		assertEquals(Section.isDigit('a'),false);
		assertEquals(Section.isDigit('!'),false);
	}
	
	@Test
	public void testIsLabOrTuto() {
		Section s = new Section();
		s.setSectionTitle("L1");
		assertEquals(s.isLabOrTuto(),false);
		s.setSectionTitle("LA1");
		assertEquals(s.isLabOrTuto(),true);
		s.setSectionTitle("T1");
		assertEquals(s.isLabOrTuto(),true);
		s.setSectionTitle("R1");
		assertEquals(s.isLabOrTuto(),false);
		s.setSectionTitle("LX5");
		assertEquals(s.isLabOrTuto(),false);
		s.setSectionTitle("L?");
		assertEquals(s.isLabOrTuto(),false);
	}
	
	@Test
	public void testIsValid() {
		Section s = new Section();
		s.setSectionTitle("L1");
		assertEquals(s.isValid(),true);
		s.setSectionTitle("T5");
		assertEquals(s.isValid(),true);
		s.setSectionTitle("LA8");
		assertEquals(s.isValid(),true);
		s.setSectionTitle("R1");
		assertEquals(s.isValid(),false);
		s.setSectionTitle("?1");
		assertEquals(s.isValid(),false);
		s.setSectionTitle("L$");
		assertEquals(s.isValid(),false);
		s.setSectionTitle("LAJ1");
		assertEquals(s.isValid(),false);
	}
	
	@Test
	public void testSectionGetCourseCode() {
		Course c = new Course();
		Section s = new Section();
		c.addSection(s);
		c.setTitle("COMP 1021 - Introduction to Computer Science (3 units)");
		assertEquals(s.getCourseCode(),"COMP1021");
	}
	
	/*@Test
	public void testSectionGetCourseName() {
		Course c = new Course();
		Section s = new Section();
		c.addSection(s);
		c.setTitle("COMP 1021 - Introduction to Computer Science (3 units)");
		assertEquals(s.getCourseName(),"Introduction to Computer Science");
	}*/
	
	@Test
	public void testSetEnrolled() {
		Section s = new Section();
		s.setEnrolled(true);
		assertEquals(s.getEnrolled(),true);
	}
	
	/*
	 *  Test on Slot
	 */
	@Test
	public void testSetVenue() {
		Slot s = new Slot();
		s.setVenue("LSK");
		assertEquals(s.getVenue(),"LSK");
	}
	
	@Test
	public void testSetDay() {
		Slot s = new Slot();
		s.setDay(0);
		assertEquals(s.getDay(),0);
	}
	
	@Test
	public void testSlotIsOn() {
		Slot s = new Slot();
		s.setDay(4);
		assertEquals(s.isOn(4),true);
		assertEquals(s.isOn(3),false);
	}
}
