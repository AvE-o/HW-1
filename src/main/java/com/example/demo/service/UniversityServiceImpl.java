package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class UniversityServiceImpl implements UniversityService{

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public List<Map<String, Object>> getAllUniversities() {
        String url = "http://universities.hipolabs.com/search";
        Map<String, Object>[] universities = restTemplate.getForObject(url, Map[].class);
        return Arrays.stream(universities)
                .map(university -> Map.of(
                        "name", university.get("name"),
                        "domains", university.get("domains"),
                        "web_pages", university.get("web_pages")
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getUniversitiesByCountries(List<String> countries) throws InterruptedException, ExecutionException {
        List<CompletableFuture<Map<String, Object>[]>> futures = countries.stream()
                .map(country -> CompletableFuture.supplyAsync(() -> {
                    String url = "http://universities.hipolabs.com/search?country=" + country.replace(" ", "+");
                    return restTemplate.getForObject(url, (Class<Map<String, Object>[]>) (Class<?>) Map[].class);
                }))
                .collect(Collectors.toList());

        List<Map<String, Object>[]> results = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                .get();

        return results.stream()
                .flatMap(Arrays::stream)
                .map(university -> Map.of(
                        "name", university.get("name"),
                        "domain", university.get("domain"),
                        "web_pages", university.get("web_pages")
                ))
                .collect(Collectors.toList());
    }
}
