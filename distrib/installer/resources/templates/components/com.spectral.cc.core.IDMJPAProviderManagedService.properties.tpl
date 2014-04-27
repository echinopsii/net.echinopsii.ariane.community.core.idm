# - hibernate h2 base configuration
hibernate.connection.driver_class=##hibernateDriverClass
hibernate.dialect=##hibernateDialect
hibernate.connection.url=##hibernateConnectionURL
hibernate.connection.username=##hibernateConnectionUsername
hibernate.connection.password=##hibernateConnectionPassword
hibernate.c3p0.min_size=5
hibernate.c3p0.max_size=40
hibernate.c3p0.timeout=300
hibernate.c3p0.max_statements=50
hibernate.c3p0.idle_test_period=100
hibernate.cache.use_second_level_cache=true
hibernate.cache.use_query_cache=true
hibernate.cache.region.factory_class=org.hibernate.cache.infinispan.InfinispanRegionFactory
hibernate.cache.infinispan.cfg=##hibernateCacheInfinispanCfg