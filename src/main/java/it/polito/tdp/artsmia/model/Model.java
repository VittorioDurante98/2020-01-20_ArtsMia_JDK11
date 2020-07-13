package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Artist, DefaultWeightedEdge> grafo;
	private List<String> ruoli;
	private List<Arco> archi;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		this.ruoli = new ArrayList<String>(dao.listRuoli());
	}

	public List<String> getRuoli() {
		return ruoli;
	}
	
	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<Artist, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.listArtist(ruolo));
		this.archi= new ArrayList<Arco>(dao.listArchi(ruolo));
		for(Arco a: archi) {
			Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}

	public Graph<Artist, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public String getConnessi() {
		Collections.sort(archi);
		String elenco="";
		for(Arco a: archi) {
			elenco+= a.getA1().getId()+" "+a.getA2().getId()+" "+a.getPeso()+"\n";
		}
		return elenco;
	}

}
