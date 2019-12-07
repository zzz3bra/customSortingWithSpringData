package org.example.customSortingWithSpringData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorQueryService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorQueryService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> queryAllAuthorsWithSortingByPseudonymOrFullName() {
        return authorRepository.findAll(AuthorSpecifications.orderByPseudonymOrFullName());
    }

    public List<Author> queryAllAuthorsWithSortingByPseudonymOrFullName(int page, int size) {
        return authorRepository.findAll(AuthorSpecifications.orderByPseudonymOrFullName(),
                PageRequest.of(page, size, Sort.unsorted())).getContent();
    }
}
