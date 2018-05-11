package fr.dima.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import fr.dima.batch.model.Personne;



public class PersonItemProcessor implements ItemProcessor<Personne, Personne> {

    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

    @Override
    public Personne process(final Personne person) throws Exception {
        final String prenom = person.getPrenom().toUpperCase();
        final String nom = person.getNom().toUpperCase();

        final Personne transformedPerson = new Personne(prenom, nom,person.getEmail(),person.getSociete(),person.getTel());

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}