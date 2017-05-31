package calculate;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import jsf31kochfractalfx.JSF31KochFractalFX;
import jsf31kochfractalfx.LessFuckedEdge;
import org.apache.commons.lang.time.StopWatch;
import timeutil.TimeStamp;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
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
        application.clearKochPanel();// new Thread(new EdgeGenerator(currentLevel, this, application)).start();
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            RandomAccessFile f = new RandomAccessFile(System.getProperty("user.home") + File.separator + currentLevel + ".mmkoch", "r");
            MappedByteBuffer m = f.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, 0xE66666);
       m.rewind();
        byte[] b = new byte[m.remaining()];
        m.get(b);
        Input i = new Input(b);
            Kryo k = new Kryo();
            try {
                i.rewind();;
                while (true) {
              //      System.out.println(i.getBuffer().toString());
                    edges.add(k.readObject(i, LessFuckedEdge.class).getEdge());
                }
            } catch (Exception e) {
                e.printStackTrace();
                //nothing. Im this evil. Dont want to bother figuring out the end.

     //      e.printStackTrace();
            }

//            Scanner sc = new Scanner((new BufferedReader(new FileReader(fjSon))));
//            Gson gson = new GsonBuilder().create();
//            while (sc.hasNextLine()) {
//                edges.add(gson.fromJson(sc.nextLine(), LessFuckedEdge.class).getEdge());
//            }
         //   System.out.println(i.readString());

   //         edges.add(k.readObject(i, LessFuckedEdge.class).getEdge());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sw.stop();
        System.out.println(sw.getTime());
        System.out.println(edges.size());
        drawEdges();

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


