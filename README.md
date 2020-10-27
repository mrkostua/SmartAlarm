<p align="center"><img src="https://github.com/mrkostua/SmartAlarm/blob/master/app/src/main/res/drawable/smart_alarm_text_logo.png" alt="SmartAlarm" height="100px"></p>


### Description
  Fully working alarm clock tested from API 16 -> API 28.
Simple rule - only one alarm can be set at time. Contains basic features for typical alarm clock with simple animated task in the end to stop the alarm (which prevents user from stopping  alarm in the first 2 seconds and gives some time to wake up).
#### Google Play link: [SmartAlarm](https://play.google.com/store/apps/details?id=com.mrkostua.mathalarm)
### Functionality 
| Name | Description |
| ------------- | ------------- |
|Show alarm activity above the lock screen|Wake up system using PartialWakeLock from background service and enabling few window flags .|
|option set time|Simple fragment with TimePicker to set time and textView to inform about remaining time to sleep.|
|option set ringtone|Fragment with RecycleView populated with custom layout(play/pause button), MediaPlayer, RingtoneManager. |
|option set text message|Just scrollable EditText to type some message for morning  (reminder, motivation to wake up).|
|option set deepSleep WakeUp| Plays some calm music in the background and slowly increase its volume before starting main alarm ringtone.|
|drag-drop animation task to stop alarm| 5 numbers <=10 dynamically added to layout with random layoutParams (top and left margin from calculated bounds), algorithm to generate random values with bounds, borders(not overlap each other) returns result from AsyncTask.

### Used technologies:
  * main languages:
    * Kotlin
    * Java (10%)
    * Xml
  * architecture:
    * Dagger 2
    * MVP (70%)
  * additional :
    * RecycleView
    * DataBinding / MVVM
    * Material Design
  * testing:
    * JUnit
    * Mockito
    * Robolectric
    * Espresso
    
    
### App screenshots: 
  <img src="https://github.com/mrkostua/SmartAlarm/blob/master/readMe_screen_shot.png" alt="smartAlarm app screenshots"/>
  
### Future releases plans:
  * [Perform remote actions in case of not stopping alarm or others scenarios](https://github.com/mrkostua/SmartAlarm/issues/4)
  * [Adding ringtone from an external path](https://github.com/mrkostua/SmartAlarm/issues/15)
  * [To create the amazing design]
  * [To do something with UI/UX]
  * [DO SOMETHING WITH THIS!]
  
