package grapher.ui;

import java.util.Optional;

import grapher.fc.Function;
import grapher.fc.FunctionFactory;
import javafx.application.Application.Parameters;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;

public class FunctionPane extends Pane {

	BorderPane m_left;
	ToolBar m_tb;
	GrapherCanvas m_canvas;
	Menu m_exp;

	public FunctionPane(Parameters params, GrapherCanvas canvas) {
		m_canvas = canvas;

		ListView<Function> lv = new ListView<Function>();
		for (Function f : m_canvas.functions) {
			lv.getItems().add(f);
		}

		lv.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				m_canvas.bold(lv.getSelectionModel().getSelectedItem());
			}
		});

		toolbarConstruct(lv.getItems());
		expMenuConstruct(lv.getItems());

		m_left = new BorderPane(lv);
		m_left.setBottom(m_tb);

		lv.setEditable(true);
		lv.setCellFactory(TextFieldListCell.forListView(new StringConverter<Function>() {

			@Override
			public String toString(Function f) {
				return f.toString();
			}

			@Override
			public Function fromString(String fname) {
				return FunctionFactory.createFunction(fname);
			}
		}));

		lv.setOnEditCommit(new EventHandler<ListView.EditEvent<Function>>() {
			@Override
			public void handle(EditEvent<Function> event) {
				int idx = event.getIndex();
				lv.getItems().set(idx, event.getNewValue());
				m_canvas.removeFunction(idx);
				m_canvas.addFunction(idx, event.getNewValue());
			}
		});
	}

	public BorderPane getLeft() {
		return m_left;
	}

	public Menu getExpression() {
		return m_exp;
	}

	private void toolbarConstruct(ObservableList<Function> names) {
		Button plus = new Button("+");
		plus.setPrefSize(30, 30);
		plus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				plusDialog(names);
			}
		});

		Button minus = new Button("-");
		minus.setPrefSize(30, 30);
		minus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				minusDialog(names);
			}
		});
		m_tb = new ToolBar(plus, minus);
	}

	private void expMenuConstruct(ObservableList<Function> names) {
		MenuItem ajout = new MenuItem("Ajout");
		ajout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				plusDialog(names);
			}
		});
		ajout.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

		MenuItem suppression = new MenuItem("Suppression");
		suppression.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				minusDialog(names);
			}
		});
		suppression.setAccelerator(new KeyCodeCombination(KeyCode.BACK_SPACE, KeyCombination.CONTROL_DOWN));

		m_exp = new Menu("Expression");
		m_exp.getItems().addAll(ajout, suppression);
	}

	private void plusDialog(ObservableList<Function> names) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("plus");
		dialog.setContentText("func : ");
		Optional<String> f = dialog.showAndWait();
		if (f.isPresent()) {
			if (f.get().equals("")) {
				Alert alert = new Alert(AlertType.INFORMATION, "Argument Vide");
				alert.showAndWait();
				return;
			} else
				try {
					m_canvas.addFunction(f.get());
					names.add(m_canvas.functions.get(m_canvas.functions.size() - 1));
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR, "Fonction Invalide");
					alert.showAndWait();
					return;
				}
		}
	}

	private void minusDialog(ObservableList<Function> names) {
		int index = names.size() - 1;
		if (index < 0) {
			Alert alert = new Alert(AlertType.INFORMATION, "Pas de fonctions a enlever");
			alert.showAndWait();
			return;
		}
		names.remove(index);
		m_canvas.removeFunction(index);
	}
}
