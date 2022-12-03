# LifeSlice

## The Repository

This repository is where I record my first work of HarmonyOS App development. Firstly, I used Axure RP to develop a rapid prototype of the electrical diary system. Then I used Java in DevEco Studio to implement the functionality of acquiring the mobile phone media resources such as camera photography, memory read/write, and geographic location, and I also built an object-relational mapping database to store data including user profile information, input diary content, and the acquired media content. 

## An electrical diary system

I named it LifeSlice, because that I treat each episode of my life as a treasure worth recording. You open the app and you watch the box roll down which contains your records, the gift of life.

<img src="picture/home.png" width="200" height="400" alt="home"/> <img src="picture/login.png" width="200" height="400" alt="login"/> <img src="picture/editor.png" width="200" height="400" alt="editor"/><br/>  
You can add records by clicking the "Add" button at the top right of the screen.The system supports the input of "title" and "content", and you can choose different emoticons as the "mood of the day". You can also access the camera to take photos or obtain geographical location after authorizing, and these operations are completely consistent with what you usually use. 

The home page list is responsible for displaying records. "Mood of the day" is used as the icon corresponding to each record, which is clear at a glance and convenient for retrieval based on it. If there are media resources, it will be displayed as "with attachments".  
The Detailed record interface displays only edited components, and provides photo and video display Windows based on user requirements. At the same time, the system uses various appearances of Button, which can be selected according to your need.

 <img src="picture/display.png" width="200" height="400" alt="display"/> <img src="picture/photo.png" width="200" height="400" alt="photo"/> <img src="picture/video.png" width="200" height="400" alt="video"/><br/> 
 
I choose to use object relational mapping database to store user information, user input and media acquisition content. It should be noted that you need to store the paths of obtained media resource to the database, not the resources itself, and decode the paths when required. In addition, the precision of the address can also be adjusted using methods under locationDetails.


## Contributing
The DevEco Studio is not as smart and convenient as Android Studio for the moment, and there are not many references, so the development process needs some gradual explorations, and that's why I want this system provides a reference for others, even though it's immaturity.
In addition, I will really appreciate if you can participate in this project. If you are interested in fixing issues and contributing ideas or functions to this system, please contact me (fablerr@163.com).


