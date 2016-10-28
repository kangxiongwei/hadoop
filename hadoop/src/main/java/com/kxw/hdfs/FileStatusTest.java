package com.kxw.hdfs;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

/**
 * 查询文件系统状态
 * 
 * @author kangxiongwei
 * @date 2016年9月7日 下午10:32:41
 */
public class FileStatusTest {

	public static void main(String[] args) throws Exception {
		String uri = "hdfs://localhost:9000/user/kangxiongwei";
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		FileStatus stat = fs.getFileStatus(new Path(uri));
		System.out.println(stat.getPath().toUri().getPath());
		System.out.println(stat.isDir());
		System.out.println(stat.getModificationTime());
		System.out.println(stat.getLen());
		System.out.println(stat.getReplication());
		System.out.println(stat.getBlockSize());
		System.out.println(stat.getOwner());
		System.out.println(stat.getGroup());
		System.out.println(stat.getPermission().toString());
		System.out.println("-----------------------------");

		String path1 = uri;
		String path2 = uri + "/testDir";
		String path3 = path2 + "/1901";
		String path4 = path2 + "/1902";
		Path[] paths = new Path[4];
		paths[0] = new Path(path1);
		paths[1] = new Path(path2);
		paths[2] = new Path(path3);
		paths[3] = new Path(path4);

		FileStatus[] stats = fs.listStatus(paths);
		Path[] result = FileUtil.stat2Paths(stats);
		for (Path p : result) {
			System.out.println(p);
		}
		System.out.println("-----------------------------");
		
		FileStatus[] stats2 = fs.listStatus(paths[1]);
		result = FileUtil.stat2Paths(stats2);
		for (Path p : result) {
			System.out.println(p);
		}
		System.out.println("-----------------------------");

	}

}
