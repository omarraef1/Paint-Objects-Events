
// Omar R. Gebril SID 23323978 	CSC210

package model;

import java.awt.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Picture extends PaintObject {

	public Picture(Point from, Point to, String string) {
		super(from, to, string);
	}

	// Image bingo = new Image(image, false);

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

		g.drawImage(image, x1, y1, x2 - x1, y2 - y1);
	}

}
