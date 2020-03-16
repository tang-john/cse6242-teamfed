package edu.gatech.teamfed.base

import java.sql.{Connection, DriverManager, Statement}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

abstract class AbstractBase(val dbPath: String)  {

  val spark = SparkSession.builder()
    .appName("Import Team Fed Data")
    .config("spark.master", "local")
    .getOrCreate();

  val jdbcURL = "jdbc:sqlite:" + this.dbPath;


  def getConnection(): Connection = {
    val conn = DriverManager.getConnection (this.jdbcURL);
    return conn;
  }




}
