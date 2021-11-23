package br.senac.devweb.api.ecommerce.client;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ClientRepository extends PagingAndSortingRepository<Client, Long>,
        QuerydslPredicateExecutor<Client>,
        QuerydslBinderCustomizer<QClient> {

    List<Client> findAll(Predicate filter);

    default void customize(QuerydslBindings bindings, QClient client) {
        bindings.bind(client.fullName).first(StringExpression::containsIgnoreCase);
    }
}
