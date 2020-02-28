
package com.ss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.Company;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoCompany;
import com.ss.model.dto.DtoCompanyStats;
import com.ss.model.dto.DtoSearch;
import com.ss.repository.RepositoryCompany;
import com.ss.service.ServiceCompany;
import com.ss.service.ServiceResponse;

/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the comapny database table.
 * Name of Project: SmartUserSoftware
 * Created on: fab 11, 2020
 * Modified on: fab 11, 2020 11:19:38 AM
 * @author shahnawaz
 * Version: 
 */
@RestController
@RequestMapping("/company")
public class ControllerCompany {

	@Autowired
	ServiceCompany serviceCompnay;

	@Autowired
	SessionManager sessionManager;

	@Autowired
	ServiceResponse serviceResponse;

	@Autowired
	RepositoryCompany repositoryCompany;

	/**
	 * @description : Create Company
	 * @param request
	 * @param dtoCompany
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseMessage createCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			
			Company company=repositoryCompany.findTop1ByIsDeletedAndIsActiveAndName(false,true,dtoCompany.getName());
			if(company == null){
			dtoCompany = serviceCompnay.saveOrUpdateCompany(dtoCompany);
			if (dtoCompany != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_CREATED, false), dtoCompany);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_NOT_CREATED, false), dtoCompany);
			}
		}
		else
		{
			responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_ALREADY_EXIST, false),
					dtoCompany);
		}
			
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseMessage updateCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoCompany = serviceCompnay.saveOrUpdateCompany(dtoCompany);
			if (dtoCompany != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_UPDATE_SUCCESS, false), dtoCompany);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_LIST_NOT_GETTING, false), dtoCompany);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.PUT)
	public ResponseMessage deleteCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			if (dtoCompany.getIds() != null && !dtoCompany.getIds().isEmpty()) {
				DtoCompany dtoCompany2 = serviceCompnay.deleteCompany(dtoCompany.getIds());
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_DELETED, false), dtoCompany2);

			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LIST_IS_EMPTY, false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	/**
	 * @description : Get all companies
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/getAll", method = RequestMethod.PUT)
	public ResponseMessage getAllCompany(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceCompnay.getAllCompany(dtoCompany);
			if (dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_LIST_NOT_GETTING, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/getCompanyById", method = RequestMethod.POST)
	public ResponseMessage getCompanyById(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoCompany dtoCompanyObj = serviceCompnay.getCompanyByCompanyId(dtoCompany.getId());
			if (dtoCompanyObj != null) {
				if (dtoCompany.getMessageType() == null) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_DETAIL, false), dtoCompanyObj);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted(dtoCompany.getMessageType(), false),
							dtoCompanyObj);
				}

			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_NOT_GETTING, false), dtoCompanyObj);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}


	
	@RequestMapping(value="/getCompanyByTenantId",method=RequestMethod.POST)
	public ResponseMessage getCompanyByTenantId(@RequestBody DtoCompany dtoCompany , HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoCompany = serviceCompnay.getCompanyByTenantId(dtoCompany);
			if(dtoCompany != null) {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_ALREADY_EXIST, false),dtoCompany);
			}else {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		}else{
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		
		return responseMessage;
	}

	
	@RequestMapping(value = "/checkCompanyName", method = RequestMethod.POST)
	public ResponseMessage checkCompanyName(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
		    boolean response = serviceCompnay.getCompanyByCompanyName(dtoCompany.getName());
			if (response) 
			{
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_ALREADY_EXIST, false));
			}
			else 
			{
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
			
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	
	@RequestMapping(value = "/getCompanyListForDropDown", method = RequestMethod.PUT)
	public ResponseMessage getCompanyListForDropDown(HttpServletRequest request, @RequestBody DtoCompany dtoCompany) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoSearch dtoSearch = serviceCompnay.getAllCompanyListForDropDown(dtoCompany);
			if (dtoSearch.getRecords() != null) {
				@SuppressWarnings("unchecked")
				List<DtoCompany> list = (List<DtoCompany>) dtoSearch.getRecords();
				if (list!=null && !list.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), dtoSearch);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));

				}

			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/blockUnblockCompany", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage blockUnblockCompany(@RequestBody DtoCompany dtoCompany, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoCompany = this.serviceCompnay.blockUnblockCompany(dtoCompany);
			if (dtoCompany != null && dtoCompany.getIsActive()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_ACTIVATED_SUCCESS, false),
						dtoCompany);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_BLOCKED_SUCCESS, false),
						dtoCompany);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	@RequestMapping(value = "/searchCompanies", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage searchCompanies(@RequestBody DtoSearch dtoSearch, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoSearch = this.serviceCompnay.searchCompanies(dtoSearch);
			if (dtoSearch != null && dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_LIST_NOT_GETTING, false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}

		return responseMessage;
	}
	
	@RequestMapping(value = "/companyListCountOfUsers", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getCompaniesListWithCountOfUsers(HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			List<DtoCompany> list = this.serviceCompnay.getTotalCompaniesWithTotalUser();
			if (list!=null && !list.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_ALL, false), list);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	@RequestMapping(value = "/getCompanyStats", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage getCompanyStats(@RequestBody DtoCompany dtoCompany, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			DtoCompanyStats dtoCompanyStats = this.serviceCompnay.getCompanyStats(dtoCompany.getId());
			if (dtoCompanyStats != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_GET_DETAIL, false),
						dtoCompanyStats);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.COMPANY_NOT_GETTING, false), null);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}
	
	}
