package io.vulpine.dots;

import javafx.animation.AnimationTimer;
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
    final Group           group;
    final Scene           scene;
    final AnimationTimer  timer;

    stage.setTitle("Stars");

    stars   = new Star[DOT_COUNT];
    canvas1 = new Canvas(MAX_X, MAX_Y);
    canvas2 = new Canvas(MAX_X, MAX_Y);
    con1    = canvas1.getGraphicsContext2D();
    con2    = canvas2.getGraphicsContext2D();

    group = new Group(canvas2);
    scene = new Scene(group);

    group.getChildren().add(canvas1);

    stage.setWidth(800);
    stage.setHeight(600);
    stage.setScene(scene);
    stage.show();

    for (int i = 0; i < DOT_COUNT; i++) stars[i] = new Star();

    con1.setGlobalBlendMode(BlendMode.SRC_OVER);

    con2.setFill(Color.hsb(243, 0.5, 1));
    con2.fillRect(0, 0, MAX_X, MAX_Y);

    timer = new StarTimer(con1, stars);
    timer.start();
  }
}
