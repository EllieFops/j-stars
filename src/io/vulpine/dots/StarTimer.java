package io.vulpine.dots;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

import static io.vulpine.dots.Application.*;
import static java.lang.Math.sqrt;

public class StarTimer extends AnimationTimer
{
  private final GraphicsContext topLayer;
  private final Star[]          stars;

  public StarTimer( final GraphicsContext con1, final Star[] stars )
  {
    this.topLayer = con1;
    this.stars = stars;
  }

  @Override
  public void handle( final long now )
  {
    topLayer.clearRect(0, 0, MAX_X, MAX_Y);

    // Position
    for (int i = 0; i < DOT_COUNT; i++) {
      final double  x, y;
      stars[i].step();

      x = stars[i].getPosX();
      y = stars[i].getPosY();

      if (x > MAX_X || x < 0 || y > MAX_Y || y < 0) stars[i] = new Star();
    }

    // Render
    for (Star d : stars) {
      final double
        cx = d.getPosX(),
        cy = d.getPosY();
      int connections = 0;

      // Set opacity for drawing lines and this star.
      topLayer.setGlobalAlpha(d.getOpacity());

      for (final Star e : stars) {
        final double
          sx = e.getPosX(),
          sy = e.getPosY();

        if (sqrt((cx-sx)*(cx-sx)+(cy-sy)*(cy-sy)) <= MAX_LINE_LENGTH) {
          topLayer.strokeLine(cx, cy, sx, sy);
          connections++;
          if (connections >=  MAX_CONNECTIONS) break;
        }
      }
      d.drawStar(topLayer);
    }
  }
}
