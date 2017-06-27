package com.eps.mdp.project.dp.mappers;

import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;


/**
 * Created by tomaszdabek on 27.06.2017.
 */
public class WordCountMap extends Mapper<LongWritable, MapWritable, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private final static Text textKey = new Text(Resources.TEXTKEY);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, MapWritable value, Context context) throws IOException, InterruptedException {
        String text = value.get(textKey).toString().toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(text);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            word.set(token);
            context.write(word, one);

        }
    }
}
