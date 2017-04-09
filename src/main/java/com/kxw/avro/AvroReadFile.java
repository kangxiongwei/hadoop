package com.kxw.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.util.Utf8;

@SuppressWarnings("deprecation")
public class AvroReadFile {

	public static void main(String[] args) throws IOException {
		Schema.Parser parser = new Schema.Parser();
		Schema schema = parser.parse(AvroReadFile.class.getClassLoader().getResourceAsStream("StringPair.avsc"));

		GenericRecord record = new GenericData.Record(schema);
		record.put("left", new Utf8("L"));
		record.put("right", new Utf8("R"));
		
		//序列化记录到输出流中
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		//DatumWriter将数据对象翻译为Encoder可以理解的对象，然后Encoder将数据写出到输出流
		DatumWriter<GenericRecord> writer = new GenericDatumWriter<GenericRecord>(schema);
		Encoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		writer.write(record, encoder);
		encoder.flush();
		out.close();

		//从字节缓冲区读取记录
		DatumReader<GenericRecord> reader = new GenericDatumReader<GenericRecord>(schema);
		Decoder decoder = DecoderFactory.get().createBinaryDecoder(out.toByteArray(), null);
		GenericRecord result = reader.read(null, decoder);
		System.out.println(result.get("left").toString());
		System.out.println(result.get("right").toString());
	}

}
