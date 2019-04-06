package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private String instName;
	private Section parent;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}

	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		s.instName = this.instName;
		return s;
	}
	public String toString() {
		return DAYS[day] + " " + start.toString() + "-" + end.toString() + ": " + venue;
	}
		
	
	/*
	 *  Instructor's Name
	 */
	/**
	 * @return the instructor's name
	 */
	public String getInstName() { return instName; }
	/**
	 * @param name the instructor's name to set
	 */
	public void setInstName(String name) { instName = name; }
	
	
	/*
	 *  Parent
	 */
	/**
	 * @return the parent section
	 */
	public Section getParent() { return parent; }
	/**
	 * @param s the parent section to set
	 */
	public void setParent(Section s) { parent = s; }
	
	
	/*
	 *  Start Time, End Time, Time Point and AM/PM Boolean Test
	 */
	/**
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @param hour the hour, from 0 to 23
	 * @param minute the minute, from 0 to 59
	 * @return whether a slot duration includes a specific time point
	 */
	public boolean include(int hour, int minute) {
		int startHour = start.getHour(),
			startMinute = start.getMinute(),
			endHour = end.getHour(),
			endMinute = end.getMinute();
		return (hour >= startHour && minute >= startMinute && hour <= endHour && minute <= endMinute) ? true : false;
	}
	/**
	 * @return whether a slot is in AM
	 */
	public boolean isAM() {
		int startHour = start.getHour();
		return (startHour < 12) ? true : false;
	}
	/**
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
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	
	/*
	 *  Day
	 */
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @param day the weekday, from 0 to 6
	 * @return whether a slot is on a specific weekday
	 */
	public boolean isOn(int day) { return (this.day == day) ? true : false; }

}
