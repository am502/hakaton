package ru.itis.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.config.DaoConfig;
import ru.itis.models.Distance;
import ru.itis.models.Msg;

import java.util.List;

public class DistanceDao {
    private JdbcTemplate jdbcTemplate;
    private static final String SAVE_DATA_SQL = "INSERT INTO distance " +
            "(device_id, data_id, dist) VALUES (?, ?, ?)";
    private static final String GET_LAST_DATA_SQL = "SELECT device_id, dist FROM distance " +
            "WHERE data_id = (SELECT max(data_id) FROM distance WHERE count(device_id) = 4);";

    public DistanceDao() {
        jdbcTemplate = new JdbcTemplate(DaoConfig.getDataSource());
    }

    public void saveData(Msg msg) {
        jdbcTemplate.update(SAVE_DATA_SQL, msg.getDevice_id(),
                msg.getTagPackId(),
                msg.getDistance());
    }

    public List<Distance> getLastDistances() {
        return jdbcTemplate.query(GET_LAST_DATA_SQL, new BeanPropertyRowMapper<>(Distance.class));
    }
}
