package edu.gatech.teamfed.base

import org.apache.spark.sql.DataFrame

abstract class AbstractTransform(override val dbPath: String)  extends AbstractBase(dbPath) {



}
