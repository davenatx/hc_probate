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
package probate.module

import probate.dto.ImportRecord
import probate._
import probate.model._
import com.ibm.as400.access._
import com.typesafe.scalalogging.LazyLogging
import java.math.BigDecimal

import scala.annotation.tailrec

/**
 * REPL Test Code
 *
 */

trait ProbateComponent extends LazyLogging {
  this: AS400Component ⇒

  /**
   * Read records from the import file
   *
   * Not currently in use.
   */
  def importRecords: List[ImportRecord] = {
    val seqFileFunc = sequentialFileFunc(importFile, importFileLib, AS400File.READ_ONLY, 0, AS400File.COMMIT_LOCK_LEVEL_NONE)

    val seqLoanWrapper = withSequentialFile[ImportRecord](seqFileFunc) _

    val seqRecords = seqLoanWrapper {
      seqFile ⇒
        {
          seqFile.positionCursorBeforeFirst()
          readRecords[ImportRecord](seqFile, Nil, () ⇒ new ImportRecord).reverse
        }
    }
    seqRecords
  }

  /**
   * Write records to the import file
   */
  def writeImportRecords(records: List[ImportRecord]) {
    val seqFileFunc = sequentialFileFunc(importFile, importFileLib, AS400File.READ_WRITE, 0, AS400File.COMMIT_LOCK_LEVEL_NONE)

    val seqLoanWrapper = withWriteSequentialFile(seqFileFunc) _

    seqLoanWrapper {
      seqFile ⇒
        {
          records.map(record ⇒ { seqFile.write(record) })
        }
    }
  }

  def convert(list: List[ProbateCSVRecord]) {
      @tailrec
      def processCSVRecords(csvRecords: List[ProbateCSVRecord], acc: List[ImportRecord]): List[ImportRecord] = csvRecords match {
        case List() ⇒ acc.reverse
        case head :: tail ⇒ {
          val record = new ImportRecord

          record.newRecord = "Y"
          record.countyCode = countyCode // From properties file
          record.documentNumber = head.causeNumber
          record.sourceType = sourceType // From properties file
          record.documentType = head.documentType
          record.fileMonthDay = new BigDecimal(head.fileMonthDay)
          record.fileYear = new BigDecimal(head.fileYear)
          record.party1Name = ProbateHelpers.formatParty(
            head.lastName,
            head.firstName,
            head.middle,
            head.suffix
          )
          record.fallout = falloutFlag // From properties file
          record.actionFlag = actionFlag // From properties file
          record.party1Encoding = p1Encoding // From properties file

          processCSVRecords(tail, record :: acc)
        }
      }

    val records = processCSVRecords(list, Nil)

    writeImportRecords(records)
  }
}