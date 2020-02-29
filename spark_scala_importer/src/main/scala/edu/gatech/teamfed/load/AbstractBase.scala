package edu.gatech.teamfed.load

import java.sql.{Connection, DriverManager, Statement}
import java.util.Properties

import org.apache.spark.sql.types.{DoubleType, LongType, StringType, StructField, StructType}
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

abstract class AbstractBase(val dbPath: String)  {


  val spark = SparkSession.builder()
    .appName("Import House Hold Debt By County")
    .config("spark.master", "local")
    .getOrCreate();

  val jdbcURL = "jdbc:sqlite:" + this.dbPath;

  def getCsvFile(): String;
  def getSqlDropTable(): String;
  def getSqlCreateTable(): String;
  def getTable(): String;
  def getSchema():StructType;

  //Method to load the data.
  def load: Unit = {

    val df = this.getDF();
    val conn = getConnection;
    val st = conn.createStatement();
    var success = createTable(st);
    if(success) {
      insertData(st, df);
    }

    st.close();
    conn.close();

    df.show(10,false);
    //df.printSchema();

  }


  def getConnection(): Connection = {
    val conn = DriverManager.getConnection (this.jdbcURL);
    return conn;
  }


  def getDF(): DataFrame = {

    val df = spark.read
      .option("header", "true")
      .schema(getSchema)
      .csv(getCsvFile)

    /*
    val year = df.col("year")
    val subDF = df.select(
     col("year"),
     col("qtr"),
     col("area_fips")
    )

    */

    return df;
  }


  def createTable(st: Statement): Boolean  = {
    var result = true;

    try {
      st.executeUpdate(getSqlDropTable);
      st.executeUpdate(getSqlCreateTable);
    } catch {
      case e: Exception => {
        println(e.getMessage);
        result = false;
      }
    }

    return result;
  }


  def insertData(statement: Statement, df: DataFrame): Boolean = {
    var result = true;
    val prop = new Properties
    prop.put("jdbcUrl", this.jdbcURL)
    df.write
      .mode(SaveMode.Append)
      .jdbc(url=jdbcURL, getTable(), prop);

    return result;
  }

}
