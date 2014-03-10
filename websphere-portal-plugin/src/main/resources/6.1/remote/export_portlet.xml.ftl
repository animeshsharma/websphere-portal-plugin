<?xml version="1.0" encoding="UTF-8"?>
<request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="PortalConfig_6.0.1.xsd" type="export">
	<portal action="locate">
	<#list unique_names as unique_name>
			<web-app action="export" uid="${unique_name}" />
	</#list>
	</portal>
</request>