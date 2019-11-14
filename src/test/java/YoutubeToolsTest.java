import com.curator.tools.YoutubeTools;
import javafx.scene.media.Media;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;

/**
 * Unit tests for com.curator.tools.YoutubeTools class
 */
public class YoutubeToolsTest {

    @BeforeAll
    public static void initialize() {
        YoutubeTools.initializeInterpreter();
    }

    @Test
    public void getIDsOfBestMatchVideos_returnsBestMatchIDs() {
        String q = YoutubeTools.createYoutubeQuery("hello world");
        ArrayList<String> ids = YoutubeTools.getIDsOfBestMatchVideos(q);
        Assertions.assertTrue(ids.contains("Yw6u6YkTgQ4"));
        Assertions.assertTrue(ids.contains("LnzuMJLZRdU"));
        Assertions.assertTrue(ids.contains("XLV2oTy8oLQ"));
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
        File f = new File("src/main/res/music/" + id + ".mp3");

        try {
            Files.deleteIfExists(f.toPath());
            Assertions.assertFalse(f.exists());

            //download file
            YoutubeTools.getMediaFileFromYoutubeId("d22CiKMPpaY", "src/main/res/music/");

            Assertions.assertTrue(f.exists());
            Files.deleteIfExists(f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMediaFileFromYoutubeId_ifExistsInLocalDoNotDownload() {
        Assertions.assertTimeout(Duration.ofMillis(500), () -> {
                    String existingFileID = "a0qKrtFdnAo";
                    YoutubeTools.getMediaFileFromYoutubeId(existingFileID, "src/main/res/music/");
                }
        );
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

// Takes too long!
    //
//    @Test
//    public void getMusicFileFromQuery_returnVideoWithLengthBelow10Minutes() {
//        String q = "beethoven 9th symphony";
//        Media m = YoutubeTools.getMusicFileFromQuery(q);
//        System.out.println(m.getDuration().toSeconds());
//        Assertions.assertTrue(m.getDuration().toSeconds() <= 600);
//    }
}
