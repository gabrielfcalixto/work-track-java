package br.com.gfctech.project_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gfctech.project_manager.dto.TimeEntryDTO;
import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import br.com.gfctech.project_manager.service.TimeEntryService;

@RestController
@RequestMapping("/time-entries")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend
public class TimeEntryController {

    @Autowired
    private TimeEntryService timeEntryService;

    @PostMapping
    public TimeEntryEntity createTimeEntry(@RequestBody TimeEntryDTO timeEntryDTO) {
        return timeEntryService.saveTimeEntry(timeEntryDTO);
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TimeEntryDTO>> getUserTimeEntries(@PathVariable Long userId) {
    List<TimeEntryDTO> entries = timeEntryService.getTimeEntriesByUserId(userId);
    return ResponseEntity.ok(entries);
}

}
