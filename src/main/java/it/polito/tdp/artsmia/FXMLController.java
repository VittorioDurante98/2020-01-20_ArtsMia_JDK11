package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	//txtResult.appendText(model.getConnessi());
    	txtResult.appendText(model.artistiConnessi());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	//PRIMO TENTATIVO
    	/*txtResult.clear();
    	String ruolo = boxRuolo.getValue();
    	if(ruolo == null) {
    		txtResult.appendText("Scegli un ruolo");
    		return;
    	}
    	model.creaGrafo(ruolo);
    	txtResult.appendText("Vertici: "+model.getGrafo().vertexSet().size());
    	txtResult.appendText("\nArchi: "+model.getGrafo().edgeSet().size());
    	btnArtistiConnessi.setDisable(false);*/
    	
    	//SECONDO TENTATIVO
    	txtResult.clear();
    	if (boxRuolo.getValue()==null) {
			txtResult.appendText("Seleziona un ruolo!\n");
			return;
		}
    	String ruolo= boxRuolo.getValue().toString();
    	model.setGrafo(ruolo);
    	txtResult.appendText("Grafo creato\n");
    	txtResult.appendText("Vertici:"+model.getGrafo().vertexSet().size()+"\n");
    	txtResult.appendText("Archi:"+model.getGrafo().edgeSet().size()+"\n");
    	this.btnArtistiConnessi.setDisable(false);
    	
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		/*this.boxRuolo.getItems().addAll(model.getRuoli());
		btnArtistiConnessi.setDisable(true);*/
		this.boxRuolo.getItems().addAll(model.getRuoli());
		btnArtistiConnessi.setDisable(true);
	}
}

