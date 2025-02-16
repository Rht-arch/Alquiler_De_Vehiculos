[Setup]
AppName=UrbanDrive
AppVersion=1.0
DefaultDirName={pf}\UrbanDrive
DefaultGroupName=\UrbanDrive
OutputDir=.
OutputBaseFilename=UrbanDriveSetup
ArchitecturesInstallIn64BitMode=x64

[Files]
Source: "Instalador\installer.exe"; DestDir: "{app}"
Source: "Instalador\Alquiler_Vehiculos.jar"; DestDir: "{app}"
Source: "Instalador\javafx-sdk-23.0.2\*"; DestDir: "{app}\javafx-sdk-23.0.2"; Flags: recursesubdirs
Source: "Instalador\jdk-23.0.1_windows-x64_bin.msi"; DestDir: "{tmp}"


[Run]
Filename: "msiexec.exe"; Parameters: "/i {tmp}\jdk-23_windows-x64_bin.msi INSTALLDIR=""C:\Program Files\Java\jdk-23"" /qn"; Flags: waituntilterminated
Filename: "cmd.exe"; Parameters: "/c setx JAVA_HOME ""C:\Program Files\Java\jdk-23"""; Flags: runhidden
Filename: "cmd.exe"; Parameters: "/c setx PATH ""%JAVA_HOME%\bin;%PATH%"""; Flags: runhidden