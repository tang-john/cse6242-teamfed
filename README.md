# CSE-6242 Team Fed Project Repository


dataset
--------------------
These data files were imported into SQLite using the custom Scala Spark code under the spark_scala_importer folder

| Description              | FIle                           | Source
|--------------------------|--------------------------------|-------------------------------------------------
| 1 Month Treasury         | GS1M.csv                       | https://fred.stlouisfed.org/series/GS1M
| 3 Month Treasury         | GS3M.csv                       | https://fred.stlouisfed.org/series/GS3
| 1 Year Treasury          | GS1.CSV                        | https://fred.stlouisfed.org/series/GS10
| 10 Year Treasury         | GS10.csv                       | https://fred.stlouisfed.org/series/GS10
| 30 Year Treasury         | GS30.csv                       | https://fred.stlouisfed.org/series/GS30
| Automobile Loans         | CARACBW027SBOG.csv             | https://fred.stlouisfed.org/series/CARACBW027SBOG
| Auto Dealer Sales        | MRTSSM4411USN.csv              | https://fred.stlouisfed.org/series/MRTSSM4411USN
| Consumer Price Index     | CPIAUCSL.csv                   | https://fred.stlouisfed.org/series/CPIAUCSL
| County Codes             | area_fips.csv                  | https://data.bls.gov/cew/doc/titles/area/area_titles.htm
| Credit Card Rate         | TERMCBCCALLNS.csv              | https://fred.stlouisfed.org/series/TERMCBCCINTNS
| Employee Cost Index      | employee_cost_index.csv        | https://data.bls.gov/cgi-bin/surveymost?bls
| Fed Effective Funds Rate | DFF.csv                        | https://fred.stlouisfed.org/series/DFF
| Household Debt By County | household-debt-by-county.csv   | https://www.federalreserve.gov/releases/z1/dataviz/household_debt/
| Household Debt By State  | household-debt-by-state.csv    | https://www.federalreserve.gov/releases/z1/dataviz/household_debt/
| Household Debt to GDP    | HDTGPDUSQ163N.csv              | https://fred.stlouisfed.org/series/HDTGPDUSQ163N
| Median Home Prices       | MSPUS.csv                      | https://fred.stlouisfed.org/series/MSPUS
| Non Farm Employmment (NFE) | TotalNonFarmEmployment.csv | https://download.bls.gov/pub/time.series/ce/ce.data.00a.TotalNonfarm.Employment
| Produce Price Index      | PPIACO.csv                     | https://fred.stlouisfed.org/series/PPIACO
| Rental Vacancy           | RRVRUSQ156N.csv                | https://fred.stlouisfed.org/series/RRVRUSQ156N
| S&P/Case-Shiller Index   | CSUSHPISA.csv                  | https://fred.stlouisfed.org/series/CSUSHPISA
| State Codes              | state_fips.csv								         | https://www.bls.gov/respondents/mwr/electronic-data-interchange/appendix-d-usps-state-abbreviations-and-fips-codes.htm
| Student Loans            | SLOAS.csv                      | https://fred.stlouisfed.org/series/SLOAS 
| Total Employee Compensation | total_compensation.csv      | https://data.bls.gov/pdq/SurveyOutputServlet
| Unemployment Rate        | Unemployment_Rate.csv          | https://fred.stlouisfed.org/series/UNRATE/
| Urban Consumer Rent      | CUSR0000SAS2RS.csv             | https://fred.stlouisfed.org/series/CUSR0000SAS2RS
| U.S. GDP                 | GDP.csv                        | https://fred.stlouisfed.org/series/GDP


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
  
  

