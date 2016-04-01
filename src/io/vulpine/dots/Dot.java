package io.vulpine.dots;

import javafx.geometry.Point2D;

import java.util.Random;

public class Dot
{
  protected final static Random rand = new Random();

  protected Point2D current;
  protected final Point2D start;
  protected final double angle;
  protected final boolean back;
  protected final double speed;
  protected final double opacity;

  public Dot()
  {
    final Point2D end;
    final int r = (int) randomDouble(0, 3);

    start = new Point2D(randomDouble(0, Application.MAX_X), randomDouble(0, Application.MAX_Y));
    speed = randomDouble(0.2, 1.5);
    opacity = randomDouble(0.2, 1);

    back  = randomDouble(-10, 10) < 0;

    current = new Point2D(start.getX(), start.getY());
    angle   = randomDouble(0, 3.14);
  }

  public static double randomDouble(double min, double max) { return min + (max - min) * rand.nextDouble(); }

  public void step()
  {
    final double x, y;
    x = Math.sin(angle) * speed;
    y = Math.cos(angle) * speed;

    current = back ? current.subtract(x, y) : current.add(x, y);
  }

  public Point2D getCurrent()
  {
    return current;
  }

  public double getOpacity()
  {
    return opacity;
  }

  public Point2D getPointFromCenter(double rad, double dist)
  {
    return current.add(
      dist*Math.cos(rad),
      dist*Math.sin(rad)
    );
  }
}
