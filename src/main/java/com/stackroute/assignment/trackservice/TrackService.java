package com.stackroute.assignment.trackservice;

import com.stackroute.assignment.domain.Track;

import java.util.List;

public interface TrackService{
    public Track saveTrack(Track track);
    public List<Track> getAllTracks();

}

