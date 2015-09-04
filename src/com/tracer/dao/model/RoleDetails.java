/**
 * @author Jp
 *
 */
package com.tracer.dao.model;

import java.util.HashSet;
import java.util.Set;

public class RoleDetails implements java.io.Serializable {
  private static final long serialVersionUID = 4137893824745089262L;
  private Long roleId;
  private String roleName;
  private String status;
  private String aliasName;
  @SuppressWarnings("rawtypes")
  private Set userDetailses = new HashSet(0);

  /** default constructor */
  public RoleDetails() {
  }

  /** minimal constructor */
  public RoleDetails(String roleName, String status) {
    this.roleName = roleName;
    this.status = status;
  }

  /** full constructor */
  @SuppressWarnings("rawtypes")
  public RoleDetails(String roleName, String status, String aliasName,
      Set userDetailses) {
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

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAliasName() {
    return this.aliasName;
  }

  public void setAliasName(String aliasName) {
    this.aliasName = aliasName;
  }

  @SuppressWarnings("rawtypes")
  public Set getUserDetailses() {
    return this.userDetailses;
  }

  @SuppressWarnings("rawtypes")
  public void setUserDetailses(Set userDetailses) {
    this.userDetailses = userDetailses;
  }

}