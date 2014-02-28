/**
 * IDM Commons Services bundle
 * User JPA impl
 * Copyright (C) 2014 Mathilde Ffrench
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.spectral.cc.core.idm.base.model.jpa;

import com.spectral.cc.core.idm.base.model.IUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@XmlRootElement
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"userEmail","userName"}))
public class User implements IUser<Group, Role>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(name = "userName", unique = true)
    @NotNull
    private String userName;

    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column(name = "userEmail", unique = true)
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private byte[] passwordSalt;

    @Column
    @NotNull
    private String phone;

    @ManyToMany
    private Set<Group> groups = new HashSet<Group>();

    @ManyToMany
    private Set<Role> roles = new HashSet<Role>();

    @OneToMany
    private Set<UserPreference> preferences = new HashSet<UserPreference>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public User setVersionR(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User setUserNameR(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User setFirstNameR(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User setLastNameR(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public User setEmailR(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        this.password = new Sha512Hash(password, salt, 2048).toBase64();
        this.passwordSalt = salt.getBytes();
    }

    public User setPasswordR(String password) {
        RandomNumberGenerator rng = new SecureRandomNumberGenerator();
        ByteSource salt = rng.nextBytes();
        this.password = new Sha512Hash(password, salt, 2048).toBase64();
        this.passwordSalt = salt.getBytes();
        return this;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public User setPasswordSaltR(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
        return this;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User setPhoneR(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public Set<Group> getGroups() {
        return groups;
    }

    @Override
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public User setGroupsR(Set<Group> groups) {
        this.groups = groups;
        return this;
    }

    @Override
    public Set<Role> getRoles() {
        return this.roles;
    }

    @Override
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User setRolesR(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Set<UserPreference> getPreferences() {
        return preferences;
    }

    public void setPreferences(Set<UserPreference> preferences) {
        this.preferences = preferences;
    }

    public User setPreferencesR(Set<UserPreference> preferences) {
        this.preferences = preferences;
        return this;
    }

    public User clone() {
        return new User().setIdR(this.id).setVersionR(this.version).setUserNameR(this.userName).setFirstNameR(this.firstName).setLastNameR(this.lastName).
                          setEmailR(this.email).setPhoneR(this.phone).setPasswordR(this.password).setPasswordSaltR(this.passwordSalt).
                          setGroupsR(new HashSet<Group>(this.groups)).setRolesR(new HashSet<Role>(this.roles)).setPreferencesR(new HashSet<UserPreference>(this.preferences));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (!id.equals(user.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}