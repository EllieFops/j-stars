package io.vulpine.dots;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static java.lang.Math.sqrt;

public class Application extends javafx.application.Application
{
  public static final int MAX_X           = 1920;
  public static final int MAX_Y           = 1068;
  public static final int DOT_COUNT       = 100;
  public static final int MAX_LINE_LENGTH = 150;
  public static final int MAX_CONNECTIONS = 4;


  public static void main( String[] args )
  {
    launch(args);
  }

  @Override
  public void start( final Stage stage ) throws Exception
  {
    final Canvas          canvas1, canvas2;
    final Star[]          stars;
    final GraphicsContext con1, con2;
    final Group           g;
    final Scene           s;
    final AnimationTimer timer;

    stage.setTitle("Stars");

    stars   = new Star[DOT_COUNT];
    canvas1 = new Canvas(MAX_X, MAX_Y);
    canvas2 = new Canvas(MAX_X, MAX_Y);
    con1    = canvas1.getGraphicsContext2D();
    con2    = canvas2.getGraphicsContext2D();

    con1.setGlobalBlendMode(BlendMode.SRC_OVER);

    g = new Group(canvas2);
    g.getChildren().add(canvas1);

    s = new Scene(g);

    stage.setWidth(800);
    stage.setHeight(600);
    stage.setScene(s);
    stage.show();

    for (int i = 0; i < DOT_COUNT; i++) stars[i] = new Star();

    timer = new AnimationTimer() {

      @Override
      public void handle( final long now )
      {
        con1.clearRect(0, 0, MAX_X, MAX_Y);

        con2.setFill(Color.hsb(Star.getHue()+180, Star.getSaturation(), Star.getBrightness()));

        con2.fillRect(0, 0, MAX_X, MAX_Y);

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
          final double cx, cy;
          int connections = 0;

          con1.setGlobalAlpha(d.getOpacity());

          cx = d.getPosX();
          cy = d.getPosY();

          for (final Star e : stars) {
            final double  sx, sy;

            sx = e.getPosX();
            sy = e.getPosY();
            if (sqrt((cx-sx)*(cx-sx)+(cy-sy)*(cy-sy)) <= MAX_LINE_LENGTH) {
              con1.strokeLine(cx, cy, sx, sy);
              connections++;
              if (connections >=  MAX_CONNECTIONS) break;
            }
          }
          d.drawStar(con1);
        }
      }
    };
    timer.start();
  }
}
