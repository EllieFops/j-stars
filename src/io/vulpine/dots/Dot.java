package io.vulpine.dots;

import java.util.Random;

public class Dot
{
  protected final static Random rand = new Random();

  protected double posX;
  protected double posY;
  protected final double angle;
  protected final boolean back;
  protected final double speed;
  protected final double opacity;

  protected static double hue = 63;
  protected static double saturation = 0.5;
  protected static int brightness = 1;

  public Dot()
  {
    posX = randomDouble(0, Application.MAX_X);
    posY = randomDouble(0, Application.MAX_Y);

    speed = randomDouble(0.2, 1.5);
    opacity = randomDouble(0.3, 1);

    back  = randomDouble(-10, 10) < 0;

    angle   = randomDouble(0, 3.14);
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

  public static double getHue()
  {
    return hue;
  }

  public static double getSaturation()
  {
    return saturation;
  }

  public static int getBrightness()
  {
    return brightness;
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
