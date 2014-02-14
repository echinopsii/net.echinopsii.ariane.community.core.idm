/**
 * IDM Commons Services bundle
 * User model interface
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

package com.spectral.cc.core.idm.commons.model;

import java.util.Set;

/**
 * CC IDM user interface
 */
public interface IUser<G, R> {

    /**
     * Get username (shiro principal)
     *
     * @return username
     */
    public String getUserName();

    /**
     * Set username (shiro principal)
     *
     * @param username (not null and must be unique)
     */
    public void setUserName(String username);

    /**
     * Get user first name
     *
     * @return user first name
     */
    public String getFirstName();

    /**
     * Set user first name
     *
     * @param firstname (not null)
     */
    public void setFirstName(String firstname);

    /**
     * Get user last name
     *
     * @return user last name
     */
    public String getLastName();

    /**
     * Set user last name
     *
     * @param lastname (not null)
     */
    public void setLastName(String lastname);

    /**
     * Get user email
     *
     * @return user email
     */
    public String getEmail();

    /**
     * Set user email
     *
     * @param email (not null and must be unique)
     */
    public void setEmail(String email);

    /**
     * Get user phone number
     *
     * @return user phone number
     */
    public String getPhone();

    /**
     * Set user phone number
     *
     * @param phone (not null and must be unique)
     */
    public void setPhone(String phone);

    /**
     * Get user groups
     *
     * @return user groups
     */
    public Set<G> getGroups();

    /**
     * Set user groups
     *
     * @param groups
     */
    public void setGroups(Set<G> groups);

    /**
     * Get user roles
     *
     * @return user roles
     */
    public Set<R> getRoles();

    /**
     * Set user roles
     *
     * @param roles
     */
    public void setRoles(Set<R> roles);
}