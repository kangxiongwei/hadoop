package com.kxw.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.RawComparator;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparator;

/**
 * hadoop序列化和反序列化测试
 * 
 * @author kangxiongwei
 * @date 2016年9月8日 下午11:10:27
 */
public class WritableTest {

	public static byte[] serialize(Writable writable) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataOut = new DataOutputStream(out);
		writable.write(dataOut);
		dataOut.close();
		return out.toByteArray();
	}

	public static byte[] deserialize(Writable writable, byte[] bytes) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		DataInputStream dataIn = new DataInputStream(in);
		writable.readFields(dataIn);
		in.close();
		return bytes;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		IntWritable writable = new IntWritable(163);
		byte[] bytes = serialize(writable);
		System.out.println(bytes.length);
		IntWritable other = new IntWritable();
		deserialize(other, bytes);
		System.out.println(other.get());

		// 比较器 RawComparator可以直接比较字节流大小，无需反序列化成对象再进行比较
		RawComparator<IntWritable> rawComparator = WritableComparator.get(IntWritable.class);
		IntWritable int1 = new IntWritable(1);
		IntWritable int2 = new IntWritable(2);
		int result1 = rawComparator.compare(int1, int2);
		System.out.println(result1);

		byte[] bytes1 = serialize(int1);
		byte[] bytes2 = serialize(int2);
		int result2 = rawComparator.compare(bytes1, 0, bytes1.length, bytes2, 0, bytes2.length);
		System.out.println(result2);
	}

}
