package com.eps.mdp.project.dp.jobs;

import com.eps.mdp.project.dp.mappers.LengthOfMessageMap;
import com.eps.mdp.project.dp.mappers.MostPopularWordMap;
import com.eps.mdp.project.dp.mappers.WordCountMap;
import com.eps.mdp.project.dp.reducers.LengthOfMessageReduce;
import com.eps.mdp.project.dp.reducers.MostPopularWordReduce;
import com.eps.mdp.project.dp.reducers.WordCountReduce;
import com.eps.mdp.project.dp.utils.JobRunner;
import com.eps.mdp.project.dp.utils.JsonInputFormat;
import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by tomaszdabek on 27.06.2017.
 */
public class MostPopularWordJob extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        args = new GenericOptionsParser(conf, args).getRemainingArgs();
        String inputDir = args[0];
        String outputDir = args[1];

        Job job1 = Job.getInstance(conf,"First step");
        job1.setInputFormatClass(JsonInputFormat.class);
        job1.setJarByClass(MostPopularWordJob.class);
        job1.setMapperClass(WordCountMap.class);
        job1.setReducerClass(WordCountReduce.class);
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job1, new Path(inputDir));
        FileOutputFormat.setOutputPath(job1, new Path(Resources.TMP_PATH));
        ControlledJob cJob1 = new ControlledJob(conf);
        cJob1.setJob(job1);


        Job job2 = Job.getInstance(conf,"second step");
        job2.setJarByClass(MostPopularWordJob.class);
        job2.setMapperClass(MostPopularWordMap.class);
        job2.setReducerClass(MostPopularWordReduce.class);
        job2.setNumReduceTasks(1);
        job2.setMapOutputKeyClass(NullWritable.class);
        job2.setMapOutputValueClass(Text.class);
        job2.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.addInputPath(job2, new Path(Resources.TMP_PATH));
        FileOutputFormat.setOutputPath(job2, new Path(outputDir));
        ControlledJob cJob2 = new ControlledJob(conf);
        cJob2.setJob(job2);


        //Jobs combining
        JobControl jobctrl = new JobControl("jobctrl");
        jobctrl.addJob(cJob1);
        jobctrl.addJob(cJob2);
        cJob2.addDependingJob(cJob1);

        //jobs running
        Thread jobRunnerThread = new Thread(new JobRunner(jobctrl));
        jobRunnerThread.start();
        while (!jobctrl.allFinished()) {
            System.out.println("Still running...");
            Thread.sleep(5000);
        }
        System.out.println("done");
        jobctrl.stop();


        // Cleaning item data
        FileSystem fs = new RawLocalFileSystem();
        fs.delete(new Path(Resources.TMP_PATH), true);
        fs.close();
        return 0;
    }

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new MostPopularWordJob(), args);
        System.exit(exitCode);
    }


}
