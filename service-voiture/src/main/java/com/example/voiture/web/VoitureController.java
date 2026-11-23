package com.example.voiture.web;

import com.example.voiture.entities.Voiture;
import com.example.voiture.repositories.VoitureRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/voitures")
public class VoitureController {
    private final VoitureRepository repo;
    private final RestTemplate restTemplate;

    public VoitureController(VoitureRepository repo, RestTemplate restTemplate) {
        this.repo = repo;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Voiture> findAll() {
        return repo.findAll();
    }

    @PostMapping
    public Voiture save(@RequestBody Voiture voiture) {
        return repo.save(voiture);
    }

    @GetMapping("/{id}")
    public Voiture findById(@PathVariable Long id) {
        Voiture v = repo.findById(id).orElse(null);
        if (v != null && v.getClientId() != null) {
            try {
                Object client = restTemplate.getForObject("http://SERVICE-CLIENT/api/clients/" + v.getClientId(), Object.class);
                v.setClient(client);
            } catch (Exception ignored) {
            }
        }
        return v;
    }
}
