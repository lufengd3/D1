# API

## Component

### appicon
```js
<appicon name={'com.android.phone'} style={width: 90; height: 90} />
```

## Module

### packageManager
```js
const pkgManager = require('@weex-module/packageManager');

/**
 * @param {String} appType
 * @return {Array<appItem>}
 */
pkgManager.getApps('all');
pkgManager.getApps('important');

/**
 * @param {String} packageName
 */
pkgManager.showAppInfo('com.android.phone');
pkgManager.runApp('com.android.phone');
pkgManager.uninstallApp('com.android.phone');
```

### wallpaper
```js
const wallpaper = require('@weex-module/wallpaper');


/**
 * @param {String} url
 */
wallpaper.update('https://images.unsplash.com/flagged/photo-1554992369-085dc418ee00');
```

### vibrator
```js
const vibrator = require('@weex-module/vibrator');

/**
 * @param {Number} milliseconds
 */
vibrator.run(60);
```

### permissionManager
```js
const permissionManager = require('@weex-module/permissionManager');

/**
 * @param {String} permission
 */
permissionManager.check('useage');
permissionManager.request('useage');
```

### systemStatus
```js
const systemStatus = require('@weex-module/systemStatus');

/**
 * @return {Object}
 */
const status = systemStatus.getHardwareStatus();
const {
  availableMemory,
  totalMemory,
  availableStorage,
  totalStorage
} = status;
```
