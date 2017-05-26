package com.spark.textmatch

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by wangxc on 2017/5/13.
  */
object TextMatch {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TextMatch").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val singles = Array("this", "is")

    val sentences = Array("this Date",
      "is there something",
      "where are something",
      "this is a string")

    val rdd = sc.parallelize(sentences) // create RDD

    val keys = singles.toSet // words required as keys.

    val result = rdd.flatMap { sen =>
      val words = sen.split(" ").toSet;
      val common = keys & words; // intersect
      common.map(x => (x, sen)) // map as key -> sen
    }.groupByKey.mapValues(_.toArray) // group values for a key
      .collect
    println(result.length)
  }

}
