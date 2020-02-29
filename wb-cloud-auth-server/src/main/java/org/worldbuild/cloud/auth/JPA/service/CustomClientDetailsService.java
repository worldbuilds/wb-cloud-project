package org.worldbuild.cloud.auth.JPA.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.worldbuild.cloud.auth.constants.GrantType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Log4j2
@Service("customClientDetailService")
public class CustomClientDetailsService implements ClientDetailsService {

    private final static String CLIENT_ID = "wbclient";
    private final static String SECRET_KEY = "wbclient";
    private final static List<String> SCOPES = new ArrayList<>();
    private final static List<String> GRANTS = new ArrayList<>();
    private final static Map<String, Object> INFOS = new LinkedHashMap<>();

    @Autowired
    @Qualifier(value = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
       /*Stream.of(Permission.values()).forEach(p->{
            SCOPES.add(p.name());
        });*/
        Stream.of(GrantType.values()).forEach(g->{
            GRANTS.add(g.getName());
        });
        INFOS.put("client_number","9696848127");
        INFOS.put("client_email","wbadmin@worldbuild.com");
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        log.debug("Client Detail called-"+clientId);
        if (clientId.equals(CLIENT_ID)) {
            BaseClientDetails clientDetails = new BaseClientDetails();
            clientDetails.setClientId(CLIENT_ID);
            clientDetails.setClientSecret(passwordEncoder.encode(SECRET_KEY));
            clientDetails.setScope(SCOPES);
            clientDetails.setAuthorizedGrantTypes(GRANTS);
            clientDetails.setAdditionalInformation(INFOS);
            clientDetails.setAccessTokenValiditySeconds(60*60); // Access token is only valid for 1 days.
            clientDetails.setRefreshTokenValiditySeconds(24*60* 60); // Refresh token is only valid for 1 hours.
            log.debug(clientDetails);
            return clientDetails;
        } else {
            log.debug("Client does not exists- "+clientId);
            throw new NoSuchClientException("Client does not exists : " + clientId);
        }

    }
}
