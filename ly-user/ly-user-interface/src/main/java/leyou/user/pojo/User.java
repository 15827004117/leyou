package leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Date;


/**
 * @author Administrator
 */
@Data
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空！")
    @Length(min = 4, max = 32, message = "用户名长度必须在4~32位之间")
    private String username;
    /**
     * 密码
     */
    @Length(min = 4, max = 32, message = "密码长度必须在4~32位之间")
    @JsonIgnore
    private String password;
    /**
     * 电话
     */
    @Pattern(regexp = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$", message = "手机号码格式有误！")
    private String phone;
    /**
     * 创建时间
     */
    private Date created;
    /**
     * 密码的盐值
     */
    @JsonIgnore
    private String salt;
}