/**
 * IDM base bundle
 * JPA provider impl
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

package net.echinopsii.ariane.community.core.idm.base.proxy.iPojo;

import net.echinopsii.ariane.community.core.idm.base.model.jpa.Group;
import net.echinopsii.ariane.community.core.idm.base.model.jpa.Role;
import net.echinopsii.ariane.community.core.idm.base.model.jpa.User;
import net.echinopsii.ariane.community.core.idm.base.proxy.IDMJPAProvider;
import org.apache.felix.ipojo.annotations.*;
import org.hibernate.FlushMode;
import org.hibernate.osgi.HibernateOSGiService;
import org.hibernate.osgi.OsgiScanner;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.spi.PersistenceProvider;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * The directory JPA provider provide tools to create EntityManager for the ariane-idm persistence unit. It also add a feature to extend the ariane-idm persistance unit through Ariane plugins. <br/><br/>
 * To make work this feature you must have the spectral hibernate distribution which enables this feature.<br/>
 *
 * @see <a href="https://github.com/mffrench/hibernate-orm/tree/4.3.0.Final.spectral">spectral hibernate distribution</a>
 *
 * This is the iPojo implementation of {@link IDMJPAProvider}. The component is instantiated at commons-services bundle startup.
 * It provides the {@link IDMJPAProvider} service.
 */
@Component(managedservice="net.echinopsii.ariane.community.core.IDMJPAProviderManagedService")
@Provides
@Instantiate
public class IDMJPAProviderImpl implements IDMJPAProvider {

    private static final String IDM_TXPERSISTENCE_CONSUMER_SERVICE_NAME = "Ariane IDM JPA Persistence Provider";
    private static final String IDM_TXPERSISTENCE_PERSISTENCE_UNIT_NAME = "ariane-idm";
    private static final Logger log = LoggerFactory.getLogger(IDMJPAProviderImpl.class);

    private EntityManagerFactory   sharedEMF        = null;
    private HashMap<String,Object> hibernateConf    = null;
    private OsgiScanner            hibernateScanner = new OsgiScanner(FrameworkUtil.getBundle(IDMJPAProviderImpl.class));

    private boolean isConfigurationValid = false;
    private boolean isStarted = false;

    @Requires
    private PersistenceProvider persistenceProvider = null;
    @Requires
    private HibernateOSGiService hibernateOSGiService = null;

    @Bind
    public void bindPersistenceProvider(PersistenceProvider pprovider) {
        log.debug("Bound to persistence provider...");
        persistenceProvider = pprovider;
    }

    @Unbind
    public void unbindPersistenceProvider() {
        log.debug("Unbound from persistence provider...");
        persistenceProvider = null;
    }

    @Bind
    public void bindHibernateOSGiService(HibernateOSGiService hosgi) {
        log.debug("Bound to hibernate osgi service...");
        this.hibernateOSGiService = hosgi;
    }

    @Unbind
    public void unbindHibernateOSGiService() {
        log.debug("Unbound from hibernate osgi service...");
        this.hibernateOSGiService = null;
    }

    /**
     * init in memory h2 db - for dev only
     */
    private void initH2() {
        User user = null;
        EntityManager em = this.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<User> cmpCriteria = builder.createQuery(User.class);
        Root<User> cmpRoot = cmpCriteria.from(User.class);
        cmpCriteria.select(cmpRoot).where(builder.equal(cmpRoot.<String>get("userName"), "root"));
        TypedQuery<User> cmpQuery = em.createQuery(cmpCriteria);
        try {
            user = cmpQuery.getSingleResult();
            log.debug("root user is already defined ...");
        } catch (NoResultException e) {
            log.debug("root user will be defined ...");
        } catch (Exception e) {
            throw e;
        }

        if (user == null) {
            em.getTransaction().begin();
            user = new User();
            user.setUserName("yoda");
            user.setFirstName("Yoda");
            user.setLastName("ZeOne");
            user.setEmail("yoda@echinopsii.net");
            user.setPhone("6969696969696969");
            user.setPassword("secret");
            em.persist(user);
            em.flush();
            em.getTransaction().commit();
        }
    }

    /**
     * init db if no user already defined (ie : just after install) : create yoda jedi user
     */
    private void initDB() {
        User  user  = null;
        Role  role  = null;
        Group group = null;

        EntityManager em = this.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<User> root = countCriteria.from(User.class);
        countCriteria = countCriteria.select(builder.count(root));
        int rowCount = (int) (long) em.createQuery(countCriteria).getSingleResult();

        if (rowCount == 0) {
            log.warn("No admin user has been defined during installation ! You may have some problem during installation... ");
            log.warn("Try to define a fake admin user to let you play with Ariane !");
            CriteriaQuery<Role> jediCriteria = builder.createQuery(Role.class);
            Root<Role> jediRoot = jediCriteria.from(Role.class);
            jediCriteria.select(jediRoot).where(builder.equal(jediRoot.<String>get("name"), "Jedi"));
            TypedQuery<Role> jediQuery = em.createQuery(jediCriteria);
            try {
                role = jediQuery.getSingleResult();
            } catch (NoResultException e) {
                log.warn("Jedi role has not been defined correctly ! You may have some problem during installation... ");
                return;
            } catch (Exception e) {
                throw e;
            }

            CriteriaQuery<Group> yodaGrpCriteria = builder.createQuery(Group.class);
            Root<Group> yodaGrpRoot = yodaGrpCriteria.from(Group.class);
            yodaGrpCriteria.select(yodaGrpRoot).where(builder.equal(yodaGrpRoot.<String>get("name"), "yoda"));
            TypedQuery<Group> yodaGrpQuery = em.createQuery(yodaGrpCriteria);
            try {
                group = yodaGrpQuery.getSingleResult();
            } catch (NoResultException e) {
                group = new Group().setNameR("yoda").setDescriptionR("yoda personal group");
            } catch (Exception e) {
                throw e;
            }

            user = new User().setUserNameR("yoda").setFirstNameR("Yoda").setLastNameR("ZeOne").setEmailR("yoda@echinopsii.net").setPhoneR("6969696969").setPasswordR("secret");
            em.getTransaction().begin();
            em.persist(user);
            em.persist(group);

            user.getGroups().add(group);
            user.getRoles().add(role);

            group.getUsers().add(user);

            role.getUsers().add(user);
            role.getGroups().add(group);
            em.flush();
            em.getTransaction().commit();
            log.warn("THIS IS YOUR FIRST ARIANE USAGE. A FAKE USER HAS BEEN CREATED WITH FULL RIGHTS (yoda / secret). YOU MUST AT LEAST CHANGE ITS PASSWORD OR CREATE NEW USER AND REMOVE THIS ONE !!!");
        }
    }

    private void start() {
        log.debug("Create shared entity manager factory from persistence provider {}...", persistenceProvider.toString());
        sharedEMF = persistenceProvider.createEntityManagerFactory(IDM_TXPERSISTENCE_PERSISTENCE_UNIT_NAME, hibernateConf);
        if (hibernateConf.get("hibernate.connection.driver_class")!=null && hibernateConf.get("hibernate.connection.driver_class").equals("org.h2.Driver"))
            initH2();
        else
            initDB();
    }

    @Validate
    public void validate() throws InterruptedException {
        while (persistenceProvider==null && !isConfigurationValid) {
            log.debug("Persistence provide or valid config is missing for {}. Sleep some times...", IDM_TXPERSISTENCE_PERSISTENCE_UNIT_NAME);
            Thread.sleep(10);
        }
        this.start();
        isStarted = true;
        log.info("{} is started", new Object[]{IDM_TXPERSISTENCE_CONSUMER_SERVICE_NAME});
    }

    private void stop() {
        if (sharedEMF != null) sharedEMF.close();
    }

    @Invalidate
    public void invalidate() {
        this.stop();
        isStarted = false;
        isConfigurationValid = false;
        log.info("{} is stopped", new Object[]{IDM_TXPERSISTENCE_CONSUMER_SERVICE_NAME});
    }

    @Updated
    public synchronized void updated(final Dictionary properties) {
        log.debug("{} is being updated by {}", new Object[]{IDM_TXPERSISTENCE_CONSUMER_SERVICE_NAME, Thread.currentThread().toString()});
        if (hibernateConf != null) hibernateConf.clear();
        Enumeration<String> dicEnum = properties.keys();
        while (dicEnum.hasMoreElements()) {
            if (hibernateConf==null)
                hibernateConf = new HashMap<String,Object>();
            String key = (String) dicEnum.nextElement();
            String value = (String) properties.get(key);
            log.debug("Hibernate conf to update : ({},{})", new Object[]{key, (key.equals("hibernate.connection.password") ? "*****" : value)});
            hibernateConf.put(key, value);
        }
        hibernateConf.put(org.hibernate.jpa.AvailableSettings.SCANNER, hibernateScanner);
        isConfigurationValid = true;

        if (isStarted) {
            final Runnable applyConfigUpdate = new Runnable() {
                @Override
                public void run() {
                    log.debug("{} will be restart to apply configuration changes...", IDM_TXPERSISTENCE_CONSUMER_SERVICE_NAME);
                    stop();
                    start();
                }
            };
            new Thread(applyConfigUpdate).start();
        }
    }

    @Override
    public EntityManager createEM() {
        log.debug("Create new entity manager from : \n\t{}\n\t{}\n\t{}\n\t{}\n\t{}\n\t{}\n\t{}",
                         new Object[]{
                                             (Thread.currentThread().getStackTrace().length>0) ? Thread.currentThread().getStackTrace()[0].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>1) ? Thread.currentThread().getStackTrace()[1].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>2) ? Thread.currentThread().getStackTrace()[2].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>3) ? Thread.currentThread().getStackTrace()[3].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>4) ? Thread.currentThread().getStackTrace()[4].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>5) ? Thread.currentThread().getStackTrace()[5].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length>6) ? Thread.currentThread().getStackTrace()[6].getClassName() : ""
                         });
        return sharedEMF.createEntityManager();
    }

    @Override
    public TypedQuery<?> setFlushModeManual(TypedQuery<?> query) {
        query.setHint("org.hibernate.flushMode", FlushMode.MANUAL);
        return query;
    }

    public void addSubPersistenceBundle(Bundle persistenceBundle) {
        hibernateScanner.addPersistenceBundle(persistenceBundle);
        hibernateOSGiService.addPersistenceBundle(persistenceBundle);
        log.debug("Reinit shared entity manager factory because new persistence bundle has been added {}...", persistenceBundle.getSymbolicName());
        sharedEMF.close();
        sharedEMF = persistenceProvider.createEntityManagerFactory(IDM_TXPERSISTENCE_PERSISTENCE_UNIT_NAME, hibernateConf);
    }
}