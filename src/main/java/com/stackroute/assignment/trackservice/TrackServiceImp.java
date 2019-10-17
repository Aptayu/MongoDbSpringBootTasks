package com.stackroute.assignment.trackservice;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.exception.TrackAlreadyExistException;
import com.stackroute.assignment.exception.TrackNotFoundException;
import com.stackroute.assignment.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Primary
@Service
public class TrackServiceImp implements TrackService {
    TrackRepository trackRepository;
    @Autowired
    public TrackServiceImp(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track saveTrack(Track track)throws TrackAlreadyExistException {
       if(trackRepository.existsById(track.getTrackId()))
           throw new TrackAlreadyExistException();

            return trackRepository.save(track);

    }

    @Override
    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    @Override
    public List<Track> getByTrackName(String trackName) throws TrackNotFoundException {
        List<Track> track = trackRepository.getByTrackName(trackName);
        if (track.size()==0)
            throw new TrackNotFoundException();

        return track;
    }

    @Override
    public Track deleteTrackById(int trackId) throws TrackNotFoundException {
        Track track = (Track) trackRepository.findById(trackId)
                .orElseThrow(()-> new TrackNotFoundException());
        trackRepository.deleteById(trackId);
        return track;
    }


}
