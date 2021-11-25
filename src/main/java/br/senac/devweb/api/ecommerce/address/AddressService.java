package br.senac.devweb.api.ecommerce.address;

import br.senac.devweb.api.ecommerce.client.Client;
import br.senac.devweb.api.ecommerce.exceptions.NotFoundException;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address save(AddressRepresentation.CreateOrUpdateAddress addressRepresentation, Client client) {
        return this.addressRepository.save(
                Address.builder()
                        .client(client)
                        .type(addressRepresentation.getType())
                        .state(addressRepresentation.getState())
                        .city(addressRepresentation.getCity())
                        .cep(addressRepresentation.getCep())
                        .street(addressRepresentation.getStreet())
                        .number(addressRepresentation.getNumber())
                        .complement(addressRepresentation.getComplement())
                        .status(Address.Status.ATIVO)
                        .build()

        );
    }

    public Address getAddressById(Long id) {
        BooleanExpression filter = QAddress
                .address
                .id
                .eq(id)
                .and(QAddress.address.status.eq(Address.Status.ATIVO));

        return this.addressRepository.findOne(filter).orElseThrow(() -> new NotFoundException("Endereço não encontrado!"));
    }

    public Page<Address> getAllAddresses(Predicate filter, Pageable pageable) {
        return this.addressRepository.findAll(filter, pageable);
    }

    public Address update(Long id, AddressRepresentation.CreateOrUpdateAddress createOrUpdateAddress, Client client) {
        Address oldAddress = this.getAddressById(id);

        Address newAddress = oldAddress
                .toBuilder()
                .client(client)
                .type(createOrUpdateAddress.getType())
                .state(createOrUpdateAddress.getState())
                .city(createOrUpdateAddress.getCity())
                .street(createOrUpdateAddress.getStreet())
                .number(createOrUpdateAddress.getNumber())
                .complement(createOrUpdateAddress.getComplement())
                .status(Address.Status.ATIVO)
                .build();

        return this.addressRepository.save(newAddress);
    }

    public void delete(Long id) {
        Address address = this.getAddressById(id);

        address.setStatus(Address.Status.INATIVO);

        this.addressRepository.save(address);
    }
}
