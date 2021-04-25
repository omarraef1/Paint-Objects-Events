
// Omar R. Gebril SID 23323978 	CSC210

package controller_view;

/**
 * A GUI for NetPaint that has all PaintObjects drawn on it. This file also
 * represents the controller as it controls how paint objects are drawn.
 * 
 * @author Rick Mercer and Omar R. Gebril
 */

import java.awt.Point;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Line;
import model.Oval;
import model.Picture;
import model.Rectangle;
import model.PaintObject;

public class PaintObjectMain extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private ColorPicker colorPicker;
	private Canvas canvas;
	private GraphicsContext gc;
	private Button Line = new Button("Line");
	private Label empty = new Label("  ");
	private Button Rectangle = new Button("Rectangle");
	private Button Oval = new Button("Oval");
	private Button Picture = new Button("Picture");
	private Point aPoint;
	private Point bPoint;
	private String decision = "";
	private int count = 0;
	private ArrayList<PaintObject> allPaintObjects;
	private int ghostCount = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane all = new BorderPane();
		GridPane windowGrid = new GridPane();

		allPaintObjects = new ArrayList<>();

		colorPicker = new ColorPicker();
		colorPicker.setValue(Color.BLUE);
		colorPicker.setOnAction(event -> {
			Color color = colorPicker.getValue();
		});

		// Layout GUI
		all.setBottom(windowGrid);

		windowGrid.setHgap(2);
		windowGrid.setVgap(2);

		windowGrid.add(colorPicker, 70, 0);
		windowGrid.add(empty, 50, 1);
		windowGrid.add(Line, 30, 0);
		windowGrid.add(Rectangle, 40, 0);
		windowGrid.add(Oval, 50, 0);
		windowGrid.add(Picture, 60, 0);

		canvas = new Canvas(560, 460);

		addHandlersTo(canvas);

		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 560, 460);
		all.setCenter(canvas);

		Scene scene = new Scene(all, 560, 540);
		primaryStage.setScene(scene);
		primaryStage.show();

		EventHandler<ActionEvent> buttonHandler = new ButtonHandler();
		Line.setOnAction(buttonHandler);
		Rectangle.setOnAction(buttonHandler);
		Oval.setOnAction(buttonHandler);
		Picture.setOnAction(buttonHandler);

	}

	private void addHandlersTo(Canvas canvas) {
		canvas.setOnMouseClicked(event -> {
			// Using java.awt.Point because we used it in NetPaint 1
			count++;
			ghostCount++;
			if (count < 2 && count > 0) {
				aPoint = new Point((int) event.getX(), (int) event.getY());
			} else if (count == 2) {
				bPoint = new Point((int) event.getX(), (int) event.getY());
				count = 0;
				ghostCount = 0;
				if (count == 0) {
					if (decision.equals("Line")) {
						PaintObject a = new Line(colorPicker.getValue(), aPoint, bPoint);
						allPaintObjects.add(a);
					} else if (decision.equals("Rectangle")) {
						PaintObject b = new Rectangle(colorPicker.getValue(), aPoint, bPoint);
						allPaintObjects.add(b);
					} else if (decision.equals("Oval")) {
						PaintObject c = new Oval(colorPicker.getValue(), aPoint, bPoint);
						allPaintObjects.add(c);
					} else if (decision.equals("Picture")) {
						PaintObject d = new Picture(aPoint, bPoint, "doge.jpeg");
						allPaintObjects.add(d);
					}
				}
			}
			gc.clearRect(0, 0, 560, 460);
			gc = canvas.getGraphicsContext2D();
			gc.setFill(Color.WHITE);
			gc.fillRect(0, 0, 560, 460);
			for (int i = 0; i < allPaintObjects.size(); i++) {
				allPaintObjects.get(i).draw(gc);
			}
		});

		canvas.setOnMouseExited(event -> {
			System.out.println("Out of bounds, get in there!");
		});

		canvas.setOnMouseMoved(event -> {

			if (ghostCount < 2 && ghostCount > 0) {
				if (aPoint != null) {
					Point end = new Point((int) event.getX(), (int) event.getY());
					gc.clearRect(0, 0, 560, 460);
					gc = canvas.getGraphicsContext2D();
					gc.setFill(Color.WHITE);
					gc.fillRect(0, 0, 560, 460);
					if (decision.equals("Line")) {
						PaintObject a = new Line(colorPicker.getValue(), aPoint, end);
						allPaintObjects.add(a);
					} else if (decision.equals("Rectangle")) {
						PaintObject b = new Rectangle(colorPicker.getValue(), aPoint, end);
						allPaintObjects.add(b);
					} else if (decision.equals("Oval")) {
						PaintObject c = new Oval(colorPicker.getValue(), aPoint, end);
						allPaintObjects.add(c);
					} else if (decision.equals("Picture")) {
						PaintObject d = new Picture(aPoint, end, "doge.jpeg");
						allPaintObjects.add(d);
					}
					for (int i = 0; i < allPaintObjects.size(); i++) {
						allPaintObjects.get(i).draw(gc);
					}

					if (!allPaintObjects.isEmpty()) {
						allPaintObjects.remove(allPaintObjects.size() - 1);
					}

					System.out.println("New Position: " + end);
				}
			}
		});

	}

	private class ButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			if (event.getSource().equals(Line)) {
				decision = "Line";
			} else if (event.getSource().equals(Oval)) {
				decision = "Oval";
			} else if (event.getSource().equals(Picture)) {
				decision = "Picture";
			} else if (event.getSource().equals(Rectangle)) {
				decision = "Rectangle";
			}
		}
	}

}