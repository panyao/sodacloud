package org.magnum.soda.examples;

import java.util.List;



import java.util.LinkedList;
import java.util.List;



public class Reports {

	private List<ReportsListener> listeners_ = new LinkedList<ReportsListener>();
	private List<Report> reports_ = new LinkedList<Report>();


	public void addReport(Report r) {
		reports_.add(r);
		for(ReportsListener l : listeners_){
			l.reportAdded(r);
		}
	}
	public void modifyReport(Report r){
		for(Report m : reports_){
			if(m.getContent().equals(r.getContent()))
				m.setContent(r.getContent());
				
		}
		for(ReportsListener l : listeners_){
			l.reportchanged(r);
		}
	}

	public List<Report> getReports() {
		return reports_;
	}

	
	public void addListener(ReportsListener l) {
		listeners_.add(l);
	}

	
	public void removeListener(ReportsListener l) {
		listeners_.remove(l);
	}

}