package to.us.kevinraneri.pathtracker.simulator.path;

public class PathBuilder {

    private Path path = new Path();
    private Point previousPoint;

    public PathBuilder(Point origin) {
        previousPoint = origin;
    }

    public PathBuilder lineTo(Point point) {
        path.addSegment(new Segment(previousPoint, point));
        previousPoint = point;
        return this;
    }

    public PathBuilder lineTo(double x, double y) {
        return lineTo(new Point(x, y));
    }

    public Path build() {
        return path;
    }

}
