package com.pineframework.core.helper;

import com.pineframework.core.helper.asset.ClassAnnotatedWithWebServlet;
import org.junit.jupiter.api.Test;

import javax.servlet.annotation.WebServlet;
import java.util.Set;

import static com.pineframework.core.helper.ReflectionUtils.getClassesByAnnotation;
import static org.junit.Assert.assertArrayEquals;

class ReflectionUtilsTest {

    @Test
    void getClassByAnnotation_WithPackageAsFirstParamAndNullAsSecondParam_ReturnSetOfClass() {
        String packageName = "com.pineframework.core.helper";
        Set<Class<?>> classes = getClassesByAnnotation(packageName, WebServlet.class);
        assertArrayEquals(new Class[]{ClassAnnotatedWithWebServlet.class}, classes.toArray(new Class[0]));
    }

}