package org.qiyu.hospital.controller;

import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.qiyu.hospital.model.dto.UserDTO;
import org.qiyu.hospital.model.entity.UserDO;
import org.qiyu.hospital.model.vo.AuthRegisterVO;
import org.qiyu.hospital.service.AuthService;
import org.qiyu.hospital.service.TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserDTO>> userRegister(
            @RequestBody @Validated AuthRegisterVO authRegisterVO
    ) {
        // TODO: 用户注册
        //    1. 获取用户数据
        //    2. VO -> Mapper 查数据
        //    3. 查到数据检查是否重复
        //    4. 数据查不到，可以插入
        //    5. 登录授权
        //    6. 完成注册

        authService.checkUserExist(authRegisterVO);
        UserDO getUser = authService.userRegister(authRegisterVO);
        String getToken = tokenService.createToken(getUser.getUuid(), 24L);
        UserDTO userDTO = new UserDTO();
        UserDTO.User newUser = new UserDTO.User();
        BeanUtils.copyProperties(getUser, newUser);
        userDTO
                .setUser(newUser)
                .setToken(getToken);
        return ResultUtil.success("注册成功", userDTO);
    }
}
