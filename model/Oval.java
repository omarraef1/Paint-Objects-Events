
// Omar R. Gebril SID 23323978 	CSC210

package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval extends PaintObject {

	public Oval(Color color, Point from, Point to) {
		super(color, from, to);
	}

	@Override
	public void draw(GraphicsContext g) {
		double x1 = from.getX();
		double y1 = from.getY();
		double x2 = to.getX();
		double y2 = to.getY();
		if (y2 < y1) {
			y2 = from.getY();
			y1 = to.getY();
		}
		if (x2 < x1) {
			x2 = from.getX();
			x1 = to.getX();
		}
		g.setFill(color);
		g.fillOval(x1, y1, x2 - x1, y2 - y1);
	}

}
