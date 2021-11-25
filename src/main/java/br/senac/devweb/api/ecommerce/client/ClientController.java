package br.senac.devweb.api.ecommerce.client;

import br.senac.devweb.api.ecommerce.utils.Pagination;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;
    private ClientRepository clientRepository;

    @PostMapping("/")
    public ResponseEntity<ClientRepresentation.ClientDetail> createClient(
            @Valid @RequestBody ClientRepresentation.CreateOrUpdateClient clientRepresentation
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ClientRepresentation.ClientDetail.from(
                                this.clientService.save(clientRepresentation)
                        )
                );
    }

    @PutMapping("/")
    public ResponseEntity<ClientRepresentation.ClientDetail> updateClient(
            @PathVariable("id") Long id,
            @Valid @RequestBody ClientRepresentation.CreateOrUpdateClient createClient
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ClientRepresentation.ClientDetail.from(
                                this.clientService.updateClient(id, createClient)
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientRepresentation.ClientDetail> getClientById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ClientRepresentation.ClientDetail.from(
                        this.clientService.getOneClient(id)
                )
        );
    }

    @GetMapping("/")
    public ResponseEntity<Pagination> getAllClients(
            @QuerydslPredicate(root = Client.class) Predicate filters,
            @Valid @RequestParam(name = "selectedPage", defaultValue = "1") Integer selectedPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        BooleanExpression filter = Objects.isNull(filters)
                ? QClient.client.status.eq(Client.Status.ATIVO)
                : QClient.client.status.eq(Client.Status.ATIVO).and(filters);

        if (selectedPage < 1) {
            throw new IllegalArgumentException("O número da página não pode ser menor que 1.");
        }

        Pageable pageRequest = PageRequest.of(selectedPage-1, pageSize);

        Page<Client> clientPage = this.clientService.getAllClients(filter, pageRequest);

        Pagination pagination = Pagination.builder()
                .content(ClientRepresentation.ClientList.from(clientPage.getContent()))
                .selectedPage(selectedPage)
                .pageSize(pageSize)
                .pageCount(clientPage.getTotalPages())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pagination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientRepresentation.ClientDetail> deleteClient(
        @PathVariable("id") Long id
    ) {
        this.clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
