# PUCs AdPlayer

PUCs AdPlayer is a powerful yet simple-to-use library to show ads over a MediaPlayer when paused.

## Integration instructions

This project already includes a working example of PUCs AdPlayer library integration. Below are instructions how to achieve this.

### Adding the library

In Android Studio switch to Project view and create a directory `libs` in `app` (if it doesn't exists) then drag and drop `pucsadplayer-debug.aar` and `pucsadplayer-release.aar` in it:

![Project view](/images/1.png?raw=true "Android Studio Project view")

### Enabling HTTP cleartext support

Starting with Android 8 cleartext HTTP requests are disabled by default. To enable them, create file res/xml/network_security_config.xml with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">localhost</domain>
    </domain-config>
</network-security-config>
```
    
After this change your AndroidManifest.xml with the following:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ...>
    <uses-permission android:name="android.permission.INTERNET" />
    <application
        ...
        android:networkSecurityConfig="@xml/network_security_config"
        ...>
        ...
    </application>
</manifest>
```

Now, in your xml layout file add a SurfaceView:

```xml
<SurfaceView
 android:id="@+id/surface_view"
 android:layout_width="match_parent"
 android:layout_height="320dp" />
```

After you got a reference to your `SurfaceView` with `SurfaceView surfaceView = findViewById(R.id.surface_view);` instantiate an AdView with a Context instance and initialize it with a clientId and the reference to your `SurfaceView`:

```java
PUCsAdView adView = new PUCsAdView(this);
adView.initialize("", surfaceView);
```

Optionally you may want to set a dimension for the `adView` instance so it only covers part of the video:

```java
adView.transitionAdViewToSize(320, 240);
```

When you need to display ads (e.g. when video player is paused) call the following line:

```java
adView.playAds(null, null);
```

and to stop them call:

```java
adView.stopAds();
```
