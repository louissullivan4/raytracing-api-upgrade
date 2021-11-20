package raytracer;

import java.awt.Color;

/**
 * The type Color util.
 */
public class ColorUtil {
    /**
     * Blend color.
     *
     * @param base  the base
     * @param mixin the mixin
     * @return the color
     */
    public static Color blend(Color base, Color mixin) {
		float[] baseC = base.getRGBColorComponents(null);
		float[] mixinC = mixin.getRGBColorComponents(null);

		float red = clamp(baseC[0] + mixinC[0]);
		float green = clamp(baseC[1] + mixinC[1]);
		float blue = clamp(baseC[2] + mixinC[2]);
		return new Color(red, green, blue);
	}

    /**
     * Clamp float.
     *
     * @param x the x
     * @return the float
     */
    public static float clamp(float x) {
		return Math.max(0.0f, Math.min(1.0f, x));
	}

    /**
     * Intensify color.
     *
     * @param color     the color
     * @param intensity the intensity
     * @return the color
     */
    public static Color intensify(Color color, float intensity) {
		// TODO: clamp should not be necessary here
		return intensify(color, new Color(clamp(intensity), clamp(intensity), clamp(intensity)));
	}

    /**
     * Intensify color.
     *
     * @param color     the color
     * @param intensity the intensity
     * @return the color
     */
    public static Color intensify(Color color, Color intensity) {
		float[] c = color.getRGBColorComponents(null);
		float[] i = intensity.getRGBColorComponents(null);

		return new Color(c[0] * i[0], c[1] * i[1], c[2] * i[2]);
	}

    /**
     * Average color.
     *
     * @param colors the colors
     * @return the color
     */
    public static Color average(Color... colors) {
		float[] rgb = new float[3];
		float mult = 1.0f / colors.length;
		for(Color c: colors) {
			float[] cc = c.getRGBColorComponents(null);
			rgb[0] += cc[0] * mult;
			rgb[1] += cc[1] * mult;
			rgb[2] += cc[2] * mult;
		}

		return new Color(rgb[0], rgb[1], rgb[2]);
	}
}