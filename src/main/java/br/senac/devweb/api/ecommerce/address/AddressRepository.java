package br.senac.devweb.api.ecommerce.address;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressRepository extends PagingAndSortingRepository<Address, Long>,
        QuerydslPredicateExecutor<Address>,
        QuerydslBinderCustomizer<QAddress> {
    List<Address> findAll(Predicate filter);

    default void customize(QuerydslBindings bindings, QAddress address) {
        bindings.bind(address.complement).first(StringExpression::containsIgnoreCase);
        bindings.bind(address.cep).first(StringExpression::containsIgnoreCase);
        bindings.bind(address.street).first(StringExpression::containsIgnoreCase);
        bindings.bind(address.city).first(StringExpression::containsIgnoreCase);
        bindings.bind(address.state).first(StringExpression::containsIgnoreCase);
    }
}
