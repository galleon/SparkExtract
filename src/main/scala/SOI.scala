/**
 * Created by alleon_g on 18/08/2016.
 */

/**
 * Need POIFS & HWPS
 **/

import java.io._
import java.net.URI

import org.apache.poi.hwpf.HWPFDocument
import java.text.Normalizer

import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.deploy.SparkHadoopUtil

import scala.collection.mutable.ListBuffer

case class SOIDoc(var filename: String, var operation: String, var loa: String, var lom: String)

object SOI {
  def removeDiacritics(in: String) : String = {
    // Separate accents from characters and then remove non-unicode characters
    Normalizer.normalize(in, Normalizer.Form.NFD).replaceAll("\\p{M}", "")
  }

  def extract(filePath: URI): SOIDoc =  {
    def fsdis = FileSystem.get(SparkHadoopUtil.get.conf).open(new Path(filePath))

    val doc: HWPFDocument = new HWPFDocument(fsdis)

    val r = doc.getRange

    val numParagraphs = r.numParagraphs()
    val numSections = r.numSections()

    // Support
    val thing1 = "v\\d{11}".r
    // Tyrap ?
    val thing2 = "abs\\w{7}".r
    // Vis
    val thing3 = "nsa\\w{4}-\\w{3}".r
    // Rondelle
    val thing4 = "nas\\w{10}".r

    // We set the dictionnary of actions
    val actions = ("(ebavurer|derocher|mise en place|serrer|assembler|ne pas serrer|s'assurer|faire|installer|"+
                   "appliquer|aleser|controler|epingler|desepingler|positionner|mesurer)").r

    // Define list of things
    var lot1 = new ListBuffer[String]()
    var lot2 = new ListBuffer[String]()
    var lot3 = new ListBuffer[String]()
    var listOfMaterials = new ListBuffer[String]()
    var listOfActions   = new ListBuffer[String]()

    var snipplet: String = null

    for (k <- 0 until r.numParagraphs()) {
      val p = r.getParagraph(k)
      if (p.text().trim().length > 0) {

        snipplet = removeDiacritics(p.text().toLowerCase())

        thing1.findAllIn(snipplet).foreach(listOfMaterials += _)
        thing2.findAllIn(snipplet).foreach(listOfMaterials += _)
        thing3.findAllIn(snipplet).foreach(listOfMaterials += _)
        thing4.findAllIn(snipplet).foreach(listOfMaterials += _)

        actions.findAllIn(snipplet).foreach(listOfActions += _)

      }
    }

    val name = filePath.getPath().split('_')(1) + "_" + filePath.getPath().split('_')(2)

    return SOIDoc(name, "000", listOfActions.mkString("-"), listOfMaterials.mkString("-"))
  }
}
