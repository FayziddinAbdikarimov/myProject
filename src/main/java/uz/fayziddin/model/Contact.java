package uz.fayziddin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    private int id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private int attachment_id;
}
