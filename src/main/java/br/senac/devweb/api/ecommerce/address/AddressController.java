package br.senac.devweb.api.ecommerce.address;

import br.senac.devweb.api.ecommerce.client.Client;
import br.senac.devweb.api.ecommerce.client.ClientService;
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
@RequestMapping("/address")
@AllArgsConstructor
public class AddressController {
    private AddressService addressService;
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<AddressRepresentation.AddressDetail> createAddress(
            @Valid @RequestBody AddressRepresentation.CreateOrUpdateAddress addressRepresentation
    ) {
        Client client = this.clientService.getOneClient((addressRepresentation.getClient()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        AddressRepresentation.AddressDetail.from(
                                this.addressService.save(addressRepresentation, client)
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressRepresentation.AddressDetail> updateAddress(
            @PathVariable("id") Long id,
            @Valid @RequestBody AddressRepresentation.CreateOrUpdateAddress addressRepresentation
    ) {
        Client client = this.clientService.getOneClient((addressRepresentation.getClient()));

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        AddressRepresentation.AddressDetail.from(
                                this.addressService.update(id, addressRepresentation, client)
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressRepresentation.AddressDetail> getAddressById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        AddressRepresentation.AddressDetail.from(
                                this.addressService.getAddressById(id)
                        )
                );
    }

    @GetMapping("/")
    public ResponseEntity<Pagination> getAllAddresses(
            @QuerydslPredicate(root = Address.class)Predicate filters,
            @Valid @RequestParam(name = "selectedPage", defaultValue = "1") Integer selectedPage,
            @RequestParam(name = "pageSize", required = false, defaultValue = "20") Integer pageSize
        ) {
        BooleanExpression filter = Objects.isNull(filters)
                ? QAddress.address.status.eq(Address.Status.ATIVO)
                : QAddress.address.status.eq(Address.Status.ATIVO).and(filters);

        if (selectedPage < 1) {
            throw new IllegalArgumentException("O número da página não pode ser menor que 1!");
        }

        Pageable pageRequest = PageRequest.of(selectedPage-1, pageSize);

        Page<Address> addressPage = this.addressService.getAllAddresses(filter, pageRequest);

        Pagination pagination = Pagination.builder()
                .content(AddressRepresentation.AddressList.from(addressPage.getContent()))
                .selectedPage(selectedPage)
                .pageSize(pageSize)
                .pageCount(addressPage.getTotalPages())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(pagination);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressRepresentation.AddressDetail> deleteAddress(
            @PathVariable("id") Long id
    ) {
        this.addressService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
