<div align="center">
    <img alt="Rapid React 2023 Icon" src="https://www.firstinspires.org/sites/default/files/uploads/resource_library/frc/game-and-season-info/competition-manual/2022/rapidreact-transparent-vertical-200.png" />
    <h3><strong>frc-2022</strong></h3>
    <h7>Robot code for FRC 2022 Rapid React</h7>
</div>
<img alt="Java CI With Gradle" src="https://github.com/Pigmice2733/frc-2022/actions/workflows/gradle.yml/badge.svg" />
<br>

## Prerequisites

Make sure you have [Git](https://git-scm.com/downloads) installed, as well as some form of code editor. [Visual Studio Code](https://code.visualstudio.com/) is recommended, as well as the [Microsoft Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

## Installation

1. Open a terminal and [`cd`](https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/cd) into the directory you want to clone the repository into.
2. Install [Git](https://git-scm.com/downloads) if you haven't already.
3. Run the command `git clone https://github.com/Pigmice2733/frc-2023.git`.

And you're done! The project will have been downloaded into a new folder called `frc-2023`.

## Building and Running

1. Open a terminal and [`cd`](https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/cd) into the project directory (`frc-2023`).
2. To build the code, run `./gradlew build`.
3. To run all unit tests, run `./gradlew test`.
4. To deploy the code to the robot, make sure you're connected to the robot either over WiFi or through Ethernet. Then run `./gradlew deploy`.
