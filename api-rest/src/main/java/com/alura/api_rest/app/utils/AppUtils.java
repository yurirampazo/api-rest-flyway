package com.alura.api_rest.app.utils;

import java.text.Normalizer;

public final class AppUtils {
  private AppUtils() {
  }

  public static String maskEmail(String email) {
    int atIndex = email.indexOf('@');
    if (atIndex == -1) return email;

    String user = email.substring(0, atIndex);
    String domain = email.substring(atIndex);

    if (user.length() <= 5) {
      return user.substring(0, 3) + "***" + domain;
    } else {
      String start = user.substring(0, 3);
      String end = user.substring(user.length() - 2);
      return start + "*****" + end + domain;
    }
  }

  public static String removeAccentsFromWords(String text) {
    String withoutAccents = Normalizer.normalize(text, Normalizer.Form.NFD);
    withoutAccents = withoutAccents.replaceAll("[^\\p{ASCII}]", "");
    withoutAccents = withoutAccents.replaceAll("[^a-zA-Z0-9 ]", "");
    return withoutAccents;
  }
}
