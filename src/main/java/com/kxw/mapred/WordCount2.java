package com.kxw.mapred;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by kangxiongwei on 2017/5/27.
 */
public class WordCount2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        //使用yarn提交任务
        Configuration conf = new Configuration();
        //本地运行模式，改为yarn为集群模式
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("yarn.resourcemanager.hostname", "mini1");
        conf.set("fs.defaultFS", "hdfs://mini1:9000/");
        Job job = Job.getInstance(conf);

        //指定jar包所在路径
        job.setJarByClass(WordCount2.class);
        //job.setJar("/Users/kangxiongwei/JavaSoft/workspace/hadoop/target/hadoop-0.0.1-SNAPSHOT.jar");
        //指定使用的Map和Reduce
        job.setMapperClass(WordMapper.class);
        job.setReducerClass(WordReducer.class);

        //指定分区
        //job.setPartitionerClass(WordPartition.class);
        //指定reduce的大小。需要和分区数量一致或者大于分区数量
        //job.setNumReduceTasks(4);


        //设置输入流格式,合并小文件的一种策略，默认为TextFileInputStream
        /*job.setInputFormatClass(CombineFileInputFormat.class);
        CombineFileInputFormat.setMaxInputSplitSize(job, 4000000);
        CombineFileInputFormat.setMinInputSplitSize(job, 2000000);*/


        //指定输入输出的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //指定输入输出路径
        FileInputFormat.setInputPaths(job, new Path("hdfs://mini1:9000/wordcount/input"));
        FileOutputFormat.setOutputPath(job, new Path("hdfs://mini1:9000/wordcount/output10"));
        //提交作业
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }

    static class WordMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] split = line.split(" ");
            for (String s: split) {
                context.write(new Text(s), new IntWritable(1));
            }
        }
    }

    static class WordReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            Iterator<IntWritable> iterator = values.iterator();
            int res = 0;
            while (iterator.hasNext()) {
                int v = iterator.next().get();
                res += v;
            }
            context.write(key, new IntWritable(res));
        }
    }


}
