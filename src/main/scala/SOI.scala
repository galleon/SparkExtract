/**
 * Created by alleon_g on 18/08/2016.
 */

/**
 * Need POIFS & HWPS
 **/

import java.io._

import org.apache.poi.hwpf.HWPFDocument
import org.apache.spark.input.PortableDataStream

import scala.collection.mutable.ListBuffer

case class SOIDoc(var loa: ListBuffer[String])

object SOI {
  def extract(filePath: String): Unit =  {
    val doc: HWPFDocument = new HWPFDocument(new FileInputStream(filePath))

    val r = doc.getRange

    val numParagraphs = r.numParagraphs()
    val numSections = r.numSections()

    println(numParagraphs)
    println(numSections)

    println(doc.getDocProperties()) // getShapesTable().getAllShapes();
    /*
val f = doc.getFields()

val picturesTable = doc.getPicturesTable()
List<Picture> allPictures = picturesTable.getAllPictures()
println "${allPictures.size()} pictures"
for (int k = 0; k < allPictures.size(); k++){
  Picture p = allPictures.get(k)
  println "> " + p.suggestFullFileName()
}

val range = doc.getRange
for (i <- 0 until range.numParagraphs()) {
  val par = range.getParagraph(i)
  //println(par.text())
}
val tablePar = range.getParagraph(0)
*/

    println(" -----------------")

    // Support
    val thing1 = "v\\d{11}".r
    // Tyrap ?
    val thing2 = "abs\\w{7}".r
    // Vis
    val thing3 = "nsa\\w{4}-\\w{3}".r
    // Rondelle
    val thing4 = "nas\\w{10}".r

    val actions = "(mise en place|serrer|assembler|ne pas serrer|s'assurer|faire|installer|appliquer)".r

    // Define list of things
    var lot1 = new ListBuffer[String]()
    var lot2 = new ListBuffer[String]()
    var lot3 = new ListBuffer[String]()
    var lot4 = new ListBuffer[String]()
    var listOfActions = new ListBuffer[String]()

    //var findThing = null
    var snipplet: String = null

    for (k <- 0 until r.numParagraphs()) {
      val p = r.getParagraph(k)
      if (p.text().trim().length > 0) {

        snipplet = p.text().toLowerCase()

        thing1.findAllIn(snipplet).foreach {
          lot1 += _
        }

        //    findThing = snipplet =~ (/$thing2/)
        //    if (findThing.size() > 0) findThing.each {it -> lot2 << it}

        thing2.findAllIn(snipplet).foreach {
          lot2 += _
        }
        thing3.findAllIn(snipplet).foreach(lot3 += _)
        thing4.findAllIn(snipplet).foreach(lot4 += _)

        actions.findAllIn(snipplet).foreach(listOfActions += _)

        println(k.toString() + "> " + snipplet)
      }
    }

    println(lot1)
    println(lot2)
    println(lot3)
    println(lot4)
    println(listOfActions)

    val nrPages: Int = 1
    var counterChar: Int = 0

    for (k <- 0 until numParagraphs) {
      //println k
      //println p.text().length()
      counterChar = counterChar + r.getParagraph(k).text().length()

      val par = r.getParagraph(k)
      try {
        val table = r.getTable(par)
        println("---- New Table ----")
        for (j <- 0 until table.getRow(0).numCells()){
          var title = table.getRow(0).getCell(j).text()
          println (s"colonne $j has name $title")
        }
        //println("> " + table.toString + "contains paragraph " + k.toString)
      } catch{
        case ex: IllegalArgumentException => {
          //println(s"paragraph $k does not belong to a table")
        }
      }
    }
    println(counterChar)

    println(SOIDoc(listOfActions))
  }
}

/*
object SOIApp {
  def main(args: Array[String]) {
    val pds: PortableDataStream = Po
    SOI.extract(args(0))
  }
}
*/