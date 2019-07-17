package com.example.mediabase;

import com.example.mediabase.moviesui.MovieClient;
import com.example.mediabase.moviesui.MoviesInitialList;
import com.example.mediabase.podcastsUI.PodcastClient;
import com.example.mediabase.podcastsUI.PodcastsInitialList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class RootController {
    private final MovieClient movieClient;
    private final PodcastClient podcastClient;
    private final MoviesInitialList moviesInitialList;
    private final PodcastsInitialList podcastsInitialList;

    public RootController(MovieClient movieClient, PodcastClient podcastClient, MoviesInitialList moviesInitialList, PodcastsInitialList podcastsInitialList) {
        this.movieClient = movieClient;
        this.podcastClient = podcastClient;
        this.moviesInitialList = moviesInitialList;
        this.podcastsInitialList = podcastsInitialList;
    }

    @GetMapping("/")
    public String rootPath() {
        return "index";
    }

    @GetMapping("/setup")
    public String setupDatabase(Map<String, Object> model) {

        moviesInitialList.asList().forEach(movieClient::create);
        model.put("movies", movieClient.getAll());

        podcastsInitialList.asList().forEach(podcastClient::create);
        model.put("podcasts", podcastClient.getAll());
        return "setup";
    }

}
