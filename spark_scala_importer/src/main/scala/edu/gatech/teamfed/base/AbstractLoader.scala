package edu.gatech.teamfed.base

import java.sql.Statement
import java.util.Properties

import org.apache.spark.sql.{DataFrame, SaveMode}
import org.apache.spark.sql.types.StructType

abstract class AbstractLoader(override val dbPath: String) extends AbstractBase(dbPath) {
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
    val success = createTable(st);
    if(success) {
      insertData(st, df);
      df.show(10,false);
      //df.printSchema();
    } else {
      System.out.println("Failure");
    }

    st.close();
    conn.close();

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
    val result = true;
    val prop = new Properties
    prop.put("jdbcUrl", this.jdbcURL)
    df.write
      .mode(SaveMode.Append)
      .jdbc(url=jdbcURL, getTable(), prop);

    return result;
  }

}
