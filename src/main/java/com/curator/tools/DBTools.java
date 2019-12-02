package com.curator.tools;

import com.curator.models.Album;
import com.curator.models.Artist;
import com.curator.models.Playlist;
import com.curator.models.Track;

import java.sql.*;
import java.util.ArrayList;

public class DBTools {
    private static Connection conn;
    private static ResultSet rs;
    private static String USER_ID;

    private DBTools() {
    }

    public static void initialize(String user_id) {
        USER_ID = user_id;

        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://music-curator-user-preference.cd6o4ckow0r1.us-east-2.rds.amazonaws.com:3306/music_curator_user_preference?user=admin&password=musiccurator");
                System.out.println("Connection Successful. Logged in as " + USER_ID);

                if (isNewUser()){
                    addNewUser();
                    //initialize dummy data
                    storePlaylist(new Playlist("New Playlist"));
                } else {
                    incrementLoginCount();
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex);
            }
        }
    }


    public static int incrementLoginCount(){
        try {
            String q =  "UPDATE user_meta SET login_count = ? WHERE username = ?;";
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setInt(1, getLoginCount() + 1);
            stmt.setString(2, USER_ID);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getLoginCount(){
        try {
            String q =  "SELECT login_count FROM user_meta WHERE username = ?;";
            PreparedStatement preStSong = conn.prepareStatement(q);
            preStSong.setString(1, USER_ID);
            rs = preStSong.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addNewUser(){
        String q = "INSERT INTO user_meta (username, login_count) VALUES(?, ?);";
        try {
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setString(1, USER_ID);
            stmt.setInt(2, 1);
            stmt.execute();
            System.out.println("Added new user: " + USER_ID);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean isNewUser(){
        String q = "SELECT EXISTS(SELECT username FROM user_meta WHERE username = ? LIMIT 1);";
        try {
            PreparedStatement preStPlaylistExist = conn.prepareStatement(q);
            preStPlaylistExist.setString(1, USER_ID);

            rs = preStPlaylistExist.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                System.out.println(USER_ID + " is not a new user");
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(USER_ID + " is a new user");
        return true;
    }

    /*
     * Returns an ArrayList of user liked songs by passing in the USER_ID
     */
    public static ArrayList<Track> getUserLikedSongs() {

        ArrayList<Track> userLikedSongs = new ArrayList<>();
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
        ArrayList<Artist> userLikedArtist = new ArrayList<>();
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
        ArrayList<Album> userLikedAlbum = new ArrayList<>();
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
        ArrayList<Track> userDislikedSongs = new ArrayList<>();
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
        ArrayList<Artist> userDislikedArtist = new ArrayList<>();
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
        ArrayList<Album> userDislikedAlbum = new ArrayList<>();
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
        //if track exists in preference table, update like/dislike value
        if (isTrackExistInUPSong(trackID)) {
            updateTrackPreference(like, trackID);
        } else {
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
    }

    /*
     * Stores the user's liked/disliked artists into according database
     */
    public static void storeUserPreferenceArtist(String artistID, boolean like) {
        //if artist exists in preference table, update like/dislike value
        if (isArtistExistInUPArtist(artistID)) {
            updateArtistPreference(like, artistID);
        } else {
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
    }

    /*
     * Stores the user's liked/disliked albums into according database
     */
    public static void storeUserPreferenceAlbum(String albumID, boolean like) {
        //if artist exists in preference table, update like/dislike value
        if (isAlbumExistInUPAlbum(albumID)) {
            updateAlbumPreference(like, albumID);
        } else {
            try {
                String q = "INSERT INTO User_Preference_Album (User_id, Album_id, `Like/Dislike`) VALUES(?,?,?);";
                PreparedStatement preSt3 = conn.prepareStatement(q);
                preSt3.setString(1, USER_ID);
                preSt3.setString(2, albumID);
                preSt3.setBoolean(3, like);
                preSt3.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if itemId exists in the table specified in query
     *
     * @param itemId id of the item to be checked, e.g. artistId, trackId
     * @param q      SQL query to check the item existence
     * @return true if itemId exists in the table
     */
    private static boolean isItemExistInTable(String itemId, String q) {
        try {
            PreparedStatement stmt = conn.prepareStatement(q);
            stmt.setString(1, USER_ID);
            stmt.setString(2, itemId);
            rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if track exists in preference song table
     *
     * @param trackId trackId of the track to be checked
     * @return true if  track exists in preference song table, otherwise false
     */
    private static boolean isTrackExistInUPSong(String trackId) {
        String q = "SELECT EXISTS(SELECT Track_id FROM User_Preference_Song WHERE User_id = ? " +
                "AND Track_id = ? LIMIT 1);";
        return isItemExistInTable(trackId, q);
    }

    /**
     * Checks if album exists in preference album table
     *
     * @param albumId of the album to be checked
     * @return true if album exists in preference album table, otherwise false
     */
    private static boolean isAlbumExistInUPAlbum(String albumId) {
        String q = "SELECT EXISTS(SELECT Album_id FROM User_Preference_Album " +
                "WHERE User_id = ? AND Album_id = ? LIMIT 1);";
        return isItemExistInTable(albumId, q);
    }

    /**
     * Checks if artist exists in preference artist table
     *
     * @param artistId of the artist to be checked
     * @return true if artist exists in preference artist table, otherwise false
     */
    private static boolean isArtistExistInUPArtist(String artistId) {
        String q = "SELECT EXISTS(SELECT Artist_id FROM User_Preference_Artist " +
                "WHERE User_id = ? AND Artist_id = ? LIMIT 1);";
        return isItemExistInTable(artistId, q);
    }

    /**
     * Update user's preference on item
     * @param newPreference like (true) or dislike (false)
     * @param itemId artistId/trackId/albumId
     * @param q SQL query to update user's preference
     */
    private static void updateItemPreference(boolean newPreference, String itemId, String q) {
        try {
            PreparedStatement preSt1 = conn.prepareStatement(q);
            preSt1.setBoolean(1, newPreference);
            preSt1.setString(2, itemId);
            preSt1.setString(3, USER_ID);
            preSt1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update user's track preference
     * @param newPreference like (true) or dislike (false)
     * @param trackId trackId of the track to be updated
     */
    private static void updateTrackPreference(boolean newPreference, String trackId) {
        String q = "UPDATE User_Preference_Artist SET `Like/Dislike` = ? " +
                "WHERE Artist_id = ? AND User_id = ?";
        updateItemPreference(newPreference, trackId, q);
    }


    /**
     * Update user's album preference
     * @param newPreference like (true) or dislike (false)
     * @param albumId albumId of the album to be updated
     */
    private static void updateAlbumPreference(boolean newPreference, String albumId) {
        String q = "UPDATE User_Preference_Album SET `Like/Dislike` = ? " +
                "WHERE Album_id = ? AND User_id = ?";
        updateItemPreference(newPreference, albumId, q);
    }


    /**
     * Update user's artist preference
     * @param newPreference like (true) or dislike (false)
     * @param artistId artistId of the artist to be updated
     */
    private static void updateArtistPreference(boolean newPreference, String artistId) {
        String q = "UPDATE User_Preference_Artist SET `Like/Dislike` = ? " +
                "WHERE Artist_id = ? AND User_id = ?";
        updateItemPreference(newPreference, artistId, q);
    }


    // ==========================================================================
    // ==========================================================================
    //
    // PLAYLIST METHODS
    //
    // ==========================================================================
    // ==========================================================================

    /*
    describe playlist;
    +-------------+-------------+------+-----+---------+----------------+
    | Field       | Type        | Null | Key | Default | Extra          |
    +-------------+-------------+------+-----+---------+----------------+
    | id          | int(11)     | NO   | PRI | NULL    | auto_increment |
    | user_id     | varchar(45) | NO   |     | NULL    |                |
    | playlist_id | varchar(45) | NO   |     | NULL    |                |
    | track_id    | varchar(45) | NO   |     | NULL    |                |
    +-------------+-------------+------+-----+---------+----------------+
    */

    /**
     * Store playlist to DB
     *
     * @param playlist playlist to be stored
     */
    public static void storePlaylist(Playlist playlist) {
        if (isPlaylistExist(playlist)) {
            System.out.println("Playlist " + playlist.getId() + " already exists.");
            return;
        } else {
            storePlaylistMeta(playlist);

            //if playlist is not empty
            if (playlist.getTracks().size() != 0) {
                storeTracksToPlaylist(playlist.getTracks(), playlist);
            }
        }
    }

    /**
     * Get all of user's playlist
     *
     * @return ArrayList of all user's playlist
     */
    public static ArrayList<Playlist> getAllPlaylist() {
        ArrayList<Playlist> playlists = new ArrayList<>();
        for (String playlist_id : getAllPlaylistIDs()) {
            playlists.add(getPlaylist(playlist_id));
        }
        return playlists;
    }

    /**
     * Get user's playlist with playlist_id
     *
     * @param playlist_id playlist id of the playlist to be obtained
     * @return returns Playlist object if playlist exists
     */
    public static Playlist getPlaylist(String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            System.out.println("Playlist" + playlist_id + " doesn't exist");
            return null;
        }
        return new Playlist(getPlaylistName(playlist_id), getTracksFromPlaylistID(playlist_id), playlist_id);
    }

    /**
     * Get playlist IDs of all user's playlist
     *
     * @return ArrayList of string containing user's playlist ID
     */
    public static ArrayList<String> getAllPlaylistIDs() {
        ArrayList<String> playlistIDs = new ArrayList<>();
        try {
            String q = "SELECT playlist_id FROM playlist_meta WHERE user_id = ?;";
            PreparedStatement ps = conn.prepareStatement(q);

            ps.setString(1, USER_ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                playlistIDs.add(rs.getString("playlist_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistIDs;
    }

    /**
     * Given playlist id, return its tracks
     *
     * @param playlist_id the playlist_id of the playlist to be queried
     * @return ArrayList of Track belonging to the playlist
     */
    public static ArrayList<Track> getTracksFromPlaylistID(String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            System.out.println("Playlist " + playlist_id + " doesn't exist");
            return null;
        }

        ArrayList<Track> tracks = new ArrayList<>();
        try {
            String q = "SELECT track_id FROM playlist WHERE user_id = ? AND playlist_id = ?;";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, USER_ID);
            ps.setString(2, playlist_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                tracks.add(SpotifyTools.getTrack(rs.getString("track_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracks;


    }

    /**
     * Given Playlist object, return its tracks'IDs
     *
     * @param playlist playlist to be queried
     * @return ArrayList of track IDs belonging to the playlist
     */
    public static ArrayList<String> getTracksIDsFromPlaylist(Playlist playlist) {
        if (!isPlaylistExist(playlist)) {
            System.out.println("Playlist " + playlist.getId() + " doesn't exist");
            return null;
        }

        ArrayList<String> tracksID = new ArrayList<>();
        try {
            String q = "SELECT track_id FROM playlist WHERE User_id = ? AND playlist_id = ?;";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, USER_ID);
            ps.setString(2, playlist.getId() + "");
            rs = ps.executeQuery();
            while (rs.next()) {
                tracksID.add(rs.getString("track_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tracksID;
    }

    /**
     * Store track to the playlist
     *
     * @param track    track to be stored
     * @param playlist destination playlist
     */
    public static void storeTrackToPlaylist(Track track, Playlist playlist) {
        storeTrackToPlaylist(track.getTrackID(), playlist.getId());
    }

    /**
     * Store track to the playlist
     *
     * @param track_id    track_id of the track to be stored
     * @param playlist_id playlist_id of the destination playlist
     */
    public static void storeTrackToPlaylist(String track_id, String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            System.out.println("Playlist " + playlist_id + " doesn't exist");
            return;
        }
        if (isTrackExistInPlaylist(track_id, playlist_id)) {
            System.out.println("Track " + track_id + " already exist in playlist " + playlist_id);
            return;
        }

        String q = "INSERT INTO playlist (user_id, playlist_id, track_id) VALUES(?,?,?);";
        PreparedStatement psStoreTrackToPlaylist;

        try {
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, USER_ID);
            psStoreTrackToPlaylist.setString(2, playlist_id);
            psStoreTrackToPlaylist.setString(3, track_id);
            psStoreTrackToPlaylist.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Store multiple tracks to the playlist
     *
     * @param tracks   tracks to be stored
     * @param playlist the destination playlist
     */
    public static void storeTracksToPlaylist(ArrayList<Track> tracks, Playlist playlist) {
        for (Track track : tracks) {
            storeTrackToPlaylist(track, playlist);
        }
    }

    /**
     * Rename playlist
     *
     * @param newName     the new name of the playlist
     * @param playlist_id the playlist_id of the playlist to be renamed
     */
    public static void renamePlaylist(String newName, String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            System.out.println("Playlist " + playlist_id + " doesn't exist");
            return;
        }
        String q = "UPDATE playlist_meta SET name = ? WHERE user_id = ? AND playlist_id = ?;";
        PreparedStatement psStoreTrackToPlaylist;
        try {
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, newName);
            psStoreTrackToPlaylist.setString(2, USER_ID);
            psStoreTrackToPlaylist.setString(3, playlist_id);
            psStoreTrackToPlaylist.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Remove track from playlist
     *
     * @param track    track to be removed
     * @param playlist playlist where the track to be removed belongs
     */
    public static void removeTrackFromPlaylist(Track track, Playlist playlist) {
        removeTrackFromPlaylist(track.getTrackID(), playlist.getId());
    }

    /**
     * Remove track from playlist
     *
     * @param track_id    track_id of the track to be removed
     * @param playlist_id playlist_id of the playlist where the track to be removed belongs
     */
    public static void removeTrackFromPlaylist(String track_id, String playlist_id) {
        if (!isTrackExistInPlaylist(track_id, playlist_id)) {
            return;
        }

        String q = "DELETE FROM playlist WHERE user_id = ? AND playlist_id = ? AND track_id = ?;";
        PreparedStatement psStoreTrackToPlaylist;

        try {
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, USER_ID);
            psStoreTrackToPlaylist.setString(2, playlist_id);
            psStoreTrackToPlaylist.setString(3, track_id);
            psStoreTrackToPlaylist.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove playlist from user's playlist
     *
     * @param playlist playlist to be removed
     */
    public static void removePlaylist(Playlist playlist) {
        removePlaylist(playlist.getId());
    }

    /**
     * Remove playlist from user's playlist
     *
     * @param playlist_id playlist_id of the playlist to be removed
     */
    public static void removePlaylist(String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            return;
        }
        PreparedStatement psStoreTrackToPlaylist;

        try {
            //delete from playlist
            String q = "DELETE FROM playlist WHERE user_id = ? AND playlist_id = ?;";
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, USER_ID);
            psStoreTrackToPlaylist.setString(2, playlist_id);
            psStoreTrackToPlaylist.executeUpdate();

            //delete from playlist_meta
            q = "DELETE FROM playlist_meta WHERE user_id = ? AND playlist_id = ?;";
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, USER_ID);
            psStoreTrackToPlaylist.setString(2, playlist_id);
            psStoreTrackToPlaylist.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a track exist in user's playlist with id playlist_id
     *
     * @param track    the track to be checked
     * @param playlist the playlist to be checked
     * @return true if the track is in the playlist
     */
    public static boolean isTrackExistInPlaylist(Track track, Playlist playlist) {
        return isTrackExistInPlaylist(track.getTrackID(), playlist.getId());
    }

    /**
     * Checks if a track exist in user's playlist with id playlist_id
     *
     * @param track_id    the track_id of the track to be checked
     * @param playlist_id the playlist_id of the playlist to be checked
     * @return true if the track is in the playlist
     */
    public static boolean isTrackExistInPlaylist(String track_id, String playlist_id) {
        if (isPlaylistExist(playlist_id)) {
            try {
                String q = "SELECT EXISTS(SELECT id FROM playlist " +
                        "WHERE user_id = ? AND playlist_id = ? AND track_id = ? LIMIT 1);";
                PreparedStatement preStPlaylistExist = conn.prepareStatement(q);
                preStPlaylistExist.setString(1, USER_ID);
                preStPlaylistExist.setString(2, playlist_id);
                preStPlaylistExist.setString(3, track_id);

                rs = preStPlaylistExist.executeQuery();
                rs.next();

                if (rs.getInt(1) == 1) {
                    //System.out.println("Track " + track_id + " exists in " + playlist_id);
                    return true;
                }
                return false;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            return false;
        } else {
            //System.out.println("Track " + track_id + " doesn't exist in " + playlist_id);
            return false;
        }
    }


    // ==========================================================================
    // ==========================================================================
    //
    // PLAYLIST META METHODS
    //
    // ==========================================================================
    // ==========================================================================

    /*
    describe playlist_meta;
    +-------------+-------------+------+-----+---------+----------------+
    | Field       | Type        | Null | Key | Default | Extra          |
    +-------------+-------------+------+-----+---------+----------------+
    | id          | int(11)     | NO   | PRI | NULL    | auto_increment |
    | user_id     | varchar(50) | NO   |     | NULL    |                |
    | playlist_id | varchar(50) | NO   |     | NULL    |                |
    | name        | varchar(50) | NO   |     | NULL    |                |
    | image_link  | varchar(50) | YES  |     | NULL    |                |
    +-------------+-------------+------+-----+---------+----------------+
    */

    /**
     * Store playlist's meta data
     *
     * @param playlist playlist which meta data will be stored
     */
    public static void storePlaylistMeta(Playlist playlist) {
        String q = "INSERT INTO playlist_meta (user_id, playlist_id, name) VALUES(?,?,?);";
        PreparedStatement psStoreTrackToPlaylist;

        try {
            psStoreTrackToPlaylist = conn.prepareStatement(q);
            psStoreTrackToPlaylist.setString(1, USER_ID);
            psStoreTrackToPlaylist.setString(2, playlist.getId() + "");
            psStoreTrackToPlaylist.setString(3, playlist.getName());
            psStoreTrackToPlaylist.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get playlist name given playlist_id
     *
     * @param playlist_id the playlist_id of the playlist whose name to be queried
     * @return the name of the playlist, if playlist exists
     */
    public static String getPlaylistName(String playlist_id) {
        if (!isPlaylistExist(playlist_id)) {
            return null;
        }

        try {
            String q = "SELECT name FROM playlist_meta WHERE User_id = ? AND playlist_id = ?;";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, USER_ID);
            ps.setString(2, playlist_id);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString("name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Checks if user has a playlist with id playlist_id
     *
     * @param playlist_id the id of the playlist to be checked
     * @return true if user has previplaylist
     */
    public static boolean isPlaylistExist(String playlist_id) {
        try {
            String q = "SELECT EXISTS(SELECT playlist_id FROM playlist_meta WHERE user_id = ? AND playlist_id = ? LIMIT 1);";
            PreparedStatement preStPlaylistExist = conn.prepareStatement(q);
            preStPlaylistExist.setString(1, USER_ID);
            preStPlaylistExist.setString(2, playlist_id);

            rs = preStPlaylistExist.executeQuery();
            rs.next();
            if (rs.getInt(1) == 1) {
                //System.out.println("Playlist " + playlist_id + " exists.");
                return true;
            }
            return false;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        //System.out.println("Playlist " + playlist_id + " doesn't exist.");
        return false;
    }

    /**
     * Checks if user has a playlist with id playlist_id
     *
     * @param playlist playlist to be checked
     * @return true if user has previplaylist
     */
    public static boolean isPlaylistExist(Playlist playlist) {
        return isPlaylistExist(playlist.getId());
    }


    /**
     * Terminate DB connection session
     */
    public static void terminate() {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUserId() {
        return USER_ID;
    }
}