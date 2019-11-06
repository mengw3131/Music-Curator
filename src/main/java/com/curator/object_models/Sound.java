package com.curator.object_models;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sound {
    private Clip clip;
    private int currentFramePosition = 0;

    public Sound(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip.open(audioInputStream);
            }

        } catch (Exception e) {
        }
    }
    public Sound(File file) {
        try {
            if (file.exists()) {
                clip = AudioSystem.getClip();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip.open(audioInputStream);
            }

        } catch (Exception e) {
        }
    }

    public void play(){
        clip.setFramePosition(currentFramePosition);
        clip.loop(0);
        clip.start();
    }

    public void stop(){
        clip.stop()
        ;
    }

    public void pause(){
        currentFramePosition = clip.getFramePosition();
        stop();
    }

    public int length(){
        return (int)clip.getMicrosecondLength() / 1000000;
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
