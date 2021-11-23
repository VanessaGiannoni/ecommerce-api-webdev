package br.senac.devweb.api.ecommerce.client;

import br.senac.devweb.api.ecommerce.exceptions.NotFoundException;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

public class ClientService {
    private ClientRepository clientRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Client save(ClientRepresentation.CreateOrUpdateClient createClient) {
        return this.clientRepository.save(
            Client.builder()
                    .fullName(createClient.getFullName())
                    .birthDate(createClient.getBirthDate())
                    .password(this.bCryptPasswordEncoder.encode(createClient.getPassword()))
                    .status(Client.Status.ATIVO)
                    .user(createClient.getUser())
                    .build()
        );
    }

    public Page<Client> getAllClients (Predicate filter, Pageable pageable) {
        return this.clientRepository.findAll(filter, pageable);
    }

    public Client getOneClient (Long id) {
        BooleanExpression filter = QClient.client.id.eq(id)
                .and(QClient
                        .client
                        .status
                        .eq(Client.Status.ATIVO));

        return this.clientRepository
                .findOne(filter)
                .orElseThrow(
                        () -> new NotFoundException("Cliente não encontrado!")
                );
    }

    public Client updateClient (Long id, ClientRepresentation.CreateOrUpdateClient updateClient) {
        Client oldClient = this.getOneClient(id);

        Client newClient = oldClient.toBuilder()
                .fullName(updateClient.getFullName())
                .birthDate(updateClient.getBirthDate())
                .user(updateClient.getUser())
                .password(updateClient.getPassword())
                .status(Client.Status.ATIVO)
                .build();
        return this.clientRepository.save(newClient);
    }

    public Client deleteClient (Long id) {
        Client client = this.getOneClient(id);

        client.setStatus(Client.Status.INATIVO);

        return this.clientRepository.save(client);
    }
}
