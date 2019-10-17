package com.stackroute.assignment.repository;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.exception.TrackNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

//@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
    @Query(value = "select t from Track t where t.trackName like %:trackName%")
    List<Track> getByTrackName(String trackName) throws TrackNotFoundException;
}
