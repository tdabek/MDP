package com.eps.mdp.project.dp.reducers;

import com.eps.mdp.project.dp.utils.NameHelper;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by tomaszdabek on 20.06.2017.
 */
public class LengthOfMessageReduce extends Reducer<Text,IntWritable,Text,DoubleWritable> {

    private static Text outputKey = new Text();
    private static DoubleWritable outputValue = new DoubleWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        long length = 0;
        for(IntWritable val : values){
            count+=1;
            length += val.get();
        }
        double result = (double)length/(double)count;
        outputValue.set(result);
        outputKey.set(NameHelper.resolveName(key.toString()));
        context.write(outputKey,outputValue);
    }
}
