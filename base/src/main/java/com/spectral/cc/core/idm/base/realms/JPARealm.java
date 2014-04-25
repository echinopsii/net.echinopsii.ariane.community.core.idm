/**
 * IDM base bundle
 * JPA Shiro Realm
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

package com.spectral.cc.core.idm.base.realms;

import com.spectral.cc.core.idm.base.model.jpa.Group;
import com.spectral.cc.core.idm.base.model.jpa.Permission;
import com.spectral.cc.core.idm.base.model.jpa.Role;
import com.spectral.cc.core.idm.base.model.jpa.User;
import com.spectral.cc.core.idm.base.proxy.IDMJPAProvider;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

public abstract class JPARealm  extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(JPARealm.class);

    IDMJPAProvider idmjpaProvider;

    abstract public void bindIdmjpaProvider(IDMJPAProvider jprovider);

    abstract public void unbindIdmjpaProvider();

    public void setIdmjpaProvider(IDMJPAProvider idmjpaProvider) {
        this.idmjpaProvider = idmjpaProvider;
    }

    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null)
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");

        String username = (String)getAvailablePrincipal(principalCollection);

        User user = null;
        Set<String> roles       = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();

        EntityManager em = idmjpaProvider.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> cmpCriteria = builder.createQuery(User.class);
        Root<User> cmpRoot = cmpCriteria.from(User.class);
        cmpCriteria.select(cmpRoot).where(builder.equal(cmpRoot.<String>get("userName"), username));
        TypedQuery<User> cmpQuery = em.createQuery(cmpCriteria);
        cmpQuery.setHint("org.hibernate.cacheable", true);
        try {
            user = cmpQuery.getSingleResult();
        } catch (NoResultException e) {
            log.debug("Close entity manager ...");
            em.close();
            throw new AccountException("No user account found with username " + username + " in this realm...");
        } catch (Exception e) {
            log.debug("Close entity manager ...");
            em.close();
            throw new AuthenticationException("A problem occured while getting user account with " + username + " in this realm : " + e.getMessage());
        }

        for (Group group : user.getGroups()) {
             for (Role role : group.getRoles()) {
                 roles.add(role.getName());
                 for (Permission permission : role.getPermissions())
                     permissions.add(permission.getName());
             }
        }

        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission permission : role.getPermissions())
                permissions.add(permission.getName());
        }

        log.debug("Close entity manager ...");
        em.close();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    @Override
    public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (idmjpaProvider == null)
            throw new AuthenticationException("JPA provider is not ready for authentication against this realm !");

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        log.debug("get authent info for user {}", username);

        if (username == null)
            throw new AccountException("Null username are not allowed by this realm.");

        SimpleAuthenticationInfo info = null;

        User user = null;
        EntityManager em = idmjpaProvider.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> cmpCriteria = builder.createQuery(User.class);
        Root<User> cmpRoot = cmpCriteria.from(User.class);
        cmpCriteria.select(cmpRoot).where(builder.equal(cmpRoot.<String>get("userName"), username));
        TypedQuery<User> cmpQuery = em.createQuery(cmpCriteria);
        cmpQuery.setHint("org.hibernate.cacheable", true);
        try {
            user = cmpQuery.getSingleResult();
            log.debug("User {} found !", user.getUserName());
        } catch (NoResultException e) {
            log.debug("Close entity manager ...");
            em.close();
            throw new AccountException("No user account found with username " + username + " in this realm...");
        } catch (Exception e) {
            log.debug("Close entity manager ...");
            em.close();
            throw new AuthenticationException("A problem occured while getting user account with " + username + " in this realm : " + e.getMessage());
        }

        log.debug("Close entity manager ...");
        em.close();

        info = new SimpleAuthenticationInfo(username, user.getPassword().toCharArray(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getPasswordSalt()));
        return info;
    }
}