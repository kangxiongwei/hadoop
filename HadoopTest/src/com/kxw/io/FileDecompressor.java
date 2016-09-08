package com.kxw.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

/**
 * 文件解压缩，根据文件后缀判断是用哪种算法压缩的文件，然后解压缩
 * 
 * @author kangxiongwei
 * @date 2016年9月7日 下午11:58:36
 */
public class FileDecompressor {
	
	public static void main(String[] args) throws Exception {
		String uri = "hdfs://localhost:9000/user/kangxiongwei/test.gz";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		//通过该方法将压缩文件映射到相应的压缩算法实现上，压缩算法都要实现CompressionCodec接口
		CompressionCodec codec = factory.getCodec(new Path(uri));
		if(codec == null) {
			System.err.println("No codec found for " + uri);
			System.exit(1);
		}
		//去掉压缩文件的后缀名
		String outputUri = CompressionCodecFactory.removeSuffix(uri, codec.getDefaultExtension());
		InputStream in = null;
		OutputStream out = null;
		try {
			in = codec.createInputStream(fs.open(new Path(uri)));
			out = fs.create(new Path(outputUri));
			IOUtils.copyBytes(in, out, conf);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
		
	}
	

}
