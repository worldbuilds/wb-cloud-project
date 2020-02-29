package org.worldbuild.cloud.auth.service;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;
import org.worldbuild.cloud.auth.utils.TOTPUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public interface TOTPService {

    public boolean verifyCode(String totpCode, String secret);
}