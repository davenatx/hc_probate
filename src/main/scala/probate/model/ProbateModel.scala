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
 * Record formatted for the Database
 */
case class ProbateDBRecord(documentNumber: String, fileMonthDay: Int,
                           fileYear: Int, documentType: String, party1: String)

object Probate extends LazyLogging {

  type Line = String

  private def parseLine(line: Line): ProbateCSVRecord = {
    logger.debug("Line: " + line)
    val Array(
      causeNumber,
      fileMonthDay,
      fileYear,
      lastName,
      firstName,
      middle,
      suffix,
      documentType) = line.split(",").map(_.trim)
    
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

  import scala.annotation.tailrec

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
