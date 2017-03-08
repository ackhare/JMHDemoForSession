package javaPapers;

/**
 * Created by chetan on 6/3/17.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
/*
Let us know see a practical example. Assume that we need to sort a list of integer
 numbers and we should decide what data structure/API should be used for sorting the numbers.


 There are two options available either to use primitive int and Arrays or use Integer and Collections.sort.
 */
@State(Scope.Thread)
public class JMHSortBenchmark {

    List<Integer> arrayList;
    int[] array;
    Random random;
//The method is called once for each time for each full run of the benchmark.
// A full run means a full "fork" including all warmup and benchmark iterations.

    //Level.Iteration 	The method is called once for each iteration of the benchmark.
    @Setup(Level.Trial)
    public void init() {
        random = new Random();
        array = new int[25];
        arrayList = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            int randomNumber = random.nextInt();
            array[i] = randomNumber;
            arrayList.add(new Integer(randomNumber));
        }
    }

    @Benchmark
    public void arraysSort() {
        Arrays.sort(array);
    }

    @Benchmark
    public void collectionsSort() {
        Collections.sort(arrayList);
    }

    public static void main(String[] args) throws RunnerException {

        Options options = new OptionsBuilder()
                .include(JMHSortBenchmark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true).shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();

    }
}
/*


JMH Benchmark to Sort 25 integers
Benchmark                          Mode  Cnt         Score        Error  Units
JMHSortBenchmark.arraysSort       thrpt   20  28779058.746   446412.014  ops/s
JMHSortBenchmark.collectionsSort  thrpt   20  26070145.869   206077.160  ops/s


JMH Benchmark to Sort 1000 integers
Benchmark                          Mode  Cnt        Score       Error  Units
JMHSortBenchmark.arraysSort       thrpt   20  3795959.757   43679.226  ops/s
JMHSortBenchmark.collectionsSort  thrpt   20   853014.250    6256.061  ops/s

 */

/*
When the number of integers to sort increases, collections sort API decreases in performance.
 There is no significant difference in the primitive side even when the count varies significantly.
 */