<?xml version="1.0" encoding="UTF-8"?>
<project name="proj" default="main" basedir=".">
	<taskdef name="WSDL2JavaTask" classname="com.ibm.websphere.ant.tasks.WSDL2Java" />
	<target name="main">
		<WSDL2JavaTask
			url="${url}"
			output="${output}"
			verbose="true"
			role="<#if role??>${role}<#else>develop-client</#if>"
			<#if inputMappingFile??>inputMappingFile="${inputMappingFile}"<#else></#if>	<#--String-->
			<#if container??>container="${container}"<#else></#if>	<#--String-->
			<#if debug??>debug="${debug}"<#else></#if>	<#--String-->
			<#if testCase??>testCase="${testCase}"<#else></#if>	<#--String-->
			<#if deployScope??>deployScope="${deployScope}"<#else></#if>	<#--String-->
			<#if genJava??>genJava="${genJava}"<#else></#if>	<#--String-->
			<#if genXML??>genXML="${genXML}"<#else></#if>	<#--String-->
			<#if all??>all="${all}"<#else></#if>	<#--String-->
			<#if timeout??>timeout="${timeout}"<#else></#if>	<#--String-->
			<#if user??>user="${user}"<#else></#if>	<#--String-->
			<#if password??>password="${password}"<#else></#if>	<#--String-->
			<#if useResolver??>useResolver="${useResolver}"<#else></#if>	<#--String-->
			<#if generateResolver??>generateResolver="${generateResolver}"<#else></#if>	<#--boolean-->
			<#if classpath??>classpath="${classpath}"<#else></#if>	<#--String-->
			<#if introspect??>introspect="${introspect}"<#else></#if>	<#--String-->
			<#if javaSearch??>javaSearch="${javaSearch}"<#else></#if>	<#--String-->
			<#if noDataBinding??>noDataBinding="${noDataBinding}"<#else></#if>	<#--boolean-->
			<#if noWrappedArrays??>noWrappedArrays="${noWrappedArrays}"<#else></#if>	<#--boolean-->
			<#if noWrappedOperations??>noWrappedOperations="${noWrappedOperations}"<#else></#if>	<#--boolean-->
			<#if fileNStoPkg??>fileNStoPkg="${fileNStoPkg}"<#else></#if>	<#--String-->
			<#if genEquals??>genEquals="${genEquals}"<#else></#if>	<#--boolean-->
			<#if genImplSer??>genImplSer="${genImplSer}"<#else></#if>	<#--boolean-->
			<#if scenario??>scenario="${scenario}"<#else></#if>	<#--String-->
			>
		</WSDL2JavaTask>
	</target>
</project>