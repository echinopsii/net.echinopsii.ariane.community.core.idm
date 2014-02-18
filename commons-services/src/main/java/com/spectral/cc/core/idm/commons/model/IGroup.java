/**
 * IDM Commons Services bundle
 * Group model interface
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

/**
 * CC IDM group interface
 */
public interface IGroup<R> {

    /**
     * Get the group name
     *
     * @return group name
     */
    public String getName();

    /**
     * Set the group name
     *
     * @param name (must be unique)
     */
    public void setName(String name);

    /**
     * Get the group description
     *
     * @return group description
     */
    public String getDescription();

    /**
     * Set the group description
     *
     * @param description
     */
    public void setDescription(String description) ;
}