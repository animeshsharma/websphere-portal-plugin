"<#if java_home??>${java_home}/bin/java<#else>java</#if>" -cp "assets/xmlaccess_runtime/wp.xml.client.jar" com.ibm.wps.xmlaccess.XmlAccess $* -in "${xmlaccess_script}" -user ${wps_config_username} -pwd ${wps_config_password} -url ${wps_config_url} -out "${xmlaccess_output}"