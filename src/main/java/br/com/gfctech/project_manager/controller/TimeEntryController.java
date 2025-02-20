package br.com.gfctech.project_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.gfctech.project_manager.dto.TimeEntryDTO;
import br.com.gfctech.project_manager.entity.TimeEntryEntity;
import br.com.gfctech.project_manager.service.TimeEntryService;

@RestController
@RequestMapping("/time-entries")
@CrossOrigin(origins = "*")
public class TimeEntryController {

    @Autowired
    private TimeEntryService timeEntryService;

    @PostMapping
    public TimeEntryEntity createTimeEntry(@RequestBody TimeEntryDTO timeEntryDTO) {
        return timeEntryService.saveTimeEntry(timeEntryDTO);
    }
}
