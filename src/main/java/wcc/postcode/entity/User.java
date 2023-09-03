
package wcc.postcode.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author hamizan
 */
@Entity
public class User {

    @Id
    @GeneratedValue(generator = "sequence-generator-2")
    @GenericGenerator(
      name = "sequence-generator-2",
      parameters = {
        @Parameter(name = "sequence_name", value = "user_seq"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )
    private Integer id;
    @Column(unique = true, length = 16)
    private String username;    
    @Column
    private String password;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
