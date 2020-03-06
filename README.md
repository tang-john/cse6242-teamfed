# CSE-6242 Team Fed Project Repository

deliverables/proposal_submission
--------------------
These files were submitted as part of the Proposal assignement which was due on 2/29/2020 with 2 day grace period.

spark_scala_importer 
---------------------
This is a Intellij sbt project of the Apache Spark Scala code that imports CSV data into SQLite.  

Requirements:
1. Java 1.8+
2. IntelliJ community edition or enterprise (GA students have access to enterprise but it's not required)
  * Add park_scala_importer\winutils\ to your Windows PATH. Linux OS does not need this file. This is to trick Spark that Haddop is installed on your computer.
  * Add Scala support when installing IntelliJ. You can also install it using step below.
  * Add plugins by selecting from the menu bar: File -> Settings -> Plugins. Type in a plugin name in the search bar.
  * Add "Database Navigator" plugin
  * Add "CSV Plugin"
  * Add "Rainbow Plugin"
  * Add "AWS Toolkit" plugin if you want to work on AWS
  
  
Use the following instructions to view SQLite database from within IntelliJ. Make sure you have the Database Navigator plugin installed with IntelliJ.

1. Select from the menu bar: View -> Tools Windows -> DB Browser. The DB Browser tab will appear.
2. Click on the + sign from within the DB Browser.
3. Select SQLite
4. Enter for the name field: TeamFed
5. The driver source should be built-in library.
6. Look for the "Database files" text box. It's not really a text box but it looks like it. Click on the + sign next to it.
7. An empty row will appear. Type in the location of the fully qualified path to the teamfed.db file. You can also click on the three dots to navigate to the teamfed.db file. It should be in a path similar to C:\Users\jtang\e-learning\GeorgiaTech\CSE_6242\project\data\import\src\main\resources\db\teamfed.db
8. Click the Test Connection button. 
9. Click the OK button. 
10. You will now see a TeamFed connection. Right click on it.
11. Click on connect. You will now see Consoles and Schemas after connecting.
12. Click on Schemas -> main -> Taables
13. Look for the gear icon in DB Browser menu bar. Hover on the icon to the LEFT of it. You will see "Open SQL Console". Click on it.
15. Click on Connection.
16. A connection panel will open where you can execute SQL.
  
  

