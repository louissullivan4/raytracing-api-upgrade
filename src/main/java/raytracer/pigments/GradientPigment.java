package raytracer.pigments;

import raytracer.*;

import java.awt.Color;

/**
 * The type Gradient pigment.
 */
public class GradientPigment implements Pigment {
	private final Point origin;
	private final Vector v;
	private final Color start;
	private final Color end;

	/**
	 * Instantiates a new Gradient pigment.
	 *
	 * @param origin the origin
	 * @param vector the vector
	 * @param start  the start
	 * @param end    the end
	 */
	public GradientPigment(Point origin, Vector vector, Color start, Color end) {
		this.origin = origin;
		this.v = vector;
		this.start = start;
		this.end = end;

		Log.warn("Gradient Pigment is not implemented. Start color will be used instead.");
	}

	public Color getColor(Point p) {
		if(true) return start;

		double d = Math.abs(new Vector(origin, p).dot(v)) / v.magnitude();
		double percent = (d / v.magnitude());

//		Log.info("percent: " + d + " / " + v.magnitude() + " = " + percent);

//		percent = Math.abs(percent);
//		while(percent < 0) percent = 0.0 - percent;

		while(percent >= 1) percent = 1.0 - (percent - 1.0);

		return ColorUtil.blend(ColorUtil.intensify(start, (float)percent), ColorUtil.intensify(end, 1.0f - (float)percent));
	}

	public String toString() {
		return "gradiented";
	}
}
