package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public interface UniversityService {
    List<Map<String, Object>> getAllUniversities();
    List<Map<String, Object>> getUniversitiesByCountries(List<String> countries) throws InterruptedException, ExecutionException;
}
