package snow.configure;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import snow.SnowParameters;

public class CloseButtonForStage {

    private static final double SIZE = 10;

    private Polygon polygon;
    private BorderPane bp;

    private boolean dragging;
    private boolean hasDragged;
    private double dragStartX;
    private double dragStartY;

    public CloseButtonForStage(SnowParameters snowParameters) {

        polygon = createCross();
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);

        bp = new BorderPane(polygon);
        bp.setStyle("-fx-background-color: #70707070");

        bp.setOnMouseEntered(event -> polygon.setStroke(Color.RED));
        bp.setOnMouseExited(event -> polygon.setStroke(Color.BLACK));

        bp.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (!hasDragged) {
                    System.exit(0);
                } else {
                    hasDragged = false;
                }
            } else {
//                showConfigureView(snowParameters);
            }
        });

        setupDragging();
    }

    private void showConfigureView(SnowParameters snowParameters) {

        Stage stage = new Stage();

        VBox configureView = new ConfigureView(snowParameters).getNode();
        Scene scene = new Scene(configureView);
//        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

//        Bounds bounds = bp.localToScreen(bp.getBoundsInLocal());
//        stage.setX(bounds.getMinX());
//        stage.setY(bounds.getMinY());
        stage.setWidth(configureView.getPrefWidth());
        stage.setHeight(configureView.getPrefHeight());
        stage.setWidth(200);
        stage.setHeight(200);

//        stage.setAlwaysOnTop(true);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    private Polygon createCross() {
        Polygon polygon = new Polygon(
                0, 0,
                -SIZE, -SIZE,
                0, 0,
                -SIZE, SIZE,
                0, 0,
                SIZE, SIZE,
                0, 0,
                SIZE, -SIZE
        );
        polygon.setFill(null);

        return polygon;
    }

    private void setupDragging() {
        bp.setOnMousePressed(event -> {
            dragging = true;
            dragStartX = event.getScreenX();
            dragStartY = event.getScreenY();
        });

        bp.setOnMouseDragged(event -> {
            if (dragging) {
                hasDragged = true;
                double newScreenX = event.getScreenX();
                double newScreenY = event.getScreenY();

                Window stage = bp.getScene().getWindow();
                stage.setX(stage.getX() + (newScreenX - dragStartX));
                stage.setY(stage.getY() + (newScreenY - dragStartY));

                dragStartX = newScreenX;
                dragStartY = newScreenY;
            }
        });

        bp.setOnMouseReleased(event -> dragging = false);
    }

    public BorderPane getNode() {
        return bp;
    }

}
