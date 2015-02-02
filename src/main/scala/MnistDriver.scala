import org.apache.log4j.Logger
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.apache.spark.mllib.tree.RandomForest
import HelperFunctions._


object MnistDriver {

  val logger = Logger.getLogger(getClass().getName())

  val errorMsg =
    """ MnistSpark needs 2 parameters to start:
      |
      | 1) A train CSV file.
      | 2) A test CSV file.
      |
    """.stripMargin
  
  def main(args: Array[String]) {
    if (args.length < 2) {
      println(errorMsg)
      throw new RuntimeException("Not enough args")
    }

    val trainFilePath = args(0)
    val testFilePath = args(1)

    val conf = new SparkConf()
      .setAppName("MNIST resolver")
      .setMaster("local")
    val sc = new SparkContext(conf)


    val rawTrainData = sc.textFile(trainFilePath).cache()
    val data = getLabeledPoints(rawTrainData)

    val trainAndCVAndTestData = data.randomSplit(Array(0.8, 0.2))

    val trainData = trainAndCVAndTestData(0).cache()
    val cvData = trainAndCVAndTestData(1).cache()

    val forest = RandomForest.trainClassifier(
      trainData, 10, Map.empty, 25,
      "auto", "gini", 20, 300)

    val prediction = cvData.map{ example =>
      (forest.predict(example.features), example.label)
    }

    val metrics = new MulticlassMetrics(prediction)

    // Accuracy: % of examples classified correctly
    logger.info(s"metrics.precision: ${metrics.precision}")

    val rawResData = sc.textFile(testFilePath).cache()
    val resData = getProblemCSV(rawResData)


    val resPredictionsAndLabels = forest.predict(resData)
    resPredictionsAndLabels.saveAsTextFile("results")
  }
}
