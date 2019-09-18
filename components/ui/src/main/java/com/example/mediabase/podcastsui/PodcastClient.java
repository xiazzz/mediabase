package com.example.mediabase.podcastsui;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.util.List;

public class PodcastClient {
    private static ParameterizedTypeReference<List<PodcastUI>> movieListType = new ParameterizedTypeReference<List<PodcastUI>>() {
    };
    private RestOperations restOperations;
    private String podcastsURL;


    public PodcastClient(String moviesURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.podcastsURL = moviesURL;
    }

    public void save(PodcastUI podcast) {
        restOperations.postForEntity(podcastsURL, podcast, PodcastUI.class);
    }

    public List<PodcastUI> findAll() {
        return restOperations.exchange(podcastsURL, HttpMethod.GET, null, movieListType).getBody();
    }


}
