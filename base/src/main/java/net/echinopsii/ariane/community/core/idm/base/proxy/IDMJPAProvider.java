/**
 * IDM base bundle
 * JPA provider Interface
 * Copyright (C) 31/12/13 mffrench
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

package net.echinopsii.ariane.community.core.idm.base.proxy;

import net.echinopsii.ariane.community.core.idm.base.model.jpa.UXPermission;
import org.osgi.framework.Bundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * The IDM JPA provider provide tools to create EntityManager for the ariane-idm persistence unit. It also add a feature to extend the ariane-idm persistance unit through Ariane plugins. <br/><br/>
 * To make work this feature you must have the echinopsii hibernate distribution which enables this feature.<br/>
 *
 * @see <a href="https://github.com/echinopsii/net.echinopsii.3rdparty.hibernate-orm">echinopsii hibernate distribution</a>
 *
 */
public interface IDMJPAProvider {
    /**
     * Create entity manager for ariane-idm pu
     *
     * @return entity manager for ariane-idm pu
     */
    public EntityManager createEM();

    /**
     * Set FlushMode to manual for specified query.
     *
     * @param query the query
     */
    public TypedQuery<?> setFlushModeManual(TypedQuery<?> query);

    /**
     * Add a persistence bundle to ariane-idm pu
     *
     * @param persistenceBundle
     */
    public void addSubPersistenceBundle(Bundle persistenceBundle);

    /**
     * Get UX permission from name and actorType
     *
     * @param name of ux permission to get
     * @param actorType of ux permission to get
     *
     * @return the uxpermission from name + actorType
     */
    public UXPermission getUXLikeResourcesPermissionsFromName(String name, String actorType);
}