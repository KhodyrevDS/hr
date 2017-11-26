package ru.kds.hr.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Letter template repository
 */
public interface LetterTemplateRepository extends CrudRepository<LetterTemplate, Long> {

    /**
     * Find letter template by name
     *
     * @param name letter template name
     * @return the letter template if any
     */
    LetterTemplate findByName(String name);
}
