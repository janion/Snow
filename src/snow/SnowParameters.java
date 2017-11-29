package snow;

public class SnowParameters {

    public static final double DEFAULT_MAX_FLAKE_SPEED = 2.5;
    public static final double DEFAULT_FLAKES_PER_SECOND_DENSITY = 0.15;

    private double maxFlakeSpeed;
    private double flakesPerSecondDensity;

    public SnowParameters() {
        maxFlakeSpeed = DEFAULT_MAX_FLAKE_SPEED;
        flakesPerSecondDensity = DEFAULT_FLAKES_PER_SECOND_DENSITY;
    }

    public void setMaxFlakeSpeed(double maxFlakeSpeed) {
        this.maxFlakeSpeed = maxFlakeSpeed;
    }

    public void setFlakesPerSecondDensity(double flakesPerSecondDensity) {
        this.flakesPerSecondDensity = flakesPerSecondDensity;
    }

    public double getMaxFlakeSpeed() {
        return maxFlakeSpeed;
    }

    public double getFlakesPerSecondDensity() {
        return flakesPerSecondDensity;
    }
}
