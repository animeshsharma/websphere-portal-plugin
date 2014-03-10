<?xml version="1.0" encoding="UTF-8" ?>
<request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="PortalConfig_8.0.0.xsd" type="export">
	<portal action="locate">		<url-mapping-context action="export" <#if unique_name??>uniquename="${unique_name}"<#else>label="${label}"</#if> />
	</portal>
</request>