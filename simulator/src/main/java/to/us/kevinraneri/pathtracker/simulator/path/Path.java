package to.us.kevinraneri.pathtracker.simulator.path;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class Path {

    List<Segment> segments = new ArrayList<>();

    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    public Segment getSegmentAt(int index) {
        return segments.get(index);
    }

    public int getSegmentCount() {
        return segments.size();
    }
}
