package il.ac.shenkar.CurrencyExchanger;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class View{

    // Variables declaration                     
    private JButton button;
    private JComboBox<String> options1;
    private JComboBox<String> options2;
    private JLabel title;
    private JLabel date;
    private JLabel currDate;
    
    //getters
    public JButton getButton() {
		return button;
	}

	public JTable getTable() {
		return table;
	}

	public JLabel getCurrDate() {
		return currDate;
	}
	
    public Logger getMyLog() {
		return myLog;
	}

	private JLabel from;
    private JLabel to;
    private JTextField input;
    private JTextField result;
    private JTable table;
    private JScrollPane jScrollPane1;
    private JFrame frame;
    private static Logger myLog = Logger.getLogger(View.class);

  /**
   * Constructor - call to initComponents() that creates the GUI
   * 
   */
    public View() {
        initComponents();
    }

    /**
     * Creates the GUI
     * 
     */               
    public void initComponents() {

    	title = new JLabel();
        date = new JLabel();
        currDate=new JLabel();
        from=new JLabel("From");
        to=new JLabel("To");
        input = new JTextField();
        result = new JTextField();
        options1 = new JComboBox<String>();
        options2 = new JComboBox<String>();
        button = new JButton();
        table = new JTable(new Object [][] {
                {"USD", null},
                {"GBP", null},
                {"JPY", null},
                {"EUR", null},
                {"AUD", null},
                {"CAD", null},
                {"DKK", null},
                {"NOK", null},
                {"ZAR", null},
                {"SEK", null},
                {"CHF", null},
                {"JOD", null},
                {"LBP", null},
                {"EGP", null}
            },
            new String [] {
                "Currency", "Rate"
            });
        jScrollPane1 = new JScrollPane();
        frame= new JFrame("Currency Exchanger");
        
        frame.setLayout(null);
		frame.setSize(580,350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        title.setForeground(new Color(0, 0, 100));
        title.setText("Currency Exchanger");
        title.setSize(150,20);
        title.setLocation(20,10);
        
        currDate.setSize(150,20);
        currDate.setLocation(390,10);
        
        date.setText("Last Update: ");
        date.setSize(150,20);
        date.setLocation(300,10);
        
        from.setSize(50,20);
        from.setLocation(20,80);
        
        options1.setSize(55, 20);
        options1.setLocation(55, 80);
        options1.setModel(new DefaultComboBoxModel<String>(new String[] {"NIS", "USD", "GBP", "JPY", "EUR","AUD","CAD","DKK","NOK","ZAR","SEK","CHF","JOD","LBP","EGP" }));
  
        to.setSize(50,20);
        to.setLocation(120,80);
        
        options2.setSize(55, 20);
        options2.setLocation(140, 80);
        options2.setModel(new DefaultComboBoxModel<String>(new String[] {"NIS", "USD", "GBP", "JPY", "EUR","AUD","CAD","DKK","NOK","ZAR","SEK","CHF","JOD","LBP","EGP" }));
        
        input.setText("Please Enter Amount");
        input.setSize(170, 20);
        input.setLocation(20, 50);
        input.addFocusListener(new FocusListener() {
			
        	/**
        	 * 
        	 * Responsible at the input field
        	 * 
        	 */
			@Override
			public void focusLost(FocusEvent e) {
				
				
			}
			
        	/**
        	 * 
        	 * Responsible at the input field
        	 * 
        	 */
			@Override
			public void focusGained(FocusEvent e) {
				input.setText("");
				
			}
		});
        
        result.setSize(170, 20);
        result.setLocation(20, 160);
        
        button.setText("Convert");
        button.setSize(90,25);
        button.setLocation(65,120); 
        
        button.addActionListener(new ActionListener() {
			
            /**
        	 * Responsible at the convert button action
             * @param ActionEvent e
             * 
             */
			@Override
			public void actionPerformed(ActionEvent e) {
				Exchanger ex = new Exchanger();
				String amount = input.getText();
				if (isDouble(amount) == false){
				    myLog.error("User wrote illegal input");
					result.setText("Illegal Input");
				}
				else{
					String currFrom = (String)options1.getSelectedItem();
					String currTo = (String)options2.getSelectedItem();
					myLog.info("User ask to convert " + amount+ " " + currFrom + " to: " + currTo );
					double sum = Double.parseDouble(amount);
					double res = ex.exchange(sum, currFrom, currTo);
					result.setText(String.valueOf(res));
					myLog.info("After converting the result is: " + result.getText() + " "+ currTo );
				}
			}
		});
        
        table.setSize(200,225);
        table.setLocation(300,50);
            
        frame.add(button);
        frame.add(from);
        frame.add(to);
        frame.add(options1);
        frame.add(input);
        frame.add(title);
        frame.add(options2);
        frame.add(result);
        frame.add(jScrollPane1);
        frame.add(table); 
        frame.add(date);
        frame.add(currDate);
        
        button.requestFocusInWindow();
        frame.setVisible(true);
        
    }
    
    public static void main(String args[]) throws InterruptedException {
    	
    	PropertyConfigurator.configure("log4j.properties");
    	BasicConfigurator.configure(); // default setting
    	
    	View view = new View();
    	Exchanger model = new Exchanger();
    	String status = model.parseXML();	
    	if (status == "First time connection"){
    		myLog.info("No internet Connection");
    		view.currDate.setText("No internet Connection");
    		view.button.setEnabled(false);
    	}
    	else{
    		view.button.setEnabled(true);
    		if (status != "OK"){
            	myLog.info("No internet Connection, Using Old File");
    		}
    		view.currDate.setText(model.returnDate());
    		myLog.info("Last time data was updated on: "+view.currDate.getText());
   			String initTable = model.initTable();
   			String[] data = initTable.split("\\,");
   			if (view.currDate.getText() != "No connection"){
   				for (int row = 0; row < view.table.getRowCount(); row++){
    				view.table.setValueAt(data[row], row, 1); 
    			}
    		}
    	}
    	CheckCurrentDate checkThread = new CheckCurrentDate(view);
   		checkThread.start();
    }
    

	/**
     * Checks if the input is correctly
     * 
     * @param input                  the parameters that the user puts in the fields
     * @return true or false         double or not
     */
    boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}