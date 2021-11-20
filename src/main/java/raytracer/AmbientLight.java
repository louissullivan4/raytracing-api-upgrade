package raytracer;

import java.awt.Color;

/**
 * The type Ambient light.
 */
public class AmbientLight extends Light {
    /**
     * Instantiates a new Ambient light.
     *
     * @param location the location
     * @param color    the color
     * @param a        the a
     * @param b        the b
     * @param c        the c
     */
    public AmbientLight(Point location, Color color, float a, float b, float c) {
		super(location, color, a, b, c);
	}

	@Override
	public Color getColor(RayHit hit, Ray lightRay) {
		return ColorUtil.intensify(color, hit.shape.finish.amb);
	}
}
