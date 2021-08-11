package uz.fayziddin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.fayziddin.model.Contact;
import uz.fayziddin.model.Result;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {
    String url = "jdbc:postgresql://localhost/twentyfive";
    String username = "postgres";
    String password = "password";

    @Autowired
    DataSource dataSource;

    public List<Contact> getContactList() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        List<Contact> contacts = new ArrayList<Contact>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from contact order by id");
            while (resultSet.next()) {
                contacts.add(new Contact(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return contacts;
    }

    public Result addContact(String first_name, String last_name, String phone, String email, int attachment_id) {
        Result result = new Result();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("insert into contact(first_name, last_name, phone, email, attachment_id) " +
                    "values (?, ?, ?, ?,?)");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setInt(5,attachment_id);
            statement.execute();
            result.setSuccess(true);
            result.setMessage("Contact saved!");
        } catch (SQLException e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("Error in adding contact.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Contact getContactById(int contact_id) {
        Connection connection = null;
        Contact aContact = new Contact();
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from contact where id = ?");
            statement.setInt(1, contact_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                aContact.setId(resultSet.getInt(1));
                aContact.setFirst_name(resultSet.getString(2));
                aContact.setLast_name(resultSet.getString(3));
                aContact.setPhone(resultSet.getString(4));
                aContact.setEmail(resultSet.getString(5));
                aContact.setAttachment_id(resultSet.getInt(6));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return aContact;
    }

    public Result editContact(int id, String first_name, String last_name, String phone, String email) {
        Connection connection = null;
        Result result = new Result();
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("update contact set first_name=?, last_name=?, phone=?, email=? where id = ?");
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, phone);
            statement.setString(4, email);
            statement.setInt(5, id);
            statement.execute();
            result.setSuccess(true);
            result.setMessage("Edited successfully!");
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("Error in editing");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public Result deleteContact(int id) {
        Result result = new Result();
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("delete  from contact where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            result.setMessage("Successfully deleted");
            result.setSuccess(true);
//            System.out.println(result);

        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMessage("Error deleted");
            e.printStackTrace();
//            System.out.println(result);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return result;
    }
}
