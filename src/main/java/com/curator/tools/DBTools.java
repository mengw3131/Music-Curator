package com.curator.tools;

import java.sql.*;
import com.curator.tools.SpotifyTools;
import java.util.*;
import com.curator.models.*;

public class DBTools {
	private Connection conn;
	private Statement st;
	private ResultSet rs;
	//private SpotifyTools spotify;
	 private com.wrapper.spotify.model_objects.specification.Track sTrack;
	
	public DBTools() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://music-curator-user-preference.cd6o4ckow0r1.us-east-2.rds.amazonaws.com:3306/music_curator_user_preference?user=admin&password=musiccurator");
			st = conn.createStatement();
		} catch (Exception ex) {
			System.out.println("Error: " + ex);
		}
	}
	
	
	/*
	 * Returns an ArrayList of user liked songs by passing in the userID
	 */
	public ArrayList<Track> getUserLikedSongs(String userID) {
		ArrayList<Track> userLikedSongs= new ArrayList<>();	
		String trackID = "";
		try {
			String query = "SELECT Track_id FROM User_Preference_Song WHERE `Like/Dislike` = '1' AND User_id = userID";
			rs = st.executeQuery(query);
			while (rs.next()) {
				trackID = rs.getString("Track_id");
				Track track = SpotifyTools.getTrack(trackID);
				userLikedSongs.add(track);
				System.out.println(rs.getString("Track_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedSongs;
		
	}
	
	/*
	 * Returns an ArrayList of user liked artists by passing in the userID
	 */
	public ArrayList<Artist> getUserLikedArtists(String userID) {
		ArrayList<Artist> userLikedArtist= new ArrayList<>();	
		String artistID = "";
		try {
			String query = "SELECT Artist_id FROM User_Preference_Artist WHERE `Like/Dislike` = '1' AND User_id = userID";
			rs = st.executeQuery(query);
			while (rs.next()) {
				artistID = rs.getString("Artist_id");
				Artist artist = SpotifyTools.getArtist(artistID);
				userLikedArtist.add(artist);
				System.out.println(rs.getString("Track_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedArtist;
	}
	
	/*
	 * Returns an ArrayList of user liked albums by passing in the userID
	 */
	public ArrayList<Album> getUserLikedAlbum(String userID) {
		ArrayList<Album> userLikedAlbum= new ArrayList<>();	
		String albumID = "";
		try {
			String query = "SELECT Artist_id FROM User_Preference_Artist WHERE `Like/Dislike` = '1' AND User_id = userID";
			rs = st.executeQuery(query);
			while (rs.next()) {
				albumID = rs.getString("Album_id");
				Album album = SpotifyTools.getAlbum(albumID);
				userLikedAlbum.add(album);
				System.out.println(rs.getString("Track_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedAlbum;
	}
	
	/*
	 * Store the user's like/dislike action into according database
	 */
	public void storeData(String userID, Track track, Album album, Artist artist, boolean like) {
		String trackID = track.getTrackID();
		String albumID = album.getAlbumID();
		String artistID = artist.getArtistID();
		try {
			PreparedStatement preSt1 = conn.prepareStatement("INSERT INTO User_Preference_Song (User_id, Track_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt1.setString(1, userID);
			preSt1.setString(2, trackID);
			preSt1.setBoolean(3, like);
			preSt1.executeUpdate();
			
			PreparedStatement preSt2 = conn.prepareStatement("INSERT INTO User_Preference_Artist (User_id, Artist_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt2.setString(1, userID);
			preSt2.setString(2, artistID);
			preSt2.setBoolean(3, like);
			preSt2.executeUpdate();
			
			PreparedStatement preSt3 = conn.prepareStatement("INSERT INTO User_Preference_Album (User_id, Album_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt3.setString(1, userID);
			preSt3.setString(2, albumID);
			preSt3.setBoolean(3, like);
			preSt3.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
