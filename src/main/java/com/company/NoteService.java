package com.company;

import com.company.dto.Note;
import com.company.mappers.BeanPropertyRowMapper;
import com.company.sql.HikariDataSourceFactory;
import com.company.sql.JdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

public class NoteService {
    private final JdbcTemplate jdbcTemplate;

    public NoteService() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        HikariDataSourceFactory hikariDataSourceFactory = new HikariDataSourceFactory();
        DataSource dataSource = hikariDataSourceFactory.create();
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(Note note) {
        String sql = "INSERT INTO notes (note_name, note_description, note_creation_time) " +
                "VALUES (?,?,?);";
        jdbcTemplate.update(sql, new Object[]{note.getNoteName(),
                note.getNoteDescription(), LocalTime.now().toString()});
    }

    public List<Note> getAll() {
        String sql = "SELECT note_name,note_description, note_id, note_creation_time FROM notes;";
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Note.class));
    }

    public void delete(int index) {
        String sql = "DELETE FROM notes WHERE note_id =?;";
        jdbcTemplate.update(sql, new Object[]{index});
    }

}
