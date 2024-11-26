package MVC.Service.NewSingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadTaskManager {
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public void submitTask(Runnable task) {
        singleThreadExecutor.submit(task);
    }

    public void shutdown() {
        singleThreadExecutor.shutdown();
    }
}
