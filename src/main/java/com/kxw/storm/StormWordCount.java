package com.kxw.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 用storm实时计算wordcount
 * Created by kangxiongwei3 on 2017/6/19 14:37.
 */
public class StormWordCount {

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("storm_wc", new WcSpout(), 2);
        builder.setBolt("storm_word", new WcWordBolt(), 2).shuffleGrouping("storm_wc");
        builder.setBolt("storm_count", new WcCountBolt(), 4).fieldsGrouping("storm_word", new Fields("word"));

        Config conf = new Config();
        conf.setNumWorkers(2);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word_count", conf, builder.createTopology());

    }

    //输入
    static class WcSpout extends BaseRichSpout {

        private SpoutOutputCollector collector;
        private Random random;


        @Override
        public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
            this.collector = collector;
            this.random = new Random();
        }

        @Override
        public void nextTuple() {
            Utils.sleep(1000);
            String[] sentences = new String[]{"111 222 333 4444 555 666",
                    "111 222 333 4444 555 666",
                    "111 222 333 4444 555 666",
                    "111 222 333 4444 555 666", "111 222 333 4444 555 666"};
            String sentence = sentences[random.nextInt(sentences.length)];
            collector.emit(new Values(sentence));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }

    }


    static class WcWordBolt extends BaseRichBolt {

        private OutputCollector collector;

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            String sentence = input.getString(0);
            String[] sub = sentence.split(" ");
            for (String s : sub) {
                collector.emit(new Values(s));
            }
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word"));
        }
    }

    static class WcCountBolt extends BaseRichBolt {

        private OutputCollector collector;
        Map<String, Integer> counts = new HashMap<String, Integer>();

        @Override
        public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
            this.collector = collector;
        }

        @Override
        public void execute(Tuple input) {
            String word = input.getString(0);
            if (counts.containsKey(word)) {
                counts.put(word, counts.get(word) + 1);
            } else {
                counts.put(word, 1);
            }
            collector.emit(new Values(word, counts.get(word)));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
            declarer.declare(new Fields("word", "count"));
        }
    }

}
