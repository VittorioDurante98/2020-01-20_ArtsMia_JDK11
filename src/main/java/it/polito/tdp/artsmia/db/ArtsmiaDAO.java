package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
    
	public List<String> listRuoli() {
		
		String sql = "SELECT distinct(authorship.role) FROM authorship ORDER BY authorship.role ASC";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
   public List<Artist> listArtist(String ruolo) {
		
		String sql = "SELECT a.artist_id, a.name FROM artists AS a, authorship AS r WHERE a.artist_id=r.artist_id AND r.role=? GROUP BY a.artist_id";
		List<Artist> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Artist a= new Artist(res.getInt("artist_id"), res.getString("name"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
   
   public List<Arco> listArchi(String ruolo) {
		
		String sql = "SELECT a1.artist_id as id1, a1.name as n1, a2.artist_id as id2, a2.name as n2, COUNT(distinct e1.exhibition_id) AS peso " + 
				"FROM artists AS a1, artists AS a2, authorship AS r1, authorship AS r2, exhibition_objects AS e1, exhibition_objects AS e2 " + 
				"WHERE a1.artist_id > a2.artist_id AND a1.artist_id=r1.artist_id AND a2.artist_id=r2.artist_id " + 
				"      AND r1.role =? AND r2.role=? " + 
				"      AND r1.object_id = e1.object_id AND r2.object_id = e2.object_id " + 
				"		AND e1.exhibition_id=e2.exhibition_id " + 
				"GROUP BY a1.artist_id, a2.artist_id";
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			st.setString(2, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Arco a= new Arco((new Artist(res.getInt("id1"), res.getString("n1"))), 
						          new Artist(res.getInt("id2"), res.getString("n2")), res.getInt("peso"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
