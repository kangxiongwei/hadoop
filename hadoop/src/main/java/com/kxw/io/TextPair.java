package com.kxw.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

/**
 * 实现自定义Writable
 * 
 * @author kangxiongwei
 * @date 2016年9月8日 下午11:43:33
 */
public class TextPair implements WritableComparable<TextPair> {

	private Text first;
	private Text second;

	public void set(Text first, Text second) {
		this.first = first;
		this.second = second;
	}

	public TextPair() {
		set(new Text(), new Text());
	}

	public TextPair(String first, String second) {
		set(new Text(first), new Text(second));
	}

	public TextPair(Text first, Text second) {
		set(first, second);
	}

	public Text getFirst() {
		return first;
	}

	public Text getSecond() {
		return second;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		first.readFields(in);
		second.readFields(in);
	}

	@Override
	public void write(DataOutput dataOut) throws IOException {
		first.write(dataOut);
		second.write(dataOut);
	}

	@Override
	public int compareTo(TextPair o) {
		int cmp = first.compareTo(o.first);
		if (cmp != 0) {
			cmp = second.compareTo(o.second);
		}
		return cmp;
	}

	@Override
	public int hashCode() {
		return first.hashCode() * 163 + second.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TextPair) {
			TextPair pair = (TextPair)obj;
			return first.equals(pair.first) && second.equals(pair.second);
		}
		return false;
	}

	@Override
	public String toString() {
		return first + "\t" + second;
	}
	
}
