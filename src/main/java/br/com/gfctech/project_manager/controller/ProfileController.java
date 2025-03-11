package br.com.gfctech.project_manager.controller;

import br.com.gfctech.project_manager.dto.ProfileDTO;
import br.com.gfctech.project_manager.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@CrossOrigin("http://localhost:4200")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long userId) {
        ProfileDTO profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }
}