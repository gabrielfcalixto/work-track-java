package br.com.gfctech.project_manager.dto;

import br.com.gfctech.project_manager.entity.UserRole;

public record RegisterDTO(String login , String password, UserRole role) {

}
