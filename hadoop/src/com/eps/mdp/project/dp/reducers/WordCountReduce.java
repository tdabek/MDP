package com.eps.mdp.project.dp.reducers;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by tomaszdabek on 27.06.2017.
 */
public class WordCountReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for(IntWritable val: values){
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}
