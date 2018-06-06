package it.polito.tdp.rivers;

import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

public class Model {

	private List<River> rivers;
	private List<Flow> flussi;
	private RiversDAO dao;
	private River fiume;
	
	private Simulatore sim;
	
	public Model() {
		dao= new RiversDAO();
		flussi= new LinkedList<>();
	}

	//lista rivers
	public List<River> getRivers() {
		this.rivers= dao.getAllRivers();
		return rivers;
	}
	
	//lista flow dato un river
	public List<Flow> getFlowByRiver(River r){
		this.fiume=r;
		this.flussi= dao.getFlussi(fiume);
		fiume.setFlows(flussi);
		return flussi;
	}


	
	
}
