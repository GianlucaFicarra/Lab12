package it.polito.tdp.rivers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RiversController {

	private Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<River> boxRiver;

    @FXML
    private TextField txtStartDate;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtNumMeasurements;

    @FXML
    private TextField txtFMed;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    private TextArea txtResult;


	public void setModel(Model model) {
		this.model = model;
		
		//setto i valori dell tendina
		boxRiver.getItems().addAll(model.getRivers());		
	}
	
    @FXML
    void doRiver(ActionEvent event) {
    	txtResult.clear();
    	txtStartDate.clear();
 		txtEndDate.clear();
 		txtNumMeasurements.clear();
        txtFMed.clear();
         
    	River r = boxRiver.getValue();
    	if(r==null) {
    		txtResult.setText("Seleziona un fiume!!!");
    		return;
    	}
    	
    	List<Flow> flussi= model.getFlowByRiver(r);//prende flussi di fiume e li setta
    	
    	//setto i valori dei campi
        txtStartDate.appendText(String.format("%s", flussi.get(0).getDay()));
		txtEndDate.appendText(String.format("%s",flussi.get(flussi.size()-1).getDay()));
		txtNumMeasurements.appendText(String.format("%d",flussi.size()));
        txtFMed.appendText(String.format("%.2f",r.getFlowAvg()));
    }
	
    
    @FXML
    void doSimula(ActionEvent event) {

    txtResult.clear();
    	
    	try {
    		//prendo valore fiume
    		River river = this.boxRiver.getValue();
        	
        	if (river == null) {
        		this.txtResult.appendText("Selezionare un fiume!\n");
        		return;
        	}
        	
        	
        	//prendo fattore di proporzionalita e lo controllo
    		double k = Double.parseDouble(this.txtK.getText());
    		
    		if (k > 0) { //k deve essere espressamente POS, se si avvio simulazione
    			
    			Simulatore sim = new Simulatore();//
    			sim.init(k, river);
    			sim.run();
    			this.txtResult.appendText(String.format("Numero di giorni in cui non si è potuta garantire l'erogazione minima: %d\n", sim.getN_giorni()));
    			this.txtResult.appendText(String.format("Occupazione media del bacino: %.3f", sim.getC_med()));
    		
    		} else
    			this.txtResult.appendText("Inserire k positivo\n");
    		
    	} catch (NumberFormatException e){//gestisce campo vuoto e lettere
    		this.txtResult.appendText("Inserire un valore valido di K!\n");
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Rivers.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Rivers.fxml'.";

        
    }
}
