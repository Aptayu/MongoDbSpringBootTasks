package com.stackroute.assignment.trackservice;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.exception.TrackAlreadyExistException;
import com.stackroute.assignment.exception.TrackNotFoundException;

import java.util.List;

public class TrackDummyServiceImpl implements TrackService {
    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistException {
        return null;
    }

    @Override
    public List<Track> getAllTracks() {
        return null;
    }

    @Override
    public List<Track> getByTrackName(String trackName) throws TrackNotFoundException {
        return null;
    }

    @Override
    public Track deleteTrackById(int trackId) throws TrackNotFoundException {
        return null;
    }
}
