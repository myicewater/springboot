package com.jaxon.demo.designpattern.adapter;

public class Mp4Player implements AdvancedPlayer {

    @Override
    public void playmp4() {
        System.out.println("play mp4");
    }

    @Override
    public void playvlc() {

    }
}
