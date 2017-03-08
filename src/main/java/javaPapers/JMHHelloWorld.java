package javaPapers;

/**
 * Created by chetan on 6/3/17.
 */
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.io.IOException;

public class JMHHelloWorld {
//@Benchmark is the annotation that tells the JMH to benchmark the method
    @Benchmark
    public void helloWorld() {
        // a dummy method to check the overhead
    }

	/*
	 * It is better to run the benchmark from command-line instead of IDE.
	 *
	 * To run, in command-line: $ mvn clean install exec:exec
	 */

    public static void main(String[] args) throws RunnerException, IOException {
//        Options options = new OptionsBuilder()
//                .include(JMHHelloWorld.class.getSimpleName()).forks(1).build();
        String filePath ="/home/chetan/cccc/"+ JMHHelloWorld.class.getSimpleName() + ".json";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        Options options = new OptionsBuilder().resultFormat(ResultFormatType.JSON).result(filePath)
                .include(JMHHelloWorld.class.getSimpleName()).forks(1).build();



        new Runner(options).run();
    }

}