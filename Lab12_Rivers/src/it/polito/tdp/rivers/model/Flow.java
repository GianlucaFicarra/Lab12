package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow {
	
	private LocalDate day;
	private double flow;
	/*private River river;
	 * elimino  i riferimenti al fiume tanto dalla query 
	 * ne analizzo solo 1
	 */


	public Flow(LocalDate day, double flow) {
		this.day = day;
		this.flow = flow;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public double getFlow() {
		return flow;
	}
	
	public void setFlow(double flow) {
		this.flow = flow;
	}

	@Override
	public String toString() {
		return "Flow [day=" + day + ", flow=" + flow + "]";
	}

	
}
