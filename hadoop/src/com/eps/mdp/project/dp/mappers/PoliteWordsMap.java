package com.eps.mdp.project.dp.mappers;

import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * Created by tomaszdabek on 22.06.2017.
 */
public class PoliteWordsMap extends Mapper<LongWritable, MapWritable, Text, DoubleWritable> {

    private static Text textKey = new Text(Resources.TEXTKEY);

    private static Text sourceKey = new Text(Resources.SOURCEKEY);

    private static Text outputKey = new Text();

    private static DoubleWritable outputValue = new DoubleWritable();

    private HashSet<String> politeWords = new HashSet<>();

    @Override
    protected void setup(Context context) throws IOException {
        Path[] cacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        fillHashSetFromCachedTextFile(cacheFiles[0].toUri(), politeWords);
    }

    private void fillHashSetFromCachedTextFile(URI cacheFile, HashSet<String> hashSet) throws IOException {
        File file = new File(cacheFile);

        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            String line = br.readLine();

            while (line != null) {
                if (!line.startsWith(";") && !line.isEmpty())
                    hashSet.add(line);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }

    @Override
    protected void map(LongWritable key, MapWritable value, Mapper.Context context) throws IOException, InterruptedException {
        String text = value.get(textKey).toString().toLowerCase();

        int politeWordsCounter = 0;

        int postLength = text.split("\\s+").length;
        if(postLength==0)
            return;
        for(String word: politeWords){
            if(text.contains(word)){
                politeWordsCounter += word.split("\\s+").length;
            }
        }

        double politeWordsRatio = (double)politeWordsCounter/(double)postLength;
        outputValue.set(politeWordsRatio);
        context.write(value.get(sourceKey),outputValue);
    }
}
