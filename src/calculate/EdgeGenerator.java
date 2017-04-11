package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by jasve_29 on 11-Apr-17.
 */
public class EdgeGenerator implements Runnable {
    private int level;
    private KochManager manager;
    private ExecutorService executor;
    private List<Callable<KochCallable>> edgeCallables = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    public EdgeGenerator(int currentLevel, KochManager kochManager) {
        level = currentLevel;
        manager = kochManager;
        executor = Executors.newWorkStealingPool();
    }

    @Override
    public void run() {
        edgeCallables.add(new KochCallable(level, Edges.LeftEdge));
        edgeCallables.add(new KochCallable(level, Edges.RightEdge));
        edgeCallables.add(new KochCallable(level, Edges.BottomEdge));

        try {
            List<Future<KochCallable>> futures = new ArrayList<>();
            futures = executor.invokeAll(edgeCallables);
            for (Future<KochCallable> future : futures) edges.addAll((List<Edge>) future.get());
            manager.edges = edges;
            manager.threadFinished();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
