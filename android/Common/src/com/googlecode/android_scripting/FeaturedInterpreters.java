// Copyright 2010 Google Inc. All Rights Reserved.

package com.googlecode.android_scripting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeaturedInterpreters {
  private static final Map<String, FeaturedInterpreter> mNameMap =
      new HashMap<String, FeaturedInterpreter>();
  private static final Map<String, FeaturedInterpreter> mExtensionMap =
      new HashMap<String, FeaturedInterpreter>();

  static {
    try {
      FeaturedInterpreter interpreters[] =
          {
            new FeaturedInterpreter("BeanShell 2.0b4", ".bsh",
                "http://android-scripting.googlecode.com/files/beanshellforandroid.apk"),
            new FeaturedInterpreter("JRuby-1.4", ".rb",
                "http://android-scripting.googlecode.com/files/jrubyforandroid.apk"),
            new FeaturedInterpreter("Lua 5.1.4", ".lua",
                "http://android-scripting.googlecode.com/files/luaforandroid.apk"),
            new FeaturedInterpreter("Perl 5.10.1", ".pl",
                "http://android-scripting.googlecode.com/files/perlforandroid.apk"),
            new FeaturedInterpreter("Python 2.6.2", ".py",
                "http://android-scripting.googlecode.com/files/pythonforandroid.apk"),
            new FeaturedInterpreter("Rhino 1.7R2", ".js",
                "http://android-scripting.googlecode.com/files/rhinoforandroid.apk") };

      for (FeaturedInterpreter interpreter : interpreters) {
        mNameMap.put(interpreter.mmName, interpreter);
        mExtensionMap.put(interpreter.mmExtension, interpreter);
      }

    } catch (MalformedURLException e) {
      Log.e(e);
    }

  }

  public static List<String> getList() {
    ArrayList<String> list = new ArrayList<String>(mNameMap.keySet());
    Collections.sort(list);
    return list;
  }

  public static URL getUrlForName(String name) {
    if (!mNameMap.containsKey(name)) {
      return null;
    }
    return mNameMap.get(name).mmUrl;
  }

  public static String getInterpreterNameForScript(String fileName) {
    String extension = getExtension(fileName);
    if (extension == null || !mExtensionMap.containsKey(extension)) {
      return null;
    }
    return mExtensionMap.get(extension).mmName;
  }

  public static boolean isSupported(String fileName) {
    String extension = getExtension(fileName);
    return (extension != null) && (mExtensionMap.containsKey(extension));
  }

  private static String getExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');
    if (dotIndex == -1) {
      return null;
    }
    return fileName.substring(dotIndex);
  }

  private static class FeaturedInterpreter {
    private final String mmName;
    private final String mmExtension;
    private final URL mmUrl;

    private FeaturedInterpreter(String name, String extension, String url)
        throws MalformedURLException {
      mmName = name;
      mmExtension = extension;
      mmUrl = new URL(url);
    }
  }

}