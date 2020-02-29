package edu.gatech.teamfed.load

import org.apache.spark.sql.types.{DoubleType, LongType, StringType, StructField, StructType}

object ImportRunner extends App {

  var baseDir = "C:\\Users\\jtang\\e-learning\\GeorgiaTech\\CSE_6242\\project\\teamfed_repo\\src\\main";
  val dbFile = baseDir + "\\resources\\db\\teamfed.db";
  val importer = new Importer(dbFile);


  //Fed schema
  val schemaFed = StructType(Array(
    StructField("date", StringType),
    StructField("rate", DoubleType)
  ));

  importFedEffFundsRate
  importFed1Year
  importFed10Year
  importFed30Year
  importFed1Month
  importFed3Month
  importHouseHoldDebtByCounty
  importHouseHoldDebtByState
  importAreaFips
  importStateFips
  importGdp

  def importFedEffFundsRate(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\DFF.csv";
    val table = "FedEffFundsRate";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;

  }

  def importFed1Year(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\GS1.csv";
    val table = "Fed1Year";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;
  }

  def importFed10Year(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\GS10.csv";
    val table = "Fed10Year";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;
  }

  def importFed30Year(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\GS30.csv";
    val table = "Fed30Year";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;
  }

  def importFed1Month(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\GS1M.csv";
    val table = "Fed1Month";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;
  }

  def importFed3Month(): Unit = {
    val csvFile = baseDir + "\\resources\\data\\GS3M.csv";
    val table = "Fed3Month";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schemaFed).load;
  }

  def importHouseHoldDebtByCounty(): Unit = {
    val schema = StructType(Array(
      StructField("year", LongType),
      StructField("qtr", LongType),
      StructField("area_fips", StringType),
      StructField("low", DoubleType),
      StructField("high", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\household-debt-by-county.csv";
    val table = "HouseHoldDebtByCounty";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, qtr INTEGER, area_fips TEXT, low REAL, high REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importHouseHoldDebtByState(): Unit = {
    val schema = StructType(Array(
      StructField("year", LongType),
      StructField("qtr", LongType),
      StructField("state_fips", StringType),
      StructField("low", DoubleType),
      StructField("high", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\household-debt-by-state.csv";
    val table = "HouseHoldDebtByState";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, qtr INTEGER, state_fips TEXT, low REAL, high REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }


  def importAreaFips(): Unit = {
    val schema = StructType(Array(
      StructField("area_fips", StringType),
      StructField("area_title", StringType)
    ));
    val csvFile = baseDir + "\\resources\\data\\area_fips.csv";
    val table = "AreaFips";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, area_fips TEXT, area_title TEXT)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }


  def importStateFips(): Unit = {
    val schema = StructType(Array(
      StructField("stname", StringType),
      StructField("st", StringType),
      StructField("stusps", StringType)
    ));
    val csvFile = baseDir + "\\resources\\data\\state_fips.csv";
    val table = "StateFips";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, stname TEXT, st TEXT, stusps TEXT)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }


  def importGdp(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("gdp", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\gdp.csv";
    val table = "Gdp";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, gdp REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }



}

