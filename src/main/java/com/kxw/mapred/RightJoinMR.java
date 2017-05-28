package com.kxw.mapred;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 右连接实现join
 *
 * 订单表： t_order
 * 产品表： t_product
 *
 * Created by kangxiongwei on 2017/5/28.
 */
public class RightJoinMR {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        //输入输出参数
        //String input = "file:///Users/kangxiongwei/JavaSoft/workspace/hadoop/src/main/resources/rjoin/";
        String input = "hdfs://mini1:9000/rjoin/input/";
        String output = "hdfs://mini1:9000/rjoin/output/";
        args = new String[] {input, output}; //输入输出路径

        Configuration conf = new Configuration();

        //配置
        conf.set("mapreduce.framework.name", "yarn");
        conf.set("yarn.resourcemanager.hostname", "mini1");
        conf.set("fs.defaultFS", "hdfs://mini1:9000/");

        Job job = Job.getInstance(conf);

        //设置运行的jar
        job.setJarByClass(RightJoinMR.class);
        //job.setJar("/Users/kangxiongwei/JavaSoft/workspace/hadoop/target/hadoop-0.0.1-SNAPSHOT.jar");
        //设置输入输出的路径
        job.setMapperClass(RightJoinMapper.class);
        job.setReducerClass(RightJoinReducer.class);
        //设置输入输出格式
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(ProductOrderBean.class);
        job.setOutputKeyClass(ProductOrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        //输入输出文件
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //提交任务到集群运行，等待返回结果
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }

    /**
     * 解析文件的每一行数据，将pid作为key，内容作为value输出到Reduce
     */
    static class RightJoinMapper extends Mapper<LongWritable, Text, Text, ProductOrderBean> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            FileSplit split = (FileSplit) context.getInputSplit();
            String fileName = split.getPath().getName();
            ProductOrderBean bean;
            String pid;
            if (StringUtils.isBlank(line)) return;
            String[] lines = line.split(" ");
            if (fileName.contains("order")) {
                //订单表
                pid = lines[2];
                bean = new ProductOrderBean(Integer.parseInt(lines[0]), lines[1], pid, Integer.parseInt(lines[3]), 0);
            } else {
                //产品表
                pid = lines[0];
                bean = new ProductOrderBean(pid, lines[1], Integer.parseInt(lines[2]), Float.parseFloat(lines[3]), 1);
            }
            context.write(new Text(pid), bean);
        }
    }

    /**
     * 获取每个pid所对应的所有信息，其中，产品应该只有一个，但是订单有多个，需要合并并输出
     */
    static class RightJoinReducer extends Reducer<Text, ProductOrderBean, ProductOrderBean, NullWritable> {

        @Override
        protected void reduce(Text key, Iterable<ProductOrderBean> values, Context context) throws IOException, InterruptedException {
            ProductOrderBean product = new ProductOrderBean();
            List<ProductOrderBean> orderList = new ArrayList<>();

            for (ProductOrderBean bean: values) {
                if (bean.getFlag() == 1) {
                    try {
                        BeanUtils.copyProperties(product, bean);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    ProductOrderBean order = new ProductOrderBean();
                    try {
                        BeanUtils.copyProperties(order, bean);
                        orderList.add(order);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            //已经将一个pid对应的产品、订单封装好，输出
            for (ProductOrderBean bean: orderList) {
                bean.setCategaryId(product.getCategaryId());
                bean.setPrice(product.getPrice());
                bean.setPname(product.getPname());
                context.write(bean, NullWritable.get());
            }
        }
    }


}
