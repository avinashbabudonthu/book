package com.itools.component;

import com.itools.record.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        String firstName = person.firstName().toUpperCase();
        String lastName = person.lastName().toUpperCase();
        return new Person(firstName, lastName);
    }

}