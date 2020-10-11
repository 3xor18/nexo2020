package com.nexo.app.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.nexo.app.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.nexo.app.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.nexo.app.domain.User.class.getName());
            createCache(cm, com.nexo.app.domain.Authority.class.getName());
            createCache(cm, com.nexo.app.domain.User.class.getName() + ".authorities");
            createCache(cm, com.nexo.app.domain.Persona.class.getName());
            createCache(cm, com.nexo.app.domain.Direccion.class.getName());
            createCache(cm, com.nexo.app.domain.Categoria.class.getName());
            createCache(cm, com.nexo.app.domain.Producto.class.getName());
            createCache(cm, com.nexo.app.domain.ProductoCategoria.class.getName());
            createCache(cm, com.nexo.app.domain.CostoDelivery.class.getName());
            createCache(cm, com.nexo.app.domain.Pais.class.getName());
            createCache(cm, com.nexo.app.domain.Region.class.getName());
            createCache(cm, com.nexo.app.domain.Comuna.class.getName());
            createCache(cm, com.nexo.app.domain.Carrito.class.getName());
            createCache(cm, com.nexo.app.domain.CarritoProducto.class.getName());
            createCache(cm, com.nexo.app.domain.UnidadMedida.class.getName());
            createCache(cm, com.nexo.app.domain.ProductoImpuestos.class.getName());
            createCache(cm, com.nexo.app.domain.ProductoImagenes.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
