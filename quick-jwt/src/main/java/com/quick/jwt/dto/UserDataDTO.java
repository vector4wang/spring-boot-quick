package com.quick.jwt.dto;

import com.quick.jwt.model.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UserDataDTO {

	@ApiModelProperty(position = 0)
	private String username;
    @ApiModelProperty(position = 1)
	private String password;

	@ApiModelProperty(position = 2)
	private String email;
	@ApiModelProperty(position = 3)
	List<Role> roles;

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

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}