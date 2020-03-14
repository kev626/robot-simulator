package to.us.kevinraneri.pathtracker.simulator.controller;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Vector2f;
import to.us.kevinraneri.pathtracker.simulator.Robot;
import to.us.kevinraneri.pathtracker.simulator.path.Path;
import to.us.kevinraneri.pathtracker.simulator.path.PathBuilder;
import to.us.kevinraneri.pathtracker.simulator.path.Point;
import to.us.kevinraneri.pathtracker.simulator.path.Segment;
import to.us.kevinraneri.pathtracker.simulator.util.Fills;
import to.us.kevinraneri.pathtracker.simulator.util.GraphicsUtil;
import to.us.kevinraneri.pathtracker.simulator.util.MathUtil;

import java.util.stream.Stream;

public class Controller {

    private Robot robot;
    private ControllerResponse lastResponse = new ControllerResponse(0, 0, 0);;

    private static final float TRACKING_RADIUS = 16;
    private static final float ADVANCE_RADIUS = 13;

    private Point setPoint = new Point(0, 0);
    private Point[] intersections = new Point[0];

    private Path path;
    private int currentSegment = 0;

    public Controller(Robot robot) {
        this.robot = robot;
        path = new PathBuilder(new Point(18, 18))
                .lineTo(50, 50)
                .lineTo(51, 100)
                .lineTo(115, 115)
                .lineTo(100, 10)
                .lineTo(18, 18)
                .build();
    }

    public ControllerResponse update(double deltaTime) {
        Point currentLocation = new Point(robot.getX(), robot.getY());
        Segment trackingSegment;

        // Check to advance any segments that we can.
        while (true) {
            if (currentSegment >= path.getSegmentCount()) {
                currentSegment = 0;
            }
            trackingSegment = path.getSegmentAt(currentSegment);
            if (MathUtil.distBetweenPoints(trackingSegment.getTo(), currentLocation) <= ADVANCE_RADIUS) {
                currentSegment++;
                System.out.println("Advancing to segment " + currentSegment);
            } else {
                break;
            }
        }

        // Now we have advanced as many segments as possible. Calculate a setpoint.
        intersections = MathUtil.circularIntersection(trackingSegment, currentLocation, TRACKING_RADIUS);

        if (intersections.length == 0) {

        } else {
            double firstDist = MathUtil.distBetweenPoints(intersections[0], trackingSegment.getTo());
            double secondDist = MathUtil.distBetweenPoints(intersections[1], trackingSegment.getTo());
            if (firstDist < secondDist) {
                setPoint = intersections[0];
            } else {
                setPoint = intersections[1];
            }
        }

        // Now track to the setpoint
        Point spToOrigin = new Point(setPoint.getX() - currentLocation.getX(),
                setPoint.getY() - currentLocation.getY());
        Vector2f vec = new Vector2f((float) spToOrigin.getX(), (float) spToOrigin.getY());

        double motAngle = Math.toRadians(vec.getTheta() - robot.getAngle());

        // Prioritize facing towards the setpoint.
        double angleRate = 2 * Math.toRadians(vec.getTheta() - robot.getAngle());

        lastResponse = new ControllerResponse(angleRate, motAngle, .4);
        return lastResponse;
    }

    public void drawDebug(Graphics g) {
        path.getSegments().forEach(segment -> GraphicsUtil.drawSegment(g, segment, Color.magenta));

        Circle trackingCircle = new Circle(robot.getScreenX(), robot.getScreenY(), MathUtil.toScreenDimension(TRACKING_RADIUS));
        g.draw(trackingCircle, Fills.RED);

        Circle advanceCircle = new Circle(robot.getScreenX(), robot.getScreenY(), MathUtil.toScreenDimension(ADVANCE_RADIUS));
        g.draw(advanceCircle, Fills.GREEN);

        //Stream.of(intersections).forEach(p -> GraphicsUtil.drawPoint(g, p, Color.cyan));

        GraphicsUtil.drawPoint(g, setPoint, Color.orange);

        Vector2f vec = new Vector2f(-Math.toDegrees(lastResponse.getMotAngle()));
        Vector2f origin = new Vector2f(robot.getScreenX(), robot.getScreenY());
        vec.add(-robot.getAngle());
        vec.scale(200);
        vec.add(origin);
        Line dir = new Line(origin, vec);
        g.setColor(Color.red);
        g.draw(dir);

    }
}
