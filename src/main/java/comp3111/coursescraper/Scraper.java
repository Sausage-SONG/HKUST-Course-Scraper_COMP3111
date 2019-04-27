package comp3111.coursescraper;

import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import javafx.scene.control.TextArea;

import com.gargoylesoftware.htmlunit.html.DomText;
import java.util.Vector;


/**
 * WebScraper provide a sample code that scrape web content. After it is constructed, you can call the method scrape with a keyword, 
 * the client will go to the default url and parse the page by looking at the HTML DOM.  
 * <br>
 * In this particular sample code, it access to HKUST class schedule and quota page (COMP). 
 * <br>
 * https://w5.ab.ust.hk/wcq/cgi-bin/1830/subject/COMP
 *  <br>
 * where 1830 means the third spring term of the academic year 2018-19 and COMP is the course code begins with COMP.
 * <br>
 * Assume you are working on Chrome, paste the url into your browser and press F12 to load the source code of the HTML. You might be freak
 * out if you have never seen a HTML source code before. Keep calm and move on. Press Ctrl-Shift-C (or CMD-Shift-C if you got a mac) and move your
 * mouse cursor around, different part of the HTML code and the corresponding the HTML objects will be highlighted. Explore your HTML page from
 * body &rarr; div id="classes" &rarr; div class="course" &rarr;. You might see something like this:
 * <br>
 * <pre>
 * {@code
 * <div class="course">
 * <div class="courseanchor" style="position: relative; float: left; visibility: hidden; top: -164px;"><a name="COMP1001">&nbsp;</a></div>
 * <div class="courseinfo">
 * <div class="popup attrword"><span class="crseattrword">[3Y10]</span><div class="popupdetail">CC for 3Y 2010 &amp; 2011 cohorts</div></div><div class="popup attrword"><span class="crseattrword">[3Y12]</span><div class="popupdetail">CC for 3Y 2012 cohort</div></div><div class="popup attrword"><span class="crseattrword">[4Y]</span><div class="popupdetail">CC for 4Y 2012 and after</div></div><div class="popup attrword"><span class="crseattrword">[DELI]</span><div class="popupdetail">Mode of Delivery</div></div>	
 *    <div class="courseattr popup">
 * 	    <span style="font-size: 12px; color: #688; font-weight: bold;">COURSE INFO</span>
 * 	    <div class="popupdetail">
 * 	    <table width="400">
 *         <tbody>
 *             <tr><th>ATTRIBUTES</th><td>Common Core (S&amp;T) for 2010 &amp; 2011 3Y programs<br>Common Core (S&amp;T) for 2012 3Y programs<br>Common Core (S&amp;T) for 4Y programs<br>[BLD] Blended learning</td></tr><tr><th>EXCLUSION</th><td>ISOM 2010, any COMP courses of 2000-level or above</td></tr><tr><th>DESCRIPTION</th><td>This course is an introduction to computers and computing tools. It introduces the organization and basic working mechanism of a computer system, including the development of the trend of modern computer system. It covers the fundamentals of computer hardware design and software application development. The course emphasizes the application of the state-of-the-art software tools to solve problems and present solutions via a range of skills related to multimedia and internet computing tools such as internet, e-mail, WWW, webpage design, computer animation, spread sheet charts/figures, presentations with graphics and animations, etc. The course also covers business, accessibility, and relevant security issues in the use of computers and Internet.</td>
 *             </tr>	
 *          </tbody>
 *      </table>
 * 	    </div>
 *    </div>
 * </div>
 *  <h2>COMP 1001 - Exploring Multimedia and Internet Computing (3 units)</h2>
 *  <table class="sections" width="1012">
 *   <tbody>
 *    <tr>
 *        <th width="85">Section</th><th width="190" style="text-align: left">Date &amp; Time</th><th width="160" style="text-align: left">Room</th><th width="190" style="text-align: left">Instructor</th><th width="45">Quota</th><th width="45">Enrol</th><th width="45">Avail</th><th width="45">Wait</th><th width="81">Remarks</th>
 *    </tr>
 *    <tr class="newsect secteven">
 *        <td align="center">L1 (1765)</td>
 *        <td>We 02:00PM - 03:50PM</td><td>Rm 5620, Lift 31-32 (70)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td></tr><tr class="newsect sectodd">
 *        <td align="center">LA1 (1766)</td>
 *        <td>Tu 09:00AM - 10:50AM</td><td>Rm 4210, Lift 19 (67)</td><td><a href="/wcq/cgi-bin/1830/instructor/LEUNG, Wai Ting">LEUNG, Wai Ting</a></td><td align="center">67</td><td align="center">0</td><td align="center">67</td><td align="center">0</td><td align="center">&nbsp;</td>
 *    </tr>
 *   </tbody>
 *  </table>
 * </div>
 *}
 *</pre>
 * <br>
 * The code 
 * <pre>
 * {@code
 * List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
 * }
 * </pre>
 * extracts all result-row and stores the corresponding HTML elements to a list called items. Later in the loop it extracts the anchor tag 
 * &lsaquo; a &rsaquo; to retrieve the display text (by .asText()) and the link (by .getHrefAttribute()).   
 * 
 *
 */
public class Scraper {
	private WebClient client;

	/**
	 * Default Constructor 
	 */
	public Scraper() {
		client = new WebClient();
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}
	
	/**
	 * a helper function to add slots to a section
	 * @param e an html element representing a section 
	 * @param s the section object
	 * @param secondRow whether it's processing the second row (different rows have different formats)
	 */
	private void addSlot(HtmlElement e, Section s, boolean secondRow) {
		String times[] =  e.getChildNodes().get(secondRow ? 0 : 3).asText().split(" ");
		if (times[0].equals("TBA"))
			return;
		String venue = e.getChildNodes().get(secondRow ? 1 : 4).asText();
		List<?> names = (List<?>) e.getByXPath(".//a");
		List<String> nameList = new Vector<String>();
		for (HtmlElement item : (List<HtmlElement>)names)
			nameList.add(item.asText());
		if (nameList.isEmpty()) nameList.add("TBA");
		
		for (int j = 0; j < times[0].length(); j+=2) {
			String code = times[0].substring(j , j + 2);
			if (Slot.DAYS_MAP.get(code) == null)
				break;
			Slot slot = new Slot();
			slot.setDay(Slot.DAYS_MAP.get(code));
			slot.setStart(times[1]);
			slot.setEnd(times[3]);
			slot.setVenue(venue);
			slot.setInstName(nameList);
			s.addSlot(slot);
		}
	}
	
	/**
	 * scrape the course website using the given information<br/>
	 * 'enrolledSections' is passed so that this function will ignore those sections (to avoid having two copies of one single course)
	 * 
	 * @param baseurl the base url of course website
	 * @param term a four digit string (e.g. "1830")
	 * @param sub the subject to search (e.g. "COMP")
	 * @param enrolledSections a list of sections that have been enrolled
	 * @return a list of courses (scraping result)
	 */
	public List<Course> scrape(String baseurl, String term, String sub, List<Section> enrolledSections, boolean courseMustBeValid) {

		try {
			HtmlPage page = client.getPage(baseurl + "/" + term + "/subject/" + sub);
			
			// list of course divs
			List<?> items = (List<?>) page.getByXPath("//div[@class='course']");
			// vector of courses
			Vector<Course> result = new Vector<Course>();

			for (int i = 0; i < items.size(); i++) {
				Course c = new Course();
				HtmlElement htmlItem = (HtmlElement) items.get(i);
				
				// set course title, if already exist in enrolled section list, skip this one
				HtmlElement title = (HtmlElement) htmlItem.getFirstByXPath(".//h2");
				c.setTitle(title.asText());
				for (Section section : enrolledSections) {
					if (c.getSimplifiedTitle().equals(section.getCourseCode())) {
						result.add(section.getParent());
						continue;
					}
				}
				
				// set course exclusions and common core information
				List<?> popupdetailslist = (List<?>) htmlItem.getByXPath(".//div[@class='popupdetail']/table/tbody/tr");
				HtmlElement exclusion = null, commonCore = null;
				for ( HtmlElement e : (List<HtmlElement>)popupdetailslist) {
					HtmlElement t = (HtmlElement) e.getFirstByXPath(".//th");
					HtmlElement d = (HtmlElement) e.getFirstByXPath(".//td");
					if (t.asText().equals("EXCLUSION")) {
						exclusion = d;
					} else if (t.asText().equals("ATTRIBUTES")) {
						commonCore = d;
					}
				}
				c.setExclusion((exclusion == null ? "null" : exclusion.asText()));
				c.setCommonCore((commonCore == null ? "null" : commonCore.asText()));
				
				// get sections and slots
				List<?> sections = (List<?>) htmlItem.getByXPath(".//tr[contains(@class,'newsect')]");
				for ( HtmlElement e: (List<HtmlElement>)sections) {
					Section section = new Section();
					section.setSectionTitle(e.getChildNodes().get(1).asText());
					if (!section.isValid())
						continue;
					addSlot(e, section, false);
					e = (HtmlElement)e.getNextSibling();
					if (e != null && !e.getAttribute("class").contains("newsect"))
						addSlot(e, section, true);
					c.addSection(section);
				}
				
				// a course is now complete, add to result list if it's valid
				if (!(courseMustBeValid && !c.isValid()))
					result.add(c);
			}
			client.close();
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public List<String> scrapeSubject(String baseurl, String term){
		try {
			HtmlPage page = client.getPage(baseurl +"/" + term+ "/");
			List<?> items = (List<?>) page.getByXPath("//div[@class='depts']");
			
			HtmlElement htmlItem = (HtmlElement) items.get(0);
			List<?> allSub = (List<?>) htmlItem.getByXPath(".//a");
			
			Vector<String> result = new Vector<String>();
			for(int i = 0; i<allSub.size(); i++) {
				HtmlElement oneSub = (HtmlElement) allSub.get(i);
				result.add(oneSub.asText());
			}
			client.close();
			return result;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}


	public List<?> scrapeSFQ (String sfqurl){
		try {
			
			HtmlPage page = client.getPage(sfqurl);
			Vector<CourseSFQ> result = new Vector<CourseSFQ>();
			
			List<?>courseNames = (List<?>)page.getByXPath("//td[@colspan='3']");
			for(int i = 0; i<courseNames.size(); i++) {
				HtmlElement oneName = (HtmlElement)courseNames.get(i);
				if(oneName.asText().length() == 11 || oneName.asText().length() == 12) {					
					CourseSFQ c = new CourseSFQ();
					c.setName(oneName.asText());
					result.add(c);
				} else {
					continue;
				}
			}
			
			int count = -1;
			List<?> tables = (List<?>) page.getByXPath("//table[@border='1' and @width='700' and @cellpadding='0' and @cellspacing='0']");	

			for(int i = 1; i < tables.size(); i++) {   
				HtmlElement oneTable = (HtmlElement) tables.get(i);
				List<?> rows = (List<?>)oneTable.getByXPath(".//tr");
				
				for(int j = 1; j < rows.size()-1; j++) {			
					HtmlElement oneRow = (HtmlElement) rows.get(j);
					List<?>cells = (List<?>)oneRow.getByXPath(".//td");
					
					if(cells.size() == 6) { // a new course, go to the next element in the Vector
						count++;
						continue;
					}
					
					if(cells.size() == 8) {
						HtmlElement cellTwo = (HtmlElement)cells.get(1);
						HtmlElement cellThree = (HtmlElement)cells.get(2);
						HtmlElement cellFour = (HtmlElement)cells.get(3);
						HtmlElement cellFive = (HtmlElement)cells.get(4);
						
						if(cellThree.asText().length() == 1) { //cell three is empty, this row is a section												
							// get mean string of this section
							String tempMeanStr = cellFour.asText();
							int n;
							for(n = 0; n < tempMeanStr.length(); n++) {
								if(tempMeanStr.charAt(n) == '(') {
									break;
								} 
							}
							String meanStr = tempMeanStr.substring(0, n); // get the string type of mean
							
							if(!meanStr.equals("-")) {  //in case the - appears
								// num of sections ++
								result.elementAt(count).updateNumOfSections();
								double meanOfSection = Double.valueOf(meanStr.toString()); // convert to double type 
								// set mean of this section
								result.elementAt(count).getOneSection(result.elementAt(count).getNumOfSections() - 1).setMean(meanOfSection);							
							}
							
						} else { // this row is an instructor									
							// get mean of this instructor
							String tempInsMeanInThisSection = cellFive.asText();							
							int n;
							for(n = 0; n < tempInsMeanInThisSection.length(); n++) {
								if(tempInsMeanInThisSection.charAt(n) == '(') {
									break;
								} 
							}
							String insMeanInThisSection = tempInsMeanInThisSection.substring(0, n);
							
							if(!insMeanInThisSection.equals("-") && !cellThree.asText().equals("  ")) {
								// num of instructors ++
								result.elementAt(count).getOneSection(result.elementAt(count).getNumOfSections()-1).updateNumOfInstructors();
								//use this instructor's name to construct
								Instructor tempIns = new Instructor(cellThree.asText()); 															
								double insMean = Double.valueOf(insMeanInThisSection.toString()); //get double type of instructor mean in this section							
								SectionSFQ tempSection = result.elementAt(count).getOneSection(result.elementAt(count).getNumOfSections()-1); //copy the section being processed
								//add this instructor to this section
								result.elementAt(count).getOneSection(result.elementAt(count).getNumOfSections()-1).setInsByAnotherIns(tempIns, tempSection.getNumOfInstructors()-1);		
								// set mean in the double[] in the same position as the instructor in the Instructor[]
								result.elementAt(count).getOneSection(result.elementAt(count).getNumOfSections()-1).setInsMeanForThisSection(tempSection.getNumOfInstructors()-1, insMean);
							}
						
						}
					}
				}
			}
			client.close();
			return result;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
}



