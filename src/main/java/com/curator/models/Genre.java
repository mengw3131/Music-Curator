package com.curator.models;

import java.util.HashMap;

public enum Genre {
    ACOUSTIC("acoustic"),

    AFROBEAT("afrobeat"),

    ALTROCK("alt-rock"),

    ALTERNATIVE("alternative"),

    AMBIENT("ambient"),

    ANIME("anime"),

    BLACKMETAL("black-metal"),

    BLUEGRASS("bluegrass"),

    BLUES("blues"),

    BOSSANOVA("bossanova"),

    BRAZIL("brazil"),

    BREAKBEAT("breakbeat"),

    BRITISH("british"),

    CANTOPOP("cantopop"),

    CHICAGOHOUSE("chicago-house"),

    CHILDREN("children"),

    CHILL("chill"),

    CLASSICAL("classical"),

    CLUB("club"),

    COMEDY("comedy"),

    COUNTRY("country"),

    DANCE("dance"),

    DANCEHALL("dancehall"),

    DEATHMETAL("death-metal"),

    DEEPHOUSE("deep-house"),

    DETROITTECHNO("detroit-techno"),

    DISCO("disco"),

    DISNEY("disney"),

    DRUMANDBASS("drum-and-bass"),

    DUB("dub"),

    DUBSTEP("dubstep"),

    EDM("edm"),

    ELECTRO("electro"),

    ELECTRONIC("electronic"),

    EMO("emo"),

    FOLK("folk"),

    FORRO("forro"),

    FRENCH("french"),

    FUNK("funk"),

    GARAGE("garage"),

    GERMAN("german"),

    GOSPEL("gospel"),

    GOTH("goth"),

    GRINDCORE("grindcore"),

    GROOVE("groove"),

    GRUNGE("grunge"),

    GUITAR("guitar"),

    HAPPY("happy"),

    HARDROCK("hard-rock"),

    HARDCORE("hardcore"),

    HARDSTYLE("hardstyle"),

    HEAVYMETAL("heavy-metal"),

    HIPHOP("hip-hop"),

    HOLIDAYS("holidays"),

    HONKYTONK("honky-tonk"),

    HOUSE("house"),

    IDM("idm"),

    INDIAN("indian"),

    INDIE("indie"),

    INDIEPOP("indie-pop"),

    INDUSTRIAL("industrial"),

    IRANIAN("iranian"),

    JDANCE("j-dance"),

    JIDOL("j-idol"),

    JPOP("j-pop"),

    JROCK("j-rock"),

    JAZZ("jazz"),

    KPOP("k-pop"),

    KIDS("kids"),

    LATIN("latin"),

    LATINO("latino"),

    MALAY("malay"),

    MANDOPOP("mandopop"),

    METAL("metal"),

    METALMISC("metal-misc"),

    METALCORE("metalcore"),

    MINIMALTECHNO("minimal-techno"),

    MOVIES("movies"),

    MPB("mpb"),

    NEWAGE("new-age"),

    NEWRELEASE("new-release"),

    OPERA("opera"),

    PAGODE("pagode"),

    PARTY("party"),

    PHILIPPINESOPM("philippines-opm"),

    PIANO("piano"),

    POP("pop"),

    POPFILM("pop-film"),

    POSTDUBSTEP("post-dubstep"),

    POWERPOP("power-pop"),

    PROGRESSIVEHOUSE("progressive-house"),

    PSYCHROCK("psych-rock"),

    PUNK("punk"),

    PUNKROCK("punk-rock"),

    RNB("r-n-b"),

    RAINYDAY("rainy-day"),

    REGGAE("reggae"),

    REGGAETON("reggaeton"),

    ROADTRIP("road-trip"),

    ROCK("rock"),

    ROCKNROLL("rock-n-roll"),

    ROCKABILLY("rockabilly"),

    ROMANCE("romance"),

    SAD("sad"),

    SALSA("salsa"),

    SAMBA("samba"),

    SERTANEJO("sertanejo"),

    SHOWTUNES("show-tunes"),

    SINGERSONGWRITER("singer-songwriter"),

    SKA("ska"),

    SLEEP("sleep"),

    SONGWRITER("songwriter"),

    SOUL("soul"),

    SOUNDTRACKS("soundtracks"),

    SPANISH("spanish"),

    STUDY("study"),

    SUMMER("summer"),

    SWEDISH("swedish"),

    SYNTHPOP("synth-pop"),

    TANGO("tango"),

    TECHNO("techno"),

    TRANCE("trance"),

    TRIPHOP("trip-hop"),

    TURKISH("turkish"),

    WORKOUT("work-out"),

    WORLDMUSIC("world-music");

    public final String id;

    Genre(String genre) {
        this.id = genre;
    }

    @Override
    public String toString() {
        return id;
    }

    private static final HashMap<String, Genre> hm = new HashMap<>();

    public static Genre getGenreFromId(String genre){
        if (hm.size() == 0) {
            init();
        }
        return hm.get(genre);
    }

    private static void init(){
        hm.put("acoustic", Genre.ACOUSTIC);
        hm.put("afrobeat", Genre.AFROBEAT);
        hm.put("alternative", Genre.ALTERNATIVE);
        hm.put("alt-rock", Genre.ALTROCK);
        hm.put("ambient", Genre.AMBIENT);
        hm.put("anime", Genre.ANIME);
        hm.put("black-metal", Genre.BLACKMETAL);
        hm.put("bluegrass", Genre.BLUEGRASS);
        hm.put("blues", Genre.BLUES);
        hm.put("bossanova", Genre.BOSSANOVA);
        hm.put("brazil", Genre.BRAZIL);
        hm.put("breakbeat", Genre.BREAKBEAT);
        hm.put("british", Genre.BRITISH);
        hm.put("cantopop", Genre.CANTOPOP);
        hm.put("chicago-house", Genre.CHICAGOHOUSE);
        hm.put("children", Genre.CHILDREN);
        hm.put("chill", Genre.CHILL);
        hm.put("classical", Genre.CLASSICAL);
        hm.put("club", Genre.CLUB);
        hm.put("comedy", Genre.COMEDY);
        hm.put("country", Genre.COUNTRY);
        hm.put("dance", Genre.DANCE);
        hm.put("dancehall", Genre.DANCEHALL);
        hm.put("death-metal", Genre.DEATHMETAL);
        hm.put("deep-house", Genre.DEEPHOUSE);
        hm.put("detroit-techno", Genre.DETROITTECHNO);
        hm.put("disco", Genre.DISCO);
        hm.put("disney", Genre.DISNEY);
        hm.put("drum-and-bass", Genre.DRUMANDBASS);
        hm.put("dub", Genre.DUB);
        hm.put("dubstep", Genre.DUBSTEP);
        hm.put("edm", Genre.EDM);
        hm.put("electro", Genre.ELECTRO);
        hm.put("electronic", Genre.ELECTRONIC);
        hm.put("emo", Genre.EMO);
        hm.put("folk", Genre.FOLK);
        hm.put("forro", Genre.FORRO);
        hm.put("french", Genre.FRENCH);
        hm.put("funk", Genre.FUNK);
        hm.put("garage", Genre.GARAGE);
        hm.put("german", Genre.GERMAN);
        hm.put("gospel", Genre.GOSPEL);
        hm.put("goth", Genre.GOTH);
        hm.put("grindcore", Genre.GRINDCORE);
        hm.put("groove", Genre.GROOVE);
        hm.put("grunge", Genre.GRUNGE);
        hm.put("guitar", Genre.GUITAR);
        hm.put("happy", Genre.HAPPY);
        hm.put("hardcore", Genre.HARDCORE);
        hm.put("hard-rock", Genre.HARDROCK);
        hm.put("hardstyle", Genre.HARDSTYLE);
        hm.put("heavy-metal", Genre.HEAVYMETAL);
        hm.put("hip-hop", Genre.HIPHOP);
        hm.put("holidays", Genre.HOLIDAYS);
        hm.put("honky-tonk", Genre.HONKYTONK);
        hm.put("house", Genre.HOUSE);
        hm.put("idm", Genre.IDM);
        hm.put("indian", Genre.INDIAN);
        hm.put("indie", Genre.INDIE);
        hm.put("indie-pop", Genre.INDIEPOP);
        hm.put("industrial", Genre.INDUSTRIAL);
        hm.put("iranian", Genre.IRANIAN);
        hm.put("jazz", Genre.JAZZ);
        hm.put("j-dance", Genre.JDANCE);
        hm.put("j-idol", Genre.JIDOL);
        hm.put("j-pop", Genre.JPOP);
        hm.put("j-rock", Genre.JROCK);
        hm.put("kids", Genre.KIDS);
        hm.put("k-pop", Genre.KPOP);
        hm.put("latin", Genre.LATIN);
        hm.put("latino", Genre.LATINO);
        hm.put("malay", Genre.MALAY);
        hm.put("mandopop", Genre.MANDOPOP);
        hm.put("metal", Genre.METAL);
        hm.put("metalcore", Genre.METALCORE);
        hm.put("metal-misc", Genre.METALMISC);
        hm.put("minimal-techno", Genre.MINIMALTECHNO);
        hm.put("movies", Genre.MOVIES);
        hm.put("mpb", Genre.MPB);
        hm.put("new-age", Genre.NEWAGE);
        hm.put("new-release", Genre.NEWRELEASE);
        hm.put("opera", Genre.OPERA);
        hm.put("pagode", Genre.PAGODE);
        hm.put("party", Genre.PARTY);
        hm.put("philippines-opm", Genre.PHILIPPINESOPM);
        hm.put("piano", Genre.PIANO);
        hm.put("pop", Genre.POP);
        hm.put("pop-film", Genre.POPFILM);
        hm.put("post-dubstep", Genre.POSTDUBSTEP);
        hm.put("power-pop", Genre.POWERPOP);
        hm.put("progressive-house", Genre.PROGRESSIVEHOUSE);
        hm.put("psych-rock", Genre.PSYCHROCK);
        hm.put("punk", Genre.PUNK);
        hm.put("punk-rock", Genre.PUNKROCK);
        hm.put("rainy-day", Genre.RAINYDAY);
        hm.put("reggae", Genre.REGGAE);
        hm.put("reggaeton", Genre.REGGAETON);
        hm.put("r-n-b", Genre.RNB);
        hm.put("road-trip", Genre.ROADTRIP);
        hm.put("rock", Genre.ROCK);
        hm.put("rockabilly", Genre.ROCKABILLY);
        hm.put("rock-n-roll", Genre.ROCKNROLL);
        hm.put("romance", Genre.ROMANCE);
        hm.put("sad", Genre.SAD);
        hm.put("salsa", Genre.SALSA);
        hm.put("samba", Genre.SAMBA);
        hm.put("sertanejo", Genre.SERTANEJO);
        hm.put("show-tunes", Genre.SHOWTUNES);
        hm.put("singer-songwriter", Genre.SINGERSONGWRITER);
        hm.put("ska", Genre.SKA);
        hm.put("sleep", Genre.SLEEP);
        hm.put("songwriter", Genre.SONGWRITER);
        hm.put("soul", Genre.SOUL);
        hm.put("soundtracks", Genre.SOUNDTRACKS);
        hm.put("spanish", Genre.SPANISH);
        hm.put("study", Genre.STUDY);
        hm.put("summer", Genre.SUMMER);
        hm.put("swedish", Genre.SWEDISH);
        hm.put("synth-pop", Genre.SYNTHPOP);
        hm.put("tango", Genre.TANGO);
        hm.put("techno", Genre.TECHNO);
        hm.put("trance", Genre.TRANCE);
        hm.put("trip-hop", Genre.TRIPHOP);
        hm.put("turkish", Genre.TURKISH);
        hm.put("work-out", Genre.WORKOUT);
        hm.put("world-music", Genre.WORLDMUSIC);
    }

}
