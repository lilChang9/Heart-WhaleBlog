package com.heart.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {

    private String username;
    //昵称
    private String nickName;
    //密码
    private String password;
    //邮箱
    private String email;

}
