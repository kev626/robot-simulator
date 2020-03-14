package to.us.kevinraneri.pathtracker.simulator.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Fills {

    public static final ShapeFill RED = new ShapeFill() {
        @Override
        public Color colorAt(Shape shape, float x, float y) {
            return Color.red;
        }

        @Override
        public Vector2f getOffsetAt(Shape shape, float x, float y) {
            return new Vector2f();
        }
    };

    public static final ShapeFill GREEN = new ShapeFill() {
        @Override
        public Color colorAt(Shape shape, float x, float y) {
            return Color.green;
        }

        @Override
        public Vector2f getOffsetAt(Shape shape, float x, float y) {
            return new Vector2f();
        }
    };

    public static final ShapeFill BLUE = new ShapeFill() {
        @Override
        public Color colorAt(Shape shape, float x, float y) {
            return Color.blue;
        }

        @Override
        public Vector2f getOffsetAt(Shape shape, float x, float y) {
            return new Vector2f();
        }
    };

}
