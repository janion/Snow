package snow;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Window;

public class CloseButtonForStage {

    private static final double SIZE = 10;

    private Polygon polygon;
    private BorderPane bp;

    private boolean dragging;
    private boolean hasDragged;
    private double dragStartX;
    private double dragStartY;

    public CloseButtonForStage() {

        polygon = createCross();
        polygon.setStroke(Color.BLACK);
        polygon.setStrokeWidth(3);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);

        bp = new BorderPane(polygon);
        bp.setStyle("-fx-background-color: #70707070");

        bp.setOnMouseEntered(event -> polygon.setStroke(Color.RED));
        bp.setOnMouseExited(event -> polygon.setStroke(Color.BLACK));

        bp.setOnMouseClicked(event -> {
            if (!hasDragged) {
                System.exit(0);
            } else {
                hasDragged = false;
            }
        });

        setupDragging();
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
