/**
 * IDM base bundle
 * Permission model interface
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

package net.echinopsii.ariane.core.idm.base.model;

/**
 * Ariane IDM permission interface
 */
public interface IPermission<R> {

    /**
     * get the permission name
     * @return permission name
     */
    public String getName();

    /**
     * set the permission name
     * @param name (must be unique)
     */
    public void setName(String name);

    /**
     * get the permission description
     * @return the permission description
     */
    public String getDescription();

    /**
     * set the permission description
     * @param description
     */
    public void setDescription(String description);

    /**
     * get the resource binded to this permission
     * @return the permission's resource
     */
    public R getResource();

    /**
     * set the resource to bind to this permission. Permission MUST be binded to a resource
     * @param resource (not null)
     */
    public void setResource(R resource);
}