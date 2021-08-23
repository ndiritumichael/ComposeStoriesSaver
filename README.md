<h1 align="center">Compose Stories Saver</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
 
 
</p>

<p align="center">  
Compose Stories Saver is a demo application based on the new Declarative UI toolkit for  designing native UI in Android. It displays recently viewed whatsapp stories and allows for the ability to share without Screenshotting or saving the media to your phone.It also includes the option to save the status if need be. 
</p>
</br>



<p align="center">
<img src="/previews/list.jpeg" width = "400"/>
<img src = "/previews/storyVideo.jpeg" width = "400"/>
</p>


## How to build on your environment
Add your whatsapp status directory in a constants file
```xml
Whatsapp="WhatsApp/Media/.Statuses"
FMWhatsapp="FMWhatsApp/Media/.Statuses"
GBWhatsapp="GBWhatsApp/Media/.Statuses"

```

<img src="/previews/preview0.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) 

- [JetPack](https://developer.android.com/jetpack)
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)


- [Accompanist](https://github.com/google/accompanist) - A collection of extension libraries for Jetpack Compose described below
  - [Accompanist-Pager](https://google.github.io/accompanist/pager/) A ViewPager-like library which provides paging layouts for Jetpack Compose.
  - [Accompanist-Navigation Animation](https://google.github.io/accompanist/navigation-animation) for providing navigation Animations e.g Pop in ,Pop Out, in Compose Navigation


- [Coil](https://coil-kt.github.io/coil/) An image loading library for Android backed by Kotlin Coroutines.
- [ExoPlayer](https://exoplayer.dev/) A media player for playing audio and video both locally and over the Internet.
    


## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/ndiritumichael/ComposeStoriesSaver/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/ndiritumichael/)__ me for more samples

# License
```xml


Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

