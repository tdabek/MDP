package com.eps.mdp.project.dp.jobs;

import com.eps.mdp.project.dp.mappers.PoliteWordsMap;
import com.eps.mdp.project.dp.mappers.ReliableSourceMap;
import com.eps.mdp.project.dp.reducers.PoliteWordsReduce;
import com.eps.mdp.project.dp.reducers.ReliableSourceReduce;
import com.eps.mdp.project.dp.utils.JsonInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

/**
 * Calculate the ratio of polite (positive words)
 */
public class PoliteWordsJob extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        args = new GenericOptionsParser(conf, args).getRemainingArgs();
        String inputDir = args[0];
        String outputDir = args[1];

        DistributedCache.addCacheFile(new URI(args[2]), conf);

        Job job = Job.getInstance(conf);
        job.setJarByClass(PoliteWordsJob.class);
        job.setInputFormatClass(JsonInputFormat.class);

        job.setMapperClass(PoliteWordsMap.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(DoubleWritable.class);

        FileInputFormat.addInputPath(job, new Path(inputDir));
        FileOutputFormat.setOutputPath(job, new Path(outputDir));

        job.setReducerClass(PoliteWordsReduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);


        return (job.waitForCompletion(true) ? 0 : 1);
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new PoliteWordsJob(), args);
        System.exit(exitCode);
    }
}
