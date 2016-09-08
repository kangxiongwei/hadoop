package com.kxw.mapred;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 使用hadoop探索最高温度
 * 
 * @author kangxiongwei
 * @date 2016年9月5日 下午10:41:30
 */
public class MaxTemperature {

	/**
	 * 运行作业
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		if (args == null || args.length != 2) {
			System.err.println("必须输入两个参数");
			System.exit(-1);
		}
		
		Configuration conf = new Configuration();

		Job job = new Job(conf);
		job.setJarByClass(MaxTemperature.class);
		job.setJobName("max temperature");
		
		//对map输出进行压缩
		conf.setBoolean("mapred.compress.map.output", true);
		conf.setClass("mapred.map.output.compression.codec", GzipCodec.class, CompressionCodec.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//压缩reduce输出文件
		FileOutputFormat.setCompressOutput(job, true);
		FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

		job.setMapperClass(MaxTemperatureMapper.class);
		job.setReducerClass(MaxTempertureReducer.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

	static class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String year = line.substring(15, 19);
			int temperture;
			if (line.charAt(87) == '+') {
				temperture = Integer.parseInt(line.substring(88, 92));
			} else {
				temperture = Integer.parseInt(line.substring(87, 92));
			}
			String quality = line.substring(92, 93);
			if (temperture != 9999 && quality.matches("[01459]")) {
				context.write(new Text(year), new IntWritable(temperture));
			}
		}

	}

	static class MaxTempertureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values,
				Reducer<Text, IntWritable, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			int max = 0;
			for (IntWritable value : values) {
				max = Math.max(max, value.get());
			}
			context.write(key, new IntWritable(max));
		}

	}

}
