/**
 * IDM base bundle
 * Group JPA impl
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

package net.echinopsii.ariane.community.core.idm.base.model.jpa;

import net.echinopsii.ariane.community.core.idm.base.model.IGroup;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ariane.core.idm.cache.group")
@XmlRootElement
@Table(name="ccGroup", uniqueConstraints = @UniqueConstraint(columnNames = {"groupName"}))
public class Group implements IGroup, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column(name="groupName", unique=true)
    @NotNull
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ariane.core.idm.cache.group.users")
    private Set<User> users = new HashSet<User>();

    @ManyToMany(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ariane.core.idm.cache.group.roles")
    private Set<Role> roles = new HashSet<Role>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Group setVersionR(int version) {
        this.version = version;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Group setNameR(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    public Group setDescriptionR(String description) {
        this.description = description;
        return this;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Group setUsersR(Set<User> users) {
        this.users = users;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Group setRolesR(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Group clone() {
        return new Group().setIdR(this.id).setVersionR(this.version).setNameR(this.name).setDescriptionR(description).
                           setUsersR(new HashSet<User>(users)).setRolesR(new HashSet<Role>(roles));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        if (!id.equals(group.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}