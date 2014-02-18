/**
 * IDM commons services
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

package com.spectral.cc.core.idm.commons.proxy;

import org.osgi.framework.Bundle;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * The IDM JPA provider provide tools to create EntityManager for the cc-idm persistence unit. It also add a feature to extend the cc-idm persistance unit through CC plugins. <br/><br/>
 * To make work this feature you must have the spectral hibernate distribution which enables this feature.<br/>
 *
 * @see <a href="https://github.com/mffrench/hibernate-orm/tree/4.3.0.Final.spectral">spectral hibernate distribution</a>
 *
 */
public interface IDMJPAProvider {
    /**
     * Create entity manager for cc-idm pu
     *
     * @return entity manager for cc-idm pu
     */
    public EntityManager createEM();

    /**
     * Add a persistence bundle to cc-idm pu
     *
     * @param persistenceBundle
     */
    public void addSubPersistenceBundle(Bundle persistenceBundle);
}