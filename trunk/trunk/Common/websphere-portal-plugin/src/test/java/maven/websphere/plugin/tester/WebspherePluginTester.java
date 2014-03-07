package maven.websphere.plugin.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Before;

/**
 * Test class to validate the maven-websphere-plugin tasks.
 * 
 * @author <a href="mailto:Juanyong.zhang@carefirst.com">Juanyong Zhang</a><br/>
 */
public class WebspherePluginTester implements WebspherePluginTesterIface {

	private enum Goal {
		DEPLOY_EAR("websphere:deploy-ear"), DEPLOY_PORTLET(
				"websphere:deploy-portlet"), DEPLOY_WAR("websphere:deploy-war"), DEPLOY_THEME(
				"websphere:deploy-theme"), EXPORT_PAGE("websphere:export-page"), EXPORT_THEME(
				"websphere:export-theme"), EXPORT_PORTLET(
				"websphere:export-portlet"), EXPORT_URLMAPPING(
				"websphere:export-urlmapping"), MIGRATE_PAGE(
				"websphere:migrate-page"), MIGRATE_PORTLET(
				"websphere:migrate-portlet"), MIGRATE_THEME(
				"websphere:migrate-theme"), MIGRATE_URLMAPPING(
				"websphere:migrate-urlmapping"), IMPORT("websphere:import"), DEPLOY_MULTIPLE_EAR(
				"websphere:deploy-multi-ear"), DEPLOY_MULTIPLE_WAR(
				"websphere:deploy-multi-war"), DEPLOY_MULTIPLE_PORTLET(
				"websphere:deploy-multi-portlet"),

		CLEAN("clean"), PACKAGE("package"), INSTALL("install");

		private String name;

		private Goal(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private static final String SAMPLE_PROJECT_DIR_EAR = "H:/Carefirst/Build_Automation/workspace/SBSMockup";
	private static final String SAMPLE_PROJECT_DIR_PORTLET = "H:/Carefirst/Portal/InfoSecurity/RLCM/workspace/RLCM/RLCM_CreateFunctionalRole";
	private static final String SAMPLE_PROJECT_DIR_WAR = "H:/Carefirst/Build_Automation/workspace/SampleWeb";

	private static final String SERVER_ARG_EAR = "-Dserver=dveweb3 ";

	private static final String SERVER_ARG_PORTLET = "-Dserver=dvptwas3 ";

	private static final String INSTALLABLE_NEXUS_PORTLET = "-DinstallableApp=http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/rlcm/RLCM_CreateFunctionalRole/0.1.0/RLCM_CreateFunctionalRole-0.1.0.war ";
	private static final String INSTALLABLE_NEXUS_EAR = "-DinstallableApp=http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/sbs/SBSMockup/1.0.0/SBSMockup-1.0.0.ear ";

	private static final String INSTALLABLE_NEXUS_MULTIPLE_PORTLET = "-DinstallableApps=http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/rlcm/RLCM_CreateFunctionalRole/0.1.0/RLCM_CreateFunctionalRole-0.1.0.war,http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/rlcm/RLCM_CreateMandatoryRole/0.1.0/RLCM_CreateMandatoryRole-0.1.0.war";
	private static final String INSTALLABLE_NEXUS_MULTIPLE_EAR = "-DinstallableApps=http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/sbs/SBSMockup/1.0.0/SBSMockup-1.0.0.ear,http://dveweb3:8086/nexus/content/repositories/releases/com/carefirst/sbs/SBSMockup/1.0.0/SBSMockup-1.0.0.ear";

	private static final String MIGRATE_SERVER_ARG = "-DsourceServer=dvptwas3 -DtargetServer=dvptwas3 ";

	public void deployEarFromNexus() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_EAR, SERVER_ARG_EAR + INSTALLABLE_NEXUS_EAR,
				Goal.DEPLOY_EAR);

	}

	public void deployMultipleEarFromNexus() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_EAR, SERVER_ARG_EAR
				+ INSTALLABLE_NEXUS_MULTIPLE_EAR, Goal.DEPLOY_MULTIPLE_EAR);

	}

	public void deployEarFromSource() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_EAR, SERVER_ARG_EAR, Goal.PACKAGE,
				Goal.DEPLOY_EAR);

	}

	public void deployPortletFromNexus() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, SERVER_ARG_PORTLET
				+ INSTALLABLE_NEXUS_PORTLET, Goal.DEPLOY_PORTLET);

	}

	public void deployPortletFromSource() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, SERVER_ARG_PORTLET
				+ " -DuniqueName=carefirst.rlcm.create.functional.role.webmod",
				Goal.PACKAGE, Goal.DEPLOY_PORTLET);

	}

	public void deployMultiplePortletFromNexus() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET,
				SERVER_ARG_PORTLET
						+ INSTALLABLE_NEXUS_MULTIPLE_PORTLET
						+ " -DuniqueNames=carefirst.rlcm.create.functional.role.webmod,carefirst.rlcm.create.mandatory.role.webmod",
				Goal.DEPLOY_MULTIPLE_PORTLET);

	}

	public void deployThemeFromSource() throws Exception {
		mvnRun(SAMPLE_PROJECT_DIR_WAR,
				SERVER_ARG_PORTLET
						+ " -DcontextRoot=/wps/themes/SampleWAR -DuniqueName=carefirst.theme.SampleWAR",
				Goal.PACKAGE, Goal.DEPLOY_THEME);
	}

	public void deployThemeFromSourceFromNexus() throws Exception {
		// TODO Auto-generated method stub

	}

	public void deployWarFromNexus() throws Exception {
		// TODO Auto-generated method stub

	}

	public void deployWarFromSource() throws Exception {

		mvnRun(SAMPLE_PROJECT_DIR_WAR, SERVER_ARG_EAR, Goal.PACKAGE,
				Goal.DEPLOY_WAR);

	}

	public void deployMultipleWarFromNexus() throws Exception {

		mvnRun(SAMPLE_PROJECT_DIR_WAR, SERVER_ARG_EAR, Goal.PACKAGE,
				Goal.DEPLOY_MULTIPLE_WAR);

	}

	public void exportPage() throws Exception {
		final String args = SERVER_ARG_PORTLET
				+ "-DuniqueName=websphere.plugin.test.page.10";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.EXPORT_PAGE);

	}

	public void exportPortlet() throws Exception {
		final String args = SERVER_ARG_PORTLET
				+ "-DuniqueNames=carefirst.member.portlet.MemberAccountRegistration.webmod,carefirst.rlcm.assistance.contact.info.webmod -DoutputFile=c:/export_portlet.xml";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.EXPORT_PORTLET);
	}

	public void exportURLMapping() throws Exception {
		final String args = SERVER_ARG_PORTLET
				+ "-Dlabel=Member -DoutputFile=c:/export_urlmapping.xml";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.EXPORT_URLMAPPING);
	}

	public void importXML() throws Exception {
		final String args = SERVER_ARG_PORTLET
				+ "-DimportXML=c:/export_urlmapping.xml";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.IMPORT);

	}

	public void migrateMapping() throws Exception {
		final String args = MIGRATE_SERVER_ARG + "-DuniqueName=Member";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.MIGRATE_URLMAPPING);

	}

	public void migratePage() throws Exception {

		// final String args = MIGRATE_SERVER_ARG
		// + "-DuniqueName=websphere.plugin.test.page.10";
		// mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.MIGRATE_PAGE);

		final String args = " -DsourceServer=dvptwas2 -DtargetServer=dvptwas3 -DuniqueName=carefirst.member.public -DreplacementFile=H:/Carefirst/Build_Automation/workspace/maven-websphere-plugin/UATandDEV2_To_DEVMemberSecureAndPublicReplacements.properties ";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.MIGRATE_PAGE);
	}

	public void migratePortlet() throws Exception {
		final String args = MIGRATE_SERVER_ARG
		// +
		// "-DuniqueName=carefirst.member.portlet.MemberAccountRegistration.webmod";
				+ "-DuniqueNames=carefirst.member.portlet.MemberAccountRegistration.webmod ";
		mvnRun(SAMPLE_PROJECT_DIR_PORTLET, args, Goal.MIGRATE_PORTLET);

	}

	private void mvnRun(String workspace, String args, Goal... goals) {
		try {
			String gString = "";
			if (null != goals) {
				for (Goal g : goals) {
					gString += g.getName() + " ";
				}
			}

			String cmd = "cmd /C mvn -e " + gString + args;
			System.out.println("exec cmd:" + cmd);
			Process p = Runtime.getRuntime().exec(cmd, null,
					new File(workspace));
			BufferedReader in = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void setup() throws Exception {
		Process p = Runtime.getRuntime().exec("cmd /C mvn install");
		BufferedReader in = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line = null;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
	}

}
