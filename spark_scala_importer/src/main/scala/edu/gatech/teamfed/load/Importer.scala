package edu.gatech.teamfed.load

import java.sql.Statement
import java.util.Properties

import edu.gatech.teamfed.base.{AbstractBase, AbstractLoader}
import org.apache.spark.sql.types._


class Importer(override val dbPath: String)  extends AbstractLoader(dbPath)   {

  var csvFile:String = null;
  var sqlCreate:String = null;
  var sqlDrop:String = null;
  var schema:StructType = null;
  var table:String = null;

  def config(csvFile:String, table:String, sqlCreate: String,  sqlDrop: String, schema:StructType): Importer = {
    this.csvFile = csvFile;
    this.table = table;
    this.sqlCreate = sqlCreate;
    this.sqlDrop = sqlDrop;
    this.schema = schema;
    return this;
  }

  override def getCsvFile(): String = this.csvFile;
  override def getSqlCreateTable(): String = this.sqlCreate;
  override def getSqlDropTable(): String = this.sqlDrop;
  override def getSchema(): StructType = this.schema;
  override def getTable(): String = this.table;
}