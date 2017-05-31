package jsf31kochfractalfx;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import calculate.Edge;
import javafx.scene.paint.Color;

import static javafx.scene.paint.Color.BLACK;


public class LessFuckedEdge {
    public double X1, Y1, X2, Y2;
  //  public Color color;
    public String color;

    public LessFuckedEdge() {
    }

    @Override
    public String toString() {
        return "LessFuckedEdge{" +
                "X1=" + X1 +
                ", Y1=" + Y1 +
                ", X2=" + X2 +
                ", Y2=" + Y2 +
                ", serializeableColor=" + color +
                '}';
    }

    public LessFuckedEdge(Edge e) {
        X1 = e.X1;
        Y1 = e.Y1;
        X2 = e.X2;
        Y2 = e.Y2;
         color = getColorFromFX(e.color);

    }
    public Edge getEdge() {
        return getEdge(X1, Y1, X2, Y2, getColor());
    }

    private Color getColor() {

      //  System.out.println("Converting: " + color);
        return Color.web(color);
    }

    private String getColorFromFX(Color c) {
        return c.toString();
    }

    public Edge getEdge(double X1, double Y1, double X2, double Y2, Color color) {
        return new Edge(X1, Y1, X2, Y2, color);
    }
}
