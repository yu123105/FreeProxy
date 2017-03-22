package com.fish.proxy.scanner.client;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.nio.conn.NHttpClientConnectionManager;
import org.apache.http.util.Args;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/3/22 0022.
 */
public final class NIdleConnectionEvictor {
    private final NHttpClientConnectionManager connectionManager;
    private final ThreadFactory threadFactory;
    private final Thread thread;
    private final long sleepTimeMs;
    private final long maxIdleTimeMs;
    private volatile Exception exception;

    public NIdleConnectionEvictor(final NHttpClientConnectionManager connectionManager, ThreadFactory threadFactory, long sleepTime, TimeUnit sleepTimeUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this.connectionManager = (NHttpClientConnectionManager) Args.notNull(connectionManager, "Connection manager");
        this.threadFactory = (ThreadFactory)(threadFactory != null?threadFactory:new NIdleConnectionEvictor.DefaultThreadFactory());
        this.sleepTimeMs = sleepTimeUnit != null?sleepTimeUnit.toMillis(sleepTime):sleepTime;
        this.maxIdleTimeMs = maxIdleTimeUnit != null?maxIdleTimeUnit.toMillis(maxIdleTime):maxIdleTime;
        this.thread = this.threadFactory.newThread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        if(!Thread.currentThread().isInterrupted()) {
                            Thread.sleep(NIdleConnectionEvictor.this.sleepTimeMs);
                            connectionManager.closeExpiredConnections();
                            if(NIdleConnectionEvictor.this.maxIdleTimeMs > 0L) {
                                connectionManager.closeIdleConnections(NIdleConnectionEvictor.this.maxIdleTimeMs, TimeUnit.MILLISECONDS);
                            }
                            continue;
                        }
                    } catch (Exception var2) {
                        NIdleConnectionEvictor.this.exception = var2;
                    }

                    return;
                }
            }
        });
    }

    public NIdleConnectionEvictor(NHttpClientConnectionManager connectionManager, long sleepTime, TimeUnit sleepTimeUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this(connectionManager, (ThreadFactory)null, sleepTime, sleepTimeUnit, maxIdleTime, maxIdleTimeUnit);
    }

    public NIdleConnectionEvictor(NHttpClientConnectionManager connectionManager, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this(connectionManager, (ThreadFactory)null, maxIdleTime > 0L?maxIdleTime:5L, maxIdleTimeUnit != null?maxIdleTimeUnit:TimeUnit.SECONDS, maxIdleTime, maxIdleTimeUnit);
    }

    public void start() {
        this.thread.start();
    }

    public void shutdown() {
        this.thread.interrupt();
    }

    public boolean isRunning() {
        return this.thread.isAlive();
    }

    public void awaitTermination(long time, TimeUnit tunit) throws InterruptedException {
        this.thread.join((tunit != null?tunit:TimeUnit.MILLISECONDS).toMillis(time));
    }

    static class DefaultThreadFactory implements ThreadFactory {
        DefaultThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Connection evictor");
            t.setDaemon(true);
            return t;
        }
    }
}
