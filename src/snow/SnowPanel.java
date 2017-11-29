package snow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import snow.Background.FlakeState;

/**
 * Widget to show a snow overlay.
 */
public class SnowPanel {

   public static final double MAX_FLAKE_SPEED = 2.5;
   public static final double FLAKES_PER_SECOND_DENSITY = 0.15;
   private List<SnowFlake> flakes = new ArrayList<>();

   private Background background;
   private List<SnowFlake> settledFlakes = new ArrayList<>();
   
   private Random random = new Random();
   private Canvas canvas;
   
   /**
    * Construct a snow panel.
    */
   public SnowPanel() {
      canvas = new Canvas();
      canvas.setMouseTransparent(true);
      background = new Background(canvas);
      startAnimation();
   }

	private void startAnimation() {
		AnimationTimer timer = new AnimationTimer() {
			long lastTime;
			long lastTimeFlakesMade;
			
			@Override
			public void handle(long now) {
				if (lastTime == 0) {
					lastTime = now;
					lastTimeFlakesMade = now;
					return;
				}
				double delta = (now - lastTime) / 1000000000.0;
				double flakeDelta = (now - lastTimeFlakesMade) / 1000000000.0;
				int flakesToCreate = (int) (flakeDelta * FLAKES_PER_SECOND_DENSITY * canvas.getWidth());
				if (flakesToCreate != 0) {
					createFlakes(flakesToCreate);
					lastTimeFlakesMade = now;
				}
				moveFlakesDown(delta);
				draw();
				lastTime = now;
			}
		};
		timer.start();
	}

	private void draw() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for (SnowFlake flake : flakes) {
			flake.draw(gc);
		}
	}

	public void createFlakes(int flakesToCreate) {
		for (int i = 0; i < flakesToCreate; i++) {
			double value = (random.nextDouble() * MAX_FLAKE_SPEED) + 1;
			for (int j = 0; j < 2; j++) {
				double newValue = (random.nextDouble() * MAX_FLAKE_SPEED) + 1;
				value = Math.min(value, newValue);
			}
			double size = value * 1.5;
			double speed = value * 100;
			double xLocation = random.nextDouble() * canvas.getWidth();
			SnowFlake flake = new SnowFlake(speed, size, xLocation, 0);
			flakes.add(flake);
		}
	}

   /**
    * Move all flakes down by their associated speed.
    */
   private void moveFlakesDown(double delta) {
      List<SnowFlake> flakesToDestroy = new ArrayList<>();
      List<SnowFlake> flakesToSettle = new ArrayList<>();
      for (SnowFlake flake : flakes) {
         if (flake.getSpeed() == 0) {
            continue;
         }
         flake.move(delta);
         FlakeState state = background.getFlakeState(flake);
         if (state == FlakeState.DESTROY) {
            flakesToDestroy.add(flake);
         } else if (state == FlakeState.SETTLE) {
            flakesToSettle.add(flake);
         }
      }

      settleFlakes(flakesToSettle);
      destroyFlakes(flakesToDestroy);
   }

   /**
    * Settle any flakes which have settled.
    * @param flakesToDestroy Flakes which are to be desrotyed this tick
    */
   private void destroyFlakes(List<SnowFlake> flakesToDestroy) {
      int randomFlake = 30000 - settledFlakes.size();
      for (SnowFlake flake : settledFlakes) {
         if ((randomFlake <= 100 && random.nextInt(100) == 1) || random.nextInt(randomFlake) == 1) {
            flakesToDestroy.add(flake);
         }
      }

      for (SnowFlake flake : flakesToDestroy) {
         flakes.remove(flake);
         settledFlakes.remove(flake);
      }
   }

   /**
    * Settle any flakes which have settled.
    * @param flakesToSettle Flakes wich have settled this tick
    */
   private void settleFlakes(List<SnowFlake> flakesToSettle) {
      for (SnowFlake flake : flakesToSettle) {
         flake.settle();
         flakes.remove(flake);
         settledFlakes.add(flake);
      }
   }

	public Canvas getCanvas() {
		return canvas;
	}
}