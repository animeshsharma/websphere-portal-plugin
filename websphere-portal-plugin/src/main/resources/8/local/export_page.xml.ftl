<?xml version="1.0" encoding="UTF-8"?>
<request xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:noNamespaceSchemaLocation="PortalConfig_8.0.0.xsd" type="export">
	<!-- sample for exporting a page -->
	<portal action="locate">
		<content-node action="export" uniquename="${unique_name}"
			export-descendants="${is_recursion}" />
	</portal>
</request>
