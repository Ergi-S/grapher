package grapher.ui;

import grapher.fc.Function;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class Funs {

	Function fname;
	ColorPicker fcol;
	GrapherCanvas m_gc;

	public Funs(Function fnm, GrapherCanvas gc) {
		fname = fnm;
		m_gc = gc;
		fcol = new ColorPicker(Color.BLACK);

		fcol.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				m_gc.redraw();
			}
		});
	}

	public Function getFname() {
		return fname;
	}
	
	public void setFname (Function f) {
		fname = f;
	}

	public ColorPicker getFcol() {
		return fcol;
	}
}