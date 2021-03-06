/**
 * IDM base bundle
 * Web Security Manager Proxy Interface
 * Copyright (C) 2013 Mathilde Ffrench
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

import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.mgt.WebSecurityManager;

/**
 * This service target is the Shiro system sharing across Ariane components.
 */
public interface WebSecurityManagerProxy {
    /**
     * Get the instantiated Shiro web security manager
     * @return
     */
    public WebSecurityManager           getWebSecurityManager() ;

    /**
     * Get the instantiated Shiro Web ini security manager factory
     * @return
     */
    public WebIniSecurityManagerFactory getWebIniSecurityManagerFactory();
}
