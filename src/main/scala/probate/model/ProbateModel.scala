/**
 * Austin Data, Inc. ("COMPANY") CONFIDENTIAL
 * Unpublished Copyright (c) 2015 Austin Data, Inc., All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains the property of
 * COMPANY. The intellectual and technical concepts contained herein are
 * proprietary to COMPANY and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material is
 * strictly forbidden unless prior written permission is obtained from COMPANY.
 * Access to the source code contained herein is hereby forbidden to anyone
 * except current COMPANY employees, managers or contractors who have executed
 * Confidentiality and Non-disclosure agreements explicitly covering such
 * access.
 *
 * The copyright notice above does not evidence any actual or intended
 * publication or disclosure of this source code, which includes information
 * that is confidential and/or proprietary, and is a trade secret, of COMPANY.
 * ANY REPRODUCTION, MODIFICATION, DISTRIBUTION, PUBLIC PERFORMANCE, OR PUBLIC
 * DISPLAY OF OR THROUGH USE OF THIS SOURCE CODE WITHOUT THE EXPRESS WRITTEN
 * CONSENT OF COMPANY IS STRICTLY PROHIBITED, AND IN VIOLATION OF APPLICABLE
 * LAWS AND INTERNATIONAL TREATIES.  THE RECEIPT OR POSSESSION OF THIS SOURCE
 * CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO
 * REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR
 * SELL ANYTHING THAT IT MAY DESCRIBE, IN WHOLE OR IN PART.
 */
package probate.model

import scala.io.Source
import scala.annotation.tailrec
import com.typesafe.scalalogging.LazyLogging
import probate._

/**
 * Record parsed from CSV File
 */
case class ProbateCSVRecord(causeNumber: String, fileMonthDay: String,
                            fileYear: String, lastName: String,
                            firstName: String, middle: String, suffix: String,
                            documentType: String)

/**
 * Helpers for handling conversion between CSV Record and DB Record
 */
object ProbateHelpers extends LazyLogging {

  /* Convert from the ProbateCSV format to the DB Party format */
  def formatParty(lastName: String, firstName: String, middle: String, suffix: String): String = {
    lastName + "," + firstName + formatToken(middle) + formatToken(suffix)
  }

  /**
   * If the token is empty String, return empty.  Otherwise prepend space
   */
  def formatToken(token: String): String = token match {
    case "" ⇒ ""
    case _  ⇒ " " + token
  }
}

/**
 * Process CSV File
 */
object ProbateCSV extends LazyLogging {

  type Line = String

  /**
   * Parse Each Line from the CSV file and create a ProbateCSVRecord
   * Each String is trimmed as it is read
   */
  private def parseLine(line: Line): ProbateCSVRecord = {
    logger.info("Line: " + line)
    val Array(
      causeNumber,
      fileMonthDay,
      fileYear,
      lastName,
      firstName,
      middle,
      suffix,
      documentType) = line.trim.split(",").map(_.trim)

    ProbateCSVRecord(
      causeNumber,
      fileMonthDay,
      fileYear,
      lastName,
      firstName,
      middle,
      suffix,
      documentType)
  }

  /**
   * Parse the CSV file and return a list of ProbateCSVRecord
   */
  def parseCSV(): List[ProbateCSVRecord] = {

      @tailrec
      def processLines(xs: List[Line], acc: List[ProbateCSVRecord]): List[ProbateCSVRecord] = xs match {
        case head :: tail ⇒ processLines(tail, parseLine(head) :: acc)
        case List()       ⇒ acc
      }

    processLines(Source.fromFile(csvFile).getLines.toList, Nil).reverse

  }

  def apply() = parseCSV()
}
