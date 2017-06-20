package com.eps.mdp.project.dp.mappers;

import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by tomaszdabek on 20.06.2017.
 */
public class LengthOfMessageMap extends Mapper<LongWritable, MapWritable, Text, IntWritable> {

    private static Text textKey = new Text(Resources.TEXTKEY);

    private static Text sourceKey = new Text(Resources.SOURCEKEY);

    private static Text outputKey = new Text();

    private static IntWritable outputValue = new IntWritable();

    @Override
    protected void map(LongWritable key, MapWritable value, Context context) throws IOException, InterruptedException {
        String text = value.get(textKey).toString();
        String source = value.get(sourceKey).toString();
        int length = text.split("\\s+").length;
        outputKey.set(source);
        outputValue.set(length);
        context.write(outputKey,outputValue);
    }
}
