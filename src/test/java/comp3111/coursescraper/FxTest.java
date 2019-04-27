/**
 * 
 * You might want to uncomment the following code to learn testFX. Sorry, no tutorial session on this.
 * 
 */
package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;


public class FxTest extends ApplicationTest {

	private Scene s;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}

	
	@Test
	public void testSFQButton() {
		clickOn("#tabSfq");
		clickOn("#buttonSfqEnrollCourse");
		Button b = (Button)s.lookup("#buttonSfqEnrollCourse");
		sleep(1000);
		assertTrue(b.isDisabled());
	}
	
	@Test
	public void testSearch() {
		clickOn("#tabMain");
		clickOn("#buttonSearch");
	}
	
	@Test
	public void testTimetable() {
		clickOn("#tabTimetable");
	}
	
	@Test
	public void testAllSubjectSearch() {
		clickOn("#tabAllSubject");
		clickOn("#AllSubjectSearch");
		clickOn("#AllSubjectSearch");
	}
}
