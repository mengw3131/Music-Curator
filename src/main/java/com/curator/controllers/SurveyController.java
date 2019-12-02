package com.curator.controllers;

import com.curator.views.SurveyButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class SurveyController implements Initializable {

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button okButton;

    @FXML
    private FlowPane genreFlowPane;

    @FXML
    private FlowPane artistFlowPane;

    @FXML
    private FlowPane trackFlowPane;

    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genreFlowPane.setHgap(5);
        artistFlowPane.setHgap(5);
        trackFlowPane.setHgap(5);

        genreFlowPane.setVgap(5);
        artistFlowPane.setVgap(5);
        trackFlowPane.setVgap(5);



        int genre_size = genres.length;
        int artist_size = artists.length;
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            genreFlowPane.getChildren().add(
                    new SurveyButton(genres[r.nextInt(genre_size)], genreFlowPane));
            artistFlowPane.getChildren().add(
                    new SurveyButton(artists[r.nextInt(artist_size)], artistFlowPane));
        }

        okButton.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
                try {
                    stage.setScene(new Scene(loader.load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setHeight(600);
                stage.setMinWidth(1300);
                stage.setTitle("Music Curator");
                stage.show();
            }
        });

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private String[] genres = new String[]{
            "acid house",
            "acid jazz",
            "acid techno",
            "acoustic blues",
            "acoustic rock",
            "afrobeat",
            "alternative country",
            "alternative dance",
            "alternative folk",
            "alternative hip hop",
            "alternative metal",
            "alternative pop",
            "alternative punk",
            "alternative rock",
            "ambient",
            "ambient house",
            "ambient techno",
            "americana",
            "anarcho-punk",
            "aor",
            "arena rock",
            "art rock",
            "atmospheric black metal",
            "audiobook",
            "avant-garde",
            "avant-garde jazz",
            "avant-garde metal",
            "avant-garde pop",
            "bachata",
            "ballad",
            "barbershop",
            "baroque",
            "bebop",
            "bhangra",
            "big band",
            "big beat",
            "black metal",
            "blackened death metal",
            "blackgaze",
            "blue-eyed soul",
            "bluegrass",
            "blues",
            "blues rock",
            "bolero",
            "bolero son",
            "boom bap",
            "bossa nova",
            "breakbeat",
            "breakcore",
            "breaks",
            "britpop",
            "broken beat",
            "brutal death metal",
            "bubblegum pop",
            "cajun",
            "calypso",
            "canterbury scene",
            "cantopop",
            "celtic",
            "celtic punk",
            "chamber pop",
            "champeta",
            "chanson",
            "chicago blues",
            "chillout",
            "chiptune",
            "christian rock",
            "christmas music",
            "city pop",
            "classic blues",
            "classic country",
            "classic jazz",
            "classic rock",
            "classical",
            "club",
            "comedy",
            "conscious hip hop",
            "contemporary christian",
            "contemporary classical",
            "contemporary folk",
            "contemporary gospel",
            "contemporary jazz",
            "contemporary r&b",
            "contra",
            "cool jazz",
            "country",
            "country blues",
            "country folk",
            "country pop",
            "country rock",
            "crossover prog",
            "crust punk",
            "cumbia",
            "d-beat",
            "dance",
            "dance-pop",
            "dance-punk",
            "dancehall",
            "dark ambient",
            "dark electro",
            "dark folk",
            "dark wave",
            "death metal",
            "death-doom metal",
            "deathcore",
            "deathgrind",
            "deathrock",
            "deep house",
            "delta blues",
            "desert rock",
            "digital hardcore",
            "disco",
            "doo-wop",
            "doom metal",
            "downtempo",
            "drill",
            "drone",
            "drum and bass",
            "dub",
            "dub techno",
            "dubstep",
            "dungeon synth",
            "east coast hip hop",
            "ebm",
            "electric blues",
            "electro",
            "electro house",
            "electro swing",
            "electro-funk",
            "electro-industrial",
            "electroclash",
            "electronic",
            "electronic rock",
            "electronica",
            "electronicore",
            "electropop",
            "electropunk",
            "emo",
            "emocore",
            "enka",
            "ethereal",
            "euro house",
            "eurodance",
            "europop",
            "experimental",
            "experimental rock",
            "fado",
            "filk",
            "flamenco",
            "folk",
            "folk metal",
            "folk pop",
            "folk punk",
            "folk rock",
            "freak folk",
            "free improvisation",
            "free jazz",
            "funk",
            "funk carioca",
            "funk metal",
            "funk rock",
            "funk soul",
            "funky house",
            "fusion",
            "future jazz",
            "futurepop",
            "g-funk",
            "gabber",
            "gangsta rap",
            "garage",
            "garage house",
            "garage punk",
            "garage rock",
            "glam",
            "glam metal",
            "glam rock",
            "glitch",
            "goa trance",
            "goregrind",
            "gospel",
            "gothic",
            "gothic metal",
            "gothic rock",
            "grebo",
            "grime",
            "grindcore",
            "groove metal",
            "grunge",
            "guaracha",
            "happy hardcore",
            "hard bop",
            "hard house",
            "hard rock",
            "hard trance",
            "hardcore punk",
            "hardcore techno",
            "hardstyle",
            "heavy metal",
            "hip hop",
            "honky tonk",
            "horror punk",
            "horrorcore",
            "house",
            "idm",
            "illbient",
            "indie",
            "indie folk",
            "indie pop",
            "indie rock",
            "indietronica",
            "indorock",
            "industrial",
            "industrial metal",
            "industrial rock",
            "instrumental",
            "instrumental jazz",
            "instrumental rock",
            "irish folk",
            "italo-disco",
            "j-pop",
            "j-rock",
            "jazz",
            "jazz blues",
            "jazz fusion",
            "jazz rap",
            "jazz rock",
            "jazz-funk",
            "jungle",
            "k-pop",
            "kayōkyoku",
            "kizomba",
            "klezmer",
            "krautrock",
            "latin",
            "latin jazz",
            "latin pop",
            "latin rock",
            "leftfield",
            "line dance",
            "lo-fi",
            "lounge",
            "lovers rock",
            "madchester",
            "mainstream rock",
            "mambo",
            "mandopop",
            "martial industrial",
            "math rock",
            "mathcore",
            "medieval",
            "melodic black metal",
            "melodic death metal",
            "melodic metalcore",
            "melodic rock",
            "melodic trance",
            "mento",
            "merengue",
            "metal",
            "metalcore",
            "microhouse",
            "milonga",
            "min'yō",
            "mincecore",
            "minimal",
            "modern blues",
            "modern classical",
            "modern country",
            "motown",
            "mpb",
            "musical",
            "neo soul",
            "neo-progressive rock",
            "neo-rockabilly",
            "neofolk",
            "nerdcore",
            "new age",
            "new jack swing",
            "new romantic",
            "new wave",
            "no wave",
            "noise",
            "noise pop",
            "noisecore",
            "non-music",
            "norteño",
            "northern soul",
            "nu jazz",
            "nu metal",
            "occult rock",
            "oi",
            "old school death metal",
            "old-time",
            "opera",
            "orchestral",
            "outlaw country",
            "p-funk",
            "pachanga",
            "pop",
            "pop metal",
            "pop punk",
            "pop rap",
            "pop rock",
            "pop soul",
            "pornogrind",
            "post-bop",
            "post-classical",
            "post-grunge",
            "post-hardcore",
            "post-metal",
            "post-punk",
            "post-rock",
            "power electronics",
            "power metal",
            "power pop",
            "powerviolence",
            "production music",
            "progressive",
            "progressive folk",
            "progressive house",
            "progressive metal",
            "progressive rock",
            "progressive trance",
            "psy-trance",
            "psychedelic",
            "psychedelic folk",
            "psychedelic pop",
            "psychedelic rock",
            "psychobilly",
            "psytrance",
            "punk",
            "punk rock",
            "queercore",
            "r&b",
            "ragga",
            "ragga hip-hop",
            "ragga jungle",
            "ragtime",
            "raï",
            "ranchera",
            "rap rock",
            "rapcore",
            "rave",
            "reggae",
            "reggaeton",
            "rhythmic noise",
            "rock",
            "rock and roll",
            "rockabilly",
            "rocksteady",
            "roots reggae",
            "rumba",
            "salsa",
            "samba",
            "schlager",
            "screamo",
            "shibuya-kei",
            "shoegaze",
            "singer-songwriter",
            "ska",
            "ska punk",
            "skacore",
            "slow waltz",
            "sludge metal",
            "smooth jazz",
            "smooth soul",
            "soca",
            "soft rock",
            "son cubano",
            "son montuno",
            "soul",
            "soul jazz",
            "southern rock",
            "southern soul",
            "space rock",
            "speed garage",
            "speed metal",
            "spoken word",
            "stoner metal",
            "stoner rock",
            "street punk",
            "surf rock",
            "swing",
            "symphonic black metal",
            "symphonic metal",
            "symphonic prog",
            "symphonic rock",
            "symphony",
            "synth-pop",
            "synthwave",
            "tango",
            "tech house",
            "technical death metal",
            "techno",
            "teen pop",
            "thrash metal",
            "thrashcore",
            "timba",
            "traditional country",
            "trance",
            "trap",
            "trap edm",
            "tribal house",
            "trip hop",
            "turntablism",
            "uk drill",
            "uk garage",
            "underground hip hop",
            "vallenato",
            "vaporwave",
            "viking metal",
            "visual kei",
            "vocal house",
            "vocal jazz",
            "vocal trance",
            "west coast hip hop",
            "west coast swing",
            "yé-yé",
            "zamrock",
            "zydeco"
    };

    private String[] artists = new String[]{
            "Mary J. Blige",
            "Steven Tyler",
            "Stevie Nicks",
            "Joe Cocker",
            "B.B. King",
            "Patti LaBelle",
            "Karen Carpenter",
            "Annie Lennox",
            "Morrissey",
            "Levon Helm",
            "The Everly Brothers",
            "Solomon Burke",
            "Willie Nelson",
            "Don Henley",
            "Art Garfunkel",
            "Sam Moore",
            "Darlene Love",
            "Patti Smith",
            "Tom Waits",
            "John Lee Hooker",
            "Frankie Valli",
            "Mariah Carey",
            "Sly Stone",
            "Merle Haggard",
            "Steve Perry",
            "Iggy Pop",
            "James Taylor",
            "Dolly Parton",
            "John Fogerty",
            "Toots Hibbert",
            "Gregg Allman",
            "Ronnie Spector",
            "Wilson Pickett",
            "Jerry Lee Lewis",
            "Thom Yorke",
            "David Ruffin",
            "Axl Rose",
            "Dion",
            "Lou Reed",
            "Roger Daltrey",
            "Björk",
            "Rod Stewart",
            "Christina Aguilera",
            "Eric Burdon",
            "Mavis Staples",
            "Paul Rodgers",
            "Luther Vandross",
            "Muddy Waters",
            "Brian Wilson",
            "Gladys Knight",
            "Donny Hathaway",
            "Buddy Holly",
            "Jim Morrison",
            "Patsy Cline",
            "Kurt Cobain",
            "Bobby “Blue” Bland",
            "George Jones",
            "Joni Mitchell",
            "Chuck Berry",
            "Curtis Mayfield",
            "Jeff Buckley",
            "Elton John",
            "Neil Young",
            "Bruce Springsteen",
            "Dusty Springfield",
            "Whitney Houston",
            "Steve Winwood",
            "Bono",
            "Howlin’ Wolf",
            "Prince",
            "Nina Simone",
            "Janis Joplin",
            "Hank Williams",
            "Jackie Wilson",
            "Michael Jackson",
            "Van Morrison",
            "David Bowie",
            "Etta James",
            "Johnny Cash",
            "Smokey Robinson",
            "Bob Marley",
            "Freddie Mercury",
            "Tina Turner",
            "Mick Jagger",
            "Robert Plant",
            "Al Green",
            "Roy Orbison",
            "Little Richard",
            "Paul McCartney",
            "James Brown",
            "Stevie Wonder",
            "Otis Redding",
            "Bob Dylan",
            "Marvin Gaye",
            "John Lennon",
            "Sam Cooke",
            "Elvis Presley",
            "Ray Charles",
            "Aretha Franklin"
    };
}
