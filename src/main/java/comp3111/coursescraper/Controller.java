package comp3111.coursescraper;


import java.awt.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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

import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
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
    
    private Scraper scraper = new Scraper();
    
    @FXML
    void allSubjectSearch() {
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }
    
    private static List<Course> courses = new Vector<Course>();
    private static List<Section> enrolledSections = new Vector<Section>();

    @FXML
    void search() {
    	courses = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	
    	// Handle 404
    	if (courses == null) {
    		textAreaConsole.setText("Oops! 404 Not Found! Please check your input.\n");
    		return;
    	}
    	
    	// count and display # of courses and sections
    	int number_of_sections = 0,
    		number_of_courses  = 0;
    	for (Course c : courses) {
    		number_of_sections += c.getNumSections();
    		number_of_courses  += (c.hasValidSection()) ? 1 : 0;
    	}
    	textAreaConsole.setText("Total Number of difference sections in this search: " + number_of_courses +
    			                "\nTotal Number of Course in this search: " + number_of_sections + "\n");
    	
    	// find and display a list of instrutors who have teaching assignment but not at Tu 3:10PM
    	List<String> instructors = new Vector<String>();
    	for (Course c : courses) {
    		for (int i = 0; i < c.getNumSections(); ++i) {
    			Section s = c.getSection(i);
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot slot = s.getSlot(j);
    				if ((!slot.isOn(1) || !slot.include(15, 10)) && !instructors.contains(slot.getInstName()))
    					instructors.add(slot.getInstName());
    			}
    		}
    	}
    	instructors.sort(null);
    	String names = "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm: ";
    	for (String name : instructors) {
    		names += (name + ", ");
    	}
    	names += "\n";
    	textAreaConsole.setText(textAreaConsole.getText() + names);
    	
    	for (Course c : courses) {
    		String newline = c.getTitle() + "\n";
    		int counter = 1;
    		for (int i = 0; i < c.getNumSections(); i++) {
    			Section s = c.getSection(i);
    			for (int j = 0; j < s.getNumSlots(); ++j) {
    				Slot t = s.getSlot(j);
    				newline += "Slot" + i + ": " + s.getSectionTitle() + "\t" + t + "\n";
    			}
    		}
    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
    	}
    	
    	//Add a random block on Saturday
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	Label randomLabel = new Label("COMP1022\nL1");
    	Random r = new Random();
    	double start = (r.nextInt(10) + 1) * 20 + 40;

    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(600.0);
    	randomLabel.setLayoutY(start);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel); 	
    }
    
    // update timetable accordingly after enrolled course list is updated
    public void updateTimeTable() {
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	ap.getChildren().remove(0, enrolledSections.size());
    	Label[] labels = new Label[enrolledSections.size()];
    	for (int i = 0; i < enrolledSections.size(); ++i) {
    		Section s = enrolledSections.get(i);
    		String labelName = s.getParent().getSimplifiedTitle() + "\n" + s.getSimplifiedTitle();
    		for (int j = 0; j < s.getNumSlots(); ++j) {
    			Slot slot = s.getSlot(j);
    			Label label = new Label(labelName);
    			
    		}
    	}
    }
    
    
    //Task2
    @FXML
    void SelectDeselectAll(ActionEvent event) {

    }
}
