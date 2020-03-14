package to.us.kevinraneri.pathtracker.simulator.entity.impl;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import to.us.kevinraneri.pathtracker.simulator.entity.Entity;

public class RobotEntity extends Entity {

    @Override
    public void render(Graphics g) {
        Shape box = new Rectangle(0, 0, 109, 109);
        box = box.transform(Transform.createRotateTransform((float)angle, 0, 0));
        Shape smallerBox = box.transform(Transform.createScaleTransform(0.95f, 0.95f));
        box.setCenterX(x); box.setCenterY(y);
        smallerBox.setCenterX(x); smallerBox.setCenterY(y);
        g.setColor(Color.blue);
        g.fill(box);
        g.setColor(Color.black);
        g.fill(smallerBox);

        double dirPointX = Math.cos(angle) * 50 + x;
        double dirPointY = Math.sin(angle) * 50 + y;
        Shape dirPoint = new Circle((int)dirPointX, (int)dirPointY, 5, 8);
        g.setColor(Color.red);
        g.fill(dirPoint);
    }

}
