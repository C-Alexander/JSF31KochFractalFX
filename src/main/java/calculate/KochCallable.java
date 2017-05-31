package calculate;

import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

/**
 * Created by jasve_29 on 11-Apr-17.
 */
public class KochCallable implements Callable, Observer {
    private List<Edge> edges = new ArrayList<>();
    private KochFractal fractal;
    private Edges edgeToGenerate;
    private JSF31KochFractalFX app;
    public KochCallable(int level, Edges edgeToGenerate, JSF31KochFractalFX app) {
        this.edgeToGenerate = edgeToGenerate;
        fractal = new KochFractal();
        fractal.setLevel(level);
        fractal.addObserver(this);
        this.app = app;
    }


    @Override
    public void update(Observable o, Object arg) {
    //    edges.add((Edge)arg);
        Platform.runLater(() -> app.drawEdge((Edge)arg));
    }

    @SuppressWarnings("Duplicates")
    @Override
    public Object call() throws Exception {
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
        return edges;
        }
        }
