package to.us.kevinraneri.pathtracker.simulator.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import to.us.kevinraneri.pathtracker.simulator.path.Point;
import to.us.kevinraneri.pathtracker.simulator.path.Segment;

public class GraphicsUtil {

    public static void drawPoint(Graphics g, Point p, Color color) {
        Point screenCoord = MathUtil.toScreenCoord(p);
        Circle circle = new Circle((int) screenCoord.getX(), (int) screenCoord.getY(), 3, 8);
        g.setColor(color);
        g.fill(circle);
    }

    public static void drawSegment(Graphics g, Segment seg, Color color) {
        Point a = MathUtil.toScreenCoord(seg.getFrom());
        Point b = MathUtil.toScreenCoord(seg.getTo());
        Line line = new Line((float) a.getX(), (float) a.getY(), (float) b.getX(), (float) b.getY());
        g.setColor(color);
        g.draw(line);
    }

}
