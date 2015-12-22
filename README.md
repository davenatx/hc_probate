HC_Probate
===========

Overview
--------

The HC Probate index is stored in Comma Separated Values (CSV) files.  This project parses 
the CSV files, converts the data to the TIMS import format and writes the output to the IBM i.

Requirements
------------
* Is MRS a suffix?  **No**
* How should I handle records with a blank first name? **Leave them as is and set the encoding to 
  I**
* For the "last name only" records, should I post it as "lastname,"?  **I believe so, but I need to 
  test this**
* What should I do about records missing a document type? **NOTPRO**
* Verified the CSV parser sucessfully parses all 4 CSV files? **Yes**

REPL
----
Here is the code to execute this process from the REPL:
````
import probate.model._
import probate.module.ProbateModule._

val csvRecords = ProbateCSV()

probateService.convert(csvRecords)
````

Example **settings.properties** file
------------------------------------
This is an example of the settings.properties file:
````
// AS400 Information
as400Server=
as400Userprofile=
as400Password=
importFileLib=
importFile=
// TIMS Options
countyCode=HC
actionFlag=A
sourceType=P
falloutFlag=N
p1Encoding=I
// CSV File
csvFile=csv/probate_vol_4.csv
````
