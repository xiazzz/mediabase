package com.example.mediabase.podcastsui;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.ArrayList;

public class PodcastClient {
    private static ParameterizedTypeReference<List<PodcastUI>> podcastListType = new ParameterizedTypeReference<List<PodcastUI>>() {
    };
    private RestOperations restOperations;
    private String podcastsURL;
    private static final int CACHE_SIZE = 5;
    private final List<PodcastUI> lastRead = new ArrayList<>(CACHE_SIZE);
    private static final Logger log = LoggerFactory.getLogger(PodcastClient.class);


    public PodcastClient(String moviesURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.podcastsURL = moviesURL;
    }

    public void save(PodcastUI podcast) {
        restOperations.postForEntity(podcastsURL, podcast, PodcastUI.class);
    }

    @HystrixCommand(fallbackMethod="findAllFallback")
    public List<PodcastUI> findAll() {
        List<PodcastUI> read = restOperations.exchange(podcastsURL, HttpMethod.GET, null, podcastListType).getBody();
        log.debug("Read {} podcasts from {}", read.size(), podcastsURL);

        lastRead.clear();
        int copyCount = (read.size() < CACHE_SIZE) ? read.size() : CACHE_SIZE;
        for (int i =0; i < copyCount; i++)
            lastRead.add(read.get(i));
        log.debug("Copied {} podcasts into the cache", copyCount);

        return read;
    }

    public List<PodcastUI> findAllFallback() {
        log.debug("Returning {} podcasts from the fallback method", lastRead.size());

        return lastRead;
    }


}
