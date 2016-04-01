package io.vulpine.dots;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Application extends javafx.application.Application
{
  public static final int MAX_X           = 1920;
  public static final int MAX_Y           = 1068;
  public static final int DOT_COUNT       = 100;
  public static final int MAX_LINE_LENGTH = 150;
  public static final int MAX_CONNECTIONS = 4;

  public static final int DOT_WIDTH = 3;

  public static void main( String[] args )
  {
    launch(args);
  }

  @Override
  public void start( final Stage stage ) throws Exception
  {
    final Canvas          canvas1, canvas2;
    final Star[]           dots;
    final GraphicsContext gc;
    final Group           g;
    final Scene           s;
    final AnimationTimer timer;

    stage.setTitle("Dots");

    dots = new Star[DOT_COUNT];
    canvas1 = new Canvas(MAX_X, MAX_Y);
    canvas2 = new Canvas(MAX_X, MAX_Y);
    gc = canvas1.getGraphicsContext2D();
    gc.setGlobalBlendMode(BlendMode.SRC_OVER);
    canvas2.getGraphicsContext2D().setFill(Color.BLACK);
    canvas2.getGraphicsContext2D().fillRect(0, 0, MAX_X, MAX_Y);
    g = new Group(canvas2);
    g.getChildren().add(canvas1);
    s = new Scene(g);

    stage.setWidth(800);
    stage.setHeight(600);

    stage.setScene(s);
    stage.show();

    for (int i = 0; i < DOT_COUNT; i++) {
      dots[i] = new Star();
    }

    timer = new AnimationTimer() {

      @Override
      public void handle( final long now )
      {
        gc.clearRect(0, 0, MAX_X, MAX_Y);

        // Position
        for (int i = 0; i < DOT_COUNT; i++) {
          final Point2D cur;
          final double  x, y;

          dots[i].step();
          cur = dots[i].getCurrent();

          x = cur.getX();
          y = cur.getY();

          if (x > MAX_X || x < 0 || y > MAX_Y || y < 0) {
            dots[i] = new Star();
          }
        }

        // Render
        for (Star d : dots) {
          final Point2D cur;
          final double  cx, cy;
          int connections = 0;

          gc.setGlobalAlpha(d.getOpacity());
          gc.setFill(Color.RED);

          cur = d.getCurrent();
          cx = cur.getX();
          cy = cur.getY();

          gc.setStroke(Color.DARKRED);
          for (final Star e : dots) {
            final Point2D sub;
            final double  sx, sy;

            sub = e.getCurrent();
            sx = sub.getX();
            sy = sub.getY();

            if (cur.distance(sub) <= MAX_LINE_LENGTH) {
              gc.strokeLine(cx, cy, sx, sy);
              connections++;
              if (connections >=  MAX_CONNECTIONS) break;
            }
          }

          d.drawStar(gc);
        }
      }
    };

    timer.start();
  }
}
