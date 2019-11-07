package com.brest.bstu.po1.imod.dao;

import com.brest.bstu.po1.imod.model.Greeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

public class GreetingDaoImpl implements GreetingDao {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String ID = "id";
    private static final String CONTENT = "content";
    private static final String NICKNAME = "nickname";
    private static final String ROOM = "room";
    private static final String DATETIME = "datetime";

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
        LOGGER.debug("addGreeting({})", greeting);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(CONTENT, greeting.getContent());
        namedParameters.addValue(NICKNAME, greeting.getNickname());
        namedParameters.addValue(ROOM, greeting.getRoom());
        greeting.setDatetime(new Timestamp(System.currentTimeMillis()));
        greeting.setDatetime_str(greeting.getDatetime().toString());
        namedParameters.addValue(DATETIME, greeting.getDatetime());
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate
                .update(ADD_GREETING_SQL, namedParameters, generatedKeyHolder,
                        new String[]{"id"});
        greeting.setId(Objects.requireNonNull(generatedKeyHolder.getKey()).intValue());
        LOGGER.debug("addGreeting() returned {}", greeting);
        return greeting;
    }

    @Override
    public Greeting[] getAllGreetings() {
        LOGGER.debug("getAllGreetings()");
        List<Greeting> greetingList = namedParameterJdbcTemplate
                .query(GET_ALL_GREETINGS_SQL,
                       new GreetingRowMapper());
        LOGGER.debug("getAllGreetings() returned {}", greetingList);
        return greetingList.toArray(new Greeting[0]);
    }

    @Override
    public void removeAllGreetings() {
        LOGGER.debug("removeAllGreetings()");
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameterJdbcTemplate.update(REMOVE_ALL_GREETINGS_SQL, namedParameters);
        LOGGER.debug("removeAllGreetings returned.");
    }

    private static class GreetingRowMapper implements RowMapper<Greeting> {

        @Override
        public Greeting mapRow(ResultSet resultSet, int i) throws SQLException {
            Greeting goods = new Greeting();
            goods.setId(resultSet.getInt(ID));
            goods.setContent(resultSet.getString(CONTENT));
            goods.setNickname(resultSet.getString(NICKNAME));
            goods.setDatetime(resultSet.getTimestamp(DATETIME));
            goods.setRoom(resultSet.getInt(ROOM));
            return goods;
        }
    }
}
