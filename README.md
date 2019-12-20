# DynamicGridLayout
Android原生开发通过服务端下发JSON数据实现动态网格布局示例

### 效果预览

![](/screenshots/screenshot1.jpg)   
![](/screenshots/screenshot2.jpg)   

### 数据结构示例

```java
DynamicAreaEntity data = new Gson().fromJson(json, DynamicAreaEntity.class);
DynamicGridUtils.renderDynamicArea(containerView, data);
         
```

```json
{
  "item1": {
    "enable": false
  },
  "item2": {
    "enable": true,
    "background": "file:///android_asset/bg1.jpg",
    "columnCount": 120,
    "rowCount": 69,
    "indexes": [
      {
        "startX": 0,
        "startY": 0,
        "columnSpan": 60,
        "rowSpan": 69,
        "image": "file:///android_asset/test1.jpg",
        "click": "liyujiang://route?page=1"
      },
      {
        "startX": 61,
        "startY": 0,
        "columnSpan": 59,
        "rowSpan": 34,
        "image": "file:///android_asset/test2.jpg",
        "click": "liyujiang://route?page=2"
      },
      {
        "startX": 61,
        "startY": 35,
        "columnSpan": 59,
        "rowSpan": 34,
        "image": "file:///android_asset/test3.jpg",
        "click": "liyujiang://route?page=3"
      }
    ]
  },
  "item3": {
    "enable": true,
    "background": "file:///android_asset/bg2.jpg",
    "columnCount": 120,
    "rowCount": 80,
    "indexes": [
      {
        "startX": 0,
        "startY": 1,
        "columnSpan": 60,
        "rowSpan": 27,
        "image": "file:///android_asset/test4.jpg",
        "click": "liyujiang://route?page=4"
      },
      {
        "startX": 61,
        "startY": 1,
        "columnSpan": 59,
        "rowSpan": 27,
        "image": "file:///android_asset/test5.jpg",
        "click": "liyujiang://route?page=5"
      },
      {
        "startX": 0,
        "startY": 28,
        "columnSpan": 40,
        "rowSpan": 52,
        "image": "file:///android_asset/test6.jpg",
        "click": "liyujiang://route?page=6"
      },
      {
        "startX": 41,
        "startY": 28,
        "columnSpan": 50,
        "rowSpan": 52,
        "image": "file:///android_asset/test7.jpg",
        "click": "liyujiang://route?page=7"
      },
      {
        "startX": 92,
        "startY": 28,
        "columnSpan": 28,
        "rowSpan": 52,
        "image": "file:///android_asset/test8.jpg",
        "click": "liyujiang://route?page=8"
      }
    ]
  }
}
```
