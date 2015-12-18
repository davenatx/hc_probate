HC_Probate
===========

Overview
--------

This program imports the HC Probate index from CSV files and converts it to
the TIMS format

ToDo
----
* Is MRS a suffix?  I do not believe it is.
* How should I handle records with a blank first name?
* What should I do about records missing a document type? (Already added NOTPRO
 to a few blank ones in vol 1)
* I completed the rought CSV parser.  Now I need to convert that list of 
 records to Database Records