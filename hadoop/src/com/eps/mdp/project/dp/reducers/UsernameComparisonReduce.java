package com.eps.mdp.project.dp.reducers;

import com.eps.mdp.project.dp.utils.NameHelper;
import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by tomaszdabek on 21.06.2017.
 */
public class UsernameComparisonReduce extends Reducer<Text, Text, NullWritable, Text> {

    private static Text outputKey = new Text();
    private static DoubleWritable outputValue = new DoubleWritable();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        boolean fromStackOverflow = false;
        boolean fromReddit = false;
        boolean isMatch = false;
        for (Text val : values) {
            String source = val.toString();
            if (NameHelper.resolveName(source).equals(Resources.STACK)) {
                fromStackOverflow = true;
            } else
                fromReddit = true;
            if (fromReddit && fromStackOverflow)
                isMatch = true;
        }
        if (isMatch) {
            context.write(NullWritable.get(), key);
        }
    }
}
