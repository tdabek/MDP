package com.eps.mdp.project.dp.reducers;

import com.eps.mdp.project.dp.utils.NameHelper;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by tomaszdabek on 22.06.2017.
 */
public class ReliableSourceReduce extends Reducer<Text,IntWritable,IntWritable,Text> {

    private static IntWritable outputValue = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable val : values){
            count += val.get();
        }
        outputValue.set(count);
        context.write(outputValue,key);
    }
}
