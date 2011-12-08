package org.skife.terminal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class App
{
    public static void main(String[] args) throws Exception
    {
        System.out.println();

        ExecutorService es = Executors.newCachedThreadPool();

        final ProgressBar slow = new ProgressBar(Label.create("slower thing", 12),
                                                 Height.fromBottom(1),
                                                 Percentage.show());
        slow.render().get();

        final ProgressBar fast = new ProgressBar(Label.create("fast thing", 12),
                                                 Height.fromBottom(0),
                                                 Percentage.show());
        fast.render().get();

        Future one = es.submit(new Callable<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                for (int i = 0; i <= 15; i++) {
                    Thread.sleep(500);
                    slow.progress((i / 15.0)).render().get();
                }
                return null;
            }
        });

        Future two = es.submit(new Callable<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(500);
                    fast.progress((i / 10.0)).render().get();
                }
                return null;
            }
        });

        one.get();
        two.get();
        es.shutdown();
    }
}
