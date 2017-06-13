package com.kxw.mapred;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * 自定义分区
 * Created by kangxiongwei on 2017/5/27.
 */
public class WordPartition extends Partitioner<Text, IntWritable> {

    public static HashMap<String, Integer> dict = new HashMap<String, Integer>();

    static {
        dict.put("hel", 1);
        dict.put("kan", 2);
        dict.put("wor", 3);
    }


    @Override
    public int getPartition(Text key, IntWritable value, int i) {
        String prefix = key.toString();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;
        Integer partition = dict.get(prefix);
        return partition == null ? 0 : partition;
    }
}
