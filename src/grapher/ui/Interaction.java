package grapher.ui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Interaction implements EventHandler<MouseEvent> {

	public GrapherCanvas canvas;
	enum State {IDLE,WAITING_PRIMARY,WAITING_SECONDARY,DRAGGING_PRIMARY,DRAGGING_SECONDARY};
	Point2D p;
	State state = State.IDLE;
	static final int D_DRAG = 5;
	static final double pzoom = 5;

	public Interaction(GrapherCanvas grapherCanvas) {
		this.canvas = grapherCanvas;
	}

	@Override
	public void handle(MouseEvent event) {
		MouseButton button = event.getButton();
		switch(state) {
		case IDLE:
			switch (event.getEventType().getName()) {
			case "MOUSE_PRESSED":
				p = new Point2D(event.getX(),event.getY());
				if(button.equals(MouseButton.PRIMARY)) {
					state = State.WAITING_PRIMARY;
				}else if(button.equals(MouseButton.SECONDARY)) {
					state = State.WAITING_SECONDARY;
				}
				break;
			default:
				break;
			}
			break;
		case WAITING_PRIMARY:
			switch(event.getEventType().getName()) {
			case "MOUSE_RELEASED":
				canvas.zoom(p,pzoom);
				state = State.IDLE;
				break;
			case "MOUSE_DRAGGED":
				if(p.distance(new Point2D(event.getX(),event.getY()))>=D_DRAG) {
					canvas.translate(event.getX()-p.getX(),event.getY()-p.getY());
					p=new Point2D(event.getX(),event.getY());
					state = State.DRAGGING_PRIMARY;
				}
				break;
			default:
				break;
			}
			break;
		case WAITING_SECONDARY:
			switch(event.getEventType().getName()) {
			case "MOUSE_RELEASED" :
				canvas.zoom(p,-pzoom);
				state = State.IDLE;
				break;
			case "MOUSE_DRAGGED" : 
				if(p.distance(new Point2D(event.getX(),event.getY()))>=D_DRAG) {
					canvas.drawRect(event.getX(),event.getY(), p);
					state = State.DRAGGING_SECONDARY;
				}
				break;
			default:
				break;
			}
			break;
		case DRAGGING_PRIMARY:
			switch(event.getEventType().getName()) {
			case "MOUSE_RELEASED":
				state = State.IDLE;
				break;
			case "MOUSE_DRAGGED":
				if(p.distance(new Point2D(event.getX(),event.getY()))>=D_DRAG) {
					canvas.translate(event.getX()-p.getX(),event.getY()-p.getY());
					p=new Point2D(event.getX(),event.getY());
					state = State.DRAGGING_PRIMARY;
				}
				break;	
			default:
				break;
			}
			break;
		case DRAGGING_SECONDARY:
			switch(event.getEventType().getName()) {
			case "MOUSE_RELEASED":
				if(p.distance(new Point2D(event.getX(),event.getY()))>=D_DRAG) {
					canvas.drawRect(event.getX(), event.getY(), p);
					canvas.zoom(p, new Point2D(event.getX(),event.getY()));
					p=new Point2D(event.getX(),event.getY());
					state = State.IDLE;
				}
				break;
			case "MOUSE_DRAGGED":
				if(p.distance(new Point2D(event.getX(),event.getY()))>=D_DRAG) {
					canvas.drawRect(event.getX(), event.getY(), p);
					state = State.DRAGGING_SECONDARY;
				}
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		
	}

}
