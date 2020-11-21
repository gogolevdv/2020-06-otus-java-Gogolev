package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunCount {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private enum StreamName {STREAM1, STREAM2}
    private final Thread stream1;
    private final Thread stream2;
    private final Count count;

    public RunCount() {

        this.count=new Count(String.valueOf(StreamName.STREAM2), Direction.UP,1);

        stream1 = new Thread(
                () -> this.action(StreamName.STREAM1)
        );
        stream1.setName(String.valueOf(StreamName.STREAM1));

        stream2 = new Thread(
                () -> this.action(StreamName.STREAM2)
        );
        stream2.setName(String.valueOf(StreamName.STREAM2));

    }

    public void run(){

        stream1.start();
        stream2.start();

    }

    private synchronized void action(StreamName name) {

        if (name.equals(StreamName.STREAM1)) {
            System.out.println(StreamName.STREAM1 + "   " + StreamName.STREAM2);
        }
        while (true) {
            try {
                while (count.getLast().equals(String.valueOf(name))) {
                    this.wait();
                }

                if (name == StreamName.STREAM1) {
                    System.out.print(" " + count.getI() + "        ");

                    sleepAndNotify(name);

                } else {
                    System.out.println(" " + count.getI() + "   ");

                    count.changeCount();

                    sleepAndNotify(name);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void sleepAndNotify(StreamName name){
        count.setLast(String.valueOf(name));
        sleep();
        notifyAll();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
