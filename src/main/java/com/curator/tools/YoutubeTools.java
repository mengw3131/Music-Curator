package com.curator.tools;

import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;
import org.python.core.PyDictionary;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing static utility methods for youtube-related tasks
 */
public class YoutubeTools {
    private static final int MAX_AUDIO_LENGTH_SECOND = 600;  //10 minutes
    private static final String DEFAULT_OUT_PATH = "src/main/resources/music/";  //10 minutes

    //Pattern matches /watch?v=[video id], compile once only, for performance
    //Design note: avoid using doc based selector(e.g. JSoup) for matching,
    //since might break if youtube changes the structure
    private static Pattern p = Pattern.compile("(/watch\\?v=|\\.be/)([\\w\\-_]*)(&(amp;)?\u200C\u200B[\\w?\u200C\u200B=]*)?");
    private static PythonInterpreter i;

    /**
     * Prevent instance creation
     */
    private YoutubeTools() {
    }


    /**
     * Given '+'-separated query containing keywords, fetch Youtube first page results unique video id.
     * Better matches have lower index on the array list.
     *
     * @return Youtube video ids of the first page
     */
    public static ArrayList<String> getIDsOfBestMatchVideos(String query) {
        String url = "https://www.youtube.com/results?search_query=" + query;
        ArrayList<String> results = new ArrayList<String>();
        HashMap<String, String> uniqueLinks = new HashMap<>();

        try {
            //Get html of the query
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(new HttpGet(url));
            String doc = new BasicResponseHandler().handleResponse(response);
            Matcher m = p.matcher(doc);

            //for every /watch?v=id urls
            while (m.find()) {
                String link = m.group().replace("/watch?v=", "");

                if (uniqueLinks.get(link) == null) {
                    uniqueLinks.put(link, "");
                    results.add(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * TODO: if not finish downloading and user click another music, stop
     * TODO: if possible find a way to stream instead of download
     * TODO: hide python outputs
     * <p>
     * Given youtube video id, download music file to
     * default folder (named as videoid.mp3), then return Media object
     *
     * @return Sound object
     */
    public static Media getMediaFileFromYoutubeId(String id) {
        return getMediaFileFromYoutubeId(id, DEFAULT_OUT_PATH);
    }


    public static Media getMediaFileFromYoutubeId(String id, String outputFolder) {
        if (!isMediaFileExists(id, outputFolder)) {
            //ydl configs. 'outtmpl': sets output dir and filename.
            i.exec("ydl_opts = {" +
                    "'outtmpl':'" + outputFolder + id + ".%(ext)s'," +
                    "'format':'bestaudio/best', " +
                    "'postprocessors': [{'key' : 'FFmpegExtractAudio', 'preferredcodec' : 'mp3', 'preferredquality' : '192'}]}"
            );
            i.exec("with youtube_dl.YoutubeDL(ydl_opts) as ydl: ydl.download(['https://www.youtube.com/watch?v=" + id + "'])");
        }
        return new Media(new File(outputFolder + id + ".mp3").toURI().toString());
    }

    /**
     * Given '+'-separated keywords, get best matching video from youtube and download as .mp3,
     * then return Sound object connected to the music file
     * <p>
     * Convenience method for both of getIDsOfBestMatchVideos and getMediaFileFromYoutubeId.
     *
     * @param query '+'-separated keywords
     * @return Sound object of the music file
     */
    public static Media getMusicFileFromQuery(String query) {

        for (String link : getIDsOfBestMatchVideos(query)) {

            //if video is 10 minutes or shorter, proceed, otherwise check the next best match
            if (Integer.valueOf(getVideoMeta(link).get("duration").toString()) <= MAX_AUDIO_LENGTH_SECOND) {
                return getMediaFileFromYoutubeId(link);
            }
            System.out.println(link + " is too long! Checking next best");
        }
        return getMediaFileFromYoutubeId(getIDsOfBestMatchVideos(query).get(0));
    }

    /**
     * Given multiple keywords, replace space with '+' and, join all by '+'
     *
     * @param keywords query keywords, e.g. track name, artist name etc
     * @return youtube query string
     */
    public static String createYoutubeQuery(String... keywords) {
        StringBuilder s = new StringBuilder();
        for (String keyword : keywords) {
            s.append(keyword.replace(" ", "+")).append("+");
        }
        String res = s.toString();
        return res.substring(0, res.length() - 1);
    }

    /**
     * Checks if [id].mp3 exists in default local location
     *
     * @param id - youtube video id
     * @return true if exists in local
     */
    public static boolean isMediaFileExists(String id) {
        return isMediaFileExists(id, DEFAULT_OUT_PATH);
    }

    /**
     * Checks if [id].mp3 exists in local
     *
     * @param id - youtube video id
     * @return true if exists in local
     */
    public static boolean isMediaFileExists(String id, String folderPath) {
        return new File(folderPath + id + ".mp3").exists();
    }

    /**
     * Initialize python interpreter and import necessary modules.
     * Called before app launches to eliminate first load wait time during use.
     * <p>
     * Returns true if interpreter is initialized successfully
     */
    public static boolean initializeInterpreter() {
        System.out.print("Initializing interpreter... ");
        try {
            if (i == null) {
                i = new PythonInterpreter();

            /*
            sys.path contains current working directory of Jython, which is in the same dir with youtube-dl/.
            e.g.
            print(sys.path) prints
                  ['/home/username/MusicCurator/lib/Lib',
                  '/home/username/MusicCurator/lib/jython-standalone-2.7.2b2.jar/Lib',
                  '__classpath__', '__pyclasspath__/']
             */
                i.exec("import sys");
                i.exec("from __future__ import unicode_literals");

                //add to path '/home/username/Documents/MusicCurator/lib/youtube-dl-master'
                i.exec("sys.path.append(sys.path[0][:-4] + '/youtube-dl-master')");

                // now can import youtube_dl
                i.exec("import youtube_dl");

                i.exec("ydl_opts = {" +
                        "'format':'bestaudio/best', " +
                        "'postprocessors': [{'key' : 'FFmpegExtractAudio', 'preferredcodec' : 'mp3', 'preferredquality' : '192'}]}"
                );
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get Youtube video meta data, given video id
     *
     * @param id
     * @return PyDictionary containing meta data
     */
    public static PyDictionary getVideoMeta(String id) {
        i.exec("with youtube_dl.YoutubeDL(ydl_opts) as ydl: " +
                "meta = ydl.extract_info('https://www.youtube.com/watch?v=" + id + "', download=False)");

//        System.out.println(i.get("meta").toString()); //uncomment to print the whole meta
        return (PyDictionary) i.get("meta");
    }

    public static PythonInterpreter getInterpreter() {
        return i;
    }
}
