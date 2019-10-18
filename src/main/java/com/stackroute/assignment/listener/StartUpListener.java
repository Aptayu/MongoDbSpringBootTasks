package com.stackroute.assignment.listener;

import com.stackroute.assignment.domain.Track;
import com.stackroute.assignment.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    private TrackRepository trackRepository;
    @Autowired
    public void setTrackRepository(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent applicationEvent) {
        trackRepository.save(new Track(1, "what the hell", "worst place"));
        trackRepository.save(new Track(2, "why i am here", "illogical people"));
    }
}
