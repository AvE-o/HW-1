package com.example.demo.controller;

import com.example.demo.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/universities")
public class UniversityController {
    @Autowired
    private UniversityService universityService;

    @GetMapping
    public List<Map<String, Object>> getAllUniversities() {
        return universityService.getAllUniversities();
    }

    @PostMapping("/countries")
    public List<Map<String, Object>> getAllUniversitiesByCountries(@RequestBody List<String> countries) throws InterruptedException, ExecutionException {
        return universityService.getUniversitiesByCountries(countries);
    }
}
