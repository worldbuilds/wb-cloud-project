package org.worldbuild.cloud.auth.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.worldbuild.cloud.auth.utils.TOTPUtils;

@Log4j2
@Service
public class TOTPServiceImpl implements TOTPService {

    @Override
    public boolean verifyCode(String totpCode, String secret) {
        String totpCodeBySecret = generateTotpBySecret(secret);
        log.info("User code- "+totpCode+" System code- "+totpCodeBySecret);
        return totpCodeBySecret.equals(totpCode);
    }

    private String generateTotpBySecret(String secret) {
        //
        long timeFrame = System.currentTimeMillis() / 1000L / 30;
        log.info("Getting current timestamp representing 30 seconds time frame-"+timeFrame);
        //
        String timeEncoded = Long.toHexString(timeFrame);
        log.info("Encoding time frame value to HEX string- "+timeEncoded);
        String totpCodeBySecret;
        try {
            log.info("Secrete key- "+secret);
            //  requred by TOTP generator which is used here.
            String secretEncoded = String.copyValueOf((char[]) new Hex().encode(secret));
            log.info("Encoding given secret string to HEX string -"+secretEncoded);
            // Generating TOTP by given time and secret - using TOTP algorithm implementation provided by IETF.
            totpCodeBySecret = TOTPUtils.generateTOTP256(secretEncoded, timeEncoded, "6");
            log.info("HmacSHA256- "+totpCodeBySecret);
            totpCodeBySecret = TOTPUtils.generateTOTP512(secretEncoded, timeEncoded, "6");
            log.info("HmacSHA512- "+totpCodeBySecret);
            totpCodeBySecret = TOTPUtils.generateTOTP(secretEncoded, timeEncoded, "6");
            log.info("HmacSHA1- "+totpCodeBySecret);
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }
        return totpCodeBySecret;
    }

}
