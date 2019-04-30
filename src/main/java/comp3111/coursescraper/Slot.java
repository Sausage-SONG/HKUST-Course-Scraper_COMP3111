package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;

/**
 *  a slot has a Section type reference that refers to the section it belongs to. ( Course - Section - Slot)<br>
 *  <br>
 *  Attributes:<br>
 *  int day: from 0 to 5 (Mon. to Sat.)<br>
 *  LocalTime start: the start time<br>
 *  LocalTime end: the end time<br>
 *  String venue: the venue (e.g. "Rm3111")<br>
 *  List instName: a list of instructors' names<br>
 *  Section parent: refer to the parent section<br>
 */
public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private List<String> instName;
	private Section parent;
	
	/**
	 * An array represents six days in a week, from Monday to Saturday
	 */
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	
	/**
	 * 
	 */
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}
	
	/**
	 *  Default Constructor
	 */
	public Slot() { instName = new Vector<String>(); }
	
	/**
	 * 
	 */
	public String toString() {
		return DAYS[day] + " " + start.toString() + "-" + end.toString() + ": " + venue;
	}
		
	
	/*
	 *  Instructor's Name
	 */
	/**
	 * get the instructors' names
	 * @return the instructors' names
	 */
	public List<String> getInstName() { return instName; }
	/**
	 * add an instructor's name
	 * @param name the instructor's name to add
	 */
	public void addInstName(String name) { instName.add(name); }
	/**
	 * set instructors' names
	 * @param names a list of strings (names)
	 */
	public void setInstName(List<String> names) { instName = names; }
	
	
	/*
	 *  Parent
	 */
	/**
	 * get the parent section
	 * @return the parent section
	 */
	public Section getParent() { return parent; }
	/**
	 * set the parent section
	 * @param s the parent section to set
	 */
	public void setParent(Section s) { parent = s; }
	
	
	/*
	 *  Start Time, End Time, Time Point and AM/PM Boolean Test
	 */
	/**
	 * get the start time
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * set the start time
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * get the end time
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * set the end time
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * test whether a slot's duration includes a specific time point
	 * @param hour the hour, from 0 to 23
	 * @param minute the minute, from 0 to 59
	 * @return whether a slot's duration includes a specific time point
	 */
	public boolean include(int hour, int minute) {
		int startHour = start.getHour(),
			startMinute = start.getMinute(),
			endHour = end.getHour(),
			endMinute = end.getMinute();
		return (hour >= startHour && minute >= startMinute && hour <= endHour && minute <= endMinute) ? true : false;
	}
	/**
	 * test whether a slot is in AM
	 * @return whether a slot is in AM
	 */
	public boolean isAM() {
		int startHour = start.getHour();
		return (startHour < 12) ? true : false;
	}
	/**
	 * test whether a slot is in PM
	 * @return whether a slot is in PM
	 */
	public boolean isPM() {
		int endHour = end.getHour();
		return (endHour >= 12) ? true : false;
	}
	
	
	/*
	 *  Venue
	 */
	/**
	 * get the venue
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * set the venue
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	
	/*
	 *  Day
	 */
	/**
	 * get the day
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * set the day
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * test whether a slot is on a specific weekday
	 * @param day the weekday, from 0 to 6
	 * @return whether a slot is on a specific weekday
	 */
	public boolean isOn(int day) { return (this.day == day) ? true : false; }

}
