/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rajesh.lms.dao;

import com.rajesh.lms.model.Role;
import java.util.List;


public interface RoleDao {
    public Integer addRole(Role role);
    public Integer updateRole(Role role);
    public Integer deleteRole(Integer roleId);
    public Role getRoleById(Integer id);
    public Integer getRoleIdByName(String name);
    public List<Role> getAllRole();
}
