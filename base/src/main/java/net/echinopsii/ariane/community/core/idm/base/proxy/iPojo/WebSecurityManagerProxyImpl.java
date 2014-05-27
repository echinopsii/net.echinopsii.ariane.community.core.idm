/**
 * IDM base bundle
 * Web Security Manager Proxy iPojo impl
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
package net.echinopsii.ariane.community.core.idm.base.proxy.iPojo;

import net.echinopsii.ariane.community.core.idm.base.proxy.IDMJPAProvider;
import net.echinopsii.ariane.community.core.idm.base.proxy.WebSecurityManagerProxy;
import net.echinopsii.ariane.community.core.idm.base.realms.JPARealm;
import org.apache.felix.ipojo.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.config.Ini;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * This service target is the Shiro system sharing accross CC components.<br/><br/>
 *
 * This is the iPojo implementation of {@link net.echinopsii.ariane.community.core.idm.base.proxy.WebSecurityManagerProxy}. The component is instantiated at commons-services bundle startup.
 * It provides the {@link net.echinopsii.ariane.community.core.idm.base.proxy.WebSecurityManagerProxy} service.
 */
@Component
@Provides
@Instantiate
public class WebSecurityManagerProxyImpl extends JPARealm implements WebSecurityManagerProxy {

    private static final String SECURITY_MANAGER_PROXY_SERVICE_NAME = "Ariane IDM Security Manager Proxy";
    private static final String SHIRO_BASE_INI_FILE_PATH = "shiro.ini";
    private static final Logger log = LoggerFactory.getLogger(WebSecurityManagerProxyImpl.class);

    private WebIniSecurityManagerFactory factory;
    private WebSecurityManager securityManager;

    @Override
    @Bind
    public void bindIdmjpaProvider(IDMJPAProvider jprovider) {
        log.debug("Bound to IDM JPA provider...");
        setIdmjpaProvider(jprovider);
    }

    @Override
    @Unbind
    public void unbindIdmjpaProvider() {
        log.debug("Unbound from IDM JPA provider...");
        setIdmjpaProvider(null);
    }

    private static Ini convertPathToIni() {
        Ini ini = null;
        if (StringUtils.hasText(SHIRO_BASE_INI_FILE_PATH)) {
            InputStream is = new WebSecurityManagerProxyImpl().getClass().getResourceAsStream("/" + SHIRO_BASE_INI_FILE_PATH);
            if (is != null) {
                ini = new Ini();
                ini.load(is);
            } else {
                throw new ConfigurationException("Unable to load resource path '" + SHIRO_BASE_INI_FILE_PATH + "'");
            }
        }
        return ini;
    }

    @Validate
    public void validate(){
        factory = new WebIniSecurityManagerFactory(convertPathToIni());
        securityManager = (WebSecurityManager)factory.getInstance();

        ((DefaultWebSecurityManager)securityManager).setSessionManager(new DefaultWebSessionManager());
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-512");
        matcher.setStoredCredentialsHexEncoded(false);
        matcher.setHashIterations(2048);
        this.setCredentialsMatcher(matcher);
        ((DefaultWebSecurityManager)securityManager).setRealm(this);

        Cookie cookie = new SimpleCookie();
        cookie.setHttpOnly(true);
        cookie.setName("CC_SSO");
        cookie.setPath("/");
        // TODO: problems with rememberMe... to be investigating
        //CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        //rememberMeManager.setCookie(cookie);
        //((DefaultWebSecurityManager) securityManager).setRememberMeManager(rememberMeManager);
        ((DefaultWebSessionManager)((DefaultWebSecurityManager)securityManager).getSessionManager()).setSessionIdCookie(cookie);
        //((DefaultWebSessionManager)((DefaultWebSecurityManager)securityManager).getSessionManager()).setSessionIdCookie(rememberMeManager.getCookie());
        ((DefaultWebSessionManager)((DefaultWebSecurityManager)securityManager).getSessionManager()).setSessionIdCookieEnabled(true);

        SecurityUtils.setSecurityManager(securityManager);
        log.info("{} is started", new Object[]{SECURITY_MANAGER_PROXY_SERVICE_NAME});
    }

    @Invalidate
    public void invalidate(){
        log.info("{} is stopped", new Object[]{SECURITY_MANAGER_PROXY_SERVICE_NAME});
    }

    @Override
    public WebSecurityManager getWebSecurityManager() {
        return securityManager;
    }

    @Override
    public WebIniSecurityManagerFactory getWebIniSecurityManagerFactory() {
        return factory;
    }
}