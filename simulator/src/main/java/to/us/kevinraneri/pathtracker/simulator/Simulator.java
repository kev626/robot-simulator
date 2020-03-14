package to.us.kevinraneri.pathtracker.simulator;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import to.us.kevinraneri.pathtracker.simulator.controller.Controller;
import to.us.kevinraneri.pathtracker.simulator.controller.ControllerResponse;
import to.us.kevinraneri.pathtracker.simulator.entity.impl.HistoryPoint;
import to.us.kevinraneri.pathtracker.simulator.util.EvictingList;
import to.us.kevinraneri.pathtracker.simulator.util.MathUtil;

import java.util.List;

import static to.us.kevinraneri.pathtracker.simulator.util.MathUtil.TWO_PI;

public class Simulator extends BasicGame {

    private static final float MAX_SPEED = 48;

    private Robot robot;
    private List<HistoryPoint> historyPoints;
    private int updateCount;
    private Controller controller;

    public Simulator() {
        super("Simulator");
    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc = new AppGameContainer(new Simulator());
            appgc.setDisplayMode(800, 800, false);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        gameContainer.setAlwaysRender(true);
        gameContainer.setUpdateOnlyWhenVisible(false);
        gameContainer.setShowFPS(true);
        gameContainer.setMaximumLogicUpdateInterval(10);
        gameContainer.setMinimumLogicUpdateInterval(10);

        historyPoints = new EvictingList<>(200);

        robot = new Robot();
        robot.setPos(new Vector2f(18, 18));
        robot.setAngle(45);
        controller = new Controller(robot);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        updateCount++;

        float deltaSec = delta/1000f;

        ControllerResponse response = controller.update(deltaSec);
        double newAngle = Math.toDegrees(response.getAngleRate()) * deltaSec + robot.getAngle();
        robot.setAngle(newAngle);

        Vector2f impulse = new Vector2f(Math.toDegrees(response.getMotAngle()));
        impulse.add(robot.getAngle());


        float constSpeedForce = robot.getBallistics().frictionMagnitude;

        if (response.getMotRate() == 0) {
            impulse.scale(0);
        } else {
            impulse.scale((float)MathUtil.lerp(constSpeedForce, 350, response.getMotRate()));
        }

        if (robot.getBallistics().velocity.length() > MAX_SPEED &&
                impulse.length() > constSpeedForce) {
            impulse.normalise().scale(constSpeedForce);
        }

        robot.updateBallistics(impulse, deltaSec);

        if (updateCount % 5 == 0)
            historyPoints.add(new HistoryPoint(robot.getScreenX(), robot.getScreenY()));
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        robot.getEntity().render(g);

        historyPoints.forEach(p -> p.render(g));
        controller.drawDebug(g);
    }
}
