package ir.myandroidapp.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Remember {

    private static final Remember INSTANCE = new Remember();

    private static final String TAG = Remember.class.getSimpleName();

    private static final Object SHARED_PREFS_LOCK = new Object();

    private volatile boolean mWasInitialized = false;

    private volatile Context mAppContext;

    private static String mSharedPrefsName;

    private ConcurrentMap<String,Object> mData;

    private Remember() {
    }

    private void initWithContext(Context context, String sharedPrefsName) {
        // Time ourselves
        long start = SystemClock.uptimeMillis();

        // Set vars
        mAppContext = context.getApplicationContext();
        mSharedPrefsName = sharedPrefsName;

        // Read from shared prefs
        SharedPreferences prefs = getSharedPreferences();
        mData = new ConcurrentHashMap<String,Object>();
        mData.putAll(prefs.getAll());
        mWasInitialized = true;

        long delta = SystemClock.uptimeMillis() - start;
        Log.i(TAG, "Remember took " + delta + " ms to init");
    }

    public static synchronized Remember init(Context context, String sharedPrefsName) {
        if (context == null || TextUtils.isEmpty(sharedPrefsName)) {
            throw new RuntimeException(
                    "You must provide a valid context and shared prefs name when initializing Remember");
        }

        if (!INSTANCE.mWasInitialized) {
            INSTANCE.initWithContext(context, sharedPrefsName);
        }

        return INSTANCE;
    }

    private static Remember getInstance() {
        if (!INSTANCE.mWasInitialized) {
            throw new RuntimeException(
                    "Remember was not initialized! You must call Remember.init() before using this.");
        }
        return INSTANCE;
    }

    private SharedPreferences getSharedPreferences() {
        return mAppContext.getSharedPreferences(mSharedPrefsName, Context.MODE_PRIVATE);
    }

    private boolean saveToDisk(final String key, final Object value) {
        boolean success = false;
        synchronized (SHARED_PREFS_LOCK) {
            // Save it to disk
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            boolean didPut = true;
            if (value instanceof Float) {
                editor.putFloat(key, (Float) value);

            } else if (value instanceof Integer) {
                editor.putInt(key, (Integer) value);

            } else if (value instanceof Long) {
                editor.putLong(key, (Long) value);

            } else if (value instanceof String) {
                editor.putString(key, (String) value);

            } else if (value instanceof Boolean) {
                editor.putBoolean(key, (Boolean) value);

            } else {
                didPut = false;
            }

            if (didPut) {
                success = editor.commit();
            }
        }

        return success;
    }

    private <T> Remember saveAsync(final String key, final T value, final Callback callback) {
        // Put it in memory
        mData.put(key, value);

        // Save it to disk
        new AsyncTask<Void,Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return saveToDisk(key, value);
            }

            @Override
            protected void onPostExecute(Boolean success) {
                // Fire the callback
                if (callback != null) {
                    callback.apply(success);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return this;
    }

    public static void clear() {
        getInstance().clear(null);
    }

    public static void clear(final Callback callback) {
        getInstance().mData.clear();
        new AsyncTask<Void,Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                synchronized (SHARED_PREFS_LOCK) {
                    SharedPreferences.Editor editor = getInstance().getSharedPreferences().edit();
                    editor.clear();
                    return editor.commit();
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    callback.apply(success);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static void remove(String key) {
        getInstance().remove(key, null);
    }

    public static void remove(final String key, final Callback callback) {
        getInstance().mData.remove(key);
        new AsyncTask<Void,Void,Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                synchronized (SHARED_PREFS_LOCK) {
                    SharedPreferences.Editor editor = getInstance().getSharedPreferences().edit();
                    editor.remove(key);
                    return editor.commit();
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (callback != null) {
                    callback.apply(success);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public static Remember putFloat(final String key, final float value) {
        return getInstance().saveAsync(key, value, null);
    }

    public static Remember putInt(String key, int value) {
        return getInstance().saveAsync(key, value, null);
    }

    public static Remember putLong(String key, long value) {
        return getInstance().saveAsync(key, value, null);
    }

    public static Remember putString(String key, String value) {
        return getInstance().saveAsync(key, value, null);
    }

    public static Remember putBoolean(String key, boolean value) {
        return getInstance().saveAsync(key, value, null);
    }

    public static Remember putFloat(final String key, final float value, final Callback callback) {
        return getInstance().saveAsync(key, value, callback);
    }

    public static Remember putInt(String key, int value, final Callback callback) {
        return getInstance().saveAsync(key, value, callback);
    }

    public static Remember putLong(String key, long value, final Callback callback) {
        return getInstance().saveAsync(key, value, callback);
    }

    public static Remember putString(String key, String value, final Callback callback) {
        return getInstance().saveAsync(key, value, callback);
    }

    public static Remember putBoolean(String key, boolean value, final Callback callback) {
        return getInstance().saveAsync(key, value, callback);
    }

    public static float getFloat(String key, float fallback) {
        Float value = getInstance().get(key, Float.class);
        return value != null ? value : fallback;
    }

    public static int getInt(String key, int fallback) {
        Integer value = getInstance().get(key, Integer.class);
        return value != null ? value : fallback;
    }

    public static long getLong(String key, long fallback) {
        Long value = getInstance().get(key, Long.class);
        return value != null ? value : fallback;
    }

    public static String getString(String key, String fallback) {
        String value = getInstance().get(key, String.class);
        return value != null ? value : fallback;
    }

    public static boolean getBoolean(String key, boolean fallback) {
        Boolean value = getInstance().get(key, Boolean.class);
        return value != null ? value : fallback;
    }

    public static boolean containsKey(String key) {
        return getInstance().mData.containsKey(key);
    }

    private <T> T get(String key, Class<T> clazz) {
        Object value = mData.get(key);
        T castedObject = null;
        if (clazz.isInstance(value)) {
            castedObject = clazz.cast(value);
        }
        return castedObject;
    }

    public interface Callback {

        void apply(Boolean success);
    }

}
