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

import net.echinopsii.ariane.community.core.idm.base.model.jpa.*;
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
import java.util.*;

/**
 * The IDM JPA provider provide tools to create EntityManager for the ariane-idm persistence unit. It also add a feature to extend the ariane-idm persistance unit through Ariane plugins. <br/><br/>
 * To make work this feature you must have the echinopsii hibernate distribution which enables this feature.<br/>
 *
 * @see <a href="https://github.com/echinopsii/net.echinopsii.3rdparty.hibernate-orm">echinopsii hibernate distribution</a>
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

    private void initUXPerms(EntityManager em) {
        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_RD_PERM, UXPermission.UX_LIKE_U_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_RD_PERM+"."+UXPermission.UX_LIKE_U_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_RD_PERM, UXPermission.UX_LIKE_G_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_RD_PERM+"."+UXPermission.UX_LIKE_G_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_RD_PERM, UXPermission.UX_LIKE_O_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_RD_PERM+"."+UXPermission.UX_LIKE_O_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_WR_PERM, UXPermission.UX_LIKE_U_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_WR_PERM+"."+UXPermission.UX_LIKE_U_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_WR_PERM, UXPermission.UX_LIKE_G_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_WR_PERM+"."+UXPermission.UX_LIKE_G_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_WR_PERM, UXPermission.UX_LIKE_O_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_WR_PERM+"."+UXPermission.UX_LIKE_O_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_CH_PERM, UXPermission.UX_LIKE_U_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_CH_PERM+"."+UXPermission.UX_LIKE_U_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_CH_PERM, UXPermission.UX_LIKE_G_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_CH_PERM+"."+UXPermission.UX_LIKE_G_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }

        try {
            getUXLikeResourcesPermissionsFromName(UXPermission.UX_LIKE_CH_PERM, UXPermission.UX_LIKE_O_ACTOR_TYPE);
        } catch (NoResultException e) {
            em.getTransaction().begin();
            UXPermission readUPerm = new UXPermission().setNameR(UXPermission.UX_LIKE_CH_PERM+"."+UXPermission.UX_LIKE_O_ACTOR_TYPE);
            em.persist(readUPerm);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.close();
            throw e;
        }
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
            em.close();
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

        initUXPerms(em);

        if (em.isOpen())
            em.close();
    }

    /**
     * init db if no user already defined (ie : just after install) : create yoda jedi user
     */
    private void initDB() {
        User  user       = null;
        Role  role       = null;
        Group group      = null;
        boolean initUser = false;

        EntityManager em = this.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
        Root<User> root = countCriteria.from(User.class);
        countCriteria = countCriteria.select(builder.count(root));
        int rowCount = (int) (long) em.createQuery(countCriteria).getSingleResult();

        if (rowCount == 0) {
            user = new User().setUserNameR("yoda").setFirstNameR("Yoda").setLastNameR("ZeOne").setEmailR("yoda@echinopsii.net").setPhoneR("6969696969").setPasswordR("secret");
            initUser = true;
        } else if (rowCount == 1) {
            CriteriaQuery<User> yodaCriteria = builder.createQuery(User.class);
            Root<User> yodaRoot = yodaCriteria.from(User.class);
            yodaCriteria.select(yodaRoot).where(builder.equal(yodaRoot.<String>get("userName"), "yoda"));
            TypedQuery<User> jediQuery = em.createQuery(yodaCriteria);

            try {
                user = jediQuery.getSingleResult();
            } catch (NoResultException e) {
                log.warn("Jedi user has not been defined correctly ! You may have some problem during installation... ");
                user = new User().setUserNameR("yoda").setFirstNameR("Yoda").setLastNameR("ZeOne").setEmailR("yoda@echinopsii.net").setPhoneR("6969696969").setPasswordR("secret");
                initUser = true;
            } catch (Exception e) {
                em.close();
                throw e;
            }

            if (user.getPasswordSalt().equals("") || user.getPasswordSalt()==null ||
                user.getPassword().equals("") || user.getPassword() == null) {
                user.setPassword("secret");
                initUser = true;
            }
        }

        if (initUser) {
            CriteriaQuery<Role> jediCriteria = builder.createQuery(Role.class);
            Root<Role> jediRoot = jediCriteria.from(Role.class);
            jediCriteria.select(jediRoot).where(builder.equal(jediRoot.<String>get("name"), "Jedi"));
            TypedQuery<Role> jediQuery = em.createQuery(jediCriteria);
            try {
                role = jediQuery.getSingleResult();
            } catch (NoResultException e) {
                log.warn("Jedi role has not been defined correctly ! You may have some problem during installation... ");
                em.close();
                return;
            } catch (Exception e) {
                em.close();
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
                em.close();
                throw e;
            }


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

        initUXPerms(em);

        if (em.isOpen())
            em.close();
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
                                             (Thread.currentThread().getStackTrace().length > 0) ? Thread.currentThread().getStackTrace()[0].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 1) ? Thread.currentThread().getStackTrace()[1].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 2) ? Thread.currentThread().getStackTrace()[2].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 3) ? Thread.currentThread().getStackTrace()[3].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 4) ? Thread.currentThread().getStackTrace()[4].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 5) ? Thread.currentThread().getStackTrace()[5].getClassName() : "",
                                             (Thread.currentThread().getStackTrace().length > 6) ? Thread.currentThread().getStackTrace()[6].getClassName() : ""
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

    @Override
    public UXPermission getUXLikeResourcesPermissionsFromName(String name, String actorType) {
        EntityManager em = this.createEM();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UXPermission> uxpermCriteria = builder.createQuery(UXPermission.class);
        Root<UXPermission> uxpermRoot = uxpermCriteria.from(UXPermission.class);
        uxpermCriteria = uxpermCriteria.select(uxpermRoot).where(builder.equal(uxpermRoot.<String>get("name"), name+"."+actorType));
        TypedQuery<UXPermission> permQuery = em.createQuery(uxpermCriteria);
        UXPermission perm = null;
        try {
            perm = permQuery.getSingleResult();
        } catch (Exception e) {
            em.close();
            throw e;
        }
        em.close();
        return perm;
    }
}