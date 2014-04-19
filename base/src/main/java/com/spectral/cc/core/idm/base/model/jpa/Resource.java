/**
 * IDM base bundle
 * Resource JPA impl
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

import com.spectral.cc.core.idm.base.model.IResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@XmlRootElement
@Table(name="resource", uniqueConstraints = @UniqueConstraint(columnNames = {"resourceName"}))
public class Resource implements IResource<Permission>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(name="resourceName", unique=true)
    @NotNull
    private String name;

    @Column
    private String description;

    @OneToMany
    private Set<Permission> permissions = new HashSet<Permission>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Resource setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Resource setVersionR(int version) {
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

    public Resource setNameR(String name) {
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

    public Resource setDescriptionR(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Resource setPermissionsR(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Resource clone() {
        return new Resource().setIdR(this.id).setVersionR(this.version).setNameR(this.name).setDescriptionR(this.description).setPermissionsR(new HashSet<Permission>(this.permissions));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource resource = (Resource) o;

        if (!id.equals(resource.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}