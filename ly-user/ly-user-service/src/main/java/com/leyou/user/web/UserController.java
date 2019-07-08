package com.leyou.user.web;

import com.leyou.common.vo.RespBean;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param data
     * @param type :要校验的数据类型 1：用户名，2：手机号码
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<RespBean> check(
            @PathVariable("data") String data,@PathVariable("type") Integer type){
        if(userService.checkData(data,type)){
            return ResponseEntity.noContent().build();
        }else{
            if(type==1){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(RespBean.status(HttpStatus.BAD_REQUEST.value(),"该用户名已存在"));
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(RespBean.status(HttpStatus.BAD_REQUEST.value(),"该电话号码已被注册"));
            }

        }
    }

    /**
     * 发送短信验证码
     * @param phone
     * @return
     */
    @PostMapping("/code")
    public ResponseEntity<RespBean> sendCode(@RequestParam("phone") String phone){
        userService.sendCode(phone);
        //RespBean.ok(HttpStatus.OK.value(),"短信发送成功，请注意查收！","");
        return ResponseEntity.ok(RespBean.status(HttpStatus.OK.value(),"短信发送成功，请注意查收！"));
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Validated User user, BindingResult result, @RequestParam(value = "code",defaultValue = "0")String code){
        if(result.hasFieldErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespBean.status(HttpStatus.BAD_REQUEST.value(),
                            result.getFieldErrors().stream().map(e -> e.getDefaultMessage())
                                    .collect(Collectors.joining("|"))));
        }
        if(userService.checkData(user.getUsername(),1) && userService.checkData(user.getPhone(),2)){
            userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RespBean.status(HttpStatus.CREATED.value(),"注册成功"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespBean.status(HttpStatus.BAD_REQUEST.value(),"注册失败,用户名或电话号码已被注册!"));
        }
    }
}
