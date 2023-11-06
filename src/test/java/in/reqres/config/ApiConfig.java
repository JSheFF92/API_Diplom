package in.reqres.config;

import org.aeonbits.owner.Config;

@ApiConfig.Sources({
        "classpath:config.properties"
})
public interface ApiConfig extends Config {

        @Key("baseUri")
        String BaseUri();

        @Key("basePath")
        String BasePath();
}
