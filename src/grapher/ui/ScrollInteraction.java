package grapher.ui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.ScrollEvent;

public class ScrollInteraction implements EventHandler<ScrollEvent>{
	public GrapherCanvas canvas;
	Point2D p;
	static final double pzoom = 5;
	
	public ScrollInteraction(GrapherCanvas grapherCanvas) {
		canvas = grapherCanvas;
	}
	@Override
	public void handle(ScrollEvent event) {
		p = new Point2D(event.getX(),event.getY());
		if(event.getDeltaY() > 0 ) {
			canvas.zoom(p,pzoom);
		} else {
			canvas.zoom(p, -pzoom);
		}
	}
} 
