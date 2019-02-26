package com.appt.qa.common.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"classpath:${service_specific}.properties",
        "classpath:${default_env}.properties"})
public interface ClientConfiguration extends Config {

    @Key("ufo.service.url")
    String ufoServiceUrl();

}
