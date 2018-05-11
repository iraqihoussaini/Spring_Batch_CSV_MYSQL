package fr.dima.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import fr.dima.batch.model.Personne;



public class PersonItemProcessor implements ItemProcessor<Personne, Personne> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Personne process(final Personne person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Personne transformedPerson = new Personne(firstName, lastName,person.getEmail(),person.getSociete(),person.getRemarques(),person.getMobile());

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}