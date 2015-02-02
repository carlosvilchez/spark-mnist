name := "Spark MNIST"

version := "0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.scalatest"       %% "scalatest"     % "2.0"    % "test",
  "org.apache.spark"    %% "spark-core"    % "1.2.0",
  "org.apache.spark"    %% "spark-mllib"   % "1.2.0",
  "org.apache.hadoop"   %  "hadoop-client" % "2.4.0",
  "com.google.protobuf" %  "protobuf-java" % "2.3.0",
  "log4j"               %  "log4j"         % "1.2.14"
)
