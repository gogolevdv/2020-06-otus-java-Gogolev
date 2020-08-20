package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class Benchmark implements BenchmarkMBean{

    private final int loopCounter;
    private volatile int size = 0;

    static final List<String> qq1 = new ArrayList<>();
    static int qq1_size = 0;
    long beginTime;

    public Benchmark(int loopCounter) { this.loopCounter = loopCounter; }

    void run() throws Exception {

        String qq = "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111";
        beginTime = System.currentTimeMillis();

        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;

            qq1_size = qq1.size();

            if (qq1.size()>size) {
                while (qq1.size() > qq1_size-495000) qq1.remove(qq1.size()-1);
            }
            for (int i = 0; i < local; i++) {
                qq1.add(qq.substring(1, 5));
            }

//            Thread.sleep(10); //Label_1
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

}
