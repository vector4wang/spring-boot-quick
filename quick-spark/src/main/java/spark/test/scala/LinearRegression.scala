package com.spark.scala

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created with IDEA
  * User: vector 
  * Data: 2017/5/5
  * Time: 9:58
  * Description: 
  */
object LinearRegression {
  val conf = new SparkConf().setMaster("local").setAppName("LinearRegression")
  val sc = new SparkContext(conf)

  def main(args: Array[String]): Unit = {
    val data = sc.textFile("D:\\githubspace\\springbootquick\\src\\main\\java\\com\\quick\\scala\\lr.txt")
    val parsedData = data.map { line =>
      val parts = line.split('|')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(',').map(_.toDouble)))
    }.cache()

    val model = LinearRegressionWithSGD.train(parsedData, 2, 0.1)

    val result = model.predict(Vectors.dense(1, 3))

    println(model.weights)
    println(model.weights.size)
    println(result)
  }
}
