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
//import probate.model.{ SDN, SDNEntry, XmlUrl, DateHelpers, TimsDate, IndividualName, CorporateName, SDNAka }
import probate._
import com.ibm.as400.access._
import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec

/**
 * REPL Test Code
 *
 */

trait ProvateComponent extends LazyLogging {
  this: AS400Component =>

  /**
   * Read records from the import file
   *
   * Not currently in use.
   */
  def importRecords: List[ImportRecord] = {
    val seqFileFunc = sequentialFileFunc(importFile, importFileLib, AS400File.READ_ONLY, 0, AS400File.COMMIT_LOCK_LEVEL_NONE)

    val seqLoanWrapper = withSequentialFile[ImportRecord](seqFileFunc) _

    val seqRecords = seqLoanWrapper {
      seqFile =>
        {
          seqFile.positionCursorBeforeFirst()
          readRecords[ImportRecord](seqFile, Nil, () => new ImportRecord).reverse
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
      seqFile =>
        {
          records.map(record => { seqFile.write(record) })
        }
    }
  }

  /*

  def convert(url: XmlUrl, documentType: String) {

    // Should I handle an exception?
    val sdnList = SDN.load(url)
    logger.info("Loaded " + sdnList.recordCount + " records with Publish Date " + sdnList.publishDate + " from " + url.url)

    val timsDate = DateHelpers.format(sdnList.publishDate)

    processSDNRecords(sdnList.sdnEntries, timsDate, documentType)
  }

  private def processSDNRecords(sdnEntries: Seq[SDNEntry], timsDate: TimsDate, documentType: String) {

    @tailrec
    def processAKARecords(sdnEntry: SDNEntry, masterRecord: ImportRecord, xs: Seq[SDNAka], acc: List[ImportRecord]): List[ImportRecord] = xs match {
      case List() => acc.reverse
      case head :: tail => {
        val record = new ImportRecord

        record.documentNumber = masterRecord.documentNumber
        record.sourceType = masterRecord.sourceType
        /* Confirm I only need the master remark for the first record */
        record.documentType = masterRecord.documentType
        record.fileMonthDay = masterRecord.fileMonthDay
        record.fileYear = masterRecord.fileYear
        record.party1Name = masterRecord.party1Encoding match {
          case "I" => IndividualName(head.lastName, head.firstName).format
          case "C" => CorporateName(head.lastName).format
        }
        record.party1Remark = "AKA"
        record.fallout = masterRecord.fallout
        record.actionFlag = masterRecord.actionFlag
        record.party1Encoding = masterRecord.party1Encoding

        logger.debug("SDNAka Record Converted: " + record.documentNumber + ", party1Name: " + record.party1Name)

        // Type is Individual and firstName is blank, format the last name as Corporate.
        if (masterRecord.party1Encoding == "I" && head.firstName == "") {

          logger.debug("SDN Record: " + record.documentNumber + " has SDNAka.lastName: " + head.lastName + " and a blank firstName")
          record.party1Name = CorporateName(head.lastName).format
          record.party1Encoding = "C"

          processAKARecords(sdnEntry, masterRecord, tail, record :: acc)

        } // If the AKAName is the same as the master name, do not include the record
        else if (masterRecord.party1Name == record.party1Name) {
          logger.debug("SDN Record: " + record.documentNumber + " has SDNAka with same name as master record: " + record.party1Name)
          processAKARecords(sdnEntry, masterRecord, tail, acc)
        } else processAKARecords(sdnEntry, masterRecord, tail, record :: acc)
      }
    }

    /* Map over SDNEntries and convert */
    sdnEntries map (x => {

      /* Retrieve the TIMS encoding from the SDN record */
      val encoding = x.sdnType match {
        case "Individual" => "I"
        case _ => "C"
      }

      val `type` = encoding match {
        case "I" => "INDIVIDUAL"
        case "C" => "ENTITY"
      }

      val record = new ImportRecord

      record.newRecord = "Y"
      record.documentNumber = documentType + x.uid
      record.sourceType = sourceType
      record.remark = "PROGRAM: " + x.programList.head.program + "; TYPE: " + `type`
      record.documentType = documentType
      record.fileMonthDay = timsDate.fileMonthDay
      record.fileYear = timsDate.fileYear
      record.party1Name = encoding match {
        case "I" => IndividualName(x.lastName, x.firstName).format
        case "C" => CorporateName(x.lastName).format
      }
      record.fallout = falloutFlag
      record.actionFlag = actionFlag
      record.party1Encoding = encoding

      logger.debug("SDN Record Converted: " + record.documentNumber + ", party1Name: " + record.party1Name)

      x.akaList match {
        case Some(list) => {
          val akaRecords = processAKARecords(x, record, list, List())
          writeImportRecords(record :: akaRecords)
        }
        case None => writeImportRecords(record :: Nil)
      }
    })
  }
*/
}