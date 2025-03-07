package br.com.gfctech.project_manager.dto;

import br.com.gfctech.project_manager.entity.ClientEntity;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String cnpj;
    private String razaoSocial;
    private String cpf;  // Adicionar o campo cpf aqui
    private String email;
    private String phone;
    private String address;

    public ClientDTO(ClientEntity client) {
        this.id = client.getId();
        this.name = client.getName();
        this.cnpj = client.getCnpj();
        this.razaoSocial = client.getRazaoSocial();
        this.cpf = client.getCpf(); 
        this.email = client.getEmail();
        this.phone = client.getPhone();
        this.address = client.getAddress();
    }
}
