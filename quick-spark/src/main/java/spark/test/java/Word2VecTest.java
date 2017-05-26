package spark.test.java;

import com.google.common.base.Strings;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.feature.Word2Vec;
import org.apache.spark.mllib.feature.Word2VecModel;
import org.apache.spark.sql.SQLContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/5/5
 * Time: 14:07
 * Description:
 */
public class Word2VecTest {
    public static void main(String[] args) {


    }

    static void trainModelAndDo(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Word2Vec");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
        String sentence = Strings.repeat("a b ", 100) + Strings.repeat("a c ", 10);
        List<String> words = Arrays.asList(sentence.split(" "));
        List<List<String>> localDoc = Arrays.asList(words, words);
        JavaRDD<List<String>> doc = sc.parallelize(localDoc);
        Word2Vec word2vec = new Word2Vec()
                .setVectorSize(10)
                .setSeed(42L);
        Word2VecModel model = word2vec.fit(doc);
//        model.save(sc.sc(),"D:\\data\\sparkModel");
        Tuple2<String, Object>[] syms = model.findSynonyms("a", 2);
        System.out.println(syms.length);
        System.out.println(syms[0]._1());
        System.out.println(syms[1]._1());
    }

    static void loadModelAndDo(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Word2Vec");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);
//        sqlContext.
    }
}
