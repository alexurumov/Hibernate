package com.company;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        String user = "root";
        String password = "root1234";

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager

                .getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        PreparedStatement stmt =
                connection.prepareStatement("SELECT * FROM employees WHERE first_name = ? AND last_name = ?");

        String first_name = sc.nextLine();

        String last_name = sc.nextLine();

        stmt.setString(1, first_name);
        stmt.setString(2, last_name);

        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            System.out.printf("Id | f_name | m_name | l_name | Job Title In The Organisation | Date in which hired | Salary%n" +
                            "%s | %s | %s | %s | %s | %s | %s",
                    rs.getString("employee_id"),
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getString("job_title"),
                    rs.getString("hire_date"),
                    rs.getDouble("salary"));
        }
        connection.close();


    }
}
