package spring.firstmvc.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import spring.firstmvc.model.Person;

@Component
public class PersonDAO {

  private static int PEOPLE_COUNT;

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public PersonDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    String selectAllPerson = "select * from person";
    return jdbcTemplate.query(selectAllPerson, new BeanPropertyRowMapper<>(Person.class));
  }

  public Person show(int id) {
    String selectPerson = String.format("select * from person where id=%s", id);
    return jdbcTemplate.query(selectPerson, new BeanPropertyRowMapper<>(Person.class))
        .stream()
        .findAny()
        .orElse(null);
  }

  public void save(Person person) {
    String insertPerson = String.format(
        "insert into person values (%s, '%s', %s, '%s')",
        1,
        person.getName(),
        person.getAge(),
        person.getEmail()
    );
    jdbcTemplate.update(insertPerson);
  }

  public void update(int id, Person updatedPerson) {
    String updatePerson = String.format(
        "update person set name='%s', age=%s, email='%s' where id=%s",
        updatedPerson.getName(),
        updatedPerson.getAge(),
        updatedPerson.getEmail(),
        id
    );
    jdbcTemplate.update(updatePerson);
  }

  public void delete(int id) {
    String deletePerson = String.format(
        "delete from person where id=%s",
        id
    );
    jdbcTemplate.update(deletePerson);
  }

}