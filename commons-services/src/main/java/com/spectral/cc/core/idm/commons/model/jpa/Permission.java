/**
 * IDM Commons Services bundle
 * Permission JPA impl
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

package com.spectral.cc.core.idm.commons.model.jpa;

import com.spectral.cc.core.idm.commons.model.IPermission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name="permission", uniqueConstraints = @UniqueConstraint(columnNames = {"permissionName"}))
public class Permission implements IPermission<Resource>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(name="permissionName", unique=true)
    @NotNull
    private String name;

    @Column
    private String description;

    @ManyToOne
    @NotNull
    private Resource resource;

    @ManyToMany
    private Set<Role> roles = new HashSet<Role>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Permission setVersionR(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Permission setNameR(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Permission setDescriptionR(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Permission setResourceR(Resource resource) {
        this.resource = resource;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Permission setRolesR(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Permission clone() {
        return new Permission().setIdR(this.id).setVersionR(this.version).setNameR(this.name).setDescriptionR(this.description).setResourceR(this.resource).
                                setRolesR(new HashSet<Role>(this.roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Permission that = (Permission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}