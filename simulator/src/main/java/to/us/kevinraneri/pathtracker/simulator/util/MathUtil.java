package to.us.kevinraneri.pathtracker.simulator.util;

import org.newdawn.slick.geom.Vector2f;
import to.us.kevinraneri.pathtracker.simulator.path.Point;
import to.us.kevinraneri.pathtracker.simulator.path.Segment;

public class MathUtil {

    public static final double TWO_PI = 2 * Math.PI;

    public static double wrapAngle(double angle) {
        while (angle < 0) {
            angle += TWO_PI;
        }

        while (angle >= TWO_PI) {
            angle -= TWO_PI;
        }

        return angle;
    }

    public static double lerp(double a, double b, double x) {
        return a + x * (b - a);
    }

    public static boolean sameSign(float a, float b) {
        return (a * b) >= 0;
    }

    public static double distBetweenPoints(Point a, Point b) {
        return Math.hypot(a.getX() - b.getX(), a.getY() - b.getY());
    }

    public static Vector2f toScreenCoord(Vector2f worldPoint) {
        double screenX = lerp(0, 800, worldPoint.x / 132);
        double screenY = lerp(800, 0, worldPoint.y / 132);
        return new Vector2f((float) screenX, (float) screenY);
    }

    public static Point toScreenCoord(Point worldPoint) {
        double screenX = lerp(0, 800, worldPoint.getX() / 132);
        double screenY = lerp(800, 0, worldPoint.getY() / 132);
        return new Point((int) screenX, (int) screenY);
    }

    public static int toScreenDimension(double a) {
        return (int) lerp(0, 800, a/132);
    }

    public static Point[] circularIntersection(Segment segment, Point center, double radius) {
        Point to = segment.getTo();
        Point from = segment.getFrom();
        double dx = segment.getTo().getX() - segment.getFrom().getX();
        double dy = segment.getTo().getY() - segment.getFrom().getY();
        double dr = Math.hypot(dx, dy);
        double D = (segment.getFrom().getX() - center.getX()) * (segment.getTo().getY() - center.getY()) -
                (segment.getTo().getX() - center.getX()) * (segment.getFrom().getY() - center.getY());

        double discrim = radius * radius * dr * dr - D * D;
        if (discrim <= 0) {
            return new Point[0];
        }

        double x1 = (D*dy+Math.signum(dy)*dx*Math.sqrt(discrim))/(dr*dr);
        double y1 = (-D*dx+Math.abs(dy)*Math.sqrt(discrim))/(dr*dr);

        double x2 = (D*dy-Math.signum(dy)*dx*Math.sqrt(discrim))/(dr*dr);
        double y2 = (-D*dx-Math.abs(dy)*Math.sqrt(discrim))/(dr*dr);

        return new Point[] {
                new Point(x1 + center.getX(), y1 + center.getY()),
                new Point(x2 + center.getX(), y2 + center.getY())
        };

    }

}
