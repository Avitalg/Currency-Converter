package il.ac.shenkar.CurrencyExchanger;

/*
@author Model this is an interface
@see className Exchanger
* 
*/
public interface Model {
	
    /**
   * Creating a connection to specific web site and reads the XML data, saves the data locally, and translate it's values to variables
   *  
   * @return String      status of connection 
   */
	public String parseXML();
	
    /**
     * Convert one value to an other
     * 
     * @param value            value to convert
     * @param currencyFrom     value to convert from
     * @param currencyTo       value to convert to
     * @return ans             value after converting
     */
	public double exchange(double value, String currencyFrom, String currencyTo);
	
    /**
   * Takes the last update from an xml
   * 
   * @return string       last time data was updated
   */
	public String returnDate();
	
    /**
   * Making concatenation
   * 
   * @return string     concatenate 
   */
	public String initTable();
}
