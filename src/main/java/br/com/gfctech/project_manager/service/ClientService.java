package br.com.gfctech.project_manager.service;

import br.com.gfctech.project_manager.dto.ClientDTO;
import br.com.gfctech.project_manager.dto.UserDTO;
import br.com.gfctech.project_manager.entity.ClientEntity;
import br.com.gfctech.project_manager.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id)
            .map(ClientDTO::new)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Transactional
    public ClientDTO addClient(ClientDTO clientDTO) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(clientDTO.getName());
        clientEntity.setCnpj(clientDTO.getCnpj());
        clientEntity.setRazaoSocial(clientDTO.getRazaoSocial());
        clientEntity.setCpf(clientDTO.getCpf());
        clientEntity.setEmail(clientDTO.getEmail());
        clientEntity.setPhone(clientDTO.getPhone());
        clientEntity.setAddress(clientDTO.getAddress());

        clientRepository.save(clientEntity);
        return new ClientDTO(clientEntity);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        ClientEntity existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
                existingClient.setName(clientDTO.getName());
                existingClient.setCnpj(clientDTO.getCnpj());
                existingClient.setRazaoSocial(clientDTO.getRazaoSocial());
                existingClient.setCpf(clientDTO.getCpf());
                existingClient.setEmail(clientDTO.getEmail());
                existingClient.setPhone(clientDTO.getPhone());
                existingClient.setAddress(clientDTO.getAddress());

        return new ClientDTO(clientRepository.save(existingClient));
    }

    @Transactional
    public void deleteClient(Long id) {
        ClientEntity client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + id));
        clientRepository.delete(client);
    }
}
