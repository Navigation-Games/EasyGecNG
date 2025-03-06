# EasyGecNG

EasyGecNG is a fork of [EasyGec](http://t.porret.free.fr/lienlogiciel.php?idmenu=60) by Thierry Porret, which is in turn a fork of [Geco](http://sdenier.github.io/Geco/begin/index.html) ([source](https://github.com/sdenier/Geco)) by Simon Denier. It has been created for [Navigation Games](https://www.navigationgames.org/), a Boston-based non-profit focused on introducing the sport of orienteering to new audiences, especially school children. Thierry has kindly given us permission to use and modify his source code.


Here is a link to a blog post on NavigationGames.org detailing goals for this project:
https://www.navigationgames.org/post/in-the-works-new-software-for-teaching-orienteering



<!-- TODO: figure out licensing oml -->
Copyright (c) 2023 Thierry Porret, Simon Denier, and Navigation Games.

GecoSI is distributed under the MIT license; some parts are released by SPORTident under the CC BY 3.0 license. 


# Normal Installation Instructions

If you are not a developer, please install EasyGecNG using these steps.

##### 1. JRE 8
  - Windows, Mac: Download and install the latest Java SE Runtime Environment from [here](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html).
  - Linux: install `jre8-openjdk` using your preferred package manager. Make sure that java 8 is your default version with `java --version`
##### 2. Drivers
  - Windows: [SportIdent Driver](https://www.sportident.com/products/96-software/161-usb-driver.html)
  - Mac: [CP210x Driver](https://www.silabs.com/developers/usb-to-uart-bridge-vcp-drivers)
  - Linux: Add yourself to the correct dialout group.
    - In a terminal (Ctrl+Alt+T), enter the command:
      - `sudo usermod -a -G dialout $USER` on debian-likes (Ubuntu, etc)
      - `sudo usermod -a -G uucp $USER` on arch-likes (Arch, Manjaro, etc)
    - Restart, then check that the `uucp/dialout` appears in the results of the `groups` command.
##### 3. Download EasyGec .Zip File
- Download the latest [release](https://github.com/Navigation-Games/EasyGecNG/releases) by clicking "**EasyGecNG-v1.1.0.zip**"
  - Alternatively, find old releases [here](https://www.navigationgames.org/about-3).
##### 4. Extract the .Zip File
- Here are instructions on [how to unzip/extract a .zip file](https://www.geeksforgeeks.org/how-to-unzip-files-in-windows-and-mac/). 
- This should create a new folder without the ".zip" in the name.
##### 5. Go into the bin folder
- Inside the new folder, open the "bin" folder.
##### 6. Double-click bin/EasyGecNG.bat
- To run EasyGec, double-click the .bat file.

Congratulations! You should now see the EasyGec app pop up.


# Developer Installation Instructions

### 1. Install Dependencies

##### 1. JDK 8
  - Windows, Mac: Download and install the latest Java SE Development Kit from [here](https://www.oracle.com/java/technologies/javase/javase8u211-later-archive-downloads.html).
  - Linux: install `jdk8-openjdk` using your preferred package manager.
> [!NOTE]
> To run this app as a developer, you _must_ use a java version between 8 and 21. Check your java version with the command `java --version`. If the listed version is not acceptable, you have two ways to fix it.
> - Uninstall all unacceptable java versions. This is the easiest, but most brute force option.
> - Set your default java version.
>   - Windows: Change the order of your PATH environment variable to list your prefered java location first. See an example guide [here](https://www.happycoders.eu/java/how-to-switch-multiple-java-versions-windows/).
>   - Debian-likes: use [update-java-alternatives](https://manpages.ubuntu.com/manpages/focal/man8/update-java-alternatives.8.html) or [update-alternatives](https://man.archlinux.org/man/update-alternatives.1.en)
>   - Arch-likes: use [archlinux-java](https://wiki.archlinux.org/title/Java#Switching_between_JVM)

##### 2. Drivers
  - Windows: [SportIdent Driver](https://www.sportident.com/products/96-software/161-usb-driver.html)
  - Mac: [CP210x Driver](https://www.silabs.com/developers/usb-to-uart-bridge-vcp-drivers)
  - Linux: The drivers have been [in the linux kernel for a while now](https://github.com/torvalds/linux/blob/master/drivers/usb/serial/cp210x.c), but you do still need to add yourself to the correct dialout group.
    - In a terminal (Ctrl+Alt+T), enter the command:
      - `sudo usermod -a -G dialout $USER` on debian-likes (Ubuntu, etc)
      - `sudo usermod -a -G uucp $USER` on arch-likes (Arch, Manjaro, etc)
    - Restart, then check that the `uucp/dialout` appears in the results of the `groups` command.


### 2. Clone the repository
`git clone git@github.com:Navigation-Games/EasyGecNG.git`

`cd EasyGecNG`

### 3. Test build
`./gradlew build`

### 4. Run the App
`./gradlew run`

### 5. That's it!
run `./gradlew tasks` to list available tasks, including running unit tests and building an output jar. Modify available tasks in `app/build.gradle`

## IDE integration

### VSCode
Simply open the correct folder in the editor. Build/run/debug from terminal, or use the following extensions to get Gradle UI buttons:
- [Language Support for Java(TM) by Red Hat](https://open-vsx.org/extension/redhat/java)
- [Gradle for Java](https://open-vsx.org/extension/vscjava/vscode-gradle)
- If you're on the Microsoft-ified version of VSCode, try [this extension pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### IntelliJ
1. Use the "New Project > Get from VCS" button. Put in the url of this git repo.
2. A popup will appear, stating "gradle build scripts found." Click `Load Gradle Project`
3. A menu should appear with the available Gradle tasks. Double click `EasyGecNG > Tasks > application > run`
4. That's it!

## Generating Documentation
TODO :)
We will use Javadoc

## Generating Releases
TODO :)


### Manually creating a Release
We need to figure out how to do this. The bundle would ideally include:
- help or doc folder with user documentation
- a jar for development version (???)
- a jar for stable release
- a distribution bundle including jar, user doc, and other files


# Development Guidelines

### CI/CD
- main branch is always releasable.
- stable releases (the archive bundle with runnable jar, doc, other files...) are built and tagged from time to time from main.
- in the future, we'd like any push to the repository to trigger an automated build, which compile the code and runs tests. This would be implemented with something like GitHub Actions or Travis.

### Good Practices
- We should be using test-driven development, but we haven't figured out how to do that yet.
- Documentation should be provided using javadoc comments on classes and methods.

# Dependencies
These are managed automatically by Gradle, but also enumerated here. TODO: Licensing
- jSerialComm
- jDom
- jUnit4
- Mockito-core
- [GecoSI](https://github.com/Navigation-Games/GecoSI)
  - jSerialComm