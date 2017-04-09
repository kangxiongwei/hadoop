package com.kxw.io;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * hadoop压缩解压缩算法
 * 
 * DEFLATE DefaultCodec
 * gzip GZipCodec
 * bzip2 BZip2Codec
 * LZO LzopCodec LZ4
 * Lz4Codec Snappy SnappyCodec
 * 
 * @author kangxiongwei
 * @date 2016年9月7日 下午11:40:48
 */
public class StreamComprocessor {

	public static void main(String[] args) throws Exception {
		String codecClassName = "org.apache.hadoop.io.compress.GzipCodec";
		Class<?> clazz = Class.forName(codecClassName);
		Configuration conf = new Configuration();
		CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clazz, conf);
		String path = "hdfs://localhost:9000/user/kangxiongwei/test.gz";
		FileSystem fs = FileSystem.get(URI.create(path), conf);
		CompressionOutputStream out = codec.createOutputStream(fs.create(new Path(path)));
		IOUtils.copyBytes(System.in, out, 4096, false);
		out.close();
	}

}
