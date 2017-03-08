package javaPapers;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by chetan on 7/3/17.
 */
public class JMHStateDemo {

    @State(Scope.Thread)
    public static class MyState {
        public int a = 1;
        public int b = 2;
        public int sum;
    }


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MINUTES)
    public void testMethod(MyState state) {
        //System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");

        state.sum = state.a + state.b;
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(JMHStateDemo.class.getSimpleName()).forks(1).build();

        new Runner(options).run();
    }

}



