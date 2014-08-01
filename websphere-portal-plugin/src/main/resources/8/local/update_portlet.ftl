<?xml version="1.0"  encoding="UTF-8"?>
<request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="PortalConfig_8.0.0.xsd" type="update"
	create-oids="false">
	<portal action="locate">
		<web-app action="update" active="true" uniquename="${portlet_uid}.webmod" uid="${portlet_uid}.webmod">
			<url>${installableApp}</url>
		</web-app>
	</portal>
</request>
