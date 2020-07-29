package tests;

import databaseConnect.JDBCConnection;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Проверка подключения к БД students и отправки запросов")
public class TestDatabase extends TestsSetups {

    @Test
    @Order(1)
    @DisplayName("Проверка подключения к БД")
    public void testConnection() {
        Assertions.assertNotNull(JDBCConnection.connectToDB());
    }

//    @Test
//    @Order(2)
//    @DisplayName("Проверка создания таблицы в БД")
//    public void testCreateTable()  {
//        String query = "CREATE TABLE professor ("
//                + "id int(6) NOT NULL,"
//                + "first_name VARCHAR(45) NOT NULL,"
//                + "last_name VARCHAR(45) NOT NULL,"
//                + "discipline VARCHAR(45),"
//                + "PRIMARY KEY (id))";
//        JDBCConnection.createTable(query);
//    }

    @Test
    @Order(2)
    @DisplayName("Отправка простого SELECT запроса. Проверка имени студента")
    public void testSelectRequest_checkFirstName() throws SQLException {
        String query = "SELECT first_name, last_name FROM students WHERE id=10";
        ResultSet rs = JDBCConnection.selectFromDB(query);
        rs.first();
        Assertions.assertEquals(rs.getString("first_name"), "Tatsiana");
    }

    @Test
    @Order(3)
    @DisplayName("Отправка SELECT JOIN запроса. Проверка фамилии студента")
    public void testSelectRequest_checkLastName() throws SQLException {
        String query = "SELECT first_name, last_name FROM students WHERE id=10";
        ResultSet rs = JDBCConnection.selectFromDB(query);
        rs.first();
        Assertions.assertEquals(rs.getString("last_name"), "Larionova");
    }

    @Test
    @Order(4)
    @DisplayName("Отправка SELECT JOIN запроса. Проверка имени последнего в списке студента")
    public void testSelectWithJoinRequest_CheckFistName() throws SQLException {
        String query = "SELECT s.first_name, s.last_name, g.group_name FROM students.students s LEFT JOIN students.groups g ON s.group_id=g.id";
        ResultSet rs = JDBCConnection.selectFromDB(query);
        rs.last();
        Assertions.assertEquals(rs.getString("first_name"), "Artem");
    }

    @Test
    @Order(5)
    @DisplayName("Отправка SELECT JOIN запроса. Проверка фамилии последнего в списке студента")
    public void testSelectWithJoinRequest_CheckLastName() throws SQLException {
        String query = "SELECT s.first_name, s.last_name, g.group_name FROM students.students s LEFT JOIN students.groups g ON s.group_id=g.id";
        ResultSet rs = JDBCConnection.selectFromDB(query);
        rs.last();
        Assertions.assertEquals(rs.getString("last_name"), "Pavlov");
    }

    @Test
    @Order(6)
    @DisplayName("Отправка SELECT JOIN запроса. Проверка группы последнего в списке студента")
    public void testSelectWithJoinRequest_CheckGroupName() throws SQLException {
        String query = "SELECT s.first_name, s.last_name, g.group_name FROM students.students s LEFT JOIN students.groups g ON s.group_id=g.id";
        ResultSet rs = JDBCConnection.selectFromDB(query);
        rs.last();
        Assertions.assertEquals(rs.getString("group_name"), "Economic and managment");
    }

    @Test
    @Order(7)
    @DisplayName("Отправка INSERT запроса")
    public void testInsertRequest() throws SQLException {
        String query = "INSERT INTO students.disciplines (ID, DISCIPLINE_NAME, HOURS) VALUES ('412890', 'Mathematic analisys', '104')";
        JDBCConnection.insertIntoDB(query);
        String selectQuery = "SELECT discipline_name FROM students.disciplines WHERE id=412890";
        ResultSet rs = JDBCConnection.selectFromDB(selectQuery);
        rs.first();
        Assertions.assertEquals(rs.getString("discipline_name"), "Mathematic analisys");
    }

    @Test
    @Order(8)
    @DisplayName("Отправка DELETE запроса")
    public void testDeleteRequest() throws SQLException {
        String query = "DELETE FROM students.disciplines WHERE id=412890";
        JDBCConnection.deleteFromDB(query);
    }

    @Test
    @Order(9)
    public void testUpdateRequest() throws SQLException {
        String query = "UPDATE students.students SET first_name = 'Anastasia' WHERE id=11";
        JDBCConnection.updateInDB(query);
        String selectQuery = "SELECT first_name FROM students.students WHERE id=11";
        ResultSet rs = JDBCConnection.selectFromDB(selectQuery);
        rs.first();
        Assertions.assertEquals(rs.getString("first_name"), "Anastasia");
    }

}
