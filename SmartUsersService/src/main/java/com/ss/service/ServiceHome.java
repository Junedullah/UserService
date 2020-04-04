/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the user_detail database table.
 * Name of Project: SmartSoftware
 * Created on: Mar 30, 2020
 * Modified on: Mar 30, 2020 11:19:38 AM
 * @author Juned Baig
 * Version: 
 */
package com.ss.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.constant.Constant;
import com.ss.model.CityMaster;
import com.ss.model.CommonConstant;
import com.ss.model.Company;
import com.ss.model.CountryMaster;
import com.ss.model.Field;
import com.ss.model.FieldValidation;
import com.ss.model.Language;
import com.ss.model.Module;
import com.ss.model.Screen;
import com.ss.model.StateMaster;
import com.ss.model.User;
import com.ss.model.UserCompanyRelation;
import com.ss.model.UserDetail;
import com.ss.model.UserSession;
import com.ss.model.ValidationMessage;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoCountry;
import com.ss.model.dto.DtoLanguage;
import com.ss.model.dto.DtoModule;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.repository.RepositoryAuthorizationSetting;
import com.ss.repository.RepositoryCityMaster;
import com.ss.repository.RepositoryCommonConstant;
import com.ss.repository.RepositoryCompany;
import com.ss.repository.RepositoryCountryMaster;
import com.ss.repository.RepositoryFieldValidation;
import com.ss.repository.RepositoryFields;
import com.ss.repository.RepositoryLanguage;
import com.ss.repository.RepositoryLoginOtp;
import com.ss.repository.RepositoryModule;
import com.ss.repository.RepositoryRole;
import com.ss.repository.RepositoryRoleGroup;
import com.ss.repository.RepositoryScreen;
import com.ss.repository.RepositoryStateMaster;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserCompanyRelation;
import com.ss.repository.RepositoryUserDetail;
import com.ss.repository.RepositoryUserDetailPagination;
import com.ss.repository.RepositoryUserDraft;
import com.ss.repository.RepositoryUserGroup;
import com.ss.repository.RepositoryUserSession;
import com.ss.repository.RepositoryValidationMessages;
import com.ss.repository.RepositoryWeekDay;
import com.ss.util.CodeGenerator;
import com.ss.util.UtilRandomKey;


/**
 * Description: Service Home
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: MARCH 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Service("serviceHome")
@Transactional
@EnableScheduling
public class ServiceHome {

	private static final Logger LOGGER = Logger.getLogger(ServiceHome.class);

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	RepositoryRole repositoryRole;

	@Autowired
	RepositoryUserGroup repositoryUserGroup;

	@Autowired
	RepositoryUserDetailPagination repositoryUserDetailPagination;

	@Autowired
	RepositoryRoleGroup repositoryRoleGroup;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepositoryLoginOtp repositoryLoginOtp;

	@Autowired
	ServiceEmailHandler serviceEmailHandler;

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanies;

	@Autowired
	RepositoryCompany repositoryCompany;

	@Autowired
	CodeGenerator codeGenerator;

	@Autowired
	RepositoryAuthorizationSetting repositoryAuthorizationSetting;

	@Autowired
	RepositoryCountryMaster repositoryCountryMaster;

	@Autowired
	RepositoryStateMaster repositoryStateMaster;

	@Autowired
	RepositoryCityMaster repositoryCityMaster;

	@Autowired
	RepositoryLanguage repositoryLanguage;	

	@Autowired
	RepositoryModule repositoryModule;

	@Autowired
	RepositoryScreen repositoryScreen;

	@Autowired
	RepositoryFields repositoryFields;

	@Autowired
	RepositoryValidationMessages repositoryValidationMessages;

	@Autowired
	RepositoryFieldValidation repositoryFieldValidation;

	@Autowired
	ServiceResponse serviceResponse;

	/*	@Autowired
	RepositoryCommonConstant repositoryCommonConstant;*/

	@Autowired
	RepositoryUserCompanyRelation repositoryUserCompanyRelation;

	/*	@Autowired
	RepositorySstMessage repositorySstMessage;*/

	@Autowired
	RepositoryWeekDay repositoryWeekDay;

	@Autowired
	RepositoryUserSession repositoryUserSession;
	
	@Autowired
	RepositoryCommonConstant repositoryCommonConstant;

	@Autowired
	RepositoryUserDraft repositoryUserDraft;

	@Value("${script.accessfinancialpath}")
	private String accessfinancialpath;

	private final Integer ENG_LANG_ID=1;


	/**
	 * Description: get country list for drop down
	 * @return
	 */
	public List<DtoCountry> getCountryList() {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> countryList = new ArrayList<>();
		List<CountryMaster> list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, Integer.parseInt(langId));

		if(list.isEmpty()){
			list = repositoryCountryMaster.findByIsDeletedAndIsActiveAndLanguageLanguageId(false, true, 1);
		}

		if (list != null && !list.isEmpty()) {
			for (CountryMaster countryMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCountryId(countryMaster.getCountryId());
				dtoCountry.setCountryName(countryMaster.getCountryName());
				dtoCountry.setShortName(countryMaster.getShortName());
				dtoCountry.setCountryCode(countryMaster.getCountryCode());
				countryList.add(dtoCountry);
			}
		}
		return countryList;
	}

	/**
	 * Description: get state list by country id for drop down
	 * @param countryId
	 * @return
	 */
	public List<DtoCountry> getStateListByCountryId(int countryId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> stateList = new ArrayList<>();
		List<StateMaster> list = repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, Integer.parseInt(langId));
		if(list.isEmpty()){
			list=repositoryStateMaster.findByCountryMasterCountryIdAndIsDeletedAndLanguageLanguageId(countryId, false, 1);
		}
		if (list != null && !list.isEmpty()) {
			for (StateMaster stateMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setStateId(stateMaster.getStateId());
				dtoCountry.setStateName(stateMaster.getStateName());
				stateList.add(dtoCountry);
			}
		}
		return stateList;
	}

	/**
	 * Description: get city list by state id for drop down
	 * @param stateId
	 * @return
	 */
	public List<DtoCountry> getCityListByStateId(int stateId) {
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<DtoCountry> cityList = new ArrayList<>();
		List<CityMaster> list = repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,Integer.parseInt(langId));

		if(list.isEmpty()){
			list=repositoryCityMaster.findByStateMasterStateIdAndIsDeletedAndLanguageLanguageId(stateId, false,1);
		}

		if (list != null && !list.isEmpty()) {
			for (CityMaster cityMaster : list) {
				DtoCountry dtoCountry = new DtoCountry();
				dtoCountry.setCityId(cityMaster.getCityId());
				dtoCountry.setCityName(cityMaster.getCityName());
				cityList.add(dtoCountry);
			}
		}
		return cityList;
	}

	/**
	 * Description: get country code by country id
	 * @param countryId
	 * @return
	 */
	public DtoCountry getCountryCodeByCountryId(int countryId) {
		DtoCountry dtoCountry = new DtoCountry();
		CountryMaster countryMaster = repositoryCountryMaster.findByCountryIdAndIsDeletedAndIsActive(countryId, false,
				true);
		if (countryMaster != null) {

			dtoCountry.setCountryId(countryMaster.getCountryId());
			dtoCountry.setCountryCode("+" + countryMaster.getCountryCode());
		}
		return dtoCountry;
	}

	/**
	 * Description: reset password for user by super admin
	 * @param user
	 * @param newPassword
	 */
	public void resetPasswordOfUser(User user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPassword(false);
		repositoryUser.saveAndFlush(user);

	}

	/**
	 * 
	 * @param user
	 * @param password
	 */
	public void changePasswordOfUser(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
		user.setResetPassword(true);
		repositoryUser.saveAndFlush(user);
	}

	/**
	 * @param dtoUser
	 * @return
	 */
	public boolean matchOldPassword(DtoUser dtoUser) {
		User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
		return user != null && passwordEncoder.matches(dtoUser.getPassword(), user.getPassword());
	}

	/**
	 * @param user
	 * @return
	 */
	public Boolean sendForgotPasswordEmail(User user) 
	{
		Boolean status = false;
		try {
			if (user != null) {
				// send forgot password email
				String newPassword = new UtilRandomKey().nextRandomKey();
				user.setPassword(this.passwordEncoder.encode(newPassword));
				user.setResetPassword(true);
				user = this.repositoryUser.saveAndFlush(user);
				UserDetail userDetail = this.repositoryUserDetail.findByUserUserId(user.getUserId());
				String firstName = null;
				if (userDetail != null && userDetail.getFirstName() != null) {
					firstName = userDetail.getFirstName();
				} else {
					firstName = "";
				}
				serviceEmailHandler.sendForgotPasswordEmail(user.getEmail(), newPassword, firstName);
				status = true;
			}
		} catch (Exception e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}

		return status;
	}
	
	/**
	 * Description: get language list for drop down
	 * @return
	 */
	public DtoSearch getLanguageList(DtoSearch dtoSearch ) 
	{
		List<Language> list =null;
		dtoSearch.setTotalCount(repositoryLanguage.getTotalCount());
		List<DtoLanguage> languagesList = new ArrayList<>();
		
		if (dtoSearch.getPageNumber() != null && dtoSearch.getPageSize() != null) 
		{
			Pageable pageable = new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize());
			list = repositoryLanguage.findByIsDeleted(false,pageable);
		}
		else
		{
			list= repositoryLanguage.findByIsDeleted(false);
		}
		
		if (list != null && !list.isEmpty()) 
		{
			for (Language language : list) 
			{
				DtoLanguage dtoLanguage = new DtoLanguage(language);
				
				if (language.getIsActive()) {
					dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted(Constant.ACTIVE, false).getMessage());
				} else {
					dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted(Constant.INACTIVE, false).getMessage());
				}
				
				languagesList.add(dtoLanguage);
			}
		}
		
		dtoSearch.setRecords(languagesList);
		return dtoSearch;
	}

	/**
	 * 
	 * @return List Languages
	 */
	public List<DtoLanguage> getLanguageListForDropDown() {
		List<DtoLanguage> languagesList=new ArrayList<>();
		List<Language> list =null;
		list= repositoryLanguage.findByIsDeletedAndIsActive(false,true);
		if (list != null && !list.isEmpty()) 
		{
			for (Language language : list) 
			{
				DtoLanguage dtoLanguage = new DtoLanguage(language);
				
				if (language.getIsActive()) {
					dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
				} else {
					dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
				}
				
				languagesList.add(dtoLanguage);
			}
		}
		
		return languagesList;
	}
	
	public DtoLanguage addNewLanguage(DtoLanguage dtoLanguage) throws IOException 
	{
		Language language= new Language();
		language.setLanguageName(dtoLanguage.getLanguageName());
		language.setLanguageOrientation(dtoLanguage.getLanguageOrientation());
		language.setIsActive(true);
		language=repositoryLanguage.save(language);	
		final ServletContext servletContext = httpServletRequest.getSession().getServletContext();      
	    final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	    final String temperotyFilePath = tempDirectory.getAbsolutePath();
	    String filePathTemp = temperotyFilePath+"//"+"newLanguage.xlsx";
	    File csvFile =  new File(filePathTemp);
	    if(csvFile.exists()){
		   csvFile.deleteOnExit();
	    }
	    dtoLanguage.getFile().transferTo(new File(filePathTemp));
	    uploadNewLanguageData(language,filePathTemp);
		return new DtoLanguage(language);
	}
	
	public DtoLanguage updateLanguage(DtoLanguage dtoLanguage) 
	{
		Language language= repositoryLanguage.findOne(dtoLanguage.getLanguageId());
		language.setLanguageName(dtoLanguage.getLanguageName());
		language.setLanguageOrientation(dtoLanguage.getLanguageOrientation());
		language=repositoryLanguage.save(language);	
		return new DtoLanguage(language);
	}
	
	public DtoLanguage getLanguageByLangId(DtoLanguage dtoLanguage) 
	{
		Language language= repositoryLanguage.findByLanguageIdAndIsDeleted(dtoLanguage.getLanguageId(), false);
		dtoLanguage= new DtoLanguage(language);
		if (language.getIsActive()) {
			dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
		} else {
			dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
		}
		return dtoLanguage;
	}
	
	public DtoLanguage getLanguageByLanguageId(DtoLanguage dtoLanguage) 
	{
		Language language= repositoryLanguage.findOne(dtoLanguage.getLanguageId());
		dtoLanguage= new DtoLanguage(language);
		
		if (language.getIsActive()) {
			dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("ACTIVE", false).getMessage());
		} else {
			dtoLanguage.setLanguageStatus(serviceResponse.getMessageByShortAndIsDeleted("INACTIVE", false).getMessage());
		}
		
		return dtoLanguage;
	}
	
	public void deleteLanguage(Language language) 
	{
		 repositoryLanguage.delete(language);
	}
	
	public void uploadNewLanguageData(Language language, String tempraryFilePath )
	{
		 try {
	            FileInputStream excelFile = new FileInputStream(new File(tempraryFilePath));
	            Workbook workbook = new XSSFWorkbook(excelFile);
	            Sheet moduleSheet = workbook.getSheetAt(0);
                
	            for (int i = 1; i <= moduleSheet.getLastRowNum(); i++) 
	            {
	            	Row nextRow = moduleSheet.getRow(i);
                    if(nextRow==null){
                        break;
                    }
                    
                    String moduleCode  =  nextRow.getCell(0).getStringCellValue();
                    String moduleDescription =  nextRow.getCell(1).getStringCellValue();
                    String modulname =  nextRow.getCell(2).getStringCellValue();
                    String helpMessage = nextRow.getCell(3).getStringCellValue();
                    
                    Module module = new Module();
                    module.setDescription(moduleDescription);
                    module.setHelpMessage(helpMessage);
                    module.setLanguage(language);
                    module.setModuleCode(moduleCode);
                    module.setName(modulname);
                    module.setCreatedBy(1);
                    repositoryModule.saveAndFlush(module);
	            }
	            
	            Sheet screenSheet = workbook.getSheetAt(1);
	            for (int i = 1; i <= screenSheet.getLastRowNum(); i++) 
	            {
	            	Row nextRow = screenSheet.getRow(i);
                    if(nextRow==null){
                        break;
                    }
                    
                    String screenCode  =  nextRow.getCell(0).getStringCellValue();
                    String moduleCode =  nextRow.getCell(1).getStringCellValue();
                    String descr =  nextRow.getCell(2).getStringCellValue();
                    String sideMenuUrl = nextRow.getCell(3).getStringCellValue();
                    String helpMessage =  nextRow.getCell(4).getStringCellValue();
                    String screenName =  nextRow.getCell(5).getStringCellValue();
                    String sideMenu = nextRow.getCell(6).getStringCellValue();
                    
                    Screen screen = new Screen();
                    screen.setScreenCode(screenCode);
                    screen.setModule(repositoryModule.findByModuleCodeAndIsDeletedAndLanguageLanguageId(moduleCode, false, language.getLanguageId()));
                    screen.setDescription(descr);
                    screen.setLanguage(language);
                    if(UtilRandomKey.isNotBlank(sideMenuUrl)){
                    	screen.setSideMenuURL(sideMenuUrl);
                    }
                    screen.setHelpMessage(helpMessage);
                    screen.setScreenName(screenName);
                    if(UtilRandomKey.isNotBlank(sideMenu)){
                    	   screen.setSideMenu(sideMenu);
                    }
                    repositoryScreen.save(screen);
	            }
	            
	            Sheet fieldSheet = workbook.getSheetAt(2);
	            for (int i = 1; i <= fieldSheet.getLastRowNum(); i++) 
	            {
	            	Row nextRow = fieldSheet.getRow(i);
                    if(nextRow==null){
                        break;
                    }
                    
                    Double fieldCode = nextRow.getCell(0).getNumericCellValue();
                    int fielCode = fieldCode.intValue();
                    String screenCode =  nextRow.getCell(1).getStringCellValue();
                    String fieldName =  nextRow.getCell(2).getStringCellValue();
                    String description = nextRow.getCell(3).getStringCellValue();
                    String fieldShort =  nextRow.getCell(4).getStringCellValue();
                    String helpMessage =  nextRow.getCell(5).getStringCellValue();
                    
                    Field field = new Field();
                    field.setFieldCode(String.valueOf(fielCode));
                    field.setFieldName(fieldName);
                    field.setDescription(description);
                    field.setScreen(repositoryScreen.findByScreenCodeAndIsDeletedAndLanguageLanguageId(screenCode, false, language.getLanguageId()));
                    field.setFieldShort(fieldShort);
                    field.setLanguage(language);
                    field.setHelpMessage(helpMessage);
                    repositoryFields.save(field);
	            }
	            
	            Sheet validationMessageSheet = workbook.getSheetAt(3);
	            for (int i = 1; i <= validationMessageSheet.getLastRowNum(); i++) 
	            {
	            	Row nextRow = validationMessageSheet.getRow(i);
	            	String messageShort =  nextRow.getCell(0).getStringCellValue();
                    String message =  nextRow.getCell(1).getStringCellValue();
	            	ValidationMessage validationMessage = new ValidationMessage();
	            	validationMessage.setMessage(message);
	            	validationMessage.setMessageShort(messageShort);
	            	validationMessage.setLanguage(language);
	            	repositoryValidationMessages.save(validationMessage);
	            }
	            
	            
	            Sheet fieldValidationSheet = workbook.getSheetAt(4);
	            for (int i = 1; i <= fieldValidationSheet.getLastRowNum(); i++) 
	            {
	            	Row nextRow = fieldValidationSheet.getRow(i);
                    if(nextRow==null){
                        break;
                    }
                    
                    Double fieldCode = nextRow.getCell(0).getNumericCellValue();
                    int fielCode = fieldCode.intValue();
                    
                    Double validationMessageId = nextRow.getCell(1).getNumericCellValue();
                    int valMessageId = validationMessageId.intValue();
                    
                    FieldValidation fieldValidation = new FieldValidation();
                    fieldValidation.setField(repositoryFields.findByFieldCodeAndLanguageLanguageId(String.valueOf(fielCode), language.getLanguageId()));
                    ValidationMessage validationMessage = repositoryValidationMessages.findOne(valMessageId);
                    if(validationMessage!=null)
                    {
                	   String messageShort = validationMessage.getMessageShort();
                	   fieldValidation.setValidationMessage(repositoryValidationMessages.findByMessageShortAndLanguageLanguageId(messageShort, language.getLanguageId()));
                	   repositoryFieldValidation.save(fieldValidation);
                    }
	            }
	        } 
		    catch (IOException e) {
		    	LOGGER.info(Arrays.toString(e.getStackTrace()));
	        } 
	}
	
	public DtoLanguage blockUnblockLanguage(DtoLanguage dtoLanguage) {
		Language language = this.repositoryLanguage.findByLanguageIdAndIsDeleted(dtoLanguage.getLanguageId(), false);
		if (language != null) {
			language.setIsActive(dtoLanguage.getIsActive());
			this.repositoryLanguage.saveAndFlush(language);
		}
		return dtoLanguage;
	}
	
	@Scheduled(fixedDelay = 60000*3) // executed at every 5 minutes
	public List<DtoModule> getAllModuleByLanguageAndIsActive() {
		String langId = httpServletRequest.getHeader("langid");
		List<DtoModule> dtoModuleList = new ArrayList<>();
		try {
			List<Module> moduleList = repositoryModule.findByIsDeletedAndIsActiveAndLanguageLanguageId(false,true,Integer.parseInt(langId));
			if (moduleList != null && moduleList.size() > 0) {
				for (Module module : moduleList) {
					if(module.getIsActive()) {
						DtoModule dtoModule = new DtoModule(module);
						dtoModule.setModuleId(module.getModuleId());
						dtoModule.setModuleCode(module.getModuleCode());
						dtoModule.setModuleName(module.getName());
						dtoModuleList.add(dtoModule);
					}
					
				}
			}
		} catch (Exception e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}
		return dtoModuleList;
	}
	
	public Map<String, String> getCommonConstantList() 
	{
		
		Map<String, String> map=new HashMap<>();
		String langId = httpServletRequest.getHeader(Constant.LANG_ID);
		List<CommonConstant> list = repositoryCommonConstant.findByIsDeletedAndLanguageLanguageId(false,Integer.parseInt(langId));
		if (list != null && !list.isEmpty()) 
		{
			for (CommonConstant constant : list) 
			{
				map.put(constant.getConstantShort(),constant.getConstantValue());
			}
		}
		return map;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<DtoCompany> getCompaniesListByUserId(int userId) {
		List<DtoCompany> dtoCompanyList = new ArrayList<>();
		Map<Integer,String> map = new HashMap<>();
		List<UserCompanyRelation> userCompanyRelations = this.repositoryUserCompanyRelation.findByUserUserIdAndIsDeleted(userId, false);
		if (userCompanyRelations != null && !userCompanyRelations.isEmpty()) 
		{
			for (UserCompanyRelation userCompanyRelation : userCompanyRelations) 
			{
				Company company = userCompanyRelation.getCompany();
				 
					if(company!=null && map.get(company.getCompanyId())==null)
					{
						map.put(company.getCompanyId(), company.getName());
						DtoCompany dtoCompany = new DtoCompany();
						dtoCompany.setCompanyId(company.getCompanyId());
						dtoCompany.setName(company.getName());
						dtoCompany.setCompanyCode(company.getCompanyCode());
						
						if (UtilRandomKey.isNotBlank(company.getTenantId())) {
							dtoCompany.setTenantId(company.getTenantId());
						} else {
							dtoCompany.setTenantId("");
						}
						
						dtoCompanyList.add(dtoCompany);
					}
				 
			}
		}
		return dtoCompanyList;
	}

	public String getCompanyTenant(Integer companyId) {
		Company company = repositoryCompany.findByCompanyIdAndIsDeleted(companyId, false);
		if(company!=null){
			return company.getTenantId();
		}
		return null;
	}
	
	public int getCompanyIdFromTenant(String companyTenant) {
		Company company = repositoryCompany.findByTenantIdAndIsDeleted(companyTenant, false);
		if(company!=null){
			return company.getCompanyId();
		}
		return 0;
	}
	
	public String getCompanyName(Integer companyId) {
		Company company = repositoryCompany.findByCompanyIdAndIsDeleted(companyId, false);
		if(company!=null){
			return company.getName();
		}
		return null;
	}
	
	public boolean updateActiveSession(DtoUser dtoUser){
		UserSession userSession = repositoryUserSession.findByUserUserIdAndSessionAndIsDeleted(dtoUser.getUserId(), dtoUser.getSession(), false);
		if(userSession!=null){
			userSession.setUpdatedDate(new Date());
			repositoryUserSession.saveAndFlush(userSession);
			return true;
		}
		return false;
	}
}
