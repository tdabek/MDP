package com.eps.mdp.project.dp.reducers;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by tomaszdabek on 22.06.2017.
 */
public class PoliteWordsReduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {

    private static DoubleWritable outputValue = new DoubleWritable();

    @Override
    protected void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        int postCounter = 0;
        double sumOfPartialRatio = 0.0;
        for (DoubleWritable val : values) {
            postCounter += 1;
            sumOfPartialRatio += val.get();
        }
        double finalAveragePoliteRatio = sumOfPartialRatio/(double)postCounter ;
        outputValue.set(finalAveragePoliteRatio);
        context.write(key, outputValue);
    }
}

