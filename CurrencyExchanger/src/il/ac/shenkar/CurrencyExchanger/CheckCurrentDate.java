package il.ac.shenkar.CurrencyExchanger;

import org.apache.log4j.Logger;

public class CheckCurrentDate extends Thread {
	
	private static View view;
	private Exchanger model = new Exchanger();
	
	public CheckCurrentDate(View _view)
	{
		view = _view;
	}
	@Override
	public void run() {
		while (true){
			String status = model.parseXML();
			Logger myLog = view.getMyLog();
			myLog.info("Checking if file is up to date...");
			if (status == "First time connection"){
	    		myLog.info("No internet Connection");
	    		view.getCurrDate().setText("No internet Connection");
	    		view.getButton().setEnabled(false);
	    	}
	    	else{
	    		view.getButton().setEnabled(true);
	    		if (status != "OK"){
	            	myLog.info("No internet Connection, Using Old File");
	    		}
    			view.getCurrDate().setText(model.returnDate());
	   			myLog.info("Last time data was updated on: "+view.getCurrDate().getText());
	   			String initTable = model.initTable();
	   			String[] data = initTable.split("\\,");
	   			if (view.getCurrDate().getText() != "No connection"){
	    			for (int row = 0; row < view.getTable().getRowCount(); row++){
	    				view.getTable().setValueAt(data[row], row, 1); 
	    			}
	    		}	    		
	    	}
			try {
				Thread.sleep(180000);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
	



