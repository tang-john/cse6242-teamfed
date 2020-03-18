  package edu.gatech.teamfed.transform

import java.sql.{Connection, Statement}

import org.apache.spark.sql.functions.{asc, col, concat, expr, lit, round, substring, when}
import edu.gatech.teamfed.base.AbstractBase
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.IntegerType


class Transform(override val dbPath: String)  extends AbstractBase(dbPath)   {

  def model(): Boolean = {
    var result = true
    val conn = getConnection;
    val st = conn.createStatement();
    var df:DataFrame = null;

    try {
      removeTable(st)
      //createTable(st)

      val hhdDF = householdDebtByState
      val ferDF = fedEffRate
      df = joinDF(hhdDF, ferDF, "householdDebt", "effFundsRate")

      //val cpiDF = cpi


      spark.close
    } catch {
      case e: Exception => {
        println("*************************************    ERROR    *************************************")
        println(e.getMessage);
        println("*************************************    ERROR    *************************************")
        result = false;
      }
    } finally {
      st.close
      conn.close
    }

    return result
  }

  def removeTable(st:Statement): Transform = {
    val sql = "DROP TABLE IF EXISTS MasterTable";
    st.execute(sql);
    return this;
  }

  def createTable(st: Statement) : Transform = {
    val sql = "CREATE TABLE MasterTable " +
      "(" +
           " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
           " auto_loans REAL," +
           " auto_dealer_sales REAL," +
           " case_shiller_index REAL, " +
           " countyCodes TEXT, " +
           " consumer_price_index REAL, " +
           " credit_card_rate REAL, " +
           " effFundsRate REAL, " +
           " employee_cost_index REAL, " +
           " median_home_price REAL, " +
           " non_farm_employment REAL, " +
           " producer_price_index REAL, " +
           " householdDebt REAL, " +
           " quarter INTEGER, " +
           " rental_vacancy REAL, " +
           " total_employee_compensation REAL, " +
           " treasury_1m_rate REAL, " +
           " treasury_3m_rate REAL, " +
           " treasury_1yr_rate REAL, " +
           " treasury_3yr_rate REAL, " +
           " treasury_10yr_rate REAL, " +
           " unemployment_rate REAL, " +
           " urban_consumer_rent REAL, " +
           " gdp REAL, " +
           " year INTEGER, " +
           " year_qtr TEXT " +
      ")"
    st.execute(sql);

    return this;
  }


  def joinDF(df1:DataFrame, df2:DataFrame, col1:String, col2:String): DataFrame = {

    //import spark.implicits._
    val colNames = Seq("yearQtr1", "year1", "qtr1", col1, "year2", "qtr2", col2)


    val df:DataFrame = df1
      .join(df2, Seq("yearQtr"), "full")
      .toDF(colNames: _*)
      .withColumnRenamed("yearQtr1", "yearQtr")
      .withColumn("year",
        when(col("year1").isNull, col("year2")).otherwise(col("year1"))
      )
      .withColumn("qtr",
        when(col("qtr1").isNull, col("qtr2")).otherwise(col("qtr1"))
      )
      .select(
        col("yearQtr"),
        col("year"),
        col("qtr"),
        col(col1),
        col(col2)
      )
      .orderBy(asc("yearQtr"))

    df.show()

    return df;
  }

  def householdDebtByState(): DataFrame = {

    val df = spark.read.format("jdbc")
        .option("url", this.jdbcURL)
        .option("dbtable", "HouseholdDebtByState")
        .load()

    //df.show(10)

    val lowHigh = Array(col("low"), col("high"))
    val avgFunc = lowHigh.foldLeft(lit(0)){(x, y) => x+y}/lowHigh.length
    val dfAvg = df.withColumn("average", avgFunc)
    val dfYearQtrAvg = dfAvg.drop("id", "state_fips", "low", "high")
    //dfYearQtrAvg.show(10)

    val colNames3 = Seq("year", "qtr", "debt")
    val avgDF = dfYearQtrAvg.groupBy("year", "qtr")
      .sum("average")
      .toDF(colNames3: _*)
      /*.orderBy(asc("year"), asc("qtr"))*/
      .withColumn("yearQtr", concat(col("year"), lit("-"), col("qtr") ))

    //avgDF.show(10)

    val colNames4 = Seq("yearQtr", "year", "qtr", "debt")
    val hhdDF = avgDF.select(
      col("yearQtr"),
      col("year"),
      col("qtr"),
      round(col("debt"), 2)
    )
    .toDF(colNames4: _*)
    .withColumnRenamed("debt", "householdDebt")
    .orderBy(asc("yearQtr"))

    //hhdDF.show(10)

    return hhdDF;
  }

  def fedEffRate(): DataFrame = {

    val origDF = spark.read.format("jdbc")
      .option("url", this.jdbcURL)
      .option("dbtable", "FedEffFundsRate")
      .load()

    //df.show()

    val subDF = origDF.withColumnRenamed("rate", "effFundsRate")
      .withColumn("year", substring(col("date"),0,4).cast(IntegerType))
      .withColumn("month", substring(col("date"),6, 2).cast(IntegerType))
      .withColumn("day", substring(col("date"),9, 2).cast(IntegerType))

    val qtr =  List(3, 6, 9, 12)

    val efrDF = subDF.select(
      col("year"),
      col("month"),
      col("effFundsRate")
    )
      .filter(col("month").isin(qtr:_*))
      .filter("day = 1")
      .orderBy(asc("year"), asc("month"))

    //efrDF.show(100)

    val df = efrDF.withColumn("qtr",
      expr(
        "case when month = 3 then 1 " +
          " when month = 6 then 2 " +
          " when month = 9 then 3 " +
          " else 4 end"
      )
    )
      .withColumn("yearQtr", concat(col("year"), lit("-"), col("qtr") ))
      .select("yearQtr", "year", "qtr", "effFundsRate")
      .orderBy(asc("yearQtr"))

    //df.show(10)

    return df;
  }


  def cpi(): DataFrame = {

    val origDF = spark.read.format("jdbc")
      .option("url", this.jdbcURL)
      .option("dbtable", "ConsumerPriceIndex")
      .load()

    //df.show()

    val subDF = origDF.withColumnRenamed("rate", "effFundsRate")
      .withColumn("year", substring(col("date"),0,4).cast(IntegerType))
      .withColumn("month", substring(col("date"),6, 2).cast(IntegerType))
      .withColumn("day", substring(col("date"),9, 2).cast(IntegerType))

    val qtr =  List(3, 6, 9, 12)

    val efrDF = subDF.select(
      col("year"),
      col("month"),
      col("effFundsRate")
    )
    .filter(col("month").isin(qtr:_*))
    .filter("day = 1")
    .orderBy(asc("year"), asc("month"))

    //efrDF.show(100)

    val df = efrDF.withColumn("qtr",
      expr(
        "case when month = 3 then 1 " +
            " when month = 6 then 2 " +
            " when month = 9 then 3 " +
            " else 4 end"
      )
    )
    .withColumn("yearQtr", concat(col("year"), lit("-"), col("qtr") ))
    .select("yearQtr", "year", "qtr", "effFundsRate")
    .orderBy(asc("yearQtr"))

    //df.show(10)

    return df;
  }

}

