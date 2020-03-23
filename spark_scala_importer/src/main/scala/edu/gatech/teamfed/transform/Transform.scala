  package edu.gatech.teamfed.transform

import java.sql.{Connection, Statement}

import org.apache.spark.sql.functions.{asc, col, concat, expr, lit, round, substring, when}
import edu.gatech.teamfed.base.AbstractBase
import org.apache.spark.sql.{DataFrame, SaveMode}
import org.apache.spark.sql.types.IntegerType


class Transform(override val dbPath: String)  extends AbstractBase(dbPath)   {

  val dataPath = "C:\\Users\\jtang\\e-learning\\GeorgiaTech\\CSE_6242\\project\\teamfed_repo\\spark_scala_importer\\src\\main\\resources\\data";

  def model(): Boolean = {
    var result = true
    val conn = getConnection;
    val st = conn.createStatement();
    var df:DataFrame = null;

    try {
      //removeTable(st)
      //createTable(st)

      var df:DataFrame =  null;
      var df1:DataFrame = null;
      var df2:DataFrame = null;

      /*
      df1 = householdDebtByState
      df2 = fedData("FedEffFundsRate", "rate", "effFundsRate")

      var colNames = Seq("yearQtr1", "year1", "qtr1", "householdDebt", "year2", "qtr2", "effFundsRate")
      df = joinDF(df1, df2, colNames)
      */

      df1 = fedData("HouseHoldDebt", "debt", "householdDebt")
      df2 = fedData("FedEffFundsRate", "rate", "effFundsRate")
      df = joinDF(df1, df2, null)

      df1 = fedData("AutoDealerSales", "salesamt", "autoDealerSales")
      df = joinDF(df, df1, null)


      df1 = fedData("AutoLoan", "loanamt", "autoLoan")
      df = joinDF(df, df1, null)

      df1 = fedData("CaseShillerIndex", "indexvalue", "caseShillerIndex")
      df = joinDF(df, df1, null)

      df1 = fedData("ConsumerPriceIndex", "cpi", "cpi")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed1Year", "rate", "fed1YearYield")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed10Year", "rate", "fed10YearYield")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed30Year", "rate", "fed30YearYield")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed1Month", "rate", "fed1MonthYield")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed3Month", "rate", "fed3MonthYield")
      df = joinDF(df, df1, null)

      df1 = fedData("ProducerPriceIndex", "ppi", "ppi")
      df = joinDF(df, df1, null)

      df1 = fedData("Gdp", "gdp", "gdp")
      df = joinDF(df, df1, null)

      df1 = fedData("UrbanConsumerRent", "rent", "consumerRent")
      df = joinDF(df, df1, null)

      df1 = fedData("UnemploymentRate", "unemployment_rate", "unemploymentRate")
      df = joinDF(df, df1, null)

      df1 = fedData("HouseHoldDebtToGdp", "debt", "householdDebtToGdp")
      df = joinDF(df, df1, null)

      df1 = fedData("StudentLoan", "sloas", "studentLoan")
      df = joinDF(df, df1, null)

      df1 = fedData("MedianHomeSalesPrice", "salesamt", "medianHomePrice")
      df = joinDF(df, df1, null)

      df1 = fedData("German10Year", "rate", "german10Yr")
      df = joinDF(df, df1, null)

      df1 = fedData("Fed10YrReal", "rate", "fed10YrReal")
      df = joinDF(df, df1, null)

      df = splitYrQtr(df)

      saveData(df)

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
    val sql = "DROP TABLE IF EXISTS MasterData";
    st.execute(sql);
    return this;
  }

  def createTable(st: Statement) : Transform = {
    val sql = "CREATE TABLE MasterData " +
      "(" +
           " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
           " yearQtr REAL, " +
           " year REAL, " +
           " qtr REAL, " +
           " householdDebt REAL, " +
           " effFundsRate REAL, " +
           " cpi REAL, " +
           " auto_loans REAL," +
           " auto_dealer_sales REAL," +
           " case_shiller_index REAL, " +
           " countyCodes TEXT, " +
           " credit_card_rate REAL, " +
           " employee_cost_index REAL, " +
           " median_home_price REAL, " +
           " non_farm_employment REAL, " +
           " producer_price_index REAL, " +
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


  def joinDF(df1:DataFrame, df2:DataFrame, colNames:Seq[String]): DataFrame = {

    //import spark.implicits._
    //val colNames = Seq("yearQtr1", "year1", "qtr1", col1, "year2", "qtr2", col2)


    var tmpDF:DataFrame = df1
      .join(df2, Seq("yearQtr"), "full")

    if(colNames != null) {
      tmpDF = tmpDF.toDF(colNames: _*)
        .withColumnRenamed("yearQtr1", "yearQtr")
        .withColumn("year",
          when(col("year1").isNull, col("year2")).otherwise(col("year1"))
        )
        .withColumn("qtr",
          when(col("qtr1").isNull, col("qtr2")).otherwise(col("qtr1"))
        )
        .drop("qtr1")
        .drop("qtr2")
        .drop("year1")
        .drop("year2")
    }

    //tmpDF.show(2)

    val df = tmpDF
      .drop("year")
      .drop("qtr")
      .orderBy(asc("yearQtr"))
      /*
      .select(
        col("yearQtr"),
        col("year"),
        col("qtr"),
        col(col1),
        col(col2)
      )
      *
       */


    //df.show(2)

    return df;
  }

  def readTable(table: String): DataFrame = {
    val df = spark.read.format("jdbc")
      .option("url", this.jdbcURL)
      .option("dbtable", table)
      .load()

    return df
  }

  def saveData(df: DataFrame) = {
    //Write to Spark Database
    //df.write.mode(SaveMode.Overwrite).saveAsTable("MasterData")

    //Write to CSV
    val masterFile = dataPath + "\\master.csv";
    df.coalesce(1).write.option("header", "true").mode(SaveMode.Overwrite).csv(masterFile);
    //df.write.csv(masterFile);
    //df.coalesce(1).write.write(SaveMode.Overwrite).json(masterFile)
  }

  def householdDebtByState(): DataFrame = {

    val df = readTable("HouseholdDebtByState")

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

  /*
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

   */

  def fedData(table:String, idxName:String, newIdxName:String): DataFrame = {

    val qtr =  List(3, 6, 9, 12)
    val origDF = readTable(table)
      .withColumn("year", substring(col("date"),0,4).cast(IntegerType))
      .withColumn("month", substring(col("date"),6, 2).cast(IntegerType))
      .withColumn("day", substring(col("date"),9, 2).cast(IntegerType))
      .select("year", "month", idxName)
      .filter(col("month").isin(qtr:_*))
      .filter("day = 1");

    val df = origDF
      .withColumn("qtr",
        expr(
          "case when month = 3 then 1 " +
            " when month = 6 then 2 " +
            " when month = 9 then 3 " +
            " else 4 end"
        )
      )
      .withColumn("yearQtr", concat(col("year"), lit("-"), col("qtr")))
      .select("yearQtr", "year", "qtr", idxName)
      .withColumnRenamed(idxName, newIdxName)
      .orderBy(asc("year"));

    //df.show();

    return df;

  }


  def splitYrQtr(df: DataFrame): DataFrame = {
    val newDF = df.withColumn("year", substring(col("yearQtr"), 0, 4).cast(IntegerType))
        .withColumn("qtr", substring(col("yearQtr"), 6, 1))
        .select("yearQtr", "year", "qtr", "householdDebt", "effFundsRate", "autoDealerSales", "autoLoan",
          "caseShillerIndex", "cpi", "fed1YearYield", "fed10YearYield", "fed30YearYield", "fed1MonthYield",
          "fed3MonthYield", "ppi", "gdp", "consumerRent", "unemploymentRate", "householdDebtToGdp", "studentLoan",
          "medianHomePrice", "german10Yr", "fed10YrReal");

    //newDF.show();

    return newDF;
  }
}

