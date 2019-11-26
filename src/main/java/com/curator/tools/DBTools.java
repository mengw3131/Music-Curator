package com.curator.tools;

import java.sql.*;
import com.curator.tools.SpotifyTools;
import java.util.*;
import com.curator.models.*;

public class DBTools {
	private static Connection conn;
	private static ResultSet rs;

	private static String USER_ID;

	private DBTools(){}

	public static void initialize(String user_id){
		USER_ID = user_id;

	    if (conn == null) {
			try {
				conn = DriverManager.getConnection("jdbc:mysql://music-curator-user-preference.cd6o4ckow0r1.us-east-2.rds.amazonaws.com:3306/music_curator_user_preference?user=admin&password=musiccurator");
				System.out.println("Connection Successful");
			} catch (Exception ex) {
				System.out.println("Error: " + ex);
			}
		}
	}

	/*
	 * Returns an ArrayList of user liked songs by passing in the USER_ID
	 */
	public static ArrayList<Track> getUserLikedSongs() {
		
		ArrayList<Track> userLikedSongs= new ArrayList<>();	
		String trackID = "";
		try {
			if (conn == null) {
				System.out.print("error");
			} else {
				System.out.print("success");
			}
			PreparedStatement preStSong = conn.prepareStatement("SELECT Track_id FROM User_Preference_Song WHERE `Like/Dislike` = '1' AND User_id = ?");
			preStSong.setString(1, USER_ID);
			rs = preStSong.executeQuery();
			while (rs.next()) {
				trackID = rs.getString("Track_id");
				Track track = SpotifyTools.getTrack(trackID);
				userLikedSongs.add(track);
//				System.out.println(rs.getString("Track_id"));
//				System.out.println(trackID);
//				System.out.println(track.getTrackID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedSongs;
		
	}
	
	/*
	 * Returns an ArrayList of user liked artists by passing in the USER_ID
	 */
	public static ArrayList<Artist> getUserLikedArtists() {
		ArrayList<Artist> userLikedArtist= new ArrayList<>();	
		String artistID = "";
		try {
			PreparedStatement preStArtist = conn.prepareStatement("SELECT Artist_id FROM User_Preference_Artist WHERE `Like/Dislike` = '1' AND User_id = ?");
			preStArtist.setString(1, USER_ID);
			rs = preStArtist.executeQuery();
			while (rs.next()) {
				artistID = rs.getString("Artist_id");
				Artist artist = SpotifyTools.getArtist(artistID);
				userLikedArtist.add(artist);
				System.out.println(rs.getString("Artist_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedArtist;
	}
	
	/*
	 * Returns an ArrayList of user liked albums by passing in the USER_ID
	 */
	public static ArrayList<Album> getUserLikedAlbum() {
		ArrayList<Album> userLikedAlbum= new ArrayList<>();	
		String albumID = "";
		try {
			PreparedStatement preStAlbum = conn.prepareStatement("SELECT Album_id FROM User_Preference_Album WHERE `Like/Dislike` = '1' AND User_id = ?");
			preStAlbum.setString(1, USER_ID);
			rs = preStAlbum.executeQuery();
			while (rs.next()) {
				albumID = rs.getString("Album_id");
				Album album = SpotifyTools.getAlbum(albumID);
				userLikedAlbum.add(album);
				System.out.println(rs.getString("Album_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userLikedAlbum;
	}
	
	/*
	 * Returns an ArrayList of user disliked songs by passing in the USER_ID
	 */
	public static ArrayList<Track> getUserDislikedSongs() {
		ArrayList<Track> userDislikedSongs= new ArrayList<>();	
		String trackID = "";
		try {
			PreparedStatement preStSong1 = conn.prepareStatement("SELECT Track_id FROM User_Preference_Song WHERE `Like/Dislike` = '0' AND User_id = ?");
			preStSong1.setString(1, USER_ID);
			rs = preStSong1.executeQuery();
			while (rs.next()) {
				trackID = rs.getString("Track_id");
				Track track = SpotifyTools.getTrack(trackID);
				userDislikedSongs.add(track);
				System.out.println(rs.getString("Track_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDislikedSongs;
		
	}
	
	/*
	 * Returns an ArrayList of user disliked artists by passing in the USER_ID
	 */
	public static ArrayList<Artist> getUserDislikedArtists() {
		ArrayList<Artist> userDislikedArtist= new ArrayList<>();	
		String artistID = "";
		try {
			PreparedStatement preStArtist1 = conn.prepareStatement("SELECT Artist_id FROM User_Preference_Artist WHERE `Like/Dislike` = '0' AND User_id = ?");
			preStArtist1.setString(1, USER_ID);
			rs = preStArtist1.executeQuery();
			while (rs.next()) {
				artistID = rs.getString("Artist_id");
				Artist artist = SpotifyTools.getArtist(artistID);
				userDislikedArtist.add(artist);
				System.out.println(rs.getString("Artist_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDislikedArtist;
	}
	
	/*
	 * Returns an ArrayList of user disliked albums by passing in the USER_ID
	 */
	public static ArrayList<Album> getUserDislikedAlbum() {
		ArrayList<Album> userDislikedAlbum= new ArrayList<>();	
		String albumID = "";
		try {
			PreparedStatement preStAlbum = conn.prepareStatement("SELECT Album_id FROM User_Preference_Album WHERE `Like/Dislike` = '0' AND User_id = ?");
			preStAlbum.setString(1, USER_ID);
			rs = preStAlbum.executeQuery();
			while (rs.next()) {
				albumID = rs.getString("Album_id");
				Album album = SpotifyTools.getAlbum(albumID);
				userDislikedAlbum.add(album);
				System.out.println(rs.getString("Album_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userDislikedAlbum;
	}
	
	/*
	 * Stores the user's liked/disliked songs into according database
	 */
	public static void storeUserPreferenceTracks(String trackID, boolean like) {
		try {
			PreparedStatement preSt1 = conn.prepareStatement("INSERT INTO User_Preference_Song (User_id, Track_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt1.setString(1, USER_ID);
			preSt1.setString(2, trackID);
			preSt1.setBoolean(3, like);
			preSt1.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Stores the user's liked/disliked artists into according database
	 */
	public static void storeUserPreferenceArtist(String artistID, boolean like) {
		try {
			PreparedStatement preSt2 = conn.prepareStatement("INSERT INTO User_Preference_Artist (User_id, Artist_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt2.setString(1, USER_ID);
			preSt2.setString(2, artistID);
			preSt2.setBoolean(3, like);
			preSt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Stores the user's liked/disliked albums into according database
	 */
	public static void storeUserPreferenceAlbum(String albumID, boolean like) {
		try {
			PreparedStatement preSt3 = conn.prepareStatement("INSERT INTO User_Preference_Album (User_id, Album_id, `Like/Dislike`) VALUES(?,?,?);");
			preSt3.setString(1, USER_ID);
			preSt3.setString(2, albumID);
			preSt3.setBoolean(3, like);
			preSt3.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
