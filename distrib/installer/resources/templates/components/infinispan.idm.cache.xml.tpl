<?xml version="1.0" encoding="UTF-8"?>
<infinispan xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns="urn:infinispan:config:6.0"
            xsi:schemaLocation="urn:infinispan:config:6.0 http://www.infinispan.org/schemas/infinispan-config-6.0.xsd">
   <global>
      <globalJmxStatistics enabled="true" cacheManagerName="CC_IDM_Hibernate2LC"/>
      <transport
            transportClass="org.infinispan.remoting.transport.jgroups.JGroupsTransport"
            clusterName="CC_IDM_Hibernate2LC_Cluster"
            distributedSyncTimeout="50000">
         <!-- Note that the JGroups transport uses sensible defaults if no configuration property is defined. -->
         <properties>
            <property name="configurationFile" value="##JGroupConfPath"/>
         </properties>
         <!-- See the JGroupsTransport javadocs for more flags -->
      </transport>
   </global>

   <namedCache name="CC_IDM_Hibernate2LC_USER">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_USER.GROUPS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_USER.ROLES">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_USER.PREFERENCES">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_USERPREFERENCE">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_USERPREFERENCE.USER">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="3600000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_GROUP">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_GROUP.USERS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_GROUP.ROLES">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_ROLE">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_ROLE.PERMISSIONS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_ROLE.USERS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_ROLE.GROUPS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_PERMISSION">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_PERMISSION.RESOURCES">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_PERMISSION.ROLES">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_RESOURCE">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <namedCache name="CC_IDM_Hibernate2LC_RESOURCE.PERMISSIONS">
      <clustering mode="invalidation">
         <stateTransfer fetchInMemoryState="false" timeout="20000"/>
         <sync replTimeout="20000"/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <eviction maxEntries="10000" strategy="LRU"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
   </namedCache>

   <!-- A config appropriate for query caching. Does not replicate queries. -->
   <namedCache name="local-query">
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <!--Eviction configuration.  WakeupInterval defines how often the eviction thread runs, in milliseconds.  0 means
                    the eviction thread will never run.  A separate executor is used for eviction in each cache. -->
      <eviction maxEntries="10000" strategy="LRU"/>
      <expiration maxIdle="100000" wakeUpInterval="5000"/>
      <transaction transactionMode="NON_TRANSACTIONAL"/>
      <!--
      <transaction transactionManagerLookupClass="org.infinispan.transaction.lookup.GenericTransactionManagerLookup"
                   transactionMode="TRANSACTIONAL" autoCommit="false"
                   lockingMode="OPTIMISTIC"/>
      -->
   </namedCache>

   <!-- Optimized for timestamp caching. A clustered timestamp cache
                is required if query caching is used, even if the query cache
        itself is configured with CacheMode=LOCAL. -->
   <namedCache name="timestamps">
      <clustering mode="replication">
         <async/>
      </clustering>
      <locking isolationLevel="READ_COMMITTED" concurrencyLevel="1000"
               lockAcquisitionTimeout="15000" useLockStriping="false"/>
      <!--  Don't ever evict modification timestamps -->
      <eviction strategy="NONE"/>
      <expiration wakeUpInterval="0"/>
      <!-- Explicitly non transactional -->
      <transaction transactionMode="NON_TRANSACTIONAL"/>
      <!-- State transfer forces all replication calls to be synchronous,
                      so for calls to remain async, use a cluster cache loader instead -->
      <persistence passivation="false">
         <cluster remoteCallTimeout="20000" />
      </persistence>
   </namedCache>

</infinispan>
