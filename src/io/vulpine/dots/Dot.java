package io.vulpine.dots;

import javafx.scene.paint.Color;

import java.util.Random;

public class Dot
{
  protected final static Random rand = new Random();
  protected final static int[] compliments = new int[] { 333, 153, 63 };

  protected final boolean back;
  protected final double  angle;
  protected final double  speed;
  protected final double  opacity;
  protected final Color   hue;

  protected double posX;
  protected double posY;

  public Dot()
  {
    posX = randomDouble(0, Application.MAX_X);
    posY = randomDouble(0, Application.MAX_Y);

    speed   = randomDouble(0.2, 1.5);
    opacity = randomDouble(0.3, 1);

    back  = rand.nextBoolean();

    angle   = randomDouble(0, 3.14);
    hue = Color.hsb(compliments[rand.nextInt(3)], 0.5, 1);
  }

  public static double randomDouble(double min, double max) { return min + (max - min) * rand.nextDouble(); }

  public void step()
  {
    if (back) {
      posX -= Math.sin(angle) * speed;
      posY -= Math.cos(angle) * speed;
      return;
    }

    posX += Math.sin(angle) * speed;
    posY += Math.cos(angle) * speed;
  }

  public double getOpacity()
  {
    return opacity;
  }

  public double getPosX()
  {
    return posX;
  }

  public double getPosY()
  {
    return posY;
  }
}
