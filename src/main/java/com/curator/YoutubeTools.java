package com.curator;

import com.curator.object_models.Sound;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class containing static utility methods for youtube-related tasks
 */
public class YoutubeTools {
    //TODO: load too slow on first load, maybe create a singleton + load on start prior GUI launch or use async
    private static PythonInterpreter i = new PythonInterpreter();

    //Pattern matches /watch?v=[video id], compile once only, for performance
    //Design note: avoid using doc based selector(e.g. JSoup) for matching,
    //  since they won't work if youtube changes the structure
    private static Pattern p = Pattern.compile("(/watch\\?v=|\\.be/)([\\w\\-_]*)(&(amp;)?\u200C\u200B[\\w?\u200C\u200B=]*)?");

    /**
     * Prevent instance creation
     */
    private YoutubeTools(){};

    /**
     * Given '+'-separated query containing keywords, fetch Youtube top result and return video id.
     * Id to be used for downloading.
     *
     * @return Youtube video id of the top result
     */
    public static String getBestMatchYoutubeVideoId(String query){
        String url = "https://www.youtube.com/results?search_query=" +  query;
        try {
            //Use JSoup in case we need to scrape more info
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();

            Matcher m = p.matcher(doc.body().toString());

            while (m.find()){
                return m.group().replace("/watch?v=","");  //just get the first id
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * TODO: if music file with same id already exists, DON'T download
     * TODO: if not finish downloading and user click another music, stop
     * TODO: if song is too big > 10MB, don't download, show message
     * TODO: if possible find a way to stream instead of download
     * TODO: hide python outputs
     *
     * TODO: return Music/Track instead of Sound?
     *
     * Given youtube video id, download music file to /music (named as videoid.mp3), then return Sound object
     *
     * @return Sound object
     */
    public static Sound getMusicFileFromYoutubeId(String id){

        //TODO: optimize
        PythonInterpreter i = new PythonInterpreter();
        i.exec("import sys");
        i.exec("sys.path.append('../youtube-dl-master')");
        i.exec("import youtube_dl");
        i.exec("ydl_opts = {" +
                "'outtmpl' : 'src/main/res/music/" + id + ".%(ext)s', " +
                "'format':'bestaudio/best', " +
                "'postprocessors': [{'key' : 'FFmpegExtractAudio', 'preferredcodec' : 'mp3', 'preferredquality' : '192'}]}"
        );
        i.exec("with youtube_dl.YoutubeDL(ydl_opts) as ydl: ydl.download(['https://www.youtube.com/watch?v=" + id + "'])");

        return new Sound( "src/main/res/music/" + id + ".mp3");
    }


    /**
     * Given '+'-separated keywords, get best matching video from youtube and download as .mp3,
     * then return Sound object connected to the music file
     *
     * Convenience method for both of getBestMatchYoutubeVideoId and getMusicFileFromYoutubeId.
     *
     * @param query '+'-separated keywords
     * @return Sound object of the music file
     */
    public static Sound getMusicFileFromQuery(String query){
        return getMusicFileFromYoutubeId(getBestMatchYoutubeVideoId(query));
    }

    /**
     * Given multiple keywords, replace space with '+' and, join all by '+'
     * @param keywords query keywords, e.g. track name, artist name etc
     * @return youtube query string
     */
    public static String createYoutubeQuery(String ... keywords){
        StringBuilder s = new StringBuilder();
        for (String keyword: keywords) {
            s.append(keyword.replace(" ", "+")).append("+");
        }
        return s.toString();
    }

    public static void main(String[] args) {
//        System.out.println(getBestMatchYoutubeVideoId("Ninth Symphony Beethoven"));
//        getMusicFileFromYoutubeId("http://www.youtube.com/watch?v=rtOvBOTyX00");
    }
}
