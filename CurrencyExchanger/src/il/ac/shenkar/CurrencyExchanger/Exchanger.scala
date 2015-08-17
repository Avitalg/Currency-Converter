package il.ac.shenkar.CurrencyExchanger

import scala.xml._
import java.net._
import scala.collection.mutable._
import java.io.FileNotFoundException

/*
@author Exchanger this class extend Model interface
@see className Model
* 
*/
class Exchanger extends Model {
  
  var currFrom: Double = 0
  var currTo: Double = 0
  var ans:Double = 0
  var buff:String = new String
  var date:String = new String
  var currency:ArrayBuffer[String] = new ArrayBuffer
  var currencyRate:ArrayBuffer[Double] = new ArrayBuffer
  var unit:ArrayBuffer[Int] = new ArrayBuffer
  
      /**
     * Creating a connection to specific xml, copy the data to a local file, and translate it's values to variables
     *  
     *  @return String      connection status
     */
  override
    def parseXML(): String = {
      try{
      val url = new URL("http://www.boi.org.il/currency.xml")
      val connection = url.openConnection
      val doc = XML.load(connection.getInputStream)
      XML.save("toParse.xml", doc)
      for(ob<-(doc\\"CURRENCY")){
        currency += (ob\"CURRENCYCODE").text
        currencyRate += (ob\"RATE").text.toDouble
        unit += (ob\"UNIT").text.toInt
      }
      }
      catch{
        case e:Exception =>{
          try{
            val doc = XML.load("toParse.xml")
            for(ob<-(doc\\"CURRENCY")){
              currency += (ob\"CURRENCYCODE").text
              currencyRate += (ob\"RATE").text.toDouble
              unit += (ob\"UNIT").text.toInt
            }
          }
          catch{
            case fnfe:FileNotFoundException => return "First time connection"
          }
          return "No Connection"
        }
      }
       "OK"
     }
  
      /**
     * Convert one value to an other
     * 
     * @param value            value to convert
     * @param currencyFrom     value to convert from
     * @param currencyTo       value to convert to
     * @return ans             value after converting
     */
  override
    def exchange(value:Double, currencyFrom:String, currencyTo:String): Double = {
      val doc = XML.load("toParse.xml")
      for(ob<-(doc\\"CURRENCY")){
          currency += (ob\"CURRENCYCODE").text
          currencyRate += (ob\"RATE").text.toDouble
          unit += (ob\"UNIT").text.toInt
        }
      if(currencyFrom == currencyTo)
        return value
      if (currencyFrom != "NIS"){
        for((ob, index)<-currency.zipWithIndex){
          if(currencyFrom == ob.mkString){
            currFrom = currencyRate(index)/unit(index)
            }
        }
      }
      if (currencyTo != "NIS"){
        for ((ob, index)<-currency.zipWithIndex){
          if (currencyTo == ob.mkString){
             currTo = currencyRate(index)/unit(index)     
          }
        }
      }
      if (currencyTo == "NIS")
        ans = value * currFrom
      else if (currencyFrom == "NIS")
        ans = value / currTo
      else
        ans = value * currFrom / currTo
      ans
    }
  
      /**
     * Takes the last update from an xml
     * 
     * @return string       last time data was updated
     */
  override
    def returnDate(): String = {
        try{
          val doc = XML.load("toParse.xml")
          date = (doc\\"LAST_UPDATE").text
          date
          }
        catch{
          case fnfe:FileNotFoundException => "No connection"
        }
      }
    
    
      /**
     * Making concatenation
     * 
     * @return string     concatenate 
     */
  override
    def initTable(): String = {
        for(ob<-currencyRate){
         buff = buff + ob.toString()
         buff = buff + ','
        }
      buff
      }
  

}