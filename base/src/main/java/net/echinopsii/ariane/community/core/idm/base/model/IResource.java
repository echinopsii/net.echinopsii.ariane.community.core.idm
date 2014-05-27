/**
 * IDM base bundle
 * Resource model interface
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

package net.echinopsii.ariane.community.core.idm.base.model;

import java.util.Set;

/**
 * Ariane IDM resource interface
 */
public interface IResource<P> {

    /**
     * Get the resource name
     * @return the resource name
     */
    public String getName();

    /**
     * Set the resource name
     * @param name (must be unique)
     */
    public void setName(String name);

    /**
     * Get the resource description
     * @return the resource description
     */
    public String getDescription();

    /**
     * Set the resource description
     * @param description
     */
    public void setDescription(String description);

    /**
     * Get binded permissions to this resource
     * @return
     */
    public Set<P> getPermissions();

    /**
     * Set binded permissions to this resources
     * @param permissions (valid resource must have at least one permission)
     */
    public void setPermissions(Set<P> permissions);

}