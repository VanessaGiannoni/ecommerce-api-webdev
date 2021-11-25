package br.senac.devweb.api.ecommerce.contact;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>,
        QuerydslPredicateExecutor<Contact>,
        QuerydslBinderCustomizer<QContact> {

    List<Contact> findAll(Predicate filter);

    default void customize(QuerydslBindings bindings, QContact contact) {
        bindings.bind(contact.note).first(StringExpression::containsIgnoreCase);
        bindings.bind(contact.email).first(StringExpression::containsIgnoreCase);
    }

}
