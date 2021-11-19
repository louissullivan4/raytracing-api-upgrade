package raytracer.pigments;

import raytracer.Point;

import java.awt.Color;

/**
 * The type Solid pigment.
 */
public class SolidPigment implements Pigment {
	/**
	 * The Color.
	 */
	public Color color;

	/**
	 * Instantiates a new Solid pigment.
	 *
	 * @param color the color
	 */
	public SolidPigment(Color color) {
		this.color = color;
	}

	public Color getColor(Point p) {
		return color;
	}

	public String toString() {
		return "solid";
	}
}
