package org.skife.terminal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

class Renderer
{
    private static final ExecutorService exec = Executors.newSingleThreadExecutor(new ThreadFactory()
    {
        @Override
        public Thread newThread(Runnable r)
        {
            Thread thread = new Thread(r);
            thread.setName("terminal-renderer");
            thread.setDaemon(true);
            return thread;
        }
    });

    static Future<Void> render(Callable<Void> job)
    {
        return exec.submit(job);
    }
}
