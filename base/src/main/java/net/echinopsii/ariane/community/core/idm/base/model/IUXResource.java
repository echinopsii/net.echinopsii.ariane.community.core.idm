/**
 * IDM base bundle
 * UX like resource model interface
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

import net.echinopsii.ariane.community.core.idm.base.model.jpa.Group;
import net.echinopsii.ariane.community.core.idm.base.model.jpa.User;

import java.util.Set;

/**
 * Ariane IDM Unix Like resource interface
 */
public interface IUXResource<P> {

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
     * Get the resource user owner
     *
     * @return the resource user owner
     */
    public User getUser();

    /**
     * Set the resource user owner
     *
     * @param user
     */
    public void setUser(User user);

    /**
     * Get the resource group owner
     *
     * @return
     */
    public Group getGroup();

    /**
     * Set the resource group owner
     *
     * @param group
     */
    public void setGroup(Group group);

    /**
     * Get the user permissions
     *
     * @return
     */
    public Set<P> getUserPermissions();

    /**
     * Set the user permissions
     *
     * @param userPermissions
     */
    public void setUserPermissions(Set<P> userPermissions);

    /**
     * Get the group permissions
     *
     * @return
     */
    public Set<P> getGroupPermissions();

    /**
     * Set the group permissions
     *
     * @param groupPermissions
     */
    public void setGroupPermissions(Set<P> groupPermissions);

    /**
     * Get other users permissions
     *
     * @return
     */
    public Set<P> getOtherPermissions();

    /**
     * Set other users permissions
     *
     * @param otherPermissions
     */
    public void setOtherPermissions(Set<P> otherPermissions);
}