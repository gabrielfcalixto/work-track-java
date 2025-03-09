package br.com.gfctech.project_manager.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDate joinDate;
      
}
