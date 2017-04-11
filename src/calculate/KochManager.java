package calculate;

import javafx.application.Application;
import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;
import timeutil.TimeStamp;

import java.util.*;

/**
 * Created by jasve_29 on 21-Mar-17.
 */
public class KochManager {

    private JSF31KochFractalFX application;
    public List<Edge> edges = Collections.synchronizedList(new ArrayList<Edge>());
    TimeStamp drawTimer = new TimeStamp();
    TimeStamp updateTimer = new TimeStamp();

    public KochManager(JSF31KochFractalFX application) {

        this.application = application;
    }

//    @Override
//    public void update(Observable o, Object arg) {
//     //   application.drawEdge((Edge)arg);
//        edges.add((Edge)arg);
//
//    }

    public void changeLevel(int currentLevel) {
        edges.clear();
        updateTimer.init();
        updateTimer.setBegin();
        new Thread(new EdgeGenerator(currentLevel, this)).start();

    }

    public void drawEdges() {
        drawTimer.init();
        drawTimer.setBegin();
        application.clearKochPanel();
        synchronized (edges) {
            for (Edge e : edges) {
                application.drawEdge(e);
            }
        }
        drawTimer.setEnd();
        application.setTextDraw(drawTimer.toString());
    }

    public void threadFinished() {
                updateTimer.setEnd();
            Platform.runLater(() -> application.setTextCalc(updateTimer.toString()));
            Platform.runLater(() -> application.setTextNrEdges(Integer.toString(edges.size())));
          //  drawEdges();
            application.requestDrawEdges();
        }
    }

