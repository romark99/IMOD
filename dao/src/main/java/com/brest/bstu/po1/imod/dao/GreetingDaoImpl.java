package com.brest.bstu.po1.imod.dao;

import com.brest.bstu.po1.imod.model.Greeting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class GreetingDaoImpl implements GreetingDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String ID = "id";

    private static final String CONTENT = "content";

    @Value("${greeting.insert}")
    private String ADD_GREETING_SQL;

    @Value("${greeting.select}")
    private String GET_ALL_GREETINGS_SQL;

    @Value("${greeting.delete}")
    private String REMOVE_ALL_GREETINGS_SQL;

    public void setNamedParameterJdbcTemplate(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Greeting addGreeting(Greeting greeting) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(CONTENT, greeting.getContent());

        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate
                .update(ADD_GREETING_SQL, namedParameters, generatedKeyHolder,
                        new String[]{"id"});
        greeting.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).intValue());
        return greeting;
    }

    @Override
    public Greeting[] getAllGreetings() {
        List<Greeting> greetingList = namedParameterJdbcTemplate
                .query(GET_ALL_GREETINGS_SQL,
                       new GreetingRowMapper());
        return greetingList.toArray(new Greeting[0]);
    }

    @Override
    public void removeAllGreetings() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameterJdbcTemplate.update(REMOVE_ALL_GREETINGS_SQL, namedParameters);
    }

    private static class GreetingRowMapper implements RowMapper<Greeting> {

        @Override
        public Greeting mapRow(ResultSet resultSet, int i) throws SQLException {
            Greeting goods = new Greeting();
            goods.setId(resultSet.getInt(ID));
            goods.setContent(resultSet.getString(CONTENT));
            return goods;
        }
    }
}
