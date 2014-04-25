/**
 * IDM base bundle
 * User Preference JPA impl
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

import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "CC_IDM_Hibernate2LC_USERPREFERENCE")
@XmlRootElement
@Table(name="preference")
public class UserPreference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id = null;

    @Version
    @Column(name = "version")
    private int version = 0;

    @Column
    @NotNull
    private String pkey;

    @Column
    @NotNull
    private String pvalue;

    @ManyToOne(fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "CC_IDM_Hibernate2LC_USERPREFERENCE.USER")
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserPreference setIdR(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public UserPreference setVersionR(int version) {
        this.version = version;
        return this;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String key) {
        this.pkey = key;
    }

    public UserPreference setPkeyR(String key) {
        this.pkey = key;
        return this;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String value) {
        this.pvalue = value;
    }

    public UserPreference setPvalueR(String value) {
        this.pvalue = value;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserPreference setUserR(User user) {
        this.user = user;
        return this;
    }

    public UserPreference clone() {
        return new UserPreference().setIdR(this.id).setVersionR(this.version).setPkeyR(this.pkey).setPvalueR(this.pvalue).setUserR(this.user.clone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPreference that = (UserPreference) o;

        if (!id.equals(that.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}