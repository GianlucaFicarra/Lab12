package it.polito.tdp.rivers;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;


public class Simulatore {
	
	// Parametri di simulazione
	private double Q;
	private double C_in;
	private double F_out_min;
	private double F_med;
	
	private double F_in;
	private double F_out;
	
	// Modello del mondo
	private List <Double> bacino; // capacità presente ogni giorno nel bacino
	
	// Valori in output
	private int N_giorni; // numeri di giorni con C < F_out_min
	
	// Coda degli eventi
	private LinkedList<Event> queue;
	
	private class Event {
		private Flow flow;
		
		public Event (Flow flow) {
			this.flow = flow;
		}
	}
	
	public double convertToDay (double flow) {
		return flow*24*60*60;
	}
	
	public void init (double k, River river){
		
		this.F_med = this.convertToDay(river.getFlowAvg());
		this.Q = k * F_med * 30;
		this.C_in = Q / 2;
		this.F_out_min = 0.8 * F_med;
		this.F_in = 0;
		
		this.bacino = new ArrayList<>();
		this.queue = new LinkedList<>();
		
		for (Flow f : river.getFlows())
			this.queue.add(new Event (f));
		
		this.N_giorni = 0;
		
	}
	
	public void run () {
		
		Event e;
		
		System.out.println("Capienza totale del bacino idrico " + Q);
		
		while ((e = this.queue.poll()) != null) {
			F_out = 0.0;
			this.F_in = e.flow.getFlow();
			this.C_in += this.convertToDay((this.F_in));
			
			// tracimazione
			if (this.C_in > this.Q) {
			//	this.F_out += (this.C_in - this.Q);
				this.C_in = this.Q;
			}
			
			
			// calcolo della F_out
			if (Math.random() <= 0.05) 
				this.F_out += 10 * this.F_out_min;
			else
				this.F_out += this.F_out_min;			
			
			
			if (C_in < F_out) {
				// Non riesco a garantire la quantità minima.
				N_giorni++;
				// Il bacino comunque si svuota
				C_in = 0;
			} else {
				// Faccio uscire la quantità giornaliera
				C_in -= F_out;
			}
			
			// Mantengo un lista della capacità giornaliere del bacino
			this.bacino.add(this.C_in);

		}
		
	}

	public int getN_giorni() {
		return N_giorni;
	}
	
	public double getC_med() {
		double sum = 0;
		for (Double d : this.bacino)
			sum += d;
		
		return (sum / bacino.size());
	}
	
	

}
