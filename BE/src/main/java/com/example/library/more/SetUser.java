package com.example.library.more;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUser {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String userAddress;
    private String passOld;
    private String passNew;
    public SetUser(){}
    public SetUser(Long userId, String userName, String userEmail, String userPhone, String userAddress, String passOld, String passNew){
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.passOld = passOld;
        this.passNew = passNew;
    }

}
