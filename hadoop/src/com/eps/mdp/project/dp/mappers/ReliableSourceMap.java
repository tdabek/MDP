package com.eps.mdp.project.dp.mappers;

import com.eps.mdp.project.dp.utils.Resources;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tomaszdabek on 22.06.2017.
 */
public class ReliableSourceMap extends Mapper<LongWritable, MapWritable, Text, IntWritable> {

    private static Text textKey = new Text(Resources.TEXTKEY);

    private static Text sourceKey = new Text(Resources.SOURCEKEY);

    private static IntWritable outputValue = new IntWritable(1);

    private static Text outputKey = new Text();

    @Override
    protected void map(LongWritable key, MapWritable value, Mapper.Context context) throws IOException, InterruptedException {
        String text = value.get(textKey).toString();
        List<String> urls = extractUrls(text);
        List<String> domains = new ArrayList<>();
        for(String url: urls){
            try {
                String domain = getDomainName(url);
                if(!domain.equals(""))
                    domains.add(domain);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        for(String domain: domains){
            outputKey.set(domain);
            context.write(outputKey,outputValue);
        }
    }

    public static String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        if (domain!=null)
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        return "";
    }

    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }
}
