package com.stackroute.assignment.trackservice;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackServiceImp implements TrackService {
    TrackRepository trackRepository;
    @Autowired
    public TrackServiceImp(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track) {
        Track savedTracks = trackRepository.save(track);
        return savedTracks ;
    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}
