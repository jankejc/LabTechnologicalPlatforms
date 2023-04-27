import org.apache.commons.lang3.tuple.Pair;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        // I had to make paths in arguments in double quotes and Java don't parse it ino two args.
        // That is why I give first-arg path than separator "-" and second-arg path.
        String[] argss = args[0].split("-");
        
        /// NOT PARALLEL PART OF TEST
        long notParallelTestTime = System.currentTimeMillis();
        StreamManager sm = new StreamManager();
        List<Path> files = sm.collectFilenamesToCollection(argss[0]);
        Stream<Pair<String, BufferedImage>> inputPairsStream = sm.mapPathsToFilenameAndImagePairs(files);
        Stream<Pair<String, BufferedImage>> outputPairsStream =
                sm.mapFilenameAndImagePairsToFilenameAndEditedImagePairs(inputPairsStream);
        sm.saveEditedImagesToOutputDir(outputPairsStream, argss[1]);
        System.out.println("Not parallel time in milis: "
                + Long.toString(System.currentTimeMillis() - notParallelTestTime) + ".");
    
        /// PARALLEL PART OF TEST
        int threadsNumber = 4;
        int testsNumber = 5;
        for(int i = 1; i <= threadsNumber; i++)
        {
            
            long parallelTestTime[] = new long[testsNumber];
            ForkJoinPool pool = new ForkJoinPool(i);
            // Make an average of 3 attempts on every number of threads.
            for(int j = 0; j < testsNumber; j++)
            {
                long parallelTestTimeStart = System.currentTimeMillis();;
                StreamParallelManager spm = new StreamParallelManager();
                List<Path> filesParallel = spm.collectFilenamesToCollection(argss[0]);
                Stream<Pair<String, BufferedImage>> inputPairsStreamParallel
                        = spm.mapPathsToFilenameAndImagePairsParallel(filesParallel);
                Stream<Pair<String, BufferedImage>> outputPairsStreamParallel
                        = spm.mapFilenameAndImagePairsToFilenameAndEditedImagePairsParallel(inputPairsStreamParallel);
                spm.saveEditedImagesToOutputDir(outputPairsStreamParallel, argss[1]);
                parallelTestTime[j] = System.currentTimeMillis() - parallelTestTimeStart;
            }
            pool.shutdown();
            LongStream longStream = Arrays.stream(parallelTestTime);
            System.out.println("Average (of " + testsNumber + " tests) parallel time in milis for " + i + " threads: "
                    + Long.toString((long) longStream.average().orElse(0)) + ".");
        }
    }
}
