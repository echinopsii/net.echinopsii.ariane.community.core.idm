/**
 * [DEFINE YOUR PROJECT NAME/MODULE HERE]
 * [DEFINE YOUR PROJECT DESCRIPTION HERE] 
 * Copyright (C) 6/17/14 echinopsii
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

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "ariane.core.idm.cache.uxpermission")
@XmlRootElement
@Table(name="uxpermission", uniqueConstraints = @UniqueConstraint(columnNames = {"permissionName"}))
public class UXPermission {

    @Transient
    public static final String UX_LIKE_RD_PERM = "READ";
    @Transient
    public static final String UX_LIKE_WR_PERM = "WRITE";
    @Transient
    public static final String UX_LIKE_CH_PERM = "CHPERM";

    @Transient
    public static final String UX_LIKE_U_ACTOR_TYPE = "USER";
    @Transient
    public static final String UX_LIKE_G_ACTOR_TYPE = "GROUP";
    @Transient
    public static final String UX_LIKE_O_ACTOR_TYPE = "OTHER";

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UXPermission setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UXPermission setVersionR(int version) {
        this.version = version;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UXPermission setNameR(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UXPermission that = (UXPermission) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public UXPermission clone() {
        return new UXPermission().setIdR(this.id).setVersionR(this.version).setNameR(this.name);
    }
}