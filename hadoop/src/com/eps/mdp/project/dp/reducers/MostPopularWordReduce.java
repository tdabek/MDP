package com.eps.mdp.project.dp.reducers;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by tomaszdabek on 27.06.2017.
 */
public class MostPopularWordReduce extends Reducer<NullWritable, Text, Text, IntWritable> {
    private TreeMap<Integer, String> localTop = new TreeMap<>();
    private final static IntWritable outputValue = new IntWritable();
    private final static Text outputKey = new Text();

    @Override
    public void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            String[] splited = value.toString().split("\\s+");
            localTop.put(Integer.valueOf(splited[1]), splited[0]);
            if (localTop.size() > 40) {
                localTop.remove(localTop.firstKey());
            }
        }
        for (Integer mapKey : localTop.keySet()) {
            outputKey.set(localTop.get(mapKey));
            outputValue.set(mapKey);
            context.write(outputKey, outputValue);
        }
    }
}
