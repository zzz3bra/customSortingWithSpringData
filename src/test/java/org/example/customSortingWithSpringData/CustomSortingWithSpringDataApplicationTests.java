package org.example.customSortingWithSpringData;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomSortingWithSpringDataApplicationTests {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private AuthorQueryService authorQueryService;

    @AfterEach
    void tearDown() {
        authorRepository.deleteAllInBatch();
    }

    @Test
    void testQueryAllAuthorsWithSortingByPseudonymOrFullName() {
        generateAuthors();

        final List<Author> authors = authorQueryService.queryAllAuthorsWithSortingByPseudonymOrFullName();

        final List<Author> first50 = authors.subList(0, 50);
        assertThat("First 50 authors expected to be anonymous", first50, everyItem(hasProperty("pseudonym", not(nullValue()))));

        final List<Author> last50 = authors.subList(50, 50);
        assertThat("First 50 authors expected to be Johns", last50, everyItem(hasProperty("pseudonym", nullValue())));
    }

    @Test
    void testQueryAllAuthorsWithSortingByPseudonymOrFullNameWithPaging() {
        generateAuthors();

        final List<Author> first50 = authorQueryService.queryAllAuthorsWithSortingByPseudonymOrFullName(0, 50);
        assertThat("Only first 50 authors must be queried", first50, hasSize(50));
        assertThat("First 50 authors expected to be anonymous", first50, everyItem(hasProperty("pseudonym", not(nullValue()))));

        final List<Author> last50 = authorQueryService.queryAllAuthorsWithSortingByPseudonymOrFullName(1, 50);
        assertThat("Only last 50 authors must be queried", last50, hasSize(50));
        assertThat("First 50 authors expected to be Johns", last50, everyItem(hasProperty("pseudonym", nullValue())));
    }

    @Test
    void testQueryAllAuthorsWithSortingByPseudonymOrFullNameSpringDataNeumannWithPaging() {
        generateAuthors();

        final List<Author> first50 = authorQueryService.queryAllAuthorsWithSortingByPseudonymOrFullNameSpringDataNeumann(0, 50);
        assertThat("Only first 50 authors must be queried", first50, hasSize(50));
        assertThat("First 50 authors expected to be anonymous", first50, everyItem(hasProperty("pseudonym", not(nullValue()))));

        final List<Author> last50 = authorQueryService.queryAllAuthorsWithSortingByPseudonymOrFullNameSpringDataNeumann(1, 50);
        assertThat("Only last 50 authors must be queried", last50, hasSize(50));
        assertThat("First 50 authors expected to be Johns", last50, everyItem(hasProperty("pseudonym", nullValue())));
    }

    private void generateAuthors() {
        for (int i = 0; i < 100; i++) {
            final Author author;
            if (i % 2 == 0) {
                author = new Author("Anonymous#" + i, "John Doe Real#" + i);
            } else {
                author = new Author(null, "John Doe Real#" + i);
            }
            authorRepository.save(author);
        }
    }

}
