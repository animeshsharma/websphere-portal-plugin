package com.googlecode.websphere.model;

import org.apache.commons.lang.StringUtils;

/**
 * XMLAccessScriptPair for enumerate all the xmlaccess script template and it is
 * output form
 * 
 * @author <a href="mailto:Juanyong.zhang@gmail.com">Juanyong Zhang</a><br>
 */
public enum ScriptTemplatePair {

	DEPLOY_PORTLET("deploy_portlet.xml.ftl"), DEPLOY_THEME(
			"deploy_theme.xml.ftl"), EXPORT_PAGE("export_page.xml.ftl"), EXPORT_THEME(
			"export_theme.xml.ftl"), EXPORT_PORTLET("export_portlet.xml.ftl"), EXPORT_URLMAPPING(
			"export_urlmapping.xml.ftl"), DEPLOY_WAR("wsant_deploy_war.xml.ftl"),DEPLOY_EAR("wsant_deploy_ear.xml.ftl"), WSANT_CMD_WIN(
			"wsant_cmd.bat.ftl"), WSANT_CMD_UNIX("wsant_cmd.sh.ftl"), WSDL_2_JAVA(
			"wsant_wsdl2java.xml.ftl");

	private String template;

	private String script;

	private ScriptTemplatePair(String template) {
		this.template = template;
		this.script = StringUtils.substring(template, 0,
				template.lastIndexOf("."));
	}

	private ScriptTemplatePair(String template, String script) {
		this.template = template;
		this.script = script;
	}

	public String getScript() {
		return script;
	}

	public String getTemplate() {
		return template;
	}
}
