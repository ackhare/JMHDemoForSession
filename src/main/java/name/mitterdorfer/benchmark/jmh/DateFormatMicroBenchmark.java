package name.mitterdorfer.benchmark.jmh;

/**
 * Created by chetan on 5/3/17.
 */
// compares the multithreaded performance of different date formatting approaches in Java.
/*
three  contenders:
1)JDK SimpleDateFormat wrapped in a synchronized block: As SimpleDateFormat is not thread-safe,
we have to guard access to it using a synchronized block.

2)Thread-confined JDK SimpleDateFormat: One alternative to a global lock is to use one instance per thread.
We'd expect this alternative to scale much better than the first alternative, as there is no contention.

3)FastDateFormat from Apache Commons Lang: This class is a drop-in replacement for SimpleDateFormat (see also its Javadoc)


 how these three implementations behave when formatting a date in a multithreaded environment,
  they will be tested with one, two, four and eight benchmark threads. The key metric
  that should be reported is the time that is needed per invocation of the format method.

 */

import org.apache.commons.lang3.time.FastDateFormat;
import org.openjdk.jmh.annotations.*;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.concurrent.TimeUnit;

//JMH defines the output metric in the enum Mode
//After we've looked at the options, we choose Mode.AverageTime
@BenchmarkMode(Mode.AverageTime)
// we want the output time unit to be µs.
//When the microbenchmark is run, results will be reported as µs/op,
// i.e. how many µs one invocation of the benchmark method took. Let's move on.
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class DateFormatMicroBenchmark {
    // This is the date that will be formatted in the benchmark methods

    /*
    we need to define the three microbenchmark candidates.
    We need to keep the three implementations around during a benchmark run. That's what @State is for in JMH
    gives an instance

    define the scope here; in our case either Scope.Benchmark, i.e.
    one instance for the whole benchmark and Scope.Thread, i.e. one instance per benchmark thread.

     */
    @State(Scope.Benchmark)
    public static class DateToFormat {
        final Date date = new Date();
    }

    // These are the three benchmark candidates

    @State(Scope.Thread)
    public static class JdkDateFormatHolder {
        final Format format = DateFormat.getDateInstance(DateFormat.MEDIUM);

        public String format(Date d) {
            return format.format(d);
        }
    }

    //We defined  holder classes for each Format implementation. That's needed,
    // as we need a place to put the @State annotation
    //Later on, we can have JMH inject instances of these classes to benchmark methods.

    //JMH will ensure that instances have a proper scope


    @State(Scope.Benchmark)
    public static class SyncJdkDateFormatHolder {
        final Format format = DateFormat.getDateInstance(DateFormat.MEDIUM);

        // SyncJdkDateFormatHolder achieves thread-safety by defining #format() as synchronized.
        public synchronized String format(Date d) {
            return format.format(d);
        }
    }


    @State(Scope.Benchmark)
    public static class CommonsDateFormatHolder {
        final Format format = FastDateFormat.getDateInstance(FastDateFormat.MEDIUM);

        public String format(Date d) {
            return format.format(d);
        }
    }

    //The actual benchmark code starts from here
    /*
    First, JMH figures out that we need an instance of JdkDateFormatHolder and DateToFormat and
    injects a properly scoped instance.

    Second, the method needs to return the result in order to avoid dead-code elimination.

     @Benchmark
public String measureJdkFormat_1(JdkDateFormatHolder df, DateToFormat date) {
    return df.format(date.date);
}


    As we did not specify anything, the method will run single-threaded.
     */

    @Benchmark
    @Threads(1)
    public String measureJdkFormat_1(
            JdkDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(2)
    public String measureJdkFormat_2(
            JdkDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(4)
    public String measureJdkFormat_4(
            JdkDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(8)
    public String measureJdkFormat_8(
            JdkDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(1)
    public String measureSyncJdkFormat_1(
            SyncJdkDateFormatHolder df,
            DateToFormat date) {

        return df.format(date.date);
    }

    @Benchmark
    @Threads(2)
    public String measureSyncJdkFormat_2(
            SyncJdkDateFormatHolder df,
            DateToFormat date) {

        return df.format(date.date);
    }

    @Benchmark
    @Threads(4)
    public String measureSyncJdkFormat_4(
            SyncJdkDateFormatHolder df,
            DateToFormat date) {

        return df.format(date.date);
    }

    @Benchmark
    @Threads(8)
    public String measureSyncJdkFormat_8(
            SyncJdkDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(1)
    public String measureCommonsFormat_1(
            CommonsDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(2)
    public String measureCommonsFormat_2(
            CommonsDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(4)
    public String measureCommonsFormat_4(
            CommonsDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }

    @Benchmark
    @Threads(8)
    public String measureCommonsFormat_8(
            CommonsDateFormatHolder df,
            DateToFormat date) {
        return df.format(date.date);
    }
}

/*output

 java -jar build/libs/benchmarking-experiments-0.1.0-all.jar "name.mitterdorfer.benchmark.jmh.DateFormat.*".


 */

