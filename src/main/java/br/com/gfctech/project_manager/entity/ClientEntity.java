package br.com.gfctech.project_manager.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "GFC_CLIENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String cnpj;

    private String razaoSocial;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column
    private String address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ProjectEntity> projects;
}
