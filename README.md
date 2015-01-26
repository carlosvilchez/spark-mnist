# Spark-MNIST

## Using Spark to classify handwritten digits

There is a lot of information about Spark MLlib. And once you have read it, it is time to put it in practice.
This repository is an example of how to use `Decision Forests`.

## What do you need

### SBT

We will need to have SBT in our system to compile and run the application.

### Training and testing files

You can download these files from (kaggle)[https://www.kaggle.com/c/digit-recognizer/data].

## Setup

Spark-MNIST needs SBT to run. Once it is installed, you can use `sbt "run <train-csv-path>/train.csv <test-csv-path>/test.csv"` and

As the application is very memory demanding, it is probable that you will see something like this:

```
java.lang.OutOfMemoryError: Java heap space
```

It means that your JVM cannot deal with the amount of memory it needs. You can configure it using the following line:

```
export _JAVA_OPTIONS="-Xms1024m -Xmx4096m -XX:MaxPermSize=1024m"
```

It will use a maximum of 4Gb of memory, and more or less is it what it needs. So be careful if you are running the program with less than 8Gb.