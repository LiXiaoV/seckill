package com.xiaov.seckill.vo;

import com.xiaov.seckill.validator.MobileCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author xiaov
 * @since 2021-03-04 11:02
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    @NotNull
    @MobileCheck
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
