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
package probate.dto

import com.ibm.as400.access._
import java.math.BigDecimal

/**
 * The RecordFormat and Record for the Import data dransfer object.
 *
 * Layout from T2ADIGPL/TMLIIMP
 * -----------------------------------------------------------
 * Field      Data        Field  Column
 *            Type       Length  Heading
 * $CNTY      CHAR            2  County Code
 * $DOCNEW    CHAR            1  New Doc
 * $DOC#      CHAR           15  Document Number
 * $DOC#STR   CHAR            1  Doc Num Structure
 * $FLMCD     CHAR           15  Filmcode Number
 * $DOCSRC    CHAR            1  Source Type
 * $DOCRM     CHAR           50  Document Remark
 * $DOCRMT    CHAR            1  Document Remark Type
 * $DOCRMC    CHAR            3  Document Rem Code
 * $DOCTYP    CHAR            6  Document Type/Subtype Code
 * $COMPANY   CHAR           10  Company
 * $LNDR      CHAR           10  Lender Code
 * $AMOUNT    ZONED       15  2  Money Amount
 * $AMTTYP    CHAR            1  Amount Type
 * $FILEMD    ZONED        4  0  File Date MM/DD
 * $FILEYY    ZONED        4  0  FILE DATE YEAR
 * $TRNSMD    ZONED        4  0  Trans MM/DD
 * $TRNSYY    ZONED        4  0  Trans YYYY
 * $ADRTYP    CHAR            1  Address Type Code
 * $ADRNAM    CHAR           50  Street Name
 * $ADRATT    CHAR           50  Address Attention Line
 * $ADRSUF    CHAR            3  Street Suffix
 * $ADR#      CHAR           10  Street Number
 * $ADRUNT    CHAR            6  Apt/Unt
 * $ADRCTY    CHAR           20  City
 * $ADRST     CHAR            2  State
 * $ADRZIP    ZONED        9  0  Zip Code
 * $ADRTYP2   CHAR            1  Address Type
 * $ADRNAM2   CHAR           50  Street Address
 * $ADRATT2   CHAR           50  Attention Line
 * $ADRSUF2   CHAR            3  Street Type
 * $ADR#2     CHAR           10  Address Number
 * $ADRUNT2   CHAR            6  Address Unit
 * $ADRCTY2   CHAR           20  City
 * $ADRST2    CHAR            2  State
 * $ADRZIP2   ZONED        9  0  Zip Code
 * $P1        CHAR           50  Party 1 Name
 * $INDEX     CHAR            1  Index
 * $P1RM      CHAR           50  Party 1 Remark
 * $P1RT      CHAR            1  Remark Type
 * $P1RC      CHAR            3  Remark Code
 * $P2        CHAR           50  Party 2 Name
 * $P2RM      CHAR           50  Party 2 Remark
 * $P2RT      CHAR            1  Remark Type
 * $P2RC      CHAR            3  Remark Code
 * $PRDC1     CHAR           15  Prior Doc Reference
 * $PRFC1     CHAR           15  Priors by Film Code
 * $LGLPRI    CHAR           20  Legal Primary Key
 * $LGLST     CHAR            1  Legal Structure Code
 * $LGLSTS    CHAR            1  Legal Sub Structure
 * $LGLSEC    CHAR           20  Secondary Legal Description
 * $LGLHIGH   CHAR           20  Secondary Legal High
 * $LGLRM     CHAR           50  Legal Remark
 * $LGLRMT    CHAR            1  Remark Type
 * $LGLRMC    CHAR            3  Remark Code
 * $TAGFRC    CHAR            1  Force Error
 * $TAGFALL   CHAR            1  Fall Out
 * $PARCSZ    ZONED       15  5  Parcel Size
 * $ACTIONFL  CHAR            1  Action Flag
 * $EDITNAME  CHAR           50  Map Edit Name
 * $P1ENCCD   CHAR            1  Party 1 Encode I/C
 * $P2ENCCD   CHAR            1  Party 2 Encode I/C
 */
class ImportFormat extends RecordFormat("ImportFormat") {

  val countyCodeField = new CharacterFieldDescription(new AS400Text(2), "$CNTY")
  val newRecordField = new CharacterFieldDescription(new AS400Text(1), "$DOCNEW")
  val documentNumberField = new CharacterFieldDescription(new AS400Text(15), "$DOC#")
  val documentNumberStructureField = new CharacterFieldDescription(new AS400Text(1), "$DOC#STR")
  val volumePageField = new CharacterFieldDescription(new AS400Text(15), "$FLMCD")
  val sourceTypeField = new CharacterFieldDescription(new AS400Text(1), "$DOCSRC")
  val remarkField = new CharacterFieldDescription(new AS400Text(50), "$DOCRM")
  val remarkTypeField = new CharacterFieldDescription(new AS400Text(1), "$DOCRMT")
  val remarkCodeField = new CharacterFieldDescription(new AS400Text(3), "$DOCRMC")
  val documentTypeField = new CharacterFieldDescription(new AS400Text(6), "$DOCTYP")
  val companyField = new CharacterFieldDescription(new AS400Text(10), "$COMPANY")
  val lenderCodeField = new CharacterFieldDescription(new AS400Text(10), "$LNDR")
  val moneyAmountField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(15, 2), "$AMOUNT")
  val moneyAmountTypeField = new CharacterFieldDescription(new AS400Text(1), "$AMTTYPE")
  val fileMonthDayField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(4, 0), "$FILEMD")
  val fileYearField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(4, 0), "$FILEYY")
  val transferMonthDayField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(4, 0), "$TRNSMD")
  val transerYearField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(4, 0), "$TRNSYY")
  val addressTypeField = new CharacterFieldDescription(new AS400Text(1), "$ADRTYP")
  val addressStreetNameField = new CharacterFieldDescription(new AS400Text(50), "$ADRNAM")
  val addressAttentionField = new CharacterFieldDescription(new AS400Text(50), "$ADRATT")
  val addressStreetSuffixField = new CharacterFieldDescription(new AS400Text(3), "$ADRSUF")
  val addressStreetNumberField = new CharacterFieldDescription(new AS400Text(10), "$ADR#")
  val addressUnitField = new CharacterFieldDescription(new AS400Text(6), "$ADRUNIT")
  val addressCityField = new CharacterFieldDescription(new AS400Text(20), "$ADRCTY")
  val addressStateField = new CharacterFieldDescription(new AS400Text(2), "$ADRST")
  val addressZipCodeField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(9, 0), "$ADRZIP")
  val address2TypeField = new CharacterFieldDescription(new AS400Text(1), "$ADRTYP2")
  val address2StreetNameField = new CharacterFieldDescription(new AS400Text(50), "$ADRNAM2")
  val address2AttentionField = new CharacterFieldDescription(new AS400Text(50), "$ADRATT2")
  val address2StreetSuffixField = new CharacterFieldDescription(new AS400Text(3), "$ADRSUF2")
  val address2StreetNumberField = new CharacterFieldDescription(new AS400Text(10), "$ADR#2")
  val address2UnitField = new CharacterFieldDescription(new AS400Text(6), "$ADRUNIT2")
  val address2CityField = new CharacterFieldDescription(new AS400Text(20), "$ADRCTY2")
  val address2StateField = new CharacterFieldDescription(new AS400Text(2), "$ADRST2")
  val address2ZipCodeField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(9, 0), "$ADRZIP2")
  val party1NameField = new CharacterFieldDescription(new AS400Text(50), "$P1")
  val partyIndexField = new CharacterFieldDescription(new AS400Text(1), "$INDEX")
  val party1RemarkField = new CharacterFieldDescription(new AS400Text(50), "$P1RM")
  val party1RemarkTypeField = new CharacterFieldDescription(new AS400Text(1), "$P1RT")
  val party1RemarkCodeField = new CharacterFieldDescription(new AS400Text(3), "$P1RC")
  val party2NameField = new CharacterFieldDescription(new AS400Text(50), "$P2")
  val party2RemarkField = new CharacterFieldDescription(new AS400Text(50), "$P2RM")
  val party2RemarkTypeField = new CharacterFieldDescription(new AS400Text(1), "$P2RT")
  val party2RemarkCodeField = new CharacterFieldDescription(new AS400Text(3), "$P2RC")
  val priorRefDocumentNumberField = new CharacterFieldDescription(new AS400Text(15), "$PRDC1")
  val priorRefVolumePageField = new CharacterFieldDescription(new AS400Text(15), "$PRFC1")
  val primaryLegalField = new CharacterFieldDescription(new AS400Text(20), "$LGLPRI")
  val legalStructureCodeField = new CharacterFieldDescription(new AS400Text(1), "$LGLST")
  val legalSubStructureCodeField = new CharacterFieldDescription(new AS400Text(1), "$LGLSTS")
  val secondaryLegalField = new CharacterFieldDescription(new AS400Text(20), "$LGLSEC")
  val secondaryLegalHighField = new CharacterFieldDescription(new AS400Text(20), "$LGLHIGH")
  val legalRemarkField = new CharacterFieldDescription(new AS400Text(50), "$LGLRM")
  val legalRemarkTypeField = new CharacterFieldDescription(new AS400Text(1), "$LGLRMT")
  val legalRemarkCodeField = new CharacterFieldDescription(new AS400Text(3), "$LGLRMC")
  val forceErrorField = new CharacterFieldDescription(new AS400Text(1), "$TAGFRC")
  val falloutField = new CharacterFieldDescription(new AS400Text(1), "$TAGFALL")
  val parcelSizeField = new ZonedDecimalFieldDescription(new AS400ZonedDecimal(15, 5), "$PARCSZ")
  val actionFlagField = new CharacterFieldDescription(new AS400Text(1), "$ACTIONFL")
  val mapEditNameField = new CharacterFieldDescription(new AS400Text(50), "$EDITNAME")
  val party1EncodingField = new CharacterFieldDescription(new AS400Text(1), "$P1ENCCD")
  val party2EncodingField = new CharacterFieldDescription(new AS400Text(1), "$P2ENCCD")

  addFieldDescription(countyCodeField)
  addFieldDescription(newRecordField)
  addFieldDescription(documentNumberField)
  addFieldDescription(documentNumberStructureField)
  addFieldDescription(volumePageField)
  addFieldDescription(sourceTypeField)
  addFieldDescription(remarkField)
  addFieldDescription(remarkTypeField)
  addFieldDescription(remarkCodeField)
  addFieldDescription(documentTypeField)
  addFieldDescription(companyField)
  addFieldDescription(lenderCodeField)
  addFieldDescription(moneyAmountField)
  addFieldDescription(moneyAmountTypeField)
  addFieldDescription(fileMonthDayField)
  addFieldDescription(fileYearField)
  addFieldDescription(transferMonthDayField)
  addFieldDescription(transerYearField)
  addFieldDescription(addressTypeField)
  addFieldDescription(addressStreetNameField)
  addFieldDescription(addressAttentionField)
  addFieldDescription(addressStreetSuffixField)
  addFieldDescription(addressStreetNumberField)
  addFieldDescription(addressUnitField)
  addFieldDescription(addressCityField)
  addFieldDescription(addressStateField)
  addFieldDescription(addressZipCodeField)
  addFieldDescription(address2TypeField)
  addFieldDescription(address2StreetNameField)
  addFieldDescription(address2AttentionField)
  addFieldDescription(address2StreetSuffixField)
  addFieldDescription(address2StreetNumberField)
  addFieldDescription(address2UnitField)
  addFieldDescription(address2CityField)
  addFieldDescription(address2StateField)
  addFieldDescription(address2ZipCodeField)
  addFieldDescription(party1NameField)
  addFieldDescription(partyIndexField)
  addFieldDescription(party1RemarkField)
  addFieldDescription(party1RemarkTypeField)
  addFieldDescription(party1RemarkCodeField)
  addFieldDescription(party2NameField)
  addFieldDescription(party2RemarkField)
  addFieldDescription(party2RemarkTypeField)
  addFieldDescription(party2RemarkCodeField)
  addFieldDescription(priorRefDocumentNumberField)
  addFieldDescription(priorRefVolumePageField)
  addFieldDescription(primaryLegalField)
  addFieldDescription(legalStructureCodeField)
  addFieldDescription(legalSubStructureCodeField)
  addFieldDescription(secondaryLegalField)
  addFieldDescription(secondaryLegalHighField)
  addFieldDescription(legalRemarkField)
  addFieldDescription(legalRemarkTypeField)
  addFieldDescription(legalRemarkCodeField)
  addFieldDescription(forceErrorField)
  addFieldDescription(falloutField)
  addFieldDescription(parcelSizeField)
  addFieldDescription(actionFlagField)
  addFieldDescription(mapEditNameField)
  addFieldDescription(party1EncodingField)
  addFieldDescription(party2EncodingField)
}

/**
 * These fields are not the record format order
 */
class ImportRecord extends Record(new ImportFormat) {

  def countyCode = getField("$CNTY").asInstanceOf[String].trim
  def newRecord = getField("$DOCNEW").asInstanceOf[String].trim
  def documentNumber = getField("$DOC#").asInstanceOf[String].trim
  def documentNumberStructure = getField("$DOC#STR").asInstanceOf[String].trim
  def volumePage = getField("$FLMCD").asInstanceOf[String].trim
  def sourceType = getField("$DOCSRC").asInstanceOf[String].trim
  def remark = getField("$DOCRM").asInstanceOf[String].trim
  def remarkType = getField("$DOCRMT").asInstanceOf[String].trim
  def remarkCode = getField("$DOCRMC").asInstanceOf[String].trim
  def documentType = getField("$DOCTYP").asInstanceOf[String].trim
  def company = getField("$COMPANY").asInstanceOf[String].trim
  def lenderCode = getField("$LNDR").asInstanceOf[String].trim
  def moneyAmount = getField("$AMOUNT").asInstanceOf[java.math.BigDecimal]
  def moneyAmountType = getField("$AMTTYPE").asInstanceOf[String].trim
  def fileMonthDay = getField("$FILEMD").asInstanceOf[java.math.BigDecimal]
  def fileYear = getField("$FILEYY").asInstanceOf[java.math.BigDecimal]
  def transferMonthDay = getField("$TRNSMD").asInstanceOf[java.math.BigDecimal]
  def transerYear = getField("$TRNSYY").asInstanceOf[java.math.BigDecimal]
  def addressType = getField("$ADRTYP").asInstanceOf[String].trim
  def addressStreetName = getField("$ADRNAM").asInstanceOf[String].trim
  def addressAttention = getField("$ADRATT").asInstanceOf[String].trim
  def addressStreetSuffix = getField("$ADRSUF").asInstanceOf[String].trim
  def addressStreetNumber = getField("$ADR#").asInstanceOf[String].trim
  def addressUnit = getField("$ADRUNIT").asInstanceOf[String].trim
  def addressCity = getField("$ADRCTY").asInstanceOf[String].trim
  def addressState = getField("$ADRST").asInstanceOf[String].trim
  def addressZipCode = getField("$ADRZIP").asInstanceOf[java.math.BigDecimal]
  def address2Type = getField("$ADRTYP2").asInstanceOf[String].trim
  def address2StreetName = getField("$ADRNAM2").asInstanceOf[String].trim
  def address2Attention = getField("$ADRATT2").asInstanceOf[String].trim
  def address2StreetSuffix = getField("$ADRSUF2").asInstanceOf[String].trim
  def address2StreetNumber = getField("$ADR#2").asInstanceOf[String].trim
  def address2Unit = getField("$ADRUNIT2").asInstanceOf[String].trim
  def address2City = getField("$ADRCTY2").asInstanceOf[String].trim
  def address2State = getField("$ADRST2").asInstanceOf[String].trim
  def address2ZipCode = getField("$ADRZIP2").asInstanceOf[java.math.BigDecimal]
  def party1Name = getField("$P1").asInstanceOf[String].trim
  def partyIndex = getField("$INDEX").asInstanceOf[String].trim
  def party1Remark = getField("$P1RM").asInstanceOf[String].trim
  def party1RemarkType = getField("$P1RT").asInstanceOf[String].trim
  def party1RemarkCode = getField("$P1RC").asInstanceOf[String].trim
  def party2Name = getField("$P2").asInstanceOf[String].trim
  def party2Remark = getField("$P2RM").asInstanceOf[String].trim
  def party2RemarkType = getField("$P2RT").asInstanceOf[String].trim
  def party2RemarkCode = getField("$P2RC").asInstanceOf[String].trim
  def priorRefDocumentNumber = getField("$PRDC1").asInstanceOf[String].trim
  def priorRefVolumePage = getField("$PRFC1").asInstanceOf[String].trim
  def primaryLegal = getField("$LGLPRI").asInstanceOf[String].trim
  def legalStructureCode = getField("$LGLST").asInstanceOf[String].trim
  def legalSubStructureCode = getField("$LGLSTS").asInstanceOf[String].trim
  def secondaryLegal = getField("$LGLSEC").asInstanceOf[String].trim
  def secondaryLegalHigh = getField("$LGLHIGH").asInstanceOf[String].trim
  def legalRemark = getField("$LGLRM").asInstanceOf[String].trim
  def legalRemarkType = getField("$LGLRMT").asInstanceOf[String].trim
  def legalRemarkCode = getField("$LGLRMC").asInstanceOf[String].trim
  def forceError = getField("$TAGFRC").asInstanceOf[String].trim
  def fallout = getField("$TAGFALL").asInstanceOf[String].trim
  def parcelSize = getField("$PARCSZ").asInstanceOf[java.math.BigDecimal]
  def actionFlag = getField("$ACTIONFL").asInstanceOf[String].trim
  def mapEditName = getField("$EDITNAME").asInstanceOf[String].trim
  def party1Encoding = getField("$P1ENCCD").asInstanceOf[String].trim
  def party2Encoding = getField("$P2ENCCD").asInstanceOf[String].trim

  def countyCode_=(s: String) = setField("$CNTY", s)
  def newRecord_=(s: String) = setField("$DOCNEW", s)
  def documentNumber_=(s: String) = setField("$DOC#", s)
  def documentNumberStructure_=(s: String) = setField("$DOC#STR", s)
  def volumePage_=(s: String) = setField("$FLMCD", s)
  def sourceType_=(s: String) = setField("$DOCSRC", s)
  def remark_=(s: String) = setField("$DOCRM", s)
  def remarkType_=(s: String) = setField("$DOCRMT", s)
  def remarkCode_=(s: String) = setField("$DOCRMC", s)
  def documentType_=(s: String) = setField("$DOCTYP", s)
  def company_=(s: String) = setField("$COMPANY", s)
  def lenderCode_=(s: String) = setField("$LNDR", s)
  def moneyAmount_=(bd: BigDecimal) = setField("$AMOUNT", bd)
  def moneyAmountType_=(s: String) = setField("$AMTTYPE", s)
  def fileMonthDay_=(bd: BigDecimal) = setField("$FILEMD", bd)
  def fileYear_=(bd: BigDecimal) = setField("$FILEYY", bd)
  def transferMonthDay_=(bd: BigDecimal) = setField("$TRNSMD", bd)
  def transerYear_=(bd: BigDecimal) = setField("$TRNSYY", bd)
  def addressType_=(s: String) = setField("$ADRTYP", s)
  def addressStreetName_=(s: String) = setField("$ADRNAM", s)
  def addressAttention_=(s: String) = setField("$ADRATT", s)
  def addressStreetSuffix_=(s: String) = setField("$ADRSUF", s)
  def addressStreetNumber_=(s: String) = setField("$ADR#", s)
  def addressUnit_=(s: String) = setField("$ADRUNIT", s)
  def addressCity_=(s: String) = setField("$ADRCTY", s)
  def addressState_=(s: String) = setField("$ADRST", s)
  def addressZipCode_=(bd: BigDecimal) = setField("$ADRZIP", bd)
  def address2Type_=(s: String) = setField("$ADRTYP2", s)
  def address2StreetName_=(s: String) = setField("$ADRNAM2", s)
  def address2Attention_=(s: String) = setField("$ADRATT2", s)
  def address2StreetSuffix_=(s: String) = setField("$ADRSUF2", s)
  def address2StreetNumber_=(s: String) = setField("$ADR#2", s)
  def address2Unit_=(s: String) = setField("$ADRUNIT2", s)
  def address2City_=(s: String) = setField("$ADRCTY2", s)
  def address2State_=(s: String) = setField("$ADRST2", s)
  def address2ZipCode_=(bd: BigDecimal) = setField("$ADRZIP2", bd)
  def party1Name_=(s: String) = setField("$P1", s)
  def partyIndex_=(s: String) = setField("$INDEX", s)
  def party1Remark_=(s: String) = setField("$P1RM", s)
  def party1RemarkType_=(s: String) = setField("$P1RT", s)
  def party1RemarkCode_=(s: String) = setField("$P1RC", s)
  def party2Name_=(s: String) = setField("$P2", s)
  def party2Remark_=(s: String) = setField("$P2RM", s)
  def party2RemarkType_=(s: String) = setField("$P2RT", s)
  def party2RemarkCode_=(s: String) = setField("$P2RC", s)
  def priorRefDocumentNumber_=(s: String) = setField("$PRDC1", s)
  def priorRefVolumePage_=(s: String) = setField("$PRFC1", s)
  def primaryLegal_=(s: String) = setField("$LGLPRI", s)
  def legalStructureCode_=(s: String) = setField("$LGLST", s)
  def legalSubStructureCode_=(s: String) = setField("$LGLSTS", s)
  def secondaryLegal_=(s: String) = setField("$LGLSEC", s)
  def secondaryLegalHigh_=(s: String) = setField("$LGLHIGH", s)
  def legalRemark_=(s: String) = setField("$LGLRM", s)
  def legalRemarkType_=(s: String) = setField("$LGLRMT", s)
  def legalRemarkCode_=(s: String) = setField("$LGLRMC", s)
  def forceError_=(s: String) = setField("$TAGFRC", s)
  def fallout_=(s: String) = setField("$TAGFALL", s)
  def parcelSize_=(bd: BigDecimal) = setField("$PARCSZ", bd)
  def actionFlag_=(s: String) = setField("$ACTIONFL", s)
  def mapEditName_=(s: String) = setField("$EDITNAME", s)
  def party1Encoding_=(s: String) = setField("$P1ENCCD", s)
  def party2Encoding_=(s: String) = setField("$P2ENCCD", s)

  override def toString = {
    "CountyCode: " + countyCode + ", NewRecord: " + newRecord + ", DocumentNumber: " + documentNumber +
      ", DocumentNumberStructure: " + documentNumberStructure + ", VolumePage: " + volumePage +
      ", SourceType: " + sourceType + ", Remark: " + remark + ", RemarkType: " + remarkType +
      ", RemarkCode: " + remarkCode + ", DocumentType: " + documentType + ", Company: " + company +
      ", LenderCode: " + lenderCode + ", MoneyAmount: " + moneyAmount + ", MoneyAmountType: " + moneyAmountType +
      ", FileMonthDay: " + fileMonthDay + ", FileYear: " + fileYear + ", TransferMonthDay: " + transferMonthDay +
      ", TranserYear: " + transerYear + ", AddressType: " + addressType + ", AddressStreetName: " + addressStreetName +
      ", AddressAttention: " + addressAttention + ", AddressStreetSuffix: " + addressStreetSuffix +
      ", AddressStreetNumber: " + addressStreetNumber + ", AddressUnit: " + addressUnit + ", AddressCity: " + addressCity +
      ", AddressState: " + addressState + ", AddressZipCode: " + addressZipCode + ", Address2Type: " + address2Type +
      ", Address2StreetName: " + address2StreetName + ", Address2Attention: " + address2Attention +
      ", Address2StreetSuffix: " + address2StreetSuffix + ", Address2StreetNumber: " + address2StreetNumber +
      ", Address2Unit: " + address2Unit + ", Address2City: " + address2City + ", Address2State: " + address2State +
      ", Address2ZipCode: " + address2ZipCode + ", Party1Name: " + party1Name + ", PartyIndex: " + partyIndex +
      ", Party1Remark: " + party1Remark + ", Party1RemarkTyep: " + party1RemarkType + ", Party1RemarkCode: " + party1RemarkCode +
      ", Party2Name: " + party2Name + ", Party2Remark: " + party2Remark + ", Party2RemarkType: " + party2RemarkType +
      ", Party2RemarkCode: " + party2RemarkCode + ", PriorRefDocumentNumber: " + priorRefDocumentNumber +
      ", PriorRefVolumePage: " + priorRefVolumePage + ", PrimaryLegal: " + primaryLegal + ", LegalStructureCode: " + legalStructureCode +
      ", LegalSubStructureCode: " + legalSubStructureCode + ", SecondaryLegal: " + secondaryLegal +
      ", SecondaryLegalHigh: " + secondaryLegalHigh + ", LegalRemark: " + legalRemark + ", LegalRemarkType: " + legalRemarkType +
      ", LegalRemarkCode: " + legalRemarkCode + ", ForceError: " + forceError + ", Fallout: " + fallout + ", ParcelSize: " + parcelSize +
      ", ActionFlag: " + actionFlag + ", MapEditName: " + mapEditName + ", Party1Encoding: " + party1Encoding +
      ", Party2Encoding: " + party2Encoding

  }
}
