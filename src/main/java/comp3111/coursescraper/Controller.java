package comp3111.coursescraper;

//import java.awt.event.ActionEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import javafx.util.Callback;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.text.Font;
import java.util.Random;

import java.awt.Checkbox;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.Map;
import java.util.HashMap;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;  


public class Controller {

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;
    
//A series of Checkbox obj [Task2]
    @FXML
    private CheckBox CheckboxAM;

    @FXML
    private CheckBox CheckboxPM;

    @FXML
    private CheckBox CheckboxMon;

    @FXML
    private CheckBox CheckboxTue;

    @FXML
    private CheckBox CheckboxWed;

    @FXML
    private CheckBox CheckboxThu;

    @FXML
    private CheckBox CheckboxFri;

    @FXML
    private CheckBox CheckboxSat;

    @FXML
    private CheckBox CheckboxCC;

    @FXML
    private CheckBox CheckboxNoEx;

    @FXML
    private CheckBox CheckboxWithLabs;
    
    @FXML
    private Button BtnSelectAll;
    
    @FXML
    private TableView<Section> SectionTable;
    
    @FXML
    private TableColumn<Section, String> CourseCodeColumn;

    @FXML
    private TableColumn<Section, String> SectionColumn;

    @FXML
    private TableColumn<Section, String> CourseNameColumn;

    @FXML
    private TableColumn<Section, String> InstructorColumn;

    @FXML
    private TableColumn<Section,CheckBox> EnrollColumn;
    
 //[Modified by nxy]

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    //add button AllSubjectSearch
    @FXML
    private Button AllSubjectSearch;
    
    private Scraper scraper = new Scraper();
    
    /*
     *  Task 5
     */
    int numOfClickOnAllSubjectSearch = 0;
    @FXML
    /**
     * Search every subject on the course website. 
     * The total number of subjects and the total number of courses will be printed on the console.
     * When a subject is scraped, the name of this subject and " is done" will be printed on the system console.(e.g. When COMP is scraped, "COMP is done" will be printed on the system console)
     * This function is triggered by 'All Subject Search' button
     */
    void allSubjectSearch() {
    	buttonSfqEnrollCourse.setDisable(false);
    	courses.clear();
    	List<String> allSubject = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());
    	
    	if(numOfClickOnAllSubjectSearch == 0) {
    		textAreaConsole.setText("Total Number of Categories/Code Prefix: "+allSubject.size()+"\n");  
    		numOfClickOnAllSubjectSearch ++;
    	} else {
	    	Task<Void> task = new Task<Void>() {
	    		@Override
	    		protected Void call() {   			
	    	    	for (int i = 0; i<allSubject.size(); i++) {
	    	    		List<Course> OneSubjectCourse = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), allSubject.get(i), enrolledSections, false);	
						courses.addAll(OneSubjectCourse);    	    	    	    		
	    	    		System.out.println(allSubject.get(i)+" is done;"); 
	    	    		updateProgress(i+1,allSubject.size());  //i ----> i + 1   
	    	    	}
	    			return null;
	    		}
	    	};    	
	    	progressbar.progressProperty().bind(task.progressProperty());
	    	Thread thread = new Thread(task);
	    	thread.setDaemon(true);
	    	thread.start();
	    	task.setOnSucceeded((WorkerStateEvent t) ->
	        {
		    	textAreaConsole.appendText("Total Number of Courses fetched: "+courses.size()+"\n");
	        });
	    }
    	for (Course c : courses)
    		if (!c.isValid())
    			courses.remove(c);
    }
    
    
    /*
     *  Task 6
     */
    @FXML
    /**
     * Get the SFQ score of all instructors appear on the course SFQ website. 
     * The SFQ score of all instructors appear on the course SFQ website will be printed on the console.
     * This function is triggered by 'List instructors' average SFQ' button.
     */
    public void findInstructorSfq() {
    	textAreaConsole.clear();
    	List<?>allCourses = scraper.scrapeSFQ(textfieldSfqUrl.getText());
    	Vector <Instructor> allInstructors = new Vector<Instructor>();
    	
    	//construct the Vector of all instructors
    	for(int i = 0; i<allCourses.size();i++) {
    		CourseSFQ temp = (CourseSFQ) allCourses.get(i);
    		for(int j = 0; j<temp.getNumOfSections(); j++) {   
    			for(int m = 0; m<temp.getOneSection(j).getNumOfInstructors(); m++) { 
    				boolean exist = false;
    				for(int k = 0; k<allInstructors.size();k++) {
    					if(allInstructors.elementAt(k).getName().equals(temp.getOneSection(j).getInstructor(m).getName())) {
    						exist = true;
    						allInstructors.elementAt(k).updateTotalMark(temp.getOneSection(j).getInsMeanForThisSection(m));
    						allInstructors.elementAt(k).updateNumOfSectionsTeaches();
    					}	
    				}
    				if(!exist) {
    					Instructor ins = new Instructor();
    					ins.setName(temp.getOneSection(j).getInstructor(m).getName());
    					ins.updateNumOfSectionsTeaches();
    					ins.updateTotalMark(temp.getOneSection(j).getInsMeanForThisSection(m));
    					allInstructors.add(ins);
    				}
    			}
    		}
    	}
    	
    	//print out the information
    	for(int i = 0; i<allInstructors.size();i++) {
    		String s = "The SFQ of " + allInstructors.get(i).getName() +" is "+ allInstructors.get(i).getInsSFQ();
    		textAreaConsole.appendText(s + "\n");
    	}
    }
    
    @FXML
    /**
     * Get the SFQ score of enrolled courses. 
     * The SFQ score of all the courses enrolled will be printed on the console.
     * This function is triggered by 'Find SFQ with my enrolled courses' button.
     */
    public void findSfqEnrollCourse() {
    	textAreaConsole.clear();
    	List<?>allCourses = scraper.scrapeSFQ(textfieldSfqUrl.getText());
    	
    	//get a string type Vector of the course code of all enrolled sections, without repeating course code
    	Vector<String> enrolledCoursesCode = new Vector<String>();
    	for(int i = 0; i<enrolledSections.size(); i++)
    		if (!enrolledCoursesCode.contains(enrolledSections.get(i).getCourseCode()))
        		enrolledCoursesCode.add(enrolledSections.get(i).getCourseCode());
    	
    	//if no course has been enrolled
    	if (enrolledCoursesCode.isEmpty())
    		textAreaConsole.setText("No course has been enrolled!\n");
    	
    	//compare the enrolled course code with all the courses scraped
    	for(int i = 0; i<enrolledCoursesCode.size();i++) {
    		boolean exist = false;
    		for(int j = 0; j<allCourses.size(); j++) {
    			CourseSFQ temp = (CourseSFQ) allCourses.get(j);
    			if(temp.getName().replaceAll(" ", "").equals(enrolledCoursesCode.get(i))) {
    				exist = true;
    				textAreaConsole.appendText("SFQ of " + temp.getName() + " is " + temp.getCourseMean() + "\n");
    			}
    		}
    		if(!exist) {
    			textAreaConsole.appendText("The SFQ information of " + enrolledCoursesCode.get(i) + " is not found on the website" + "\n");
    		}
    	}
    }
    
    
    /**
     *  a list to store courses (the result of search or allSubjectSearch)
     */
    private static List<Course> courses = new Vector<Course>();
    /**
     *  a list to store courses (the result of using filters)
     */
    private static List<Course> filteredCourses = new Vector<Course>();
    /**
     *  a list to store sections (this is just the expansion of 'filteredCourses', for convenience)
     */
    private static List<Section> filteredSections = new Vector<Section>();
    /**
     *  a list to store sections that have been enrolled
     */
    private static List<Section> enrolledSections = new Vector<Section>();

    /*
     *  Task 1: Search function for button "Search".
     */
    /**
     * @return a string of information required by task 1 (# of courses, # of sections, instructors' names)
     */
    public String backendInfo() {
    	String result = "";
    	
    	// count and display # of courses and sections
    	int number_of_sections = 0,
        	number_of_courses  = courses.size();
        for (Course c : courses)
       		number_of_sections += c.getNumSections();
       	result += "Total Number of difference sections in this search: " + number_of_sections + "\n" +
       			  "Total Number of Course in this search: " + number_of_courses + "\n";
       	
    	// find and display a list of instrutors who have teaching assignment but not at Tu 3:10PM
    	List<String> filteredInstructors = new Vector<String>();
    	List<String> notFilteredInstructors = new Vector<String>();
    	for (Course c : courses) {
    		for (int i = 0; i < c.getNumSections(); ++i) {
    			Section s = c.getSection(i);
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot slot = s.getSlot(j);
    				if (slot.isOn(1) && slot.include(15, 10))
    					for (String name : slot.getInstName())
    						if (!notFilteredInstructors.contains(name))
    							notFilteredInstructors.add(name);
    			}
    		}
    	}
    	for (Course c : courses) {
    		for (int i = 0; i < c.getNumSections(); ++i) {
    			Section s = c.getSection(i);
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot slot = s.getSlot(j);
					for (String name : slot.getInstName())
						if (!notFilteredInstructors.contains(name) && !filteredInstructors.contains(name))
							filteredInstructors.add(name);
    			}
    		}
    	}
    	filteredInstructors.sort(null);
    	result += "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: ";
    	for (String name : filteredInstructors) {
    		result += (name + ", ");
    	}
    	result += "\n";
       	
       	return result;
    }
    @FXML
    /**
     *  the function triggered by 'search' button, this function will call scrapers and display
     *  courses in the textArea
     */
    void search() {
    	// About Task 5
    	buttonSfqEnrollCourse.setDisable(false);
    	List<String> allSubject = scraper.scrapeSubject(textfieldURL.getText(), textfieldTerm.getText());
    	textAreaConsole.setText("Total Number of Categories/Code Prefix: "+allSubject.size()+"\n");  
    
    	courses = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText(), enrolledSections, true);
    	
    	// Handle 404
    	if (courses == null) {
    		textAreaConsole.appendText("Oops! 404 Not Found! Please check your input.\n");
    		return;
    	}
    	
    	// display task 1 information
    	textAreaConsole.appendText(this.backendInfo());
    	
    	// display details of each course
    	for (Course c : courses) {
    		String newline = c.getTitle() + "\n";
    		for (int i = 0; i < c.getNumSections(); i++) {
    			Section s = c.getSection(i);
    			newline += "Section: " + s.getSectionTitle() + "\n";
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot t = s.getSlot(j);
    				newline += "Slot" + " " + j + ": " + t + "\n";
    			}
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    }
    


    /*
     *  Task 4: Update the timetable whenever the enrolled sections list is updated.
     */
    /**
     *  a list to store all the label objects in task 4
     */
    private static List<Label> labels = new Vector<Label>();
    /**
     *  an array (size == 3) that stores a specific color in RGB format
     */
    private static int[] RGB = new int[3];
    // initialize RGB to be a random color
    static {
    	Random r = new Random();
    	for (int i = 0; i < 3; ++i) RGB[i] = r.nextInt(256);
    }
    /**
     *  update the RGB array, set a different value to each
     */
    private static void updateRGB() {
    	int[] increase = {47,73,107};
    	for (int i = 0; i < 3; ++i) 
    		RGB[i] = (RGB[i] + increase[i]) % 256;
    }
    /**
     *  update timetable according to 'enrolledSections', create all labels again
     */
    public void updateTimetable() {
    	// first remove all existing labels
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	ap.getChildren().removeAll(labels);
    	labels.clear();
    	
    	// then create new labels for each section
    	for (int i = 0; i < enrolledSections.size(); ++i) {
    		Section s = enrolledSections.get(i);
    		
    		// prepare the label name and the color, as these are shared by slots from the same section
    		String labelName = s.getParent().getSimplifiedTitle() + "\n" + s.getSimplifiedTitle();
    		updateRGB();
    		
    		// create a label for each slot, set attributes
    		for (int j = 0; j < s.getNumSlots(); ++j) {
    			Slot slot = s.getSlot(j);
    			Label label = new Label(labelName);
    			//label.setFont(new Font(7));
    			label.setBackground(new Background(new BackgroundFill(Color.rgb(RGB[0], RGB[1], RGB[2], 0.7), CornerRadii.EMPTY, Insets.EMPTY)));
    	    	label.setLayoutX((slot.getDay()+1)*100);
    	    	label.setLayoutY((float)20*(slot.getStart().getHour()-9+(float)slot.getStart().getMinute()/60)+40);
    			label.setMinWidth(100.0);
    	    	label.setMaxWidth(100.0);
    	    	label.setMinHeight((float)20*((slot.getEnd().getHour()+(float)slot.getEnd().getMinute()/60)-
    	    									  (slot.getStart().getHour()+(float)slot.getStart().getMinute()/60)));
    	    	label.setMaxHeight((float)20*((slot.getEnd().getHour()+(float)slot.getEnd().getMinute()/60)-
						  		   (slot.getStart().getHour()+(float)slot.getStart().getMinute()/60)));
    			
    	    	// after finishing, add this label to the label list
    			labels.add(label);
    		}
    	}
    	
    	// add all labels from the label list to the anchor pane
    	ap.getChildren().addAll(labels);
    }
    
    

    @FXML
    /**
     * Deal with the function of button SelectAll and DeselectAll
     * if 'Select All' is clicked, check all the checkboxes and set the text to be 'DeselectAll'
     * if 'Deselect All' is clicked, uncheck all the checkboxes and set the text to be 'SelectAll'
     * every time the checkbox change, the filter will be refreshed
	 */
    public void SelectDeselectAll() {
    	final CheckBox[] ListAll = {CheckboxAM, CheckboxPM,CheckboxMon,CheckboxTue,CheckboxWed,CheckboxThu,CheckboxFri,
    			CheckboxSat,CheckboxCC,CheckboxNoEx,CheckboxWithLabs};
    	if(BtnSelectAll.getText().contentEquals("Select All"))
    		{BtnSelectAll.setText("De-select All");
    		 for (int i=0; i<ListAll.length;i++) {
    			ListAll[i].selectedProperty().set(true);
    		 }
    		}
    	
    	else if(BtnSelectAll.getText().contentEquals("De-select All"))
    		{BtnSelectAll.setText("Select All");
    		 for (int i=0; i<ListAll.length;i++) {
			 ListAll[i].selectedProperty().set(false);
		     }
    		}
    	refreshCheckBox();
    }
    
   /**
    * 
    */
    public void refreshCheckBox() {
    	Vector<boolean[]> flags=new Vector<boolean[]>();
    	// For every section in the courses list, create a boolean array
    	for (Course item: courses) {
    		boolean [] innerflags= {false,false,false,false,false,false,false,false,false,false,false,true};
    		if(item.is4YCC()) innerflags[8]=true;
    		if(!item.hasExclusion()) innerflags[9]=true;
    		if(item.hasLabOrTuto()) innerflags[10]=true;
    		for (int i=0;i<item.getNumSections();i++) {

    			for(int j=0;j<item.getSection(i).getNumSlots();j++) {
    				if(item.getSection(i).getSlot(j).isAM()) innerflags[0]=true;
    				if(item.getSection(i).getSlot(j).isPM()) innerflags[1]=true;
    				for (int temp=0;temp<6;temp++)
    				{
    					if(item.getSection(i).getSlot(j).isOn(temp)) innerflags[2+temp]=true;
    				}
    				
    			}
    		}
    		flags.add(innerflags);
    	}
    		
    	//check whether the section satisfy the CheckBox, store boolean value in item[11]
    	final CheckBox[] ListAll = {CheckboxAM, CheckboxPM,CheckboxMon,CheckboxTue,CheckboxWed,CheckboxThu,CheckboxFri,
    			CheckboxSat,CheckboxCC,CheckboxNoEx,CheckboxWithLabs};
    	for (int i=0;i<ListAll.length;i++) {
    		if(ListAll[i].selectedProperty().get()) {
    			for (boolean[] item: flags) {
    				if(item[i]==false) item[11]=false; 
    			}
    		}
    	}
    	
    	//store the filtered Sections in the array filteredCourses
    	filteredCourses.clear();
    	for(int i=0; i<courses.size();i++){
    			if (flags.get(i)[11]) filteredCourses.add(courses.get(i));
    	}
    	
    	//print all the filtered Sections
    	textAreaConsole.clear();
    	for (Course c : filteredCourses) {
    		String newline = c.getTitle() + "\n";
    		int counter = 0;
    		for (int i = 0; i < c.getNumSections(); i++) {
    			Section s = c.getSection(i);
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot t = s.getSlot(j);
    				newline += "Slot" + " " + (counter++) + ": " + s.getSectionTitle() + "\t" + t + "\n";
    			}
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    }
    
    public void createTable() {
    	filteredSections.clear();
    	for(Course item: filteredCourses) {
    		for(int p=0;p<item.getNumSections();p++) {
    			filteredSections.add(item.getSection(p));
    		}
    	}
    	
    	SectionTable.getItems().clear();
    	
    	for (Section item:filteredSections) {
    		
    		CourseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("CourseCode"));
    		SectionColumn.setCellValueFactory(new PropertyValueFactory<>("SimplifiedTitle"));
        	CourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
        	InstructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructorList"));
        		
        	EnrollColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Section, CheckBox>, ObservableValue<CheckBox>>() {

                @Override
                public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Section, CheckBox> arg0) {
                    Section se = arg0.getValue();
                  
                    CheckBox checkBox = new CheckBox();

                    checkBox.selectedProperty().setValue(se.getEnrolled());

                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean new_val) {

                            se.setEnrolled(new_val);
                            if(new_val) enrolledSections.add(se);
                            else if(!new_val && enrolledSections.contains(se)) enrolledSections.remove(se);
                            textAreaConsole.clear();
                        	textAreaConsole.setText("The following sections are enrolled:"+'\n');
                        	for (Section item: enrolledSections) {
                        		String newline = item.getCourseCode();
                        		newline = newline + '\t'+ item.getCourseName() + '\t' +item.getSectionTitle();
                        		
                        		textAreaConsole.setText(textAreaConsole.getText()+'\n'+ newline);
                        	}
                        }
                    });
                    return new SimpleObjectProperty<CheckBox>(checkBox);
                }
            });
        	SectionTable.getItems().add(item);
    	}
    }
}

