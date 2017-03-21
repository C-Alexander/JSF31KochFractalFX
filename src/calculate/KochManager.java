package calculate;

import javafx.application.Application;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jasve_29 on 21-Mar-17.
 */
public class KochManager implements Observer {

    private JSF31KochFractalFX application;
    private KochFractal koch;
    private List<Edge> edges = new ArrayList<>();

    public KochManager(JSF31KochFractalFX application) {

        this.application = application;
        koch = new KochFractal();
        koch.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
     //   application.drawEdge((Edge)arg);
        edges.add((Edge)arg);
    }

    public void changeLevel(int currentLevel) {
        koch.setLevel(currentLevel);
        edges.clear();
        drawEdges();
    }

    public void drawEdges() {
        TimeStamp t = new TimeStamp();
        t.setBegin("Clearing panel");
        application.clearKochPanel();
        t.setEndBegin("Generating edges...");
        koch.generateLeftEdge();
        koch.generateBottomEdge();
        koch.generateRightEdge();
        t.setEndBegin("drawing..");
        for(Edge e : edges) {
            application.drawEdge(e);
        }
        t.setEnd("Finished drawing.");
        application.setTextCalc(t.toString());
        application.setTextNrEdges(Integer.toString(edges.size()));
    }
}
