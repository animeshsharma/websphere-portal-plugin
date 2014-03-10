<?xml version="1.0" encoding="UTF-8"?>
<project name="proj" default="main" basedir=".">
	<taskdef name="wsInstallApp" classname="com.ibm.websphere.ant.tasks.InstallApplication">
	</taskdef>
	<taskdef name="wsStartApp" classname="com.ibm.websphere.ant.tasks.StartApplication" />
	<taskdef name="wsUninstallApp"
		classname="com.ibm.websphere.ant.tasks.UninstallApplication" />

	<target name="main">
		<wsUninstallApp application="${appName}"
			options="-server ${profile} -node ${node} <#if cell??>-cell ${cell}<#else></#if>"
			conntype="${was_conntype}" host="${was_host}" port="${was_port}" user="${was_username}"
			password="${was_password}" />
		<wsInstallApp ear="${installableApp}"
			options="-appname ${appName} -server ${profile} -node ${node} 
			<#if cell??>-cell ${cell}<#else></#if> -preCompileJSPs"
			conntype="${was_conntype}" host="${was_host}" port="${was_port}" user="${was_username}"
			password="${was_password}" failonerror="true" />
		<wsStartApp server="${profile}" node="${node}" application="${appName}"
			conntype="${was_conntype}" host="${was_host}" port="${was_port}" user="${was_username}"
			password="${was_password}" />
	</target>
</project>