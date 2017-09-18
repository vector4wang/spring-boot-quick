package spark.test.java;

import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/5/5
 * Time: 11:50
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        // 稠密向量
        Vector denseVec = Vectors.dense(1.0,2.0,3.0);
        System.out.println(denseVec);
        // 稠密向量
        Vector sparseVec = Vectors.sparse(4,new int[]{0,2},new double[]{1.0,2.0});
        System.out.println(sparseVec);
    }
}
