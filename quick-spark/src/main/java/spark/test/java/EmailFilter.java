package spark.test.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD;
import org.apache.spark.mllib.feature.HashingTF;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.regression.LabeledPoint;

import java.util.Arrays;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/5/5
 * Time: 10:34
 * Description:
 */
public class EmailFilter {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("垃圾邮件分类");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> ham = sc.textFile("D:\\githubspace\\springbootquick\\src\\main\\resources\\ham.txt");
        JavaRDD<String> spam = sc.textFile("D:\\githubspace\\springbootquick\\src\\main\\resources\\spam.txt");

        final HashingTF tf = new HashingTF(10000);

        JavaRDD<LabeledPoint> posExamples = spam.map(h->new LabeledPoint(1,tf.transform(Arrays.asList(h.split(" ")))));
        JavaRDD<LabeledPoint> negExamples = ham.map(s->new LabeledPoint(0,tf.transform(Arrays.asList(s.split(" ")))));


        JavaRDD<LabeledPoint> trainingData = posExamples.union(negExamples);
        trainingData.cache();

        LogisticRegressionWithSGD lrLearner = new LogisticRegressionWithSGD();
        LogisticRegressionModel model = lrLearner.run(trainingData.rdd());

        Vector posTestExample = tf.transform(Arrays.asList("O M G GET cheap stuff by sending money to ...".split(" ")));

        System.out.println(posTestExample.toJson());

        Vector negTestExample = tf.transform(Arrays.asList("Hi Dad, I started studying Spark the other ...".split(" ")));

        System.out.println("Prediction for positive test example: " + model.predict(posTestExample));
        System.out.println("Prediction for negative test example: " + model.predict(negTestExample));


    }
}
