package com.alura.api_rest.infra.annotations.aspect;

import com.alura.api_rest.infra.annotations.NormalizeStrings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static com.alura.api_rest.app.utils.AppUtils.removeAccentsFromWords;

@Aspect
@Component
public class NormalizationAspect {

  @Before("execution(@NormalizeString * *(..))")
  public void normalizarCampos(Object obj) throws IllegalAccessException {
    if (obj != null && obj.getClass().isAnnotationPresent(NormalizeStrings.class)) {
      normalizeClassFields(obj);
    }
  }

  private void normalizeClassFields(Object object) throws IllegalAccessException {

    Field[] fields = object.getClass().getDeclaredFields();

    for (Field field : fields) {
      if (String.class.equals(field.getType())) {
        field.setAccessible(true);
        var valor = (String) field.get(object);
        var normalizerValue = removeAccentsFromWords(valor);
        field.set(object, normalizerValue);
      }
    }
  }
}
