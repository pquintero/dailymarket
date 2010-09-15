package ar.com.tsoluciones.emergencies.server.gui.core.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.jconfig.ConfigurationManager;
import org.jconfig.event.FileListener;
import org.jconfig.event.FileListenerEvent;

import ar.com.tsoluciones.arcom.logging.Log;
import ar.com.tsoluciones.emergencies.server.concurrent.PoolName;

/**
 * <p/>
 * Clase de configuracion que se nutre del archivo emergencies-config.xml. Es un
 * Singleton.
 * </p>
 * 
 * @author aclocchiatti, despada
 * @version 1.0, 23/11/2004
 * @since 1.0
 */
public class Configuration implements FileListener {
	private static Configuration instance;

	private static final String CONFIG_FILENAME = "emergencies";
	private static final int MODULE_GIS = 0;
	private static final int MODULE_CTI = 1;
	private static final int MODULE_RESOURCES = 2;

	/**
	 * Obtiene la instancia del singleton
	 * 
	 * @return Instancia del singleton
	 */
	public static synchronized Configuration getInstance() {
		return getInstance(false);
	}

	/**
	 * Obtiene la instancia del singleton
	 * 
	 * @param reload
	 *            Fuerza recarga de configuracion
	 * @return Instancia del singleton
	 */
	public static synchronized Configuration getInstance(boolean reload) {

		if (instance == null) {
			// Instanciar configuracion
			instance = new Configuration();
			// Escuchar cambios
			ConfigurationManager.getInstance().addFileListener(CONFIG_FILENAME,
					instance);
		}

		// Recargar si el usuario lo pidio
		if (reload)
			instance.reload();

		// Devolver objeto
		return instance;
	}

	private boolean isLocalidadSeteable;
	private String timeOldDossierLabel;
	private long timeOldDossier;
	private boolean expandirCamposDatosDomicilio;
	private HashMap<String, Long> maxCallApprovableFrame;
	private String callApprovableInstanceCode;
	private String urlSifeme;
	private String urlDatosMoviles;
	private String agenciaSalud;
	private Long alertControllerTime;
	private Long alertExpiredControllerTime;

	private Internet internet;
	private Pool commandExecutorParams;
	private Pool auditPool;
	private Pool messagingPool;

	private String gisReferenceSystem;

	/**
	 * Constructor del archivo de configuracion
	 */
	private Configuration() {
		// Categorias anidadas
		reload();
	}

	/**
	 * Obtiene el manager de configuracion
	 * 
	 * @return Manager de configuracion
	 */
	private org.jconfig.Configuration getConfigurationManager() {
		return ConfigurationManager.getConfiguration(CONFIG_FILENAME);
	}

	/**
	 * Constructor privado del singleton
	 */
	private void reload() {
		Log.getLogger(this.getClass()).info(
				"Recargando archivo de configuracion " + CONFIG_FILENAME
						+ "_config.xml");

		// Levantar datos
		org.jconfig.Configuration configuration = getConfigurationManager();

		// Core
		this.reloadCoreConfiguration(configuration);

		// CTI
		this.reloadCTIConfiguration(configuration);

		// GIS
		this.reloadGISConfiguration(configuration);

		// Data Werehousing
		this.reloadDataWarehousingConfiguration(configuration);

		// Resources
		this.reloadResourcesConfiguration(configuration);

		// Mensajeria
		this.reloadMessagingConfiguration(configuration);

		// Web Services
		this.reloadWebservicesConfiguration(configuration);

		// Internet
		this.reloadInternetConfig(configuration);

		// Reportes
		this.reloadReportConfiguration(configuration);

		this.reloadMultipleFiltersConfiguration(configuration);

		this.reloadCommentSumaryConfiguration(configuration);

		this.reloadInteroperabilidadConfiguration(configuration);

		this.reloadChooseAssistantConfiguration(configuration);

		Log.getLogger(this.getClass()).info(
				"Finalizo carga de archivo de configuracion");
	}

	/**
	 * Carga Configuraciï¿½n para el Core
	 * 
	 * @param configuration
	 */
	private void reloadCoreConfiguration(org.jconfig.Configuration configuration) {
		this.applicationName = configuration.getProperty("applicationName",
				"emergencies", "core");
		this.pageSize = configuration.getLongProperty("pageSize", 30, "core");
		this.maxEntryCallMap = configuration.getLongProperty("maxEntryCallMap",
				30, "core");
		this.inboxRefreshSeconds = configuration.getLongProperty(
				"inboxRefreshSeconds", 240, "core");
		this.newsRefreshSeconds = configuration.getLongProperty(
				"newsRefreshSeconds", 60, "core");
		this.newsMaxTime = configuration.getLongProperty("newsMaxTime", 48,
				"core");
		this.streetChooseAssistant = configuration.getLongProperty(
				"streetChooseAssistant", 100, "core");
		this.useFullTextSearch = configuration.getBooleanProperty(
				"useFullTextSearch", false, "core");
		this.derivarDespachanteLocal = configuration.getBooleanProperty(
				"derivarDespachanteLocal", true, "core");
		this.derivationUnitLocalAgency = configuration.getLongProperty(
				"derivationUnitLocalAgency", 1, "core");
		this.showOpenQuestions = configuration.getBooleanProperty(
				"showOpenQuestions", false, "core");
		this.charactersChooseAssistant = configuration.getIntProperty(
				"charactersChooseAssistant", 3, "core");
		this.priorityUpdaterRole = configuration.getProperty(
				"priorityUpdaterRole", null, "core");
		this.betaFeaturesEnabled = configuration.getBooleanProperty(
				"betaFeaturesEnabled", false, "core");
		String closeableIncidentTypes = configuration.getProperty(
				"closeableIncidentTypes", "*", "core");
		closeableIncidentTypeArray = closeableIncidentTypes.split(";");
		this.showCompany = configuration.getBooleanProperty("showCompany",
				false, "core");
		this.callDetailReport = configuration.getProperty("callDetailReport",
				"R15_detalle_de_llamado", "core");
		this.disableIncidentReportField = configuration.getBooleanProperty(
				"disableIncidentReportField", false, "core");
		this.disableDescriptionField = configuration.getBooleanProperty(
				"disableDescriptionField", false, "core");
		this.disableFields = configuration.getBooleanProperty("disableFields",
				false, "core");
		this.enableLocalizationWhenDispatched = configuration
				.getBooleanProperty("enableLocalizationWhenDispatched", false,
						"core");

		this.enableIncidentStreetBetweenTwo = configuration.getBooleanProperty(
				"enableIncidentStreetBetweenTwo", false, "core");
		this.isLocalidadSeteable = configuration.getBooleanProperty(
				"isLocalidadSeteable", false, "core");
		this.debugTime = configuration.getBooleanProperty("debugTime", false,
				"core");
		this.debugTimeFile = configuration.getProperty("debugTimeFile",
				"C:/Windows/Temp/911_Timers.log", "core");
		this.maxCallApprovableFrame = this.getMaxCallApprovableFrameConf();
		this.incidentDescriptionOptsEnabled = configuration.getBooleanProperty(
				"incidentDescriptionOptsEnabled", false, "core");
		this.expandirCamposDatosDomicilio = configuration.getBooleanProperty(
				"expandirCamposDatosDomicilio", false, "core");
		this.isBigFonts = configuration.getBooleanProperty("bigFonts", false,
				"core");
		this.allowByIdWhenClosed = configuration.getBooleanProperty(
				"allowByIdWhenClosed", false, "core");
		this.closeAfterAction = configuration.getBooleanProperty(
				"closeAfterAction", false, "core");
		this.dutyRequired = configuration.getBooleanProperty("dutyRequired",
				false, "core");
		this.closeMotiveRequired = configuration.getBooleanProperty(
				"closeMotiveRequired", false, "core");
		this.defaultPartePolicial = configuration.getBooleanProperty(
				"defaultPartePolicial", false, "core");

		this.timeOldDossier = configuration.getLongProperty("timeOldDossier",
				172800000, "core");
		this.timeOldDossierLabel = configuration.getProperty(
				"timeOldDossierLabel", "48 hs", "core");
		this.showNewDossier = configuration.getBooleanProperty(
				"showNewDossier", true, "core");
		this.maxStreetCache = configuration.getIntProperty("maxStreetCache",
				-1, "core");
		this.particularidadEnable = configuration.getBooleanProperty(
				"particularidadEnable", false, "core");
		this.saludEnabled = configuration.getBooleanProperty("saludEnabled",
				false, "core");
		this.timeZoneOffset = configuration.getIntProperty("timeZoneOffset",
				-3, "core");

		this.aliEnabled = configuration.getBooleanProperty("enabled", false,
				"ALIModule");

		this.alertControllerTime = configuration.getLongProperty(
				"alertControllerTime", 10, "core") * 1000;
		this.alertExpiredControllerTime = configuration.getLongProperty(
				"alertExpiredControllerTime", 10, "core") * 1000;

		commandExecutorParams = new Pool(PoolName.valueOf(configuration
				.getProperty("name", "", "commandExecutor")));
		commandExecutorParams.setBufferSize(configuration.getIntProperty(
				"bufferSize", 5, "commandExecutor"));
		commandExecutorParams.setCorePoolSize(configuration.getIntProperty(
				"corePoolSize", 10, "commandExecutor"));
		commandExecutorParams.setKeepAliveSeconds(configuration.getIntProperty(
				"keepAliveSeconds", 5, "commandExecutor"));
		commandExecutorParams.setMaximumPoolSize(configuration.getIntProperty(
				"maximumPoolSize", 100, "commandExecutor"));

		// Levanta los datos para el pool de threads para la auditoría
		auditPool = new Pool(PoolName.valueOf(configuration.getProperty("name",
				"", "auditPool")));
		auditPool.setBufferSize(configuration.getIntProperty("bufferSize", 20,
				"auditPool"));
		auditPool.setCorePoolSize(configuration.getIntProperty("initialSize",
				5, "auditPool"));
		auditPool.setKeepAliveSeconds(1000 * configuration.getIntProperty(
				"keepAlive", 60 * 5, "auditPool"));
		auditPool.setMaximumPoolSize(configuration.getIntProperty("maxSize",
				50, "auditPool"));

		// Levanta los datos para el pool de threads de mensajería
		messagingPool = new Pool(PoolName.valueOf(configuration.getProperty(
				"name", "", "messagingPool")));
		messagingPool.setBufferSize(configuration.getIntProperty("bufferSize",
				20, "messagingPool"));
		messagingPool.setCorePoolSize(configuration.getIntProperty(
				"initialSize", 5, "messagingPool"));
		messagingPool.setKeepAliveSeconds(1000 * configuration.getIntProperty(
				"keepAlive", 60 * 5, "messagingPool"));
		messagingPool.setMaximumPoolSize(configuration.getIntProperty(
				"maxSize", 50, "messagingPool"));

		this.quickActions = stringToCollection(configuration.getProperty(
				"quickActions", null, "core"));
		this.historyActions = stringToCollection(configuration.getProperty(
				"historyActions", null, "core"));
		this.defaultNoDispatchAction = configuration.getProperty(
				"defaultNoDispatchAction", null, "core");

		this.delayToWaitKeepAlive = configuration.getLongProperty(
				"delayToWaitKeepAlive", 30, "core");
		this.delayToTestConnectivity = configuration.getLongProperty(
				"delayToTestConnectivity", 10, "core");
		this.timeToRefreshAgenciesState = configuration.getLongProperty(
				"timeToRefreshAgenciesState", 30, "core");
		this.validatePreviousCalls = configuration.getBooleanProperty(
				"validatePreviousCalls", false, "core");
		this.daysCountPreviousCalls = configuration.getIntProperty(
				"daysCountPreviousCalls", 10, "core");
		this.maxTimeForExecuteOperation = configuration.getLongProperty(
				"maxTimeForExecuteOperation", 30, "core");
		this.discardPrep = stringToCollection(configuration.getProperty(
				"discardPrep", null, "core"));
		try {
			Document document = new SAXReader().read(Thread.currentThread()
					.getContextClassLoader()
					.getResource(CONFIG_FILENAME + "_config.xml"));
			Element tabRefreshElement = (Element) document
					.selectSingleNode("/properties/category[@name='core']/tabRefresh");
			tabXML = tabRefreshElement.asXML();
		} catch (DocumentException e) {
			throw new RuntimeException(
					"Error al intentar tomar documento de configuracion", e);
		}
	}

	private static Collection<String> stringToCollection(String property) {
		String[] values = property != null ? property.split(",") : null;
		ArrayList<String> res = new ArrayList<String>();
		if (values != null && values.length > 0) {
			for (String action : values)
				res.add(action.trim());
		}
		return res;
	}

	/**
	 * Carga Configuraciï¿½n para el CTI
	 * 
	 * @param configuration
	 */
	private void reloadCTIConfiguration(org.jconfig.Configuration configuration) {
		// this.CTIMode = configuration.getBooleanProperty("enabled", true,
		// "cti");
		this.CTIImplementation = configuration.getProperty("implementation",
				"Ninguna-VER", "cti");
		this.intervalToReconnect = configuration.getLongProperty(
				"intervalToReconnect", 10, "cti");
		this.server = configuration.getProperty("server", "F911Server1", "cti");
		this.server2 = configuration.getProperty("server2", "F911Server1",
				"cti");
		this.serverACD = configuration.getProperty("serverACD", "F911Server1",
				"cti");
		this.port = configuration.getLongProperty("port", 3010, "cti");
		this.port2 = configuration.getLongProperty("port2", 3010, "cti");
		this.ciscoACDLanguage = configuration.getProperty("ciscoACDLanguage",
				"English", "cti");
		this.ciscoCTIPortRngFrom = configuration.getLongProperty(
				"ciscoCTIPortRngFrom", 0, "cti");
		this.ciscoCTIPortRngUntil = configuration.getLongProperty(
				"ciscoCTIPortRngUntil", 0, "cti");
		this.jokeNumber = configuration
				.getProperty("jokeNumber", "9999", "cti");
		this.simulator = configuration.getBooleanProperty("simulator", true,
				"cti");
		this.incidentTypeSelect = configuration.getProperty(
				"incidentTypeSelect", "9999", "cti");
		this.incidentSubtypeSelect = configuration.getProperty(
				"incidentSubtypeSelect", "9999", "cti");
		this.internalCallType = configuration.getProperty("internalCallType",
				"9999", "cti");
		this.internalCallSubtype = configuration.getProperty(
				"internalCallSubtype", "9999", "cti");
		this.actionCloseIncidentWhenJoke = configuration.getProperty(
				"actionCloseIncidentWhenJoke", "CLOSE_INCIDENT_ACTION", "cti");
		this.extensionMobility = configuration.getBooleanProperty(
				"extensionMobility", false, "cti");
		this.extMobilityAdminPwd = configuration.getProperty("extMobilityPwd",
				"", "cti");
		this.extMobilityAdministrator = configuration.getProperty(
				"extMobilityUser", "", "cti");
		this.applicationId = configuration.getProperty("applicationId", "",
				"cti");
		this.applicationIdPassword = configuration.getProperty(
				"applicationIdPassword", "", "cti");
		this.capUser = configuration.getProperty("capUser", "", "cti");
		this.capPassword = configuration.getProperty("capPassword", "", "cti");
		this.operatorRole = configuration
				.getProperty("operatorRole", "", "cti");
	}

	/**
	 * Carga Configuraciï¿½n para el GIS
	 * 
	 * @param configuration
	 */
	private void reloadGISConfiguration(org.jconfig.Configuration configuration) {
		// this.gisEnabled = configuration.getBooleanProperty("enabled", false,
		// "gis");
		this.gisReferenceSystem = configuration.getProperty("referenceSystem",
				"MERCATOR", "gis");
		this.gisRefreshTime = configuration.getIntProperty("refreshTime", -1,
				"gis");
		this.gisCheckTime = configuration.getIntProperty("checkTime", 7, "gis");
		this.gisImplementationCode = configuration.getProperty(
				"implementationCode", "", "gis");
		this.movilesRefreshTime = configuration.getIntProperty(
				"movilesRefreshTime", -1, "gis");
		this.AVLForcedRefresh = configuration.getIntProperty(
				"avlForcedRefresh", -1, "gis");
		this.gisJurisdictionTables = configuration.getArray(
				"jurisdictionTables", new String[0], "gis");
		this.gisIncidentRadius = configuration.getLongProperty(
				"incidentRadius", 600, "gis");
		this.callDisplacement = configuration.getDoubleProperty(
				"callDisplacement", 5, "gis");
		this.avlMobileState = configuration.getBooleanProperty(
				"avlMobileState", false, "gis");
		this.avlHistory = configuration.getBooleanProperty("avlHistory", false,
				"gis");
		String[] usersForAvlState = configuration.getArray("usersForAvlState",
				new String[0], "gis");
		this.noTransmisionTime = configuration.getIntProperty(
				"noTransmisionTime", 5, "gis");
		this.maxRouteHours = configuration.getIntProperty("maxRouteHours", 8,
				"gis");
		this.routeDisabledLayers = configuration.getProperty(
				"routeDisabledLayersList", "", "gis");
		this.traceMovil = configuration.getBooleanProperty("traceMovil", false,
				"gis");
		this.routeDisabledLayersArray = this.routeDisabledLayers.split(";");
		this.dreEnabled = configuration.getBooleanProperty("dreEnabled", false,
				"gis");

		try {
			this.gisJurisdictionService = Class.forName(configuration
					.getProperty("jurisdictionService", "", "gis"));
		} catch (ClassNotFoundException e) {
			Log.getLogger(this.getClass())
					.error("No se pudo encontrar la clase servicio de jurisdicciones de GIS ",
							e);
		}
	}

	/**
	 * Carga Configuraciï¿½n para el Data Werehousing
	 * 
	 * @param configuration
	 */
	private void reloadDataWarehousingConfiguration(
			org.jconfig.Configuration configuration) {
		this.dataWareHousingEnabled = configuration.getBooleanProperty(
				"enabled", false, "DataWarehousing");
		this.dataWareHousingURL = configuration.getProperty("URL", "",
				"DataWarehousing");
		this.dataWareHousingUser = configuration.getProperty("user", "",
				"DataWarehousing");
		this.dataWareHousingPassword = configuration.getProperty("password",
				"", "DataWarehousing");
		this.dataWareHousingLinkedOLAP = configuration.getProperty(
				"linked_olap", "", "DataWarehousing");
		this.dataWareHousingTimeout = new Integer(configuration.getProperty(
				"timeout", "", "DataWarehousing"));
	}

	/**
	 * Carga Configuraciï¿½n para resources
	 * 
	 * @param configuration
	 */
	private void reloadResourcesConfiguration(
			org.jconfig.Configuration configuration) {
		// this.resourcesEnabled = configuration.getBooleanProperty("enabled",
		// false, "resources");
		this.resourcesIntegrated = configuration.getBooleanProperty(
				"integrated", false, "resources");
	}

	/**
	 * Carga la configuracion de messaging
	 * 
	 * @param configuration
	 *            Configuracion
	 */
	private void reloadMessagingConfiguration(
			org.jconfig.Configuration configuration) {
		this.messagingEnabled = configuration.getBooleanProperty("enabled",
				false, "messaging");
		this.externalBroker = configuration.getBooleanProperty(
				"externalBroker", false, "messaging");
		this.messagingProtocol = configuration.getProperty("protocol", "http",
				"messaging");
		this.messagingHost = configuration.getProperty("host", "localhost",
				"messaging");
		this.messagingPort = configuration.getIntProperty("port", 61617,
				"messaging");
		this.messagingSoTimeout = configuration.getProperty("soTimeout",
				"10000", "messaging");
		this.messagingSoWriteTimeout = configuration.getProperty(
				"SoWriteTimeout", "10000", "messaging");
		this.messagingInternalProtocol = configuration.getProperty(
				"internalProtocol", "vm", "messaging");
		this.messagingThreadName = configuration.getProperty("threadName", "",
				"messaging");
		this.enableBrokerControllers = configuration.getBooleanProperty(
				"enableBrokerControllers", false, "messaging");
		// JMX
		this.JMXHost = configuration.getProperty("JMXHost", "localhost",
				"messaging");
		this.JMXPort = configuration
				.getProperty("JMXPort", "2000", "messaging");

	}

	private void reloadWebservicesConfiguration(
			org.jconfig.Configuration configuration) {
		this.urlPatentes = configuration.getProperty("urlPatentes", "http://",
				"webservices");
		this.agenciaSalud = configuration.getProperty("agenciaSalud", "SIFEME",
				"webservices");
		this.urlSifeme = configuration.getProperty("urlSifeme", "http://",
				"webservices");
		this.urlDatosMoviles = configuration.getProperty("urlDatosMoviles",
				"http://", "webservices");
	}

	private void reloadInternetConfig(org.jconfig.Configuration configuration) {
		this.internet = new Internet();
		String CATEGORIA = "internet";
		String useProxy = configuration
				.getProperty("useProxy", null, CATEGORIA);

		internet.setUseProxy(useProxy != null && Boolean.valueOf(useProxy));
		if (internet.getUseProxy()) { // Si usa proxy
			internet.setProxyHost(configuration.getProperty("proxyHost", null,
					CATEGORIA));
			internet.setProxyPort(configuration.getProperty("proxyPort", null,
					CATEGORIA));
			internet.setProxyUser(configuration.getProperty("proxyUser", null,
					CATEGORIA));
			internet.setProxyPass(configuration.getProperty("proxyPass", null,
					CATEGORIA));
			String proxyTypeString = configuration.getProperty("proxyType",
					null, CATEGORIA);
			if (proxyTypeString != null
					&& ProxyType.NTLM
							.equals(ProxyType.valueOf(proxyTypeString))) {
				internet.setProxyType(ProxyType.NTLM);
				internet.setProxyDomain(configuration.getProperty(
						"proxyDomain", null, CATEGORIA));
			} else {
				internet.setProxyType(ProxyType.BASIC);
			}
		}
	}

	private String[] verifyPanels(String panels) {
		String[] editablePanels = panels.split(";");
		for (String panel : editablePanels) {
			if (!panel.equals("TELEFONO") && !panel.equals("DESCRIPCION")
					&& !panel.equals("LOCALIZACION")
					&& !panel.equals("TIPIFICACION")
					&& !panel.equals("PREGUNTASCERRADAS")
					&& !panel.equals("DENUNCIANTE") && !panel.equals("SALUD")
					&& !panel.equals("")) {
				try {
					throw new Exception(
							"Los nombres de los paneles editables no coinciden o están mal escritos");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return editablePanels;

	}

	// Core
	private String applicationName;
	private String urlPatentes;
	private Long pageSize;
	private Long inboxRefreshSeconds;
	private Long newsRefreshSeconds;
	private Long newsMaxTime;
	private boolean debugTime;
	private String debugTimeFile;
	private Long streetChooseAssistant;
	private boolean useFullTextSearch;
	private Long derivationUnitLocalAgency;
	private boolean derivarDespachanteLocal;
	private Integer charactersChooseAssistant;
	private String priorityUpdaterRole;
	private String[] closeableIncidentTypeArray;
	private boolean showCompany;
	private String callDetailReport;
	private boolean disableIncidentReportField;
	private boolean disableDescriptionField;
	private boolean disableFields;
	private boolean enableLocalizationWhenDispatched;
	private String tabXML;
	private boolean allowByIdWhenClosed;
	private String openClosedApprovMasterRole;
	private String[] editablePanels;
	private boolean dutyRequired;
	private boolean closeMotiveRequired;
	private boolean enableIncidentStreetBetweenTwo;
	private boolean dummyWorkstationEnabled;
	private String dummyWorkstationName;
	private boolean arrestedQuantityEnabled;
	private boolean policeReportEnabled;
	private boolean policeStationEnabled;
	private boolean callPlaceSelect;
	private boolean editableCTI;
	private boolean noEditableCTI;
	private boolean isBigFonts;
	private boolean closeAfterAction;
	private boolean sessionDebugEnabled;
	private String sessionDebugUsername;
	private boolean showNewDossier;
	private boolean showOpenQuestions;
	private Integer maxStreetCache;
	private boolean saludEnabled;
	private boolean aliEnabled;
	private boolean defaultPartePolicial;

	private boolean betaFeaturesEnabled;
	private boolean incidentDescriptionOptsEnabled;
	private Integer timeZoneOffset;

	private boolean particularidadEnable;

	private Collection<String> quickActions;
	private Collection<String> historyActions;
	private String defaultNoDispatchAction;
	private Long maxEntryCallMap;
	private Long delayToWaitKeepAlive;
	private Long delayToTestConnectivity;
	private Long timeToRefreshAgenciesState;
	private boolean validatePreviousCalls;
	private Integer daysCountPreviousCalls;
	private Long maxTimeForExecuteOperation;
	private Collection<String> discardPrep;

	public boolean isParticularidadEnable() {
		return particularidadEnable;
	}

	public boolean isShowOpenQuestions() {
		return showOpenQuestions;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public Long getInboxRefreshSeconds() {
		return inboxRefreshSeconds;
	}

	public Long getNewsRefreshSeconds() {
		return newsRefreshSeconds;
	}

	public Long getNewsMaxTime() {
		return newsMaxTime;
	}

	public boolean isDebugTime() {
		return debugTime;
	}

	public String getDebugTimeFile() {
		return debugTimeFile;
	}

	public Long getStreetChooseAssistant() {
		return streetChooseAssistant;
	}

	public boolean getUseFullTextSearch() {
		return useFullTextSearch;
	}

	public Long getDerivationUnitLocalAgency() {
		return derivationUnitLocalAgency;
	}

	public boolean getDerivarDespachanteLocal() {
		return derivarDespachanteLocal;
	}

	public Integer getCharactersChooseAssistant() {
		return charactersChooseAssistant;
	}

	public Integer getMaxStreetCache() {
		return maxStreetCache;
	}

	public boolean isSaludEnabled() {
		return saludEnabled;
	}

	public boolean isAliEnabled() {
		return aliEnabled;
	}

	public Pool getCommandExecutorParams() {
		return commandExecutorParams;
	}

	public boolean isDummyWorkstationEnabled() {
		return dummyWorkstationEnabled;
	}

	public boolean isdefaultPartePolicialEnabled() {
		return defaultPartePolicial;
	}

	public String getDummyWorkstationName() {
		return dummyWorkstationName;
	}

	public boolean isArrestedQuantityEnabled() {
		return arrestedQuantityEnabled;
	}

	public boolean isPoliceReportEnabled() {
		return policeReportEnabled;
	}

	public boolean isPoliceStationEnabled() {
		return policeStationEnabled;
	}

	public boolean isCallPlaceSelectEnabled() {
		return callPlaceSelect;
	}

	public boolean isNoEditableCTIEnabled() {
		return noEditableCTI;
	}

	public boolean isEditableCTIEnabled() {
		return editableCTI;
	}

	public boolean isSessionDebugEnabled() {
		return sessionDebugEnabled;
	}

	public String getSessionDebugUsername() {
		return sessionDebugUsername;
	}

	public boolean isBetaFeaturesEnabled() {
		return betaFeaturesEnabled;
	}

	public void setBetaFeaturesEnabled(boolean betaFeaturesEnabled) {
		this.betaFeaturesEnabled = betaFeaturesEnabled;
	}

	/**
	 * Evalua si un tipo de incidente en particular es plausible de ser cerrado
	 * por un operador
	 * 
	 * @param name
	 *            Nombre del tipo de incidente
	 * @return true si el operador puede cerrarlo
	 */
	public boolean isCloseableIncidentType(String name) {
		for (int i = 0; i < closeableIncidentTypeArray.length; i++) {
			// Es "todos" ?
			if (closeableIncidentTypeArray[i].equalsIgnoreCase("*"))
				return true;
			// Es este ?
			if (closeableIncidentTypeArray[i].equalsIgnoreCase(name))
				return true;
		}

		return false;
	}

	public boolean isShowCompany() {
		return showCompany;
	}

	public String getCallDetailReport() {
		return callDetailReport;
	}

	public boolean isDisableIncidentReportField() {
		return disableIncidentReportField;
	}

	public boolean isDisableDescriptionField() {
		return disableDescriptionField;
	}

	public boolean isDisableFields() {
		return disableFields;
	}

	public boolean isDutyRequired() {
		return dutyRequired;
	}

	public boolean isCloseMotiveRequired() {
		return closeMotiveRequired;
	}

	public boolean isEnableIncidentStreetBetweenTwo() {
		return enableIncidentStreetBetweenTwo;
	}

	/**
	 * Devuelve el XML de configuracion de los tabs
	 * 
	 * @return XML de configuracion de los tabs
	 */
	public Document getTabXMLDocument() {
		try {
			return DocumentHelper.parseText(tabXML);
		} catch (DocumentException e) {
			throw new RuntimeException(
					"Error al intentar cargar documento XML", e);
		}
	}

	// CTI

	// private boolean CTIMode;

	private String CTIImplementation;
	private Long intervalToReconnect;
	private String server;
	private String server2;
	private String serverACD;
	private Long port;
	private Long port2;
	private String ciscoACDLanguage;
	private Long ciscoCTIPortRngFrom;
	private Long ciscoCTIPortRngUntil;
	private String jokeNumber;
	private boolean simulator;
	private String incidentTypeSelect;
	private String incidentSubtypeSelect;
	private String internalCallType;
	private String internalCallSubtype;
	private String actionCloseIncidentWhenJoke;
	private String extMobilityAdministrator;
	private String extMobilityAdminPwd;
	private boolean extensionMobility;
	// Datos de la aplicacion CAP necesarios solo para Siemens.
	private String applicationId;
	private String applicationIdPassword;
	private String capUser;
	private String capPassword;
	private String operatorRole;

	public boolean getExtensionMobility() {
		return extensionMobility;
	}

	public void setExtensionMobility(boolean extensionMobility) {
		this.extensionMobility = extensionMobility;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationIdPassword() {
		return applicationIdPassword;
	}

	public void setApplicationIdPassword(String applicationIdPassword) {
		this.applicationIdPassword = applicationIdPassword;
	}

	public String getCapUser() {
		return capUser;
	}

	public void setCapUser(String capUser) {
		this.capUser = capUser;
	}

	public String getCapPassword() {
		return capPassword;
	}

	public void setCapPassword(String capPassword) {
		this.capPassword = capPassword;
	}

	public String getOperatorRole() {
		return this.operatorRole;
	}

	public void setOperatorRole(String operatorRole) {
		this.operatorRole = operatorRole;
	}

	public String getExtMobilityAdminPwd() {
		return extMobilityAdminPwd;
	}

	public void setExtMobilityAdminPwd(String extMobilityPwd) {
		this.extMobilityAdminPwd = extMobilityPwd;
	}

	public String getExtMobilityAdministrator() {
		return extMobilityAdministrator;
	}

	public void setExtMobilityAdministrator(String extMobilityUser) {
		this.extMobilityAdministrator = extMobilityUser;
	}

	public String getCTIImplementation() {
		return CTIImplementation;
	}

	public Long getIntervalToReconnect() {
		return intervalToReconnect;
	}

	public String getServer() {
		return server;
	}

	public String getServer2() {
		return server2;
	}

	public String getServerACD() {
		return serverACD;
	}

	public Long getPort() {
		return port;
	}

	public Long getPort2() {
		return port2;
	}

	public String getCiscoACDLanguage() {
		return ciscoACDLanguage;
	}

	public Long getCiscoCTIPortRngFrom() {
		return ciscoCTIPortRngFrom;
	}

	public Long getCiscoCTIPortRngUntil() {
		return ciscoCTIPortRngUntil;
	}

	public String getJokeNumber() {
		return jokeNumber;
	}

	public boolean isSimulator() {
		return simulator;
	}

	public String getIncidentSubtypeSelect() {
		return incidentSubtypeSelect;
	}

	public String getIncidentTypeSelect() {
		return incidentTypeSelect;
	}

	public String getInternalCallSubtype() {
		return internalCallSubtype;
	}

	public String getInternalCallType() {
		return internalCallType;
	}

	public String getActionCloseIncidentWhenJoke() {
		return actionCloseIncidentWhenJoke;
	}

	public void setActionCloseIncidentWhenJoke(
			String actionCloseIncidentWhenJoke) {
		this.actionCloseIncidentWhenJoke = actionCloseIncidentWhenJoke;
	}

	// Gis

	// private boolean gisEnabled;
	private Integer gisRefreshTime;
	private Integer gisCheckTime;
	private Integer movilesRefreshTime;
	private Integer AVLForcedRefresh;
	private String gisImplementationCode;
	private String[] gisJurisdictionTables;
	private Long gisIncidentRadius;
	private Class<?> gisJurisdictionService;
	private double callDisplacement;
	private Double streetLengthAverage;
	private boolean avlHistory;
	private boolean traceMovil;
	private boolean avlMobileState;
	private int noTransmisionTime;
	private int maxRouteHours;
	private String routeDisabledLayers;
	private String[] routeDisabledLayersArray;
	private boolean dreEnabled;

	public Double getStreetLengthAverage() {
		return streetLengthAverage;
	}

	public void setStreetLengthAverage(Double streetLengthAverage) {
		this.streetLengthAverage = streetLengthAverage;
	}

	public Class<?> getGisJurisdictionService() {
		return gisJurisdictionService;
	}

	public void setGisJurisdictionService(Class<?> gisJurisdictionService) {
		this.gisJurisdictionService = gisJurisdictionService;
	}

	public boolean isDreEnabled() {
		return dreEnabled;
	}

	/*
	 * public void setGisEnabled(boolean gisEnabled) { this.gisEnabled =
	 * gisEnabled; }
	 */

	public String getGisReferenceSystem() {
		return gisReferenceSystem;
	}

	public Integer getGisRefreshTime() {
		return gisRefreshTime;
	}

	public void setGisRefreshTime(Integer gisRefreshTime) {
		this.gisRefreshTime = gisRefreshTime;
	}

	public Integer getGisCheckTime() {
		return gisCheckTime;
	}

	public void setGisCheckTime(Integer gisCheckTime) {
		this.gisCheckTime = gisCheckTime;
	}

	public Integer getMovilesRefreshTime() {
		return movilesRefreshTime;
	}

	public void setMovilesRefreshTime(Integer movilesRefreshTime) {
		this.movilesRefreshTime = movilesRefreshTime;
	}

	public Integer getAVLForcedRefresh() {
		return AVLForcedRefresh;
	}

	public void setAVLForcedRefresh(Integer AVLForcedRefresh) {
		this.AVLForcedRefresh = AVLForcedRefresh;
	}

	public String getGisImplementationCode() {
		return gisImplementationCode;
	}

	public void setGisImplementationCode(String gisImplementationCode) {
		this.gisImplementationCode = gisImplementationCode;
	}

	public String[] getGisJurisdictionTables() {
		return gisJurisdictionTables;
	}

	public void setGisJurisdictionTables(String[] gisJurisdictionTables) {
		this.gisJurisdictionTables = gisJurisdictionTables;
	}

	public Long getGisIncidentRadius() {
		return gisIncidentRadius;
	}

	public void setGisIncidentRadius(Long gisIncidentRadius) {
		this.gisIncidentRadius = gisIncidentRadius;
	}

	public double getCallDisplacement() {
		return callDisplacement;
	}

	public void setCallDisplacement(double callDisplacement) {
		this.callDisplacement = callDisplacement;
	}

	public boolean isAvlMobileState() {
		return avlMobileState;
	}

	public boolean isTraceMovil() {
		return traceMovil;
	}

	public boolean isAvlHistory() {
		return avlHistory;
	}

	public void setAvlHistory(boolean avlHistory) {
		this.avlHistory = avlHistory;
	}

	public int getNoTransmisionTime() {
		return noTransmisionTime;
	}

	public void setNoTransmisionTime(int noTransmisionTime) {
		this.noTransmisionTime = noTransmisionTime;
	}

	public String[] getRouteDisabledLayersArray() {
		return routeDisabledLayersArray;
	}

	public void setRouteDisabledLayersArray(String[] routeDisabledLayersList) {
		this.routeDisabledLayersArray = routeDisabledLayersList;
	}

	public int getMaxRouteHours() {
		return maxRouteHours;
	}

	public void setMaxRouteHours(int maxRouteHours) {
		this.maxRouteHours = maxRouteHours;
	}

	public String getRouteDisabledLayers() {
		return routeDisabledLayers;
	}

	public void setRouteDisabledLayers(String routeDisabledLayers) {
		this.routeDisabledLayers = routeDisabledLayers;
	}

	// Data Warehousing
	private boolean dataWareHousingEnabled;

	private String dataWareHousingURL;

	private String dataWareHousingUser;

	private String dataWareHousingPassword;

	private String dataWareHousingLinkedOLAP;

	private Integer dataWareHousingTimeout;

	public boolean isDataWareHousingEnabled() {
		return dataWareHousingEnabled;
	}

	public void setDataWareHousingEnabled(boolean dataWareHousingEnabled) {
		this.dataWareHousingEnabled = dataWareHousingEnabled;
	}

	public String getDataWareHousingURL() {
		return dataWareHousingURL;
	}

	public void setDataWareHousingURL(String dataWareHousingURL) {
		this.dataWareHousingURL = dataWareHousingURL;
	}

	public String getDataWareHousingUser() {
		return dataWareHousingUser;
	}

	public void setDataWareHousingUser(String dataWareHousingUser) {
		this.dataWareHousingUser = dataWareHousingUser;
	}

	public String getDataWareHousingPassword() {
		return dataWareHousingPassword;
	}

	public void setDataWareHousingPassword(String dataWareHousingPassword) {
		this.dataWareHousingPassword = dataWareHousingPassword;
	}

	public String getDataWareHousingLinkedOLAP() {
		return dataWareHousingLinkedOLAP;
	}

	public void setDataWareHousingLinkedOLAP(String dataWareHousingLinkedOLAP) {
		this.dataWareHousingLinkedOLAP = dataWareHousingLinkedOLAP;
	}

	public Integer getDataWareHousingTimeout() {
		return dataWareHousingTimeout;
	}

	public void setDataWareHousingTimeout(Integer dataWareHousingTimeout) {
		this.dataWareHousingTimeout = dataWareHousingTimeout;
	}

	public String[] getCloseableIncidentTypeArray() {
		return closeableIncidentTypeArray;
	}

	public void setCloseableIncidentTypeArray(
			String[] closeableIncidentTypeArray) {
		this.closeableIncidentTypeArray = closeableIncidentTypeArray;
	}

	// Resources

	// private boolean resourcesEnabled;

	private boolean resourcesIntegrated;// Si estï¿½ integrado con GIS.

	public boolean isResourcesIntegrated() {
		return resourcesIntegrated;
	}

	// Mensajeria
	private boolean messagingEnabled;
	private String messagingProtocol;
	private String messagingInternalProtocol;
	private String messagingHost;
	private Integer messagingPort;
	private String messagingSoTimeout;
	private String messagingSoWriteTimeout;
	private String messagingThreadName;
	private boolean enableBrokerControllers;
	private boolean externalBroker;
	private String JMXHost;
	private String JMXPort;

	public String getMessagingThreadName() {
		return messagingThreadName;
	}

	public boolean isEnableBrokerControllers() {
		return enableBrokerControllers;
	}

	public String getMessagingInternalProtocol() {
		return messagingInternalProtocol;
	}

	public String getMessagingSoTimeout() {
		return messagingSoTimeout;
	}

	public String getMessagingSoWriteTimeout() {
		return messagingSoWriteTimeout;
	}

	public boolean isMessagingEnabled() {
		return messagingEnabled;
	}

	public String getMessagingProtocol() {
		return messagingProtocol;
	}

	public String getMessagingHost() {
		return messagingHost;
	}

	public Integer getMessagingPort() {
		return messagingPort;
	}

	// Watcher del archivo

	/**
	 * Escucha cambios en el archivo de configuracion y actualiza los valores de
	 * esta clase
	 * 
	 * @param event
	 *            Evento de cambio
	 */
	public void fileChanged(FileListenerEvent event) {
		Log.getLogger(this.getClass()).info(
				"Alteraciï¿½n de configuraciï¿½n detectada");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Log.getLogger(this.getClass())
					.warn("Cuidado, espera interrumpida mientras se aguardaba para cargar el archivo");
		}

		// Recargar
		Configuration.getInstance(true);
	}

	public boolean isShowNewDossier() {
		return showNewDossier;
	}

	/**
	 * Carga la configuracion de report
	 * 
	 * @param configuration
	 *            Configuracion
	 */
	private void reloadReportConfiguration(
			org.jconfig.Configuration configuration) {
		this.reportOutPutFormatDOC = configuration.getBooleanProperty(
				"reportOutPutFormatDOC", true, "report");
		this.reportOutPutFormatPDF = configuration.getBooleanProperty(
				"reportOutPutFormatPDF", true, "report");
		this.reportOutPutFormatXLS = configuration.getBooleanProperty(
				"reportOutPutFormatXLS", true, "report");
		this.maxTimeAvlReport = configuration.getLongProperty(
				"maxTimeAvlReport", 60, "report");
	}

	// Report
	private boolean reportOutPutFormatXLS;

	private boolean reportOutPutFormatPDF;

	private boolean reportOutPutFormatDOC;

	private Long maxTimeAvlReport;

	public boolean isReportOutPutFormatDOC() {
		return reportOutPutFormatDOC;
	}

	public boolean isReportOutPutFormatPDF() {
		return reportOutPutFormatPDF;
	}

	public boolean isReportOutPutFormatXLS() {
		return reportOutPutFormatXLS;
	}

	public Long getMaxTimeAvlReport() {
		return maxTimeAvlReport;
	}

	public void setMaxTimeAvlReport(Long maxTimeAvlReport) {
		this.maxTimeAvlReport = maxTimeAvlReport;
	}

	public boolean isLocalidadSeteable() {
		return isLocalidadSeteable;
	}

	public boolean isBigFonts() {
		return isBigFonts;
	}

	public long getTimeOldDossier() {
		return timeOldDossier;
	}

	public String getTimeOldDossierLabel() {
		return timeOldDossierLabel;
	}

	public void setBigFonts(boolean isBigFonts) {
		this.isBigFonts = isBigFonts;
	}

	public boolean isCloseAfterAction() {
		return closeAfterAction;
	}

	public void setCloseAfterAction(boolean closeAfterAction) {
		this.closeAfterAction = closeAfterAction;
	}

	public boolean isExpandirCamposDatosDomicilio() {
		return expandirCamposDatosDomicilio;
	}

	public HashMap<String, Long> getMaxCallApprovableFrame() {
		return this.maxCallApprovableFrame;
	}

	public Long getMaxCallApprovableFrameDefaultConf() {
		return this.maxCallApprovableFrame.get("DEFAULT");
	}

	public Long getMaxCallApprovableFrameConfByRole(String roleName) {
		return this.maxCallApprovableFrame.get(roleName);
	}

	public boolean isAllowByIdWhenClosed() {
		return allowByIdWhenClosed;
	}

	public void setAllowByIdWhenClosed(boolean allowByIdWhenClosed) {
		this.allowByIdWhenClosed = allowByIdWhenClosed;
	}

	public String getCallApprovableInstanceCode() {
		return callApprovableInstanceCode;
	}

	public Long getAlertControllerTime() {
		return alertControllerTime;
	}

	public Long getAlertExpiredControllerTime() {
		return alertExpiredControllerTime;
	}

	@SuppressWarnings("unchecked")
	private HashMap<String, Long> getMaxCallApprovableFrameConf() {
		HashMap<String, Long> retorno = new HashMap<String, Long>();

		try {
			Document document = new SAXReader().read(Thread.currentThread()
					.getContextClassLoader()
					.getResource(CONFIG_FILENAME + "_config.xml"));
			Element rootElement = (Element) document
					.selectSingleNode("/properties/category[@name='core']/maxCallApprovableFrameTab");
			Iterator<DefaultElement> i = rootElement.elementIterator();
			while (i.hasNext()) {
				DefaultElement df = i.next();
				retorno.put(df.attributeValue("name"),
						new Long(df.attributeValue("value")));
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Error al intentar tomar documento de configuracion", e);
		}

		return retorno;
	}

	public boolean getIncidentDescriptionOptsEnabled() {
		return incidentDescriptionOptsEnabled;
	}

	public void setIncidentDescriptionOptsEnabled(
			boolean incidentDescriptionOptsEnabled) {
		this.incidentDescriptionOptsEnabled = incidentDescriptionOptsEnabled;
	}

	public String getUrlSifeme() {
		return urlSifeme;
	}

	public String getUrlDatosMoviles() {
		return urlDatosMoviles;
	}

	public String getAgenciaSalud() {
		return agenciaSalud;
	}

	private boolean commentsMandatory;

	private void reloadCommentSumaryConfiguration(
			org.jconfig.Configuration configuration) {
		this.commentsMandatory = configuration.getBooleanProperty(
				"commentsMandatory", false, "commentSummary");
	}

	private Long minCharactersDescription;
	private String multipleFiltersRole;
	private Map<String, DatasourceConfig> dynamicDatasources = new HashMap<String, DatasourceConfig>();
	private String multiplesCallreport;

	private void reloadMultipleFiltersConfiguration(
			org.jconfig.Configuration configuration) {
		this.minCharactersDescription = configuration.getLongProperty(
				"minCharactersDescription", 10, "multipleFilters");
		this.multipleFiltersRole = configuration.getProperty(
				"multipleFiltersRole", "SUPERVISOR", "multipleFilters");
		this.multiplesCallreport = configuration.getProperty(
				"multiplesCallreport", "R66", "multipleFilters");

		String filename = configuration.getProperty("datasourceFileName",
				"datasources.xml", "multipleFilters");

		dynamicDatasources.clear();
		parseDatasourceConfig(filename);

	}

	@SuppressWarnings("unchecked")
	private void parseDatasourceConfig(String filename) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(getClass().getResource(filename));
			Element root = document.getRootElement();

			for (Iterator i = root.elementIterator(); i.hasNext();) {
				Element element = (Element) i.next();
				DatasourceConfig dconfig = new DatasourceConfig();
				String def = element.valueOf("@default");
				if (def != null) {
					dconfig.setDefaultDatasource(Boolean.parseBoolean(def));
				}
				String name = element.element("name").getText();
				dconfig.setName(name);
				String description = element.element("description").getText();
				dconfig.setDescription(description);
				String url = element.element("url").getText();
				dconfig.setUrl(url);
				String username = element.element("username").getText();
				dconfig.setUsername(username);
				String password = element.element("password").getText();
				dconfig.setPassword(password);
				String limit = element.element("days-limit").getText();
				if (StringUtils.isNumeric(limit)) {
					dconfig.setDaysLimit(Long.parseLong(limit));
				} else {
					dconfig.setDaysLimit(30L);
				}

				String min = element.element("pool-minsize").getText();
				if (StringUtils.isNumeric(min)) {
					dconfig.setPoolMinSize(Integer.parseInt(min));
				} else {
					dconfig.setPoolMinSize(0);
				}

				String max = element.element("pool-maxsize").getText();
				if (StringUtils.isNumeric(max)) {
					dconfig.setPoolMinSize(Integer.parseInt(max));
				} else {
					dconfig.setPoolMinSize(5);
				}

				String idleTestPeriod = element.element("pool-maxsize")
						.getText();
				if (StringUtils.isNumeric(idleTestPeriod)) {
					dconfig.setIdleTestPeriod(Integer.parseInt(idleTestPeriod));
				} else {
					dconfig.setPoolMinSize(5);
				}

				dynamicDatasources.put(name, dconfig);
			}
		} catch (DocumentException e) {
			Log.getLogger(this.getClass()).error("Error al leer el archivo", e);
		}

	}

	// Interoperabilidad

	private String centerCode;
	private String centerName;
	private String centerUrl;
	private String receiveStatus;
	private String rejectMessage;
	private boolean centerEnabled;
	private String centerWorkstation;

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getCenterUrl() {
		return centerUrl;
	}

	public void setCenterUrl(String centerUrl) {
		this.centerUrl = centerUrl;
	}

	public String getReceiveStatus() {
		return receiveStatus;
	}

	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}

	public String getRejectMessage() {
		return rejectMessage;
	}

	public void setRejectMessage(String rejectMessage) {
		this.rejectMessage = rejectMessage;
	}

	public boolean getCenterEnabled() {
		return centerEnabled;
	}

	public void setCenterEnabled(boolean centerEnabled) {
		this.centerEnabled = centerEnabled;
	}

	public String getCenterWorkstation() {
		return centerWorkstation;
	}

	public void setCenterWorkstation(String centerWorkstation) {
		this.centerWorkstation = centerWorkstation;
	}

	private void reloadInteroperabilidadConfiguration(
			org.jconfig.Configuration configuration) {
		this.centerCode = configuration.getProperty("centerCode", "",
				"interoperabilidad");
		this.centerName = configuration.getProperty("centerName", "",
				"interoperabilidad");
		this.centerUrl = configuration.getProperty("centerUrl", "",
				"interoperabilidad");
		this.receiveStatus = configuration.getProperty("receiveStatus",
				"ABIERTA", "interoperabilidad");
		this.rejectMessage = configuration.getProperty("rejectMessage",
				"&#191;Desea Rechazar la carta&#63;", "interoperabilidad");
		this.centerEnabled = configuration.getBooleanProperty("centerEnabled",
				false, "interoperabilidad");
		this.centerWorkstation = configuration.getProperty("workstation",
				"CenterWorkstation", "interoperabilidad");
	}

	private String dreWorkstation;
	private String dreDerivationUnit;
	private String dreIncidentType;
	private String dreIncidentSubtype;

	private boolean suggestStreets;
	private boolean suggestIntersections;
	private boolean routesEnabled;

	private void reloadChooseAssistantConfiguration(
			org.jconfig.Configuration configuration) {
		this.suggestStreets = configuration.getBooleanProperty(
				"suggestStreets", false, "ChooseAssistant");
		this.suggestIntersections = configuration.getBooleanProperty(
				"suggestIntersections", false, "ChooseAssistant");
		this.routesEnabled = configuration.getBooleanProperty("routesEnabled",
				false, "ChooseAssistant");
	}

	public boolean isRoutesEnabled() {
		return routesEnabled;
	}

	public boolean isSuggestStreets() {
		return suggestStreets;
	}

	public boolean isSuggestIntersections() {
		return suggestIntersections;
	}

	public enum ProxyType {
		NTLM, BASIC
	}

	/**
	 * <p>
	 * Encapsula los parï¿½metros de configuraciï¿½n necesarios para salir a
	 * Internet. Contempla la posibilidad de salir a travï¿½s de un proxy NTLM
	 * (incluye dominio) o con autenticaciï¿½n bï¿½sica.
	 * </p>
	 */
	public static class Internet {
		private Boolean useProxy;
		private String proxyHost;
		private String proxyPort;
		private String proxyUser;
		private String proxyPass;
		private ProxyType proxyType;
		private String proxyDomain;

		public String getProxyHost() {
			return proxyHost;
		}

		public void setProxyHost(String proxyHost) {
			this.proxyHost = proxyHost;
		}

		public String getProxyPass() {
			return proxyPass;
		}

		public void setProxyPass(String proxyPass) {
			this.proxyPass = proxyPass;
		}

		public String getProxyPort() {
			return proxyPort;
		}

		public void setProxyPort(String proxyPort) {
			this.proxyPort = proxyPort;
		}

		public ProxyType getProxyType() {
			return proxyType;
		}

		public void setProxyType(ProxyType proxyType) {
			this.proxyType = proxyType;
		}

		public String getProxyUser() {
			return proxyUser;
		}

		public void setProxyUser(String proxyUser) {
			this.proxyUser = proxyUser;
		}

		public Boolean getUseProxy() {
			return useProxy;
		}

		public void setUseProxy(Boolean useProxy) {
			this.useProxy = useProxy;
		}

		public String getProxyDomain() {
			return proxyDomain;
		}

		public void setProxyDomain(String proxyDomain) {
			this.proxyDomain = proxyDomain;
		}
	}

	public static class Pool {
		private PoolName name;
		private Integer bufferSize;
		private Integer corePoolSize;
		private Integer maximumPoolSize;
		private Integer keepAliveSeconds;

		public Pool(PoolName name) {
			this.name = name;
		}

		public PoolName getName() {
			return name;
		}

		public void setName(PoolName name) {
			this.name = name;
		}

		public Integer getBufferSize() {
			return bufferSize;
		}

		public void setBufferSize(Integer bufferSize) {
			this.bufferSize = bufferSize;
		}

		public Integer getCorePoolSize() {
			return corePoolSize;
		}

		public void setCorePoolSize(Integer corePoolSize) {
			this.corePoolSize = corePoolSize;
		}

		public Integer getMaximumPoolSize() {
			return maximumPoolSize;
		}

		public void setMaximumPoolSize(Integer maximumPoolSize) {
			this.maximumPoolSize = maximumPoolSize;
		}

		public Integer getKeepAliveSeconds() {
			return keepAliveSeconds;
		}

		public void setKeepAliveSeconds(Integer keepAliveSeconds) {
			this.keepAliveSeconds = keepAliveSeconds;
		}
	}

	/**
	 * Retorna la configuración para el acceso a Internet.
	 */
	public Internet getInternet() {
		return internet;
	}

	public Long getMinCharactersDescription() {
		return minCharactersDescription;
	}

	public void setMinCharactersDescription(Long minCharactersDescription) {
		this.minCharactersDescription = minCharactersDescription;
	}

	public String getMultipleFiltersRole() {
		return multipleFiltersRole;
	}

	public void setMultipleFiltersRole(String multipleFiltersRole) {
		this.multipleFiltersRole = multipleFiltersRole;
	}

	public Map<String, DatasourceConfig> getDynamicDatasources() {
		return dynamicDatasources;
	}

	public void setDynamicDatasources(
			Map<String, DatasourceConfig> dynamicDatasources) {
		this.dynamicDatasources = dynamicDatasources;
	}

	public String getMultiplesCallreport() {
		return multiplesCallreport;
	}

	public void setMultiplesCallreport(String multiplesCallreport) {
		this.multiplesCallreport = multiplesCallreport;
	}

	public boolean isCommentsMandatory() {
		return commentsMandatory;
	}

	public void setCommentsMandatory(boolean commentsMandatory) {
		this.commentsMandatory = commentsMandatory;
	}

	public Integer getTimeZoneOffset() {
		return timeZoneOffset;
	}

	public void setTimeZoneOffset(Integer timeZoneOffset) {
		this.timeZoneOffset = timeZoneOffset;
	}

	public String getDreWorkstation() {
		return dreWorkstation;
	}

	public void setDreWorkstation(String dreWorkstation) {
		this.dreWorkstation = dreWorkstation;
	}

	public String getDreDerivationUnit() {
		return dreDerivationUnit;
	}

	public void setDreDerivationUnit(String dreDerivationUnit) {
		this.dreDerivationUnit = dreDerivationUnit;
	}

	public String getDreIncidentType() {
		return dreIncidentType;
	}

	public void setDreIncidentType(String dreIncidentType) {
		this.dreIncidentType = dreIncidentType;
	}

	public String getDreIncidentSubtype() {
		return dreIncidentSubtype;
	}

	public void setDreIncidentSubtype(String dreIncidentSubtype) {
		this.dreIncidentSubtype = dreIncidentSubtype;
	}

	public String[] getEditablePanels() {
		return editablePanels;
	}

	public Pool getAuditPool() {
		return auditPool;
	}

	public Pool getMessagingPool() {
		return messagingPool;
	}

	public boolean isEnableLocalizationWhenDispatched() {
		return enableLocalizationWhenDispatched;
	}

	public void setEnableLocalizationWhenDispatched(
			boolean enableLocalizationWhenDispatched) {
		this.enableLocalizationWhenDispatched = enableLocalizationWhenDispatched;
	}

	public String getJMXHost() {
		return this.JMXHost;
	}

	public String getJMXPort() {
		return this.JMXPort;
	}

	public Collection<String> getQuickActions() {
		return quickActions;
	}

	public Collection<String> getHistoryActions() {
		return historyActions;
	}

	public String getDefaultNoDispatchAction() {
		return defaultNoDispatchAction;
	}

	public boolean isExternalBroker() {
		return externalBroker;
	}

	public Long getMaxEntryCallMap() {
		return maxEntryCallMap;
	}

	public void setMaxEntryCallMap(Long maxEntryCallMap) {
		this.maxEntryCallMap = maxEntryCallMap;
	}

	public Long getDelayToWaitKeepAlive() {
		return delayToWaitKeepAlive;
	}

	public void setDelayToWaitKeepAlive(Long delayToWaitKeepAlive) {
		this.delayToWaitKeepAlive = delayToWaitKeepAlive;
	}

	public Long getDelayToTestConnectivity() {
		return delayToTestConnectivity;
	}

	public void setDelayToTestConnectivity(Long delayToTestConnectivity) {
		this.delayToTestConnectivity = delayToTestConnectivity;
	}

	public Long getTimeToRefreshAgenciesState() {
		return timeToRefreshAgenciesState;
	}

	public void setTimeToRefreshAgenciesState(Long timeToRefreshAgenciesState) {
		this.timeToRefreshAgenciesState = timeToRefreshAgenciesState;
	}

	public boolean isValidatePreviousCalls() {
		return validatePreviousCalls;
	}

	public void setValidatePreviousCalls(boolean validatePreviousCalls) {
		this.validatePreviousCalls = validatePreviousCalls;
	}

	public Integer getDaysCountPreviousCalls() {
		return daysCountPreviousCalls;
	}

	public void setDaysCountPreviousCalls(Integer daysCountPreviousCalls) {
		this.daysCountPreviousCalls = daysCountPreviousCalls;
	}

	public Long getMaxTimeForExecuteOperation() {
		return maxTimeForExecuteOperation;
	}

	public void setMaxTimeForExecuteOperation(Long maxTimeForExecuteOperation) {
		this.maxTimeForExecuteOperation = maxTimeForExecuteOperation;
	}

	public Collection<String> getDiscardPrep() {
		return discardPrep;
	}

	public void setDiscardPrep(Collection<String> discardPrep) {
		this.discardPrep = discardPrep;
	}

}
