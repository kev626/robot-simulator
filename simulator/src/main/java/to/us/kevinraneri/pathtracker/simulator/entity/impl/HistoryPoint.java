package to.us.kevinraneri.pathtracker.simulator.entity.impl;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import to.us.kevinraneri.pathtracker.simulator.entity.Entity;

public class HistoryPoint extends Entity {

    public HistoryPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(Graphics g) {
        Shape circle = new Circle(this.x, this.y, 5, 10);
        g.setColor(Color.yellow);
        g.fill(circle);
    }

}
