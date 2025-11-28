package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck check) throws SQLException {
        var sql = "INSERT INTO url_checks (status_code, title, h1, description, url_id, created_at) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, check.getStatusCode());
            preparedStatement.setString(2, check.getTitle());
            preparedStatement.setString(3, check.getH1());
            preparedStatement.setString(4, check.getDescription());
            preparedStatement.setLong(5, check.getUrlId());
            var createdAt = LocalDateTime.now();
            preparedStatement.setTimestamp(6, Timestamp.valueOf(createdAt));

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                check.setId(generatedKeys.getLong(1));
                check.setCreatedAt(createdAt);
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static List<UrlCheck> getUrlChecks(Long urlID) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        var checks = new ArrayList<UrlCheck>();
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, urlID);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var urlId = resultSet.getLong("url_id");
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                check.setId(id);
                check.setCreatedAt(createdAt);
                checks.add(check);
            }
            return checks;
        }
    }

    public static Optional<UrlCheck> getLastUrlCheck(Long urlID) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC LIMIT 1";

        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, urlID);
            var resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }

            var id = resultSet.getLong("id");
            var statusCode = resultSet.getInt("status_code");
            var urlId = resultSet.getLong("url_id");
            var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            var title = resultSet.getString("title");
            var h1 = resultSet.getString("h1");
            var description = resultSet.getString("description");

            var check = new UrlCheck(statusCode, title, h1, description, urlId);
            check.setId(id);
            check.setCreatedAt(createdAt);

            return Optional.of(check);
        }
    }

    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        Map<Long, UrlCheck> lastChecks = new HashMap<>();
        var sql = "SELECT * "
                + "FROM url_checks "
                + "WHERE id IN ( "
                + "    SELECT MAX(id)"
                + "    FROM url_checks"
                + "    GROUP BY url_id"
                + ")";

        try (var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(sql)) {

            var resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var statusCode = resultSet.getInt("status_code");
                var urlId = resultSet.getLong("url_id");
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                var title = resultSet.getString("title");
                var h1 = resultSet.getString("h1");
                var description = resultSet.getString("description");

                var check = new UrlCheck(statusCode, title, h1, description, urlId);
                check.setId(id);
                check.setCreatedAt(createdAt);

                lastChecks.put(urlId, check);
            }
            return lastChecks;
        }
    }
}
