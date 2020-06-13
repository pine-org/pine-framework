package com.pineframework.core.spring.restapi.helper;

import com.pineframework.core.business.exception.AbstractException;
import com.pineframework.core.business.exception.CoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static java.util.Objects.nonNull;

/**
 * Help to programmer for easy use of message source
 *
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@Component
public final class I18nHelper {

    @Autowired
    @Qualifier("messageSource")
    private MessageSource messageSource;

    /**
     * Get equivalent of message in default locale
     *
     * @param code
     * @param args
     * @return string
     */
    public String getMessage(String code, Object... args) {

        String[] strings = null;

        if (nonNull(args))
            strings = Arrays.stream(args).map(o -> getMessage(String.valueOf(o))).toArray(String[]::new);

        try {
            return messageSource.getMessage(code, strings, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return code;
        }
    }

    public CoreException toLocale(AbstractException e) {
        return new CoreException(getMessage(e.getMessage(), e.getArgs()), e.getCode());
    }

}
