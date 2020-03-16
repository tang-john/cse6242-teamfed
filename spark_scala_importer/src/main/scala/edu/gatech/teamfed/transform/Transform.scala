  package edu.gatech.teamfed.transform

import java.sql.{Connection, Statement}

import org.apache.spark.sql.functions.{asc, col, expr, lit, round, substring, when}
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
      createTable(st)
      val hhdDF = householdDebtByState
      val ferDF = fedEffRate


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
           " effective_funds_rate REAL, " +
           " employee_cost_index REAL, " +
           " median_home_price REAL, " +
           " non_farm_employment REAL, " +
           " producer_price_index REAL, " +
           " household_debt_by_county REAL, " +
           " household_debt_by_state REAL, " +
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

    val colNames = Seq("year", "qtr", "debt")
    val avgDF = dfYearQtrAvg.groupBy("year", "qtr")
      .sum("average")
      .toDF(colNames: _*)
      .orderBy(asc("year"), asc("qtr"))

    //avgDF.show(10)
    val hhdDF = avgDF.select(
      col("year"),
      col("qtr"),
      round(col("debt"), 2)
    ).toDF(colNames: _*)

    hhdDF.show(10)


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

    efrDF.show(100)

    val df = efrDF.withColumn("quarter",
      expr(
        "case when month = 3 then 1 " +
            " when month = 6 then 2 " +
            " when month = 9 then 3 " +
            " else 4 end"
      )
    ).select("year", "quarter", "effFundsRate")

    df.show(100)
    return df;
  }

}

