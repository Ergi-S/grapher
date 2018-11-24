package grapher.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public void start(Stage stage) {
		GrapherCanvas gc = new GrapherCanvas(getParameters());
		FunctionPane fp = new FunctionPane(getParameters(), gc);
		SplitPane root = new SplitPane(fp.getLeft(), gc);
		root.setDividerPositions(0.4);
		
		MenuBar menubar = new MenuBar(fp.getExpression());
		BorderPane total = new BorderPane(root);
		total.setTop(menubar);
		
		stage.setTitle("grapher");
		stage.setScene(new Scene(total));
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}