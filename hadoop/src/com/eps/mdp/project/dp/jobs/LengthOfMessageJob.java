package com.eps.mdp.project.dp.jobs;

import com.eps.mdp.project.dp.mappers.LengthOfMessageMap;
import com.eps.mdp.project.dp.utils.JsonInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;

/**
 * Calculate average length of message
 * Param 1 - directory with input
 */
public class LengthOfMessageJob extends Configured implements Tool  {

    @Override
    public int run(String[] args) throws Exception {

        Configuration conf = getConf();
        args = new GenericOptionsParser(conf, args).getRemainingArgs();


        Job job = Job.getInstance(conf);
        job.setJarByClass(LengthOfMessageJob.class);
        job.setInputFormatClass(JsonInputFormat.class);

        job.setMapperClass(LengthOfMessageMap.class);



        return 0;
    }

}


