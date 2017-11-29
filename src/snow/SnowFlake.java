package snow;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Object containing details of a snow flake and its widget.
 */
public class SnowFlake {

    private static final int FLUTTER_PERIOD = 25;

    private Random random = new Random();
    private double[] flutter = new double[FLUTTER_PERIOD];
    private double speed;
    private double size;
    private double x;
    private double y;

    private int index = 0;

    /**
     * Create snow flake with given speed and location.
     *
     * @param speed     Speed at which it falls
     * @param locationX X coordinate of location
     * @param locationY Y coordinate of location
     */
    public SnowFlake(double speed, double size, double locationX, double locationY) {
        this.speed = speed;
        this.size = size;
        x = locationX;
        y = locationY;
    }

    /**
     * Move flake down by falling speed and across by flutter speed.
     */
    public void move(double delta) {
        flutter[index] = (random.nextDouble() * speed * 2) - speed;

        x += calculateFlutter() * delta;
        y += speed * delta;
    }

    /**
     * calculate the flutter speed of the flake.
     *
     * @return Flutter speed in x direction
     */
    private double calculateFlutter() {
        double total = 0;
        for (double flutterSpeed : flutter) {
            total += flutterSpeed;
        }
        index = (index + 1) % FLUTTER_PERIOD;
        return total / FLUTTER_PERIOD;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, size, size);
    }

    /**
     * Set speed to zero as the flake has hit a surface on which it can settle.
     */
    public void settle() {
        speed = 0;
    }

    public double getSpeed() {
        return speed;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
