package snow;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main launcher class for the workbench.
 */
public class SnowLauncher extends Application {

	private static final String PRIMARY_ONLY = "primary_only";

   @Override
   public void start(Stage unused) throws Exception {
		  
	  Parameters args = getParameters();
	  if (args.getUnnamed().contains(PRIMARY_ONLY)) {
		  createSnowForScreen(Screen.getPrimary());
	  } else {
	      for (Screen screen : Screen.getScreens()) {
	    	  createSnowForScreen(screen);
	      }
	  }
	  createCloseButtonForStage();
   }

	private void createSnowForScreen(Screen screen) {
		Stage stage = new Stage();

		Pane pane = new Pane();
		Canvas canvas = new SnowPanel().getCanvas();
		canvas.widthProperty().bind(pane.widthProperty());
		canvas.heightProperty().bind(pane.heightProperty());
		pane.getChildren().add(canvas);
		Scene scene = new Scene(pane);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);

		Rectangle2D bounds = screen.getBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());

		stage.setAlwaysOnTop(true);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	private void createCloseButtonForStage() {
		Stage stage = new Stage();

		Pane pane = new Pane();
		BorderPane closeButton = new CloseButtonForStage().getNode();
		pane.getChildren().add(closeButton);
		
		Scene scene = new Scene(pane);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);

		Rectangle2D bounds = Screen.getPrimary().getBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());

		stage.setAlwaysOnTop(true);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
