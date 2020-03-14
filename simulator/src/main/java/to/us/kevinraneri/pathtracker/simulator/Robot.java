package to.us.kevinraneri.pathtracker.simulator;

import lombok.Getter;
import org.newdawn.slick.geom.Vector2f;
import to.us.kevinraneri.pathtracker.simulator.entity.impl.RobotEntity;
import to.us.kevinraneri.pathtracker.simulator.kinematics.BallisticObject;
import to.us.kevinraneri.pathtracker.simulator.util.MathUtil;

@Getter
public class Robot {

    private RobotEntity entity;
    private Vector2f position;
    private BallisticObject ballistics;
    private double angle;

    public Robot() {
        entity = new RobotEntity();
        position = new Vector2f();
        ballistics = new BallisticObject(2, 10, 12f, 1f);
    }

    public void updateBallistics(Vector2f forceVector, float timeDelta) {
        Vector2f newVelocity = ballistics.update(forceVector.x, forceVector.y, timeDelta).copy();
        newVelocity.scale(timeDelta);
        position.add(newVelocity);
        updateScreenPos();
    }

    public void setX(float x) {
        position.x = x;
        updateScreenPos();
    }

    public void setY(float y) {
        position.y = y;
        updateScreenPos();
    }

    public void setAngle(double angle) {
        this.angle = angle;

        // We have to flip the angle here because of the coord flip.
        this.entity.setAngle(-Math.toRadians(angle));
    }

    public void setPos(Vector2f pos) {
        position = pos.copy();
        updateScreenPos();
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2f getPosition() {
        return position.copy();
    }

    public int getScreenX() {
        return entity.getX();
    }

    public int getScreenY() {
        return entity.getY();
    }

    public double getAngle() {
        return angle;
    }

    private void updateScreenPos() {
        Vector2f screenPos = MathUtil.toScreenCoord(position);
        entity.setX((int)screenPos.x);
        entity.setY((int)screenPos.y);

        this.entity.setAngle(-Math.toRadians(angle));
    }

}
