package com.data.tracer;

// Generated 15 Jun, 2015 3:57:49 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * RoleDetails generated by hbm2java
 */
public class RoleDetails implements java.io.Serializable {

	private Long roleId;
	private String roleName;
	private char status;
	private String aliasName;
	private Set<UserDetails> userDetailses = new HashSet<UserDetails>(0);

	public RoleDetails() {
	}

	public RoleDetails(String roleName, char status) {
		this.roleName = roleName;
		this.status = status;
	}

	public RoleDetails(String roleName, char status, String aliasName,
			Set<UserDetails> userDetailses) {
		this.roleName = roleName;
		this.status = status;
		this.aliasName = aliasName;
		this.userDetailses = userDetailses;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public char getStatus() {
		return this.status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getAliasName() {
		return this.aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public Set<UserDetails> getUserDetailses() {
		return this.userDetailses;
	}

	public void setUserDetailses(Set<UserDetails> userDetailses) {
		this.userDetailses = userDetailses;
	}

}
