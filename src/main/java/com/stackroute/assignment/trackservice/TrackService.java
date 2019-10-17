package com.stackroute.assignment.trackservice;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.exception.TrackAlreadyExistException;
import com.stackroute.assignment.exception.TrackNotFoundException;

import java.util.List;

public interface TrackService{
    public Track saveTrack(Track track) throws TrackAlreadyExistException;
    public List<Track> getAllTracks();
    public List<Track> getByTrackName(String trackName) throws TrackNotFoundException;
    public Track deleteTrackById(int trackId) throws TrackNotFoundException;



}



