package future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author Fox
 */
public class MyFutureTask<V> implements Runnable, Future {
    public static final Logger log = LoggerFactory.getLogger(MyFutureTask.class);

    private Callable<V> callable;
    private V result = null;

    public MyFutureTask(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {

        try {
            result = callable.call();
            synchronized (this){
                this.notify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return result != null;
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        if (result != null) {
            return result;
        }
        synchronized (this) {
            this.wait();
        }
        return result;
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (result != null) {
            return result;
        }
        if (timeout > 0L) {
            unit.sleep(timeout);
            if (result != null) {
                return result;
            } else {
                throw new TimeoutException();
            }
        }
        return result;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException{
        MyFutureTask task = new MyFutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(3000);
                return "返回任务结果";
            }

        });

        new Thread(task).start();
        log.debug("结果：{}",task.get());
    }
}
