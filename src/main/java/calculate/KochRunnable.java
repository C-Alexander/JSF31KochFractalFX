package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jasve_29 on 11-Apr-17.
 */
public class KochRunnable implements Runnable, Observer {
    private List<Edge> edges = new ArrayList<>();
    private KochFractal fractal;
    private KochManager manager;
    private Edges edgeToGenerate;
    public KochRunnable(int level, KochManager manager, Edges edgeToGenerate) {
        this.manager = manager;
        this.edgeToGenerate = edgeToGenerate;
        fractal = new KochFractal();
        fractal.setLevel(level);
        fractal.addObserver(this);
    }

    @Override
    public void run() {
     switch (edgeToGenerate) {
         case LeftEdge:
             fractal.generateLeftEdge();
             break;
         case RightEdge:
             fractal.generateRightEdge();
             break;
         case BottomEdge:
             fractal.generateBottomEdge();
             break;
     }
     manager.edges.addAll(edges);
     manager.threadFinished();
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}
