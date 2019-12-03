package com.curator.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Genre {
    private static HashMap<String, String[]> map = new HashMap<>();
    private static boolean initialized = false;

    private static ArrayList<String> majorGenreList = new ArrayList<>();

    private Genre() {}

    public static boolean isMajorGenre(String genre){
        if (!initialized){
            init();
        }
        return majorGenreList.contains(genre);
    }

    public static ArrayList<String> getMajorGenreList() {
        if (!initialized){
            init();
        }
        return majorGenreList;
    }

    public static ArrayList getMinorGenre(String majorGenre){
        if (!initialized){
            init();
        }
        ArrayList minorGenreList = new ArrayList();
        if (majorGenreList.contains(majorGenre)){
            minorGenreList.addAll(Arrays.asList(map.get(majorGenre)));
        }
        return minorGenreList;
    }


    private static void init() {
        map.put("Alternative", new String[]{
                "Alternative Rock",
                "College Rock",
                "Experimental Rock",
                "Goth Rock",
                "Grunge",
                "Hardcore Punk",
                "Hard Rock",
                "Indie Rock",
                "New Wave",
                "Progressive Rock",
                "Punk",
                "Shoegaze",
                "Steampunk"
        });
        map.put("Anime", new String[]{
        });
        map.put("Blues", new String[]{
                "Acoustic Blues",
                "Chicago Blues",
                "Classic Blues",
                "Contemporary Blues",
                "Country Blues",
                "Delta Blues",
                "Electric Blues",
        });
        map.put("Children’s Music", new String[]{
                "Lullabies",
                "Sing-Along",
                "Stories",
        });
        map.put("Classical", new String[]{
                "Avant-Garde",
                "Baroque",
                "Chamber Music",
                "Chant",
                "Choral",
                "Classical Crossover",
                "Early Music",
                "High Classical",
                "Impressionist",
                "Medieval",
                "Minimalism",
                "Modern Composition",
                "Opera",
                "Orchestral",
                "Renaissance",
                "Romantic",
                "Wedding Music",
        });
        map.put("Country", new String[]{
                "Alternative Country",
                "Americana",
                "Bluegrass",
                "Contemporary Bluegrass",
                "Contemporary Country",
                "Country Gospel",
                "Honky Tonk",
                "Outlaw Country",
                "Traditional Bluegrass",
                "Traditional Country",
                "Urban Cowboy",
        });
        map.put("Dance/EMD", new String[]{
                "Breakbeat",
                "Dubstep",
                "Exercise",
                "Garage",
                "Hardcore",
                "Hard Dance",
                "Hi-NRG / Eurodance",
                "House",
                "Jackin House",
                "Jungle/Drum’n'bass",
                "Techno",
                "Trance",
        });
        map.put("Easy Listening", new String[]{
                "Bop",
                "Lounge",
                "Swing",
        });
        map.put("Electronic", new String[]{
                "Ambient",
                "Crunk",
                "Downtempo",
                "Electro",
                "Electronica",
                "Electronic Rock",
                "IDM/Experimental",
                "Industrial",
        });
        map.put("Hip-Hop/Rap", new String[]{
                "Alternative Rap",
                "Bounce",
                "Dirty South",
                "East Coast Rap",
                "Gangsta Rap",
                "Hardcore Rap",
                "Hip-Hop",
                "Latin Rap",
                "Old School Rap",
                "Rap",
                "Underground Rap",
                "West Coast Rap",
        });
        map.put("Holiday", new String[]{
                "Chanukah",
                "Christmas",
                "Christmas: Children’s",
                "Christmas: Classic",
                "Christmas: Classical",
                "Christmas: Jazz",
                "Christmas: Modern",
                "Christmas: Pop",
                "Christmas: R&B",
                "Christmas: Religious",
                "Christmas: Rock",
                "Easter",
                "Halloween",
                "Holiday: Other",
                "Thanksgiving",
        });
        map.put("Inspirational - Christian & Gospel", new String[]{
                "CCM",
                "Christian Metal",
                "Christian Pop",
                "Christian Rap",
                "Christian Rock",
                "Classic Christian",
                "Contemporary Gospel",
                "Gospel",
                "Christian & Gospel",
                "Praise & Worship",
                "Qawwali",
                "Southern Gospel",
                "Traditional Gospel",
        });
        map.put("Jazz", new String[]{
                "Acid Jazz",
                "Avant-Garde Jazz",
                "Big Band",
                "Blue Note",
                "Contemporary Jazz",
                "Cool",
                "Crossover Jazz",
                "Dixieland",
                "Ethio-jazz",
                "Fusion",
                "Hard Bop",
                "Latin Jazz",
                "Mainstream Jazz",
                "Ragtime",
                "Smooth Jazz",
                "Trad Jazz",
        });
        map.put("Latino", new String[]{
                "Alternativo & Rock Latino",
                "Baladas y Boleros",
                "Brazilian",
                "Contemporary Latin",
                "Latin Jazz",
                "Pop Latino",
                "Raíces",
                "Reggaeton y Hip-Hop",
                "Regional Mexicano",
                "Salsa y Tropical",
        });
        map.put("New Age", new String[]{
                "Environmental",
                "Healing",
                "Meditation",
                "Nature",
                "Relaxation",
                "Travel",
        });
        map.put("Pop", new String[]{
                "Adult Contemporary",
                "Britpop",
                "Pop/Rock",
                "Soft Rock",
                "Teen Pop",
        });
        map.put("R&B/Soul", new String[]{
                "Contemporary R & B",
                "Disco",
                "Doo Wop",
                "Funk",
                "Motown",
                "Neo-Soul",
                "Quiet Storm",
                "Soul",
        });
        map.put("Reggae", new String[]{
                "Dance hall",
                "Dub",
                "Roots Reggae",
                "Ska",
        });
        map.put("Rock", new String[]{
                "Adult Alternative",
                "American Trad Rock",
                "Arena Rock",
                "Blues-Rock",
                "British Invasion",
                "Death Metal/Black Metal",
                "Glam Rock",
                "Hair Metal",
                "Hard Rock",
                "Metal",
                "Jam Bands",
                "Prog-Rock/Art Rock",
                "Psychedelic",
                "Rock & Roll",
                "Rockabilly",
                "Roots Rock",
                "Singer/Songwriter",
                "Southern Rock",
                "Surf",
                "Tex-Mex",
        });
        map.put("Singer/Songwriter", new String[]{
                "Alternative Folk",
                "Contemporary Folk",
                "Contemporary Singer/Songwriter",
                "Folk-Rock",
                "New Acoustic",
                "Traditional Folk",
        });
        map.put("Tex-Mex/Tejano", new String[]{
                "Chicano",
                "Classic",
                "Conjunto",
                "Conjunto Progressive",
                "New Mex",
                "Tex-Mex",
        });
        map.put("Vocal", new String[]{
                "Barbershop",
                "Doo-wop",
                "Standards",
                "Traditional Pop",
                "Vocal Jazz",
                "Vocal Pop",
        });


        majorGenreList.addAll(map.keySet());
        initialized = true;
    }
}
