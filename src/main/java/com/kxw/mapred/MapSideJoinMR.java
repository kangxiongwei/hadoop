package com.kxw.mapred;

import com.kxw.proxy.Product;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 在Map端进行join，解决负载不均衡的情况
 * Created by kangxiongwei on 2017/5/28.
 */
public class MapSideJoinMR {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        System.setProperty("HADOOP_USER_NAME", "hadoop");
        //输入输出参数
        String input = "file:///Users/kangxiongwei/JavaSoft/workspace/hadoop/src/main/resources/rjoin/";
        //String input = "hdfs://mini1:9000/rjoin/input/";
        String output = "hdfs://mini1:9000/mapsidejoin/output/";
        args = new String[]{input, output}; //输入输出路径

        Configuration conf = new Configuration();

        //配置
        conf.set("mapreduce.framework.name", "local");
        conf.set("yarn.resourcemanager.hostname", "mini1");
        conf.set("fs.defaultFS", "hdfs://mini1:9000/");

        Job job = Job.getInstance(conf);

        //设置运行的jar
        job.setJarByClass(MapSideJoinMapper.class);
        //设置输入输出的路径
        job.setMapperClass(MapSideJoinMapper.class);
        //设置输入输出格式
        job.setOutputKeyClass(ProductOrderBean.class);
        job.setOutputValueClass(NullWritable.class);

        //输入输出文件
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        // 指定需要缓存一个文件到所有的maptask运行节点工作目录
        /* job.addArchiveToClassPath(archive); */// 缓存jar包到task运行节点的classpath中
        /* job.addFileToClassPath(file); */// 缓存普通文件到task运行节点的classpath中
        /* job.addCacheArchive(uri); */// 缓存压缩包文件到task运行节点的工作目录
        /* job.addCacheFile(uri) */// 缓存普通文件到task运行节点的工作目录

        job.addCacheArchive(new URI("file:///Users/kangxiongwei/JavaSoft/workspace/hadoop/src/main/resources/rjoin/t_product.txt"));
        //不要Reduce
        job.setNumReduceTasks(0);

        //提交任务到集群运行，等待返回结果
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);
    }


    static class MapSideJoinMapper extends Mapper<LongWritable, Text, ProductOrderBean, NullWritable> {

        private static final HashMap<String, ProductOrderBean> productMap = new HashMap<>();


        /**
         * 初始化操作,读取缓存文件，初始化到map中
         *
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("t_product.txt")));
            String line;
            while (StringUtils.isNotBlank(line = reader.readLine())) {
                String[] lines = line.split(" ");
                String pid = lines[0];
                ProductOrderBean bean = new ProductOrderBean(pid, lines[1], Integer.parseInt(lines[2]), Float.parseFloat(lines[3]), 1);
                productMap.put(pid, bean);
            }
            reader.close();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            if (StringUtils.isBlank(line)) return;
            FileSplit split = (FileSplit) context.getInputSplit();
            if (split.getPath().getName().contains("product")) return;
            String[] lines = line.split(" ");
            String pid = lines[2];
            ProductOrderBean bean = new ProductOrderBean(Integer.parseInt(lines[0]), lines[1], pid, Integer.parseInt(lines[3]), 0);
            ProductOrderBean product = productMap.get(pid);
            bean.setPname(product.getPname());
            bean.setCategaryId(product.getCategaryId());
            bean.setPrice(product.getPrice());
            context.write(bean, NullWritable.get());
        }
    }


}
