package org.ticket;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;
    private volatile boolean isStopped;

    public ThreadPool(int numThreads, int maxTasks) {
        taskQueue = new LinkedBlockingQueue<>(maxTasks);
        threads = new Thread[numThreads];
        isStopped = false;

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Worker(taskQueue);
            threads[i].setName("Thread - " + (i + 1));
            threads[i].start();
        }
    }

    public void execute(Runnable task) throws InterruptedException {
        if (!isStopped) {
            taskQueue.put(task);
        }
    }

    public void waitUntilAllTasksFinished() {
        while (!taskQueue.isEmpty()) {
            // Wait for all tasks to be processed
        }
    }

    public void stop() {
        isStopped = true;
        for (Thread worker : threads ) {
            worker.interrupt();
        }
    }

    private static class Worker extends Thread {
        private final BlockingQueue<Runnable> taskQueue;

        public Worker(BlockingQueue<Runnable> taskQueue) {
            this.taskQueue = taskQueue;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}