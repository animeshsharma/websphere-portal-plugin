WebSphere Portal Plugin provides the functional of deploying WAR, EAR, THEME, PORTLETS, EXPORT/IMPORT XMLACCESS for WebSphere Portal.
Underneath it is utilizing the WebSphere ANT and WebSphere XMLAccess.

### Configure pom.xml ###
https://oss.sonatype.org/#nexus-search;quick~websphere-portal-plugin
```
<build>
    <pluginManagement>
        <plugins>
            <plugin>
			<groupId>com.googlecode.websphere-portal-plugin</groupId>
			<artifactId>websphere-portal-plugin</artifactId>
			<version>1.0.0</version>

            </plugin>
        </plugins>
    </pluginManagement>
</build>
```
### Configure WebSphere Portal Server Profile in Settings.xml ###
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
	<servers>
		...
		<server>
			<id>was_box1</id>
			<!-- The username attribute is FTP User Name, used for deploy portlet 
				remotely -->
			<username>ftpUserName</username>
			<!-- The password attribute is FTP User password, used for deploy remotely 
				portlet -->
			<password>ftpPassword</password>
			<configuration>
				<!-- The ftp_url is used for deploy portlet remotely -->
				<ftp_url>ftp://was_box1</ftp_url>
				<!-- Options of was_conntype: SOAP, RMI, and NONE, NONE means that no 
					server connection is made -->
				<was_conntype>SOAP</was_conntype>
				<!-- The host attribute is optional and only specified if the conntype 
					is specified. It contains the hostname of the machine to connect to -->
				<was_host>was_box1</was_host>
				<!-- The port attribute is optional and only specified if the conntype 
					is specified. It contains the port on the host to connect to -->
				<was_port>20025</was_port>
				<!-- WebSphere Version, available for 6 or 8 -->
				<was_version>8</was_version>
				<!-- The wasHome attribute is optional and contains the location of the 
					WebSphere Installation Directory. -->
				<was_home>/usr/WebSphere85/AppServer</was_home>
				<!-- The node option specifies the node name to install or update an 
					entire application or to update an application in order to add a new module. 
					If you want to update an entire application, this option only applies if 
					the application contains a new module that does not exist in the installed 
					application. -->
				<was_node>was_box1Node</was_node>
				<!-- The server option specifies the name of the server on which you 
					want to perform one of the following actions: 1) Install a new application. 
					2) Replace an existing application with an updated version of that application. 
					In this situation, the server option is meaningful only if the updated version 
					of the application contains a new module that does not exist in the already 
					installed version of the application. -->
				<was_profile>WebSphere_Portal</was_profile>
				<!-- The cell option specifies the cell name to install or update an 
					entire application, or to update an application in order to add a new module. 
					If you want to update an entire application, this option only applies if 
					the application contains a new module that does not exist in the installed 
					application. -->
				<was_cell>was_box1Cell</was_cell>
				<!-- The user attribute is optional and contains the user ID to authenticate 
					with -->
				<was_username>wasAdm</was_username>
				<!-- The password attribute is optional and contains the password to 
					authenticate with -->
				<was_password>wasAdmPwd</was_password>
				<!-- The config url is used for !WebSphere Portal XML Access Operations -->
				<wps_config_url>http ://was_box1:10039/wpsp/config</wps_config_url>
				<!-- The !WebSphere Portal Admin username, which is used for !WebSphere 
					Portal XML Access Operations -->
				<wps_config_username>wpsAdm</wps_config_username>
				<!-- The !WebSphere Portal Admin password, which is used for !WebSphere 
					Portal XML Access Operations -->
				<wps_config_password>wpsAdmPwd</wps_config_password>
			</configuration>
		</server>
		...
	</servers>
</settings>

```

### Set IBM JDK in classpath ###
For windows(e.g.):
```
set JAVA_HOME="C:\Program Files (x86)\IBM\SDP85\jdk"
```
For UNIX(e.g.):
```
export JAVA_HOME=/usr/WebSphere/AppServer/java
```



P.S: For using WebSphere Goals, you need create at least 1 WebSphere Server Profile under your WAS home.


### Available goals for WebSphere: ###
  * **deploy-ear**
The deploy-ear task enables you to install a new application into a WebSphere Server or Cell.This task is a wrapper for the AdminApp.install() command of the wsadmin tool. Refer to the wsadmin documentation for information on the valid options available during application installation.

Usages:
```
1. Build and Deploy EAR Application 
mvn clean package websphere:deploy-ear -Dserver=was_box1 -DwasHome=C:\IBM\WebSphere\AppServer 
2. Deploy an existing EAR Application 
mvn clean websphere:deploy-ear -Dserver=was_box1 -DwasHome=C:\IBM\WebSphere\AppServer -DinstallableApp=C:\dist\BusinessDomainServices.ear 
3. Deploy an EAR Application from Nexus 
mvn clean websphere:deploy-ear -Dserver=was_box1 -DwasHome=C:\IBM\WebSphere\AppServer -DinstallableApp =http://stype-nexus:8081/nexus/content/repositories/releases/com/xyz/BusinessDomainServices/9.34.87/BusinessDomainServices-9.34.87.ear -DappName=BusinessDomainServices 
```

  * **deploy-war**
The deploy-war task enables you to install a Web Application into a WebSphere Server or Cell.This task is a wrapper for the AdminApp.install() command of the wsadmin tool. Refer to the wsadmin documentation for information on the valid options available during application installation.


Usages:
```
1. Build and Deploy a WAR 
mvn clean package websphere:deploy-war -Dserver=was_box1 -DcontextRoot=/wpsp/themes/HomePageTheme -DwasHome=C:/IBM/WebSphere/AppServer 
2. Deploy an existing WAR 
mvn clean websphere:deploy-war -Dserver=was_box1 -DcontextRoot=/wpsp/themes/HomePageTheme -DwasHome=C:/IBM/WebSphere/AppServer -DinstallableApp=C:/dist/theme1.war 
3. Deploy a WAR from Nexus 
mvn clean websphere:deploy-war -Dserver=was_box1 -DinstallableApp=http ://stype-nexus:8081/nexus/content/repositories/releases/com/xyz/theme1/9.34.87/theme1-9.34.87.war 
```


  * **wsdl-to-java**
The wsdl-to-java task creates Java classes and deployment descriptor templates from a Web Services Description Language (WSDL) file. This task is a wrapper the com.ibm.websphere.ant.tasks.WSDL2Java, Refer to the IBM documentation for information on the valid options available.

```
<wsdl2javatask url="location of input WSDL document" 
output="root directory for emitted files" 
role="J2EE development role" 
container="j2ee container" 
genjava="generate java files"/> 
```

Usages:
```
1. Generate Java classes base by WSDL 
mvn clean websphere:wsdl-to-java package -DwasHome=C:/IBM/WebSphere/AppServer -Durl=http://memsvcuser:M3msvcus3r@dveweb1/ secure/mws/services/UserAccess.wsdl -Doutput=${project.basedir}/src/main/generated 
```


### Available goals for WebSphere Portal: ###
  * **deploy-portlet**
The deploy-portlet task enables you to install portlet into a WebSphere Portal Server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Build and deploy a portlet 
mvn clean package websphere:deploy-portlet -Dserver=was_box1 
2. Deploy an existing portlet: 
mvn clean websphere:deploy-portlet -Dserver=was_box1 -DinstallableApp=C:/dist/portlet1.war 
3. Deploy portlet with customer uniqueNames 
mvn clean websphere:deploy-portlet -Dserver=was_box1 -DinstallableApp=C:/dist/portlet1.war -DuniqueNames=com.xyz.plt1 
4. Deploy portlet from Nexus 
mvn clean websphere:deploy-portlet -Dserver=was_box1 -DinstallableApp =http://stype-nexus:8081/nexus/content/repositories/releases/com/xyz/ptl1/9.34.87/ptl1-9.34.87.war 
```

  * **deploy-multi-portlet**
The deploy-multi-portlet task enables you to install multiable portlets into a WebSphere Portal Server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Deploy multi portlets: 
mvn clean websphere:deploy-multi-portlet -Dserver=was_box1 -DinstallableApps=C:/dist/portlet1.war,C:/dist/portlet2.war 
2. Deploy multi portlets with customer uniqueNames, with comma seperated uniqueNames args: 
mvn clean websphere:deploy-multi-portlet -Dserver=was_box1 -DinstallableApps=C:/dist/portlet1.war,C:/dist/portlet2.war -DuniqueNames=com.xyz.plt1,com.xyz.plt1 
3. Deploy multi portlets from Nexus 
mvn clean websphere:deploy-multi-portlet -Dserver=was_box1 -DinstallableApps =http://stype-nexus:8081/nexus/content/repositories/releases/com/xyz/ptl1/9.34.87/ptl1-9.34.87.war,http://stype-nexus:8081/nexus/content/repositories/releases/com/xyz/ptl2/9.34.87/ptl2-9.34.87.war 
```



  * **export-page**
The export-page task enables you to export the WebSphere Portal Page based on UniqueName. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Export Page 
mvn clean websphere:export-page -Dserver=was_box1 -DuniqueName=com.xyz.page.home1 
2. Export Page without Children 
mvn clean websphere:export-page -Dserver=was_box1 -DuniqueName=com.xyz.page.home1 -Drecursion=false 
```

  * **export-portlet**
The export-portlet task enables you to export the WebSphere portlet(s) based on UniqueName(s). This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Export single portlet 
mvn clean websphere:export-portlet -Dserver=was_box1 -DuniqueName=com.xyz.ptlt1 
2. Export multi portlets 
mvn clean websphere:export-portlet -Dserver=was_box1 -DuniqueName=com.xyz.ptlt1,com.xyz.ptlt2 
```


  * **export-theme**
The export-theme task enables you to export the WebSphere Theme based on Theme. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
1. Export theme
```
mvn clean websphere:export-theme -Dserver=was_box1 -DuniqueName=com.xyz.theme1 
```


  * **export-urlmapping**
The export-urlmapping task enables you to export the WebSphere urlmapping based on UniqueName or label. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Export URL Mapping by uniqueName 
mvn clean websphere:urlmapping -Dserver=was_box1 -DuniqueName=com.xyz.urlmapping.friend1 
2. Export URL Mapping by label 
mvn clean websphere:urlmapping -Dserver=was_box1 -Dlabel=friend1 
```


  * **import**
The import task enables you to import a XMLAccess XML into WebSphere Portal. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
1. Import a XMLAccess XML
```
mvn clean websphere:import -DimportXML=c:/xmls/pages_portal1.xml 
```


  * **migrate-page**
The migrate-page task enables you to migrate XMLAccess Page XML from source WPS server to target WPS server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Migrate a specified page from was_box1 to was_box2 
mvn clean websphere:migrate-page -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueName=com.xyz.page.home1 
2. Migrate a specified page from was_box1 to was_box2 without child pages 
mvn clean websphere:migrate-page -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueName=com.xyz.page.home1 -Drecursion=false
```

  * **migrate-portlet**
The migrate-portlet task enables you to migrate XMLAccess Portlet XML(s) from source WPS server to target WPS server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Migrate single portlet from was_box1 to was_box2 
mvn clean websphere:migrate-portlet -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueName=com.xyz.ptlt1 
2. Migrate multi portlets from was_box1 to was_box2 
mvn clean websphere:migrate-portlet -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueNames=com.xyz.ptlt1,com.xyz.ptlt2 
```


  * **migrate-theme**
The migrate-theme task enables you to migrate XMLAccess theme XML from source WPS server to target WPS server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Migrate Theme XML from was_box1 to was_box2 
mvn clean websphere:migrate-theme -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueName=com.xyz.theme1 
```



  * **migrate-urlmapping**
The migrate-urlmapping task enables you to migrate XMLAccess Urlmapping XML from source WPS server to target WPS server. This task is a wrapper for the XMLAccess command of the WebSphere Portal. Refer to the WebSphere Portal documentation for more information.


Usages:
```
1. Migrate urlmapping from was_box1 to was_box2 by uniqueName 
mvn clean websphere:migrate-urlmapping -DsourceServer=was_box1 -DtargetServer=was_box2 -DuniqueName=com.xyz.urlmapping.friend1 
2. Migrate URL Mapping by label 
mvn clean websphere:migrate-urlmapping -DsourceServer=was_box1 -DtargetServer=was_box2 -Dlabel=friend1 
```