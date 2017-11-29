package snow.configure;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import snow.SnowParameters;

public class ConfigureView {

    private VBox vbox;
    private SnowParameters snowParameters;

    public ConfigureView(SnowParameters snowParameters) {
        this.snowParameters = snowParameters;

        vbox = new VBox(createMaxSpeedControl(), createFlakeDensityControl());
    }

    private HBox createMaxSpeedControl() {
        Slider slider = new Slider(1, 50, snowParameters.getMaxFlakeSpeed());
        slider.valueProperty().addListener((observable, oldVal, newVal) -> snowParameters.setMaxFlakeSpeed(newVal.doubleValue()));

        return new HBox(new Label("Max size/speed: "), slider);
    }

    private HBox createFlakeDensityControl() {
        Slider slider = new Slider(1, 50, snowParameters.getFlakesPerSecondDensity());
        slider.valueProperty().addListener((observable, oldVal, newVal) -> snowParameters.setFlakesPerSecondDensity(newVal.doubleValue()));

        return new HBox(new Label("Flake density: "), slider);
    }

    public VBox getNode() {
        return vbox;
    }
}
