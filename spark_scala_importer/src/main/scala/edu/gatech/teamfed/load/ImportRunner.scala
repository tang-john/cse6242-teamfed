package edu.gatech.teamfed.load

import org.apache.spark.sql.types.{DoubleType, IntegerType, LongType, StringType, StructField, StructType}

object ImportRunner extends App {

  var baseDir = "C:\\Users\\jtang\\e-learning\\GeorgiaTech\\CSE_6242\\project\\teamfed_repo\\spark_scala_importer\\src\\main";
  val dbFile = baseDir + "\\resources\\db\\teamfed.db";
  val importer = new Importer(dbFile);

  //Fed schema
  val schemaFed = StructType(Array(
    StructField("date", StringType),
    StructField("rate", DoubleType)
  ));



  /*
  importAreaFips
  importStateFips
  importFedEffFundsRate
  importFed1Year
  importFed10Year
  importFed30Year
  importFed1Month
  importFed3Month
  importHouseHoldDebtByCounty
  importHouseHoldDebtByState
  importHouseHoldDebtByCounty
  importHouseHoldDebtByState
  importCreditCardRate
  importStudentLoan
  importPPI
  importCPI
  importAutoLoan
  importAutoDealersSales
  importMedianHomeSalesPrice
  importCaseShillerIndex
  importUrbanConsumerRent
  importUnemploymentRate
  importTotalNonFarmEmployment
  importTotalEmployeeCompensation
  importEmployeeCostIndex
  importHouseHoldDebtToGDP
*/

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


  def importHouseHoldDebtToGDP(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("debt", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\HDTGPDUSQ163N.csv";
    val table = "HouseHoldDebtToGdp";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, debt REAL)";
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
    val csvFile = baseDir + "\\resources\\data\\GDP-mod.csv";
    val table = "Gdp";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, gdp REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importCreditCardRate(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("rate", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\TERMCBCCALLNS.csv";
    val table = "CreditCardRate";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importStudentLoan(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("sloas", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\SLOAS.csv";
    val table = "StudentLoan";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, sloas REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importPPI(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("ppi", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\PPIACO.csv";
    val table = "ProducerPriceIndex";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, ppi REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importCPI(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("cpi", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\CPIAUCSL.csv";
    val table = "ConsumerPriceIndex";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, cpi REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importAutoLoan(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("loanamt", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\CPIAUCSL.csv";
    val table = "AutoLoan";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, loanamt REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importAutoDealersSales(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("salesamt", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\MRTSSM4411USN.csv";
    val table = "AutoDealerSales";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, salesamt REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importMedianHomeSalesPrice(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("salesamt", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\MSPUS.csv";
    val table = "MedianHomeSalesPrice";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, salesamt REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importCaseShillerIndex(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("indexvalue", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\CSUSHPISA.csv";
    val table = "CaseShillerIndex";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, indexvalue REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importRentalVacancy(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("vacancyrate", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\RRVRUSQ156N.csv";
    val table = "RentalVacancy";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, vacancyrate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importUrbanConsumerRent(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("rent", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\CUSR0000SAS2RS.csv";
    val table = "UrbanConsumerRent";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, rent REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importUnemploymentRate(): Unit = {
    val schema = StructType(Array(
      StructField("date", StringType),
      StructField("unemployment_rate", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\Unemployment_Rate.csv";
    val table = "UnemploymentRate";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, unemployment_rate REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }


  def importTotalNonFarmEmployment(): Unit = {
    val schema = StructType(Array(
      StructField("series_id", StringType),
      StructField("year", IntegerType),
      StructField("period", StringType),
      StructField("value", IntegerType),
      StructField("footnote_codes", StringType)
    ));
    val csvFile = baseDir + "\\resources\\data\\TotalNonFarm.Employment.csv";
    val table = "TotalNonFarmEmployment";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, series_id TEXT, year INTEGER, period TEXT, value INTEGER, footnote_codes TEXT)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  //year,period,estimate_value,relative_standard_error
  def importTotalEmployeeCompensation(): Unit = {
    val schema = StructType(Array(
      StructField("year", IntegerType),
      StructField("period", StringType),
      StructField("estimate_value", DoubleType),
      StructField("relative_standard_error", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\total_compensation.csv";
    val table = "TotalEmpComp";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, period TEXT, estimate_value REAL, relative_standard_error REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }

  def importEmployeeCostIndex(): Unit = {
    val schema = StructType(Array(
      StructField("year", IntegerType),
      StructField("period", StringType),
      StructField("estimate_value", DoubleType),
      StructField("relative_standard_error", DoubleType)
    ));
    val csvFile = baseDir + "\\resources\\data\\employee_cost_index.csv";
    val table = "EmployeeCostIndex";
    val sqlCreate = "CREATE TABLE " + table + "(id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, period TEXT, estimate_value REAL, relative_standard_error REAL)";
    val sqlDrop = "DROP TABLE IF EXISTS " + table;

    this.importer.config(csvFile, table, sqlCreate, sqlDrop, schema).load;
  }



}


