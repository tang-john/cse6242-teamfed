package edu.gatech.teamfed.transform

object TransformRunner extends App {

  var baseDir = "C:\\Users\\jtang\\e-learning\\GeorgiaTech\\CSE_6242\\project\\teamfed_repo\\spark_scala_importer\\src\\main";
  val dbFile = baseDir + "\\resources\\db\\teamfed.db";

  val transform = new Transform(dbFile)
  transform model

}
