package uz.fayziddin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AttachmentService {
    @Autowired
    DataSource dataSource;
    public Integer saveAttachment(MultipartFile multipartFile){
        Connection connection = null;
        Integer attachmentId=getLastId();
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("insert into attachment(file_name, file_type, file_size, file_content, id)" +
                    "values (?,?,?,?,?)");
            statement.setString(1,multipartFile.getOriginalFilename());
            statement.setString(2,multipartFile.getContentType());
            statement.setLong(3,multipartFile.getSize());
            statement.setBytes(4,multipartFile.getBytes());
            statement.setInt(5,attachmentId);
            statement.execute();
            return attachmentId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public Integer getLastId(){
        Connection connection =null;
        try {
            connection= dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select max(id) from attachment");
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1)+1;
            }



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }return 1;

    }

    public byte[] getContent(int id) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement("select file_content from attachment where id=?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getBytes(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }
}
