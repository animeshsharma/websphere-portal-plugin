<?xml version="1.0" encoding="UTF-8"?>
<project name="proj" default="main" basedir=".">
	<taskdef name="wsInstallApp" classname="com.ibm.websphere.ant.tasks.InstallApplication">
		<!--Valid Options: preCompileJSPs nopreCompileJSPs distributeApp nodistributeApp 
			useMetaDataFromBinary nouseMetaDataFromBinary deployejb nodeployejb createMBeansForResources 
			nocreateMBeansForResources reloadEnabled noreloadEnabled deployws nodeployws 
			processEmbeddedConfig noprocessEmbeddedConfig allowDispatchRemoteInclude 
			noallowDispatchRemoteInclude allowServiceRemoteInclude noallowServiceRemoteInclude 
			useAutoLink nouseAutoLink enableClientModule noenableClientModule validateSchema 
			novalidateSchema usedefaultbindings nousedefaultbindings defaultbinding.force 
			allowPermInFilterPolicy noallowPermInFilterPolicy verbose update update.ignore.old 
			update.ignore.new installed.ear.destination appname reloadInterval validateinstall 
			filepermission buildVersion blaname asyncRequestDispatchType clientMode deployejb.rmic 
			deployejb.dbtype deployejb.dbschema deployejb.classpath deployejb.dbaccesstype 
			deployejb.sqljclasspath deployejb.complianceLevel deployws.classpath deployws.jardirs 
			defaultbinding.datasource.jndi defaultbinding.datasource.username defaultbinding.datasource.password 
			defaultbinding.cf.jndi defaultbinding.cf.resauth defaultbinding.ejbjndi.prefix 
			defaultbinding.virtual.host defaultbinding.strategy.file target server cell 
			cluster contextroot custom -->
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
			<#if cell??>-cell ${cell}<#else></#if> -contextroot ${contextRoot} -preCompileJSPs"
			conntype="${was_conntype}" host="${was_host}" port="${was_port}" user="${was_username}"
			password="${was_password}" failonerror="true" />
		<wsStartApp server="${profile}" node="${node}" application="${appName}"
			conntype="${was_conntype}" host="${was_host}" port="${was_port}" user="${was_username}"
			password="${was_password}" />
	</target>
</project>