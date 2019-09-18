package com.example.mediabase.podcastsui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class PodcastController {
    private PodcastClient podcastRepository;

    public PodcastController(PodcastClient podcastRepository) {
        this.podcastRepository = podcastRepository;
    }

    @GetMapping("/podcasts")
    public String allPodcasts(Map<String, Object> model) {
        model.put("podcasts", podcastRepository.findAll() );
        return "podcasts";
    }

}
