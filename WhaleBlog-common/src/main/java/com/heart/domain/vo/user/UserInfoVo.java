package com.heart.domain.vo.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    private Long id;

    private String avatar;

    //昵称
    private String nickName;

    //用户性别（0男，1女，2未知）
    private String sex;

    //邮箱
    private String email;
}
