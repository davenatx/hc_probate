HC_Probate
===========

Overview
--------

This program imports the HC Probate index from CSV files and converts it to
the TIMS format

Requirements
------------
* Is MRS a suffix?  **No**
* How should I handle records with a blank first name?  **Leave them as is and set the encoding to I**
* For the "last name only" records, should I post it as lastname,?  **I need to test this**
* What should I do about records missing a document type? **NOTPRO**
* I have verified the CSV parser sucessfully parses all 4 CSV files.
* This should live on the IBM i in library **HCBCKPLNT**.

REPL
----
Here is the code to execuite this process from the REPL:
````
import probate.model._
import probate.module.ProbateModule._

val csvRecords = ProbateCSV()

probateService.convert(csvRecords)
````
