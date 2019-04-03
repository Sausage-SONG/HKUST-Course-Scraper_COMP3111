package comp3111.coursescraper;


import org.junit.Test;

import comp3111.coursescraper.Course;

import static org.junit.Assert.*;


public class ItemTest {

	@Test
	public void testSetTitle() {
		Course i = new Course();
		i.setTitle("ABCDE");
		assertEquals(i.getTitle(), "ABCDE");
	}
	
	@Test
	public void testSlotTime() {
		Slot s = new Slot();
		s.setStart("02:00AM");
		assertEquals(s.getStartHour(),2);
		Course i = new Course();
		i.addSlot(s);
	}
	
}
