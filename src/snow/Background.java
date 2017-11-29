package snow;

import javafx.scene.canvas.Canvas;

/**
 * Background covering entire panel behind a snow panel.
 */
public class Background {

    /**
     * State of a flake after a tick.
     */
    public enum FlakeState {
        DESTROY, SETTLE
    }

    protected Canvas canvas;

    public Background(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Get the flake state of a given flake.
     *
     * @param flake Flake to inspect
     * @return State of flake
     */
    public FlakeState getFlakeState(SnowFlake flake) {
        double flakeX = flake.getX();
        double flakeY = flake.getY();
        if (flakeY > canvas.getHeight() || flakeX > canvas.getWidth() || flakeX + flake.getSpeed() < 0) {
            return FlakeState.DESTROY;
        }
        return null;
    }
}