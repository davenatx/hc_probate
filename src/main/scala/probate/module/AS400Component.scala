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

import com.ibm.as400.access._
import com.typesafe.scalalogging.LazyLogging
import probate._
import scala.annotation.tailrec

/**
 * Singleton connection pool
 *
 * Not prefilling at the moment
 */
protected object AS400Pool extends LazyLogging {
  lazy val pool = {
    val p = new AS400ConnectionPool
    logger.debug("As400Pool created")
    p
  }
}

/**
 * AS400 Services using a connectioni pool
 *
 * The AS400ConnectionPool is threadsafe so I don't need to synchronize access
 */
trait AS400Component extends LazyLogging {

  import AS400Pool._

  /** Helper to log the active and available connections in the pool*/
  private def logPoolUsage {
    logger.trace("Active Connections: " +
      pool.getActiveConnectionCount(as400Server, as400Userprofile))
    logger.trace("Available Connections: " +
      pool.getAvailableConnectionCount(as400Server, as400Userprofile))
  }

  /**
   * Retrieve AS400 object from the pool
   *
   * If the Title Plant Sybsystem is not active, throw an exception
   */
  def connection: AS400 = {
    val con = pool.getConnection(as400Server, as400Userprofile, as400Password)
    logPoolUsage
    con
  }

  /**
   * Return AS400 object to the pool
   */
  def returnConnection(as400: AS400) {
    pool.returnConnectionToPool(as400)
    logPoolUsage
  }

  /**
   * Close the pool
   */
  protected def close {
    pool.close
    logger.debug("As400Pool Closed")
  }

  /**
   * Call a program
   */
  def programCall(program: String, parmList: Array[ProgramParameter]): Array[ProgramParameter] = {
    val as400 = connection
    val pgm = new ProgramCall(as400, program, parmList)
    logger.trace("Calling program: " + program)
    pgm.run
    returnConnection(as400)
    parmList
  }

  /**
   * Call a command
   */
  def commandCall(command: String) {
    val as400 = connection
    val cmd = new CommandCall(as400)
    logger.trace("Calling command: " + command)
    cmd.run(command)
    returnConnection(as400)
  }

  // Funciton to return the QSYS Path
  val qsysObjectPath = (name: String, library: String) => new QSYSObjectPathName(library, name, "*FIRST", "MBR")

  /**
   * Create Keyed File
   */
  private def createKeyedFile(as400: AS400, name: String, library: String): KeyedFile = {
    logger.trace("Creating Keyed File: {}/{}", name, library)

    val path = qsysObjectPath(name, library)
    val recordDescription = new AS400FileRecordDescription(as400, path.getPath)
    val keyedFile = new KeyedFile(as400, path.getPath)
    keyedFile.setRecordFormat(recordDescription.retrieveRecordFormat()(0))
    keyedFile
  }

  /**
   * Create Sequential File
   */
  private def createSequentialFile(as400: AS400, name: String, library: String): SequentialFile = {
    logger.trace("Creating Sequential File: {}/{}", name, library)

    val path = qsysObjectPath(name, library)
    val recordDescription = new AS400FileRecordDescription(as400, path.getPath)
    val sequentialFile = new SequentialFile(as400, path.getPath)
    sequentialFile.setRecordFormat(recordDescription.retrieveRecordFormat()(0))
    sequentialFile
  }

  /**
   * Loan Wrapper for KeyedFile
   *
   * Enclose behavior that opens the file in fileFunc.  This encapsulates connection resouce used
   * to both open and read the file in the loan pattern.
   *
   * KeyedFile is specified so specific methods like positionCursorBefore, etc... work
   *
   */
  def withKeyedFile[T <: Record](fileFunc: AS400 => KeyedFile)(func: KeyedFile => List[T]): List[T] = {
    val as400 = connection
    val file = fileFunc(as400)
    try {
      func(file)
    } finally {
      logger.debug("Closing file: {}", file.getFileName)
      file.close
      returnConnection(as400)
    }
  }

  /**
   * Loan Wrapper for performing Write operations to KeyedFile
   *
   * Enclose behavior that opens the file in fileFunc.  This encapsulates connection resouce used
   * to both open and write the file in the loan pattern.
   *
   * KeyedFile is specified so specific methods like positionCursorBefore, etc... work
   *
   */
  def withWriteKeyedFile(fileFunc: AS400 => KeyedFile)(func: KeyedFile => Unit): Unit = {
    val as400 = connection
    val file = fileFunc(as400)
    try {
      func(file)
    } finally {
      logger.debug("Closing file: {}", file.getFileName)
      file.close
      returnConnection(as400)
    }
  }

  /**
   * Loan Wrapper for SequentialFile
   *
   * Enclose behavior that opens the file in fileFunc.  This encapsulates connection resouce used
   * to both open and read the file in the loan pattern.
   *
   */
  def withWriteSequentialFile(fileFunc: AS400 => SequentialFile)(func: SequentialFile => Unit): Unit = {
    val as400 = connection
    val file = fileFunc(as400)
    try {
      func(file)
    } finally {
      logger.debug("Closing file: {}", file.getFileName)
      file.close
      returnConnection(as400)
    }
  }

  /**
   * Loan Wrapper for SequentialFile
   *
   * Enclose behavior that opens the file in fileFunc.  This encapsulates connection resouce used
   * to both open and write the file in the loan pattern.
   *
   */
  def withSequentialFile[T <: Record](fileFunc: AS400 => SequentialFile)(func: SequentialFile => List[T]): List[T] = {
    val as400 = connection
    val file = fileFunc(as400)
    try {
      func(file)
    } finally {
      logger.debug("Closing file: {}", file.getFileName)
      file.close
      returnConnection(as400)
    }
  }

  /**
   * Recurisve method for reading records with an equal key from a KeyedFile.
   *
   * func: () = T is used as a "Factory" for the specific DTO object expected to be returned
   * from the call.
   */
  @tailrec
  final def readNextEqualRecords[T <: Record](keyedFile: KeyedFile, key: Array[Object], acc: List[T], func: () => T): List[T] = {
    keyedFile.readNextEqual(key) match {
      case null => acc
      case r => {
        val rec = func()
        rec.setContents(r.getContents)
        readNextEqualRecords(keyedFile, key, rec :: acc, func)
      }
    }
  }

  /**
   * Recurisve method for reading records from a SequentialFile.
   *
   * func: () = T is used as a "Factory" for the specific DTO object expected to be returned
   * from the call.
   */
  @tailrec
  final def readRecords[T <: Record](seqFile: SequentialFile, acc: List[T], func: () => T): List[T] = {
    seqFile.readNext() match {
      case null => acc
      case r => {
        val rec = func()
        rec.setContents(r.getContents)
        readRecords(seqFile, rec :: acc, func)
      }
    }
  }

  /**
   * Function that takes the name and library of a file and returns a function
   * that takes an AS400 object => KeyedFile object
   *
   * This function is passed as a parameter to loan wrapper methods.  This ensures the same connection is used to
   * create/open the file, read the records.
   */
  val keyedFileFunc = (name: String, library: String, openType: Int, blockingFactor: Int, commitLockLevel: Int) => {
    (as400: AS400) =>
      {
        val keyedFile = createKeyedFile(as400, name, library)
        keyedFile.open(openType, blockingFactor, commitLockLevel)
        keyedFile
      }
  }

  /**
   * Function that takes the name and library of a file and returns a function
   * that takes an AS400 object => SequentialFile
   *
   * This function is passed as a parameter to loan wrapper methods.  This ensures the same connection is used to
   * create/open the file, read the records.
   */
  val sequentialFileFunc = (name: String, library: String, openType: Int, blockingFactor: Int, commitLockLevel: Int) => {
    (as400: AS400) =>
      {
        val seqFile = createSequentialFile(as400, name, library)
        seqFile.open(openType, blockingFactor, commitLockLevel)
        seqFile
      }
  }
}