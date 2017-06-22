package com.eps.mdp.project.dp.mappers;

import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by tomaszdabek on 21.06.2017.
 */
public class UsernameComparisonMap extends Mapper<LongWritable, MapWritable, Text, Text> {

        private static Text userKey = new Text(Resources.AUTHORKEY);

        private static Text sourceKey = new Text(Resources.SOURCEKEY);

        private static Text outputKey = new Text();

        @Override
        protected void map(LongWritable key, MapWritable value, Mapper.Context context) throws IOException, InterruptedException {
        String author = value.get(userKey).toString();
        author = author.toLowerCase();
        outputKey.set(author);
        context.write(outputKey,value.get(sourceKey));
    }
}
