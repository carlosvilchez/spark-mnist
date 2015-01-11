import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD

object HelperFunctions {

  def getLabeledPoints(rawData: RDD[String]): RDD[LabeledPoint] = {
    rawData.map { line =>
      val values = line.split(",").map(_.toDouble)
      val featureVector = Vectors.dense(values.tail)
      val label = values.head

      LabeledPoint(label, featureVector)
    }
  }
  def getProblemCSV(rawData: RDD[String]): RDD[Vector] = {
    rawData.map { line =>
      Vectors.dense(line.split(",").map(_.toDouble))
    }
  }

  def classProbabilities(data: RDD[LabeledPoint]): Array[Double] = {
    val countsByCategory = data.map(_.label).countByValue()
    val counts = countsByCategory.toArray.sortBy(_._1).map(_._2)
    counts.map(_.toDouble / counts.sum)
  }

  def writingResults(resultPath: String) = {
    val lines = scala.io.Source.fromFile("~/results/result").getLines().toList

    val outputFile = new java.io.PrintWriter(resultPath)

    outputFile.println("ImageId,Label")
    lines.zipWithIndex.map{ case (value, index) =>
      outputFile.println(s"${index+1},$value")
    }

    outputFile.close()
  }
}
