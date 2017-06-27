package com.eps.mdp.project.dp.mappers;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by tomaszdabek on 27.06.2017.
 */
public class MostPopularWordMap extends Mapper<LongWritable, Text, NullWritable, Text> {

    private TreeMap<Integer, String> localTop = new TreeMap<>();

    private final static Text outputValue = new Text();

    @Override
    public void map(LongWritable inputKey, Text value, Context context) {
        String[] splited = value.toString().split("\\s+");
        localTop.put(Integer.valueOf(splited[1]), splited[0]);
        if (localTop.size() > 40) {
            localTop.remove(localTop.firstKey());
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Integer key : localTop.keySet()) {
            outputValue.set(localTop.get(key) + " " + key.toString());
            context.write(NullWritable.get(), outputValue);
        }
    }
}
