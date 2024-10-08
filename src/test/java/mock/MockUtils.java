package mock;

import com.intuit.karate.core.MockServer;
import com.intuit.karate.PerfContext;
import com.intuit.karate.Runner;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author pthomas3
 */
public class MockUtils {
    
    public static void startServer() {
        //MockServer server = MockServer.feature("classpath:mock/mock.feature").build();
        //System.setProperty("mock.cats.url", "http://localhost:" + server.getPort() + "/cats");
        System.setProperty("mock.cats.url", "http://vs167781svc368204.mock.blazemeter.com/cats");

    }

    private static final List<String> catNames = (List) Runner.runFeature("classpath:mock/feeder.feature", null, false).get("names");

    private static final AtomicInteger counter = new AtomicInteger();

    public static String getNextCatName() {
        return catNames.get(counter.getAndIncrement() % catNames.size());
    }

    public static Map<String, Object> myRpc(Map<String, Object> map, PerfContext context) {
        long startTime = System.currentTimeMillis();
        // this is just an example, you can put any kind of code here
        int sleepTime = (Integer) map.get("sleep");
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        // and here is where you send the performance data to the reporting engine
        context.capturePerfEvent("myRpc-" + sleepTime, startTime, endTime);
        return Collections.singletonMap("success", true);
    }
    
}
