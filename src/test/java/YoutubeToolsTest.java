import com.curator.tools.YoutubeTools;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
/*

 Test Coverage
 +---------------+-------------+------+-------+---------------+
 | Element       | Class, %    | Method, %    | Line %        |
 +---------------+-------------+------+-------+---------------+
 | YoutubeTools  | 100% (1/1)  | 90% (10/11)  | 78% (41/52)   |
 +---------------+-------------+------+-------+---------------+

*/
/**
 * Unit tests for com.curator.tools.YoutubeTools class
 */

public class YoutubeToolsTest {

    @BeforeAll
    public static void initialize() {
        YoutubeTools.initialize();
    }

    @Test
    public void getIDsOfBestMatchVideos_returnsBestMatchIDs() {
        String q = YoutubeTools.createYoutubeQuery("hello world");
        ArrayList<String> ids = YoutubeTools.getIDsOfBestMatchVideos(q);
        Assertions.assertTrue(ids.contains("Yw6u6YkTgQ4"));
    }

    @Test
    public void getIDsOfBestMatchVideos_returnsNoneWhenNoResultFound() {
        String q = YoutubeTools.createYoutubeQuery("aslkj234lksdjf12lk3jadf lkj123");
        ArrayList<String> ids = YoutubeTools.getIDsOfBestMatchVideos(q);
        Assertions.assertEquals(ids.size(), 0);
    }

    @Test
    public void getMediaFileFromYoutubeId_downloadsFileIfNotExistInLocal() {
        String id = "d22CiKMPpaY";
        File f = new File("src/main/resources/music/" + id + ".mp3");

        try {
            Files.deleteIfExists(f.toPath());
            Assertions.assertFalse(f.exists());

            //download file
            YoutubeTools.getMediaFileFromYoutubeId("d22CiKMPpaY", "src/main/resources/music/");

            Assertions.assertTrue(f.exists());
            Files.deleteIfExists(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initializeInterpreter_returnsTrueIfInterpreterInitializedCorrectly() {
        Assertions.assertNotNull(YoutubeTools.getInterpreter());
    }

    @Test
    public void createYoutubeQuery_returnsCorrectQuery() {
        String q = "hello world";
        Assertions.assertEquals("hello+world", YoutubeTools.createYoutubeQuery(q));
        q = "some long123sequence of char";
        Assertions.assertEquals("some+long123sequence+of+char", YoutubeTools.createYoutubeQuery(q));
    }

    @Test
    public void isMediaFileExists_returnsTrueIfExists() {
        String existingFileID = "a0qKrtFdnAo";
        YoutubeTools.getMediaFileFromYoutubeId(existingFileID); //download first
        Assertions.assertTrue(YoutubeTools.isMediaFileExists(existingFileID));
    }

    @Test
    public void isMediaFileExists_returnsFalseIfNotExists() {
        String existingFileID = "randomrandom";
        Assertions.assertFalse(YoutubeTools.isMediaFileExists(existingFileID));
    }

    @Test
    public void getVideoMeta_returnsCorrectMeta() {
        String id = "a0qKrtFdnAo";

        int meta_length = Integer.valueOf(YoutubeTools.getVideoMeta(id).get("duration").toString());
        Assertions.assertEquals(357, meta_length, 1);

        String meta_alt_title = YoutubeTools.getVideoMeta(id).get("alt_title").toString();
        Assertions.assertEquals("Suite bergamasque : III Clair de lune", meta_alt_title);
    }
}
