package com.jaxon.demo.designpattern.adapter;

public class MediaPlayerAdapter implements MediaPlayer{

    AdvancedPlayer advancedPlayer;

    public  MediaPlayerAdapter(String typeName){
        if("mp4".equals(typeName)){
            advancedPlayer = new Mp4Player();
        } else if ("vlc".equals(typeName)) {
            advancedPlayer = new VlcPlayer();
        }

    }


    @Override
    public void paly(String typeName,String file) {
        if("mp4".equals(typeName)){
            advancedPlayer.playmp4();
        } else if ("vlc".equals(typeName)) {
            advancedPlayer.playvlc();
        }

    }
}
