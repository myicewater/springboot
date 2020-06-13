package com.jaxon.demo.designpattern.adapter;

public class VlcPlayer implements AdvancedPlayer{
    @Override
    public void playmp4() {

    }

    @Override
    public void playvlc() {
        System.out.println("paly vlc");
    }
}
