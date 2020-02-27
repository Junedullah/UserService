/**SmartSoftware User - Service */
/**
 * Description: ControllerUser
 * Name of Project: SmartSoftware
 * Created on: FEB 13, 2020
 * Modified on: FEB 13, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.authetication.SessionManager;
import com.ss.config.ResponseMessage;
import com.ss.constant.Constant;
import com.ss.constant.MessageLabel;
import com.ss.model.User;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoRequestResponseLog;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUser;
import com.ss.repository.RepositoryUser;
import com.ss.repository.RepositoryUserDetail;
import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUser;
import com.ss.util.RequestResponseLogger;




@RestController
@RequestMapping("/user")
public class ControllerUser {

	private static final Logger LOGGER = Logger.getLogger(ControllerUser.class);

	@Autowired
	ServiceUser serviceUser;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RepositoryUser repositoryUser;

	@Autowired
	RepositoryUserDetail repositoryUserDetail;

	@Autowired
	SessionManager sessionManager;

	/*@Autowired
	ServiceUserMacAddress serviceUserIp;

	@Autowired
	ServiceUserGroup serviceUserGroup;
*/
	@Autowired
	ServiceResponse serviceResponse;

	/**
	 * @description : Create User
	 * @param request
	 * @param dtoUser
	 * @return
	 */
	@RequestMapping(value = "/createUser", method = RequestMethod.POST/*, produces = "application/json"*/)
	public ResponseMessage createUser(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
	//	UserSession session = sessionManager.validateUserSessionId(request);
		/*if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false), false);
			return responseMessage;
		} else {*/
		//	User userNameCheck = repositoryUser.findByusernameAndIsDeleted(dtoUser.getEmail(), false);
		/*	if (userNameCheck != null) {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NAME_ALREADY_EXIST, false));

				return responseMessage;
			} else {*/
				String[] result = serviceUser.saveorUpdateUser(dtoUser);
				if (result[0].equalsIgnoreCase(Constant.SUCCESS)) {
					responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_CREATED_SUCCESS, false));
				} else {
					User user = repositoryUser.findByUserId(Integer.parseInt(result[1]));
					if (user != null) {
					/*	UserDetail userDetail = repositoryUserDetail.findByUserUserId(user.getUserId());
						if (userDetail != null) {
							repositoryUserDetail.delete(userDetail);
						}*/
						repositoryUser.delete(user);
					}
					responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
							HttpStatus.INTERNAL_SERVER_ERROR, result[0]);
				}
		//	}
		//}
		return responseMessage;
	}

	/**
	 * @description : Delete Multiple Users
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/deleteMutipleUsers", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage deleteMutipleUsers(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			if (dtoUser.getUserIds() != null && !dtoUser.getUserIds().isEmpty()) {
				serviceUser.deleteMultiPleUsers(dtoUser.getUserIds());
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_DELETED_SUCCESS, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LIST_IS_EMPTY, false));
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Get Users List
	 * @param request
	 * @param dtoSearch
	 * @return
	 */
	@RequestMapping(value = "/getUsersList", method = RequestMethod.PUT/*, produces = "application/json"*/)
	public ResponseMessage getUsersList(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		DtoRequestResponseLog dtoRequestResponseLog = RequestResponseLogger.logRequest(request, dtoSearch);
		ResponseMessage responseMessage = null;
		/*UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {*/
			dtoSearch = serviceUser.getUsersList(dtoSearch);
			if (dtoSearch.getRecords() != null) {
				@SuppressWarnings("unchecked")
				List<DtoUser> list = (List<DtoUser>) dtoSearch.getRecords();
				if (list!=null && !list.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoSearch);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));

				}
			}
	//	}
//		RequestResponseLogger.logResponse(dtoRequestResponseLog, responseMessage);
		return responseMessage;
	}

	/**
	 * @description : Update User
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage updateUser(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		DtoRequestResponseLog dtoRequestResponseLog = RequestResponseLogger.logRequest(request, dtoUser);
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			String[] result = serviceUser.saveorUpdateUser(dtoUser);
			if (result[0].equalsIgnoreCase(Constant.SUCCESS)) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_UPDATED_SUCCESS, false));
			} else {
				User user = repositoryUser.findByUserId(Integer.parseInt(result[1]));
				if (user != null) {
					UserDetail userDetail = repositoryUserDetail.findByUserUserId(user.getUserId());
					if (userDetail != null) {
						repositoryUserDetail.delete(userDetail);
					}
					repositoryUser.delete(user);
				}
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR, result[0]);
			}
		}
		RequestResponseLogger.logResponse(dtoRequestResponseLog, responseMessage);
		return responseMessage;
	}

	*//**
	 * @description : Admin changes user password and send email
	 * @param user
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap changeUserPassword(@RequestBody DtoUser user, HttpServletRequest request) {
		LOGGER.info(" change user password service :::::: ");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		} else {
			User userExist = repositoryUser.findByUserIdAndIsDeleted(user.getUserId(), false);
			if (userExist != null) {
				boolean result = serviceUser.changeUserPasswordAndSendEmail(user.getPassword(), userExist);
				if (result) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.CHANGE_USER_PASSWORD_EMAIL_SENT, false));
				} else {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.NOT_FOUND, serviceResponse
							.getMessageByShortAndIsDeleted(MessageLabel.CHANGE_USER_PASSWORD_EMAIL_NOT_SENT, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}
		return new ModelMap("response", responseMessage);
	}

	*//**
	 * @description : User logout
	 * @param user
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap logoutService(@RequestBody DtoUser user, HttpServletRequest request) {
		LOGGER.info(" user logout service :::::: ");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		} else {
			User userExist = repositoryUser.findByUserIdAndIsDeleted(user.getUserId(), false);
			if (userExist != null) {
				boolean result = sessionManager.clearSessionLog(userExist.getUserId());
				if (result) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_LOGOUT_SUCCESSFULLY, false));
				} else {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}		
		return new ModelMap("response", responseMessage);
	}

	
	*//**
	 * @description : User logout
	 * @param user
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/logoutBeforeCompanySelection", method = RequestMethod.POST)
	@ResponseBody
	public ModelMap logoutServiceBeforeCompanySelection(@RequestBody DtoUser user, HttpServletRequest request) {
		LOGGER.info(" user logout service :::::: ");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionIdBeforeCompanySelection(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted("SESSION_EXPIRED", false));
		} else {
			User userExist = repositoryUser.findByUserIdAndIsDeleted(user.getUserId(), false);
			if (userExist != null) {
				boolean result = sessionManager.clearSessionLog(userExist.getUserId());
				if (result) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_LOGOUT_SUCCESSFULLY, false));
				} else {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}		
		return new ModelMap("response", responseMessage);
	}
	
	*//**
	 * @description : Get User Detail By UserId
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/getUserDetailByUserId", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage getUserDetailByUserId(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			DtoUser users = serviceUser.getUsersDetailByUserId(dtoUser);
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_DETAIL_SUCCESS, false), users);
		}
		return responseMessage;
	}

	*//**
	 * @description : Check User Name
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/checkUserName", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage checkUserName(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		if (!dtoUser.getUserName().isEmpty()) {
			User users = repositoryUser.findByusernameAndIsDeleted(dtoUser.getUserName(), false);
			if (users != null) {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NAME_ALREADY_EXIST, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NAME_NOT_EXIST, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NAME_BLANK, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : Admin changes user access over ip
	 * @param dtoUserIp
	 * @param request
	 * @return
	 * @throws ParseException
	 *//*
	@RequestMapping(value = "/saveAuthorizationDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage saveAuthorizationDetail(@RequestBody DtoAuthorizationDetail dtoAuthorization,
			HttpServletRequest request) throws ParseException {
		LOGGER.info(" save Authorization Detail service :::::: ");
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		} else {
			dtoAuthorization = serviceUser.saveAuthorizationDetail(dtoAuthorization);
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.AUTHORIZATION_DETAIL_SAVED, false),
					dtoAuthorization);
		}
		return responseMessage;
	}

	*//**
	 * @description : Check Email
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/checkEmail", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage checkEmail(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		if (!dtoUser.getEmail().isEmpty()) {
			List<User> usersList = repositoryUser.findByIsDeletedAndEmail(false, dtoUser.getEmail());
			if (usersList != null && !usersList.isEmpty()) {
				responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_EMAIL_EXIST, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_EMAIL_NOT_EXIST, false));
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_EMAIL_BLANK, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : Get Users List For Drop Down
	 * @param request
	 * @param dtoSearch
	 * @return
	 *//*
	@RequestMapping(value = "/getUsersListForDropDown", method = RequestMethod.PUT, produces = "application/json")
	public ResponseMessage getUsersListForDropDown(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			DtoSearch dtoSearch2 = serviceUser.getUsersListForDropDown(dtoSearch);
			if (dtoSearch2.getRecords() != null) {
				@SuppressWarnings("unchecked")
				List<DtoUser> list = (List<DtoUser>) dtoSearch2.getRecords();
				if (list!=null && !list.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoSearch2);

				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));

				}
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Set User IP
	 * @param request
	 * @param dtoUserIp
	 * @param responseMessage
	 * @return
	 *//*
	@RequestMapping(value = "/setUserIP", method = RequestMethod.PUT)
	public ResponseMessage setUserIP(HttpServletRequest request, @RequestBody DtoUserIp dtoUserIp,
			ResponseMessage responseMessage) {
		if (dtoUserIp.getIpAddress() != null) {
			UserSession session = sessionManager.validateUserSessionId(request);
			if (session == null) {
				responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
				return responseMessage;
			} else {
				DtoUserIp dtoUserIp2 = serviceUser.setUserIPForAuthentication(dtoUserIp);
				if (dtoUserIp2.getMessageType() == null) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_ADDED, false), true);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(dtoUserIp2.getMessageType(), false), false);
				}
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Update User IP
	 * @param request
	 * @param dtoUserIp
	 * @param responseMessage
	 * @return
	 *//*
	@RequestMapping(value = "/updateUserIP", method = RequestMethod.PUT)
	public ResponseMessage updateUserIP(HttpServletRequest request, @RequestBody DtoUserIp dtoUserIp,
			ResponseMessage responseMessage) {
		if (dtoUserIp.getIpAddress() != null) {
			UserSession session = sessionManager.validateUserSessionId(request);
			if (session == null) {
				responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
				return responseMessage;
			} else {
				Boolean isIPUpdated = serviceUser.updateUserIPForAuthentication(dtoUserIp);
				if (isIPUpdated) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_UPDATED, false), true);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_REQUIRED, false), false);
				}
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Search Users
	 * @param dtoSearch
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/searchUsers", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage searchUsers(@RequestBody DtoSearch dtoSearch, HttpServletRequest request) {
		DtoRequestResponseLog dtoRequestResponseLog = RequestResponseLogger.getInstance().logRequest(request, dtoSearch);
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoSearch = this.serviceUser.searchUsers(dtoSearch);
			if (dtoSearch != null && dtoSearch.getRecords() != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoSearch);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}

		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		RequestResponseLogger.logResponse(dtoRequestResponseLog, responseMessage);
		return responseMessage;
	}

	*//**
	 * @description : Block Unblock User
	 * @param dtoUser
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/blockUnblockUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
	public ResponseMessage blockUnblockUserGroup(@RequestBody DtoUser dtoUser, HttpServletRequest request) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session != null) {
			dtoUser = this.serviceUser.blockUnblockUser(dtoUser);
			if (dtoUser != null && dtoUser.getIsActive()) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_ACTIVATED_SUCCESS, false), dtoUser);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_BLOCKED_SUCCESS, false), dtoUser);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : Reset Password
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage resetPassword(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
			if (user != null) {
				serviceUser.resetPassword(user);
				responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RESET_PASSWORD_SUCCESS, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Update Admin Profile
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/updateAdminProfile", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
	public ResponseMessage updateAdminProfile(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false), false);
			return responseMessage;
		} else {
			User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
			if (user != null) {
				if (user.getRole().getRoleName().equalsIgnoreCase(SmartRoles.SUPERADMIN.name())) {
					String[] result = serviceUser.updateAdminProfile(dtoUser);
					if (result[0].equalsIgnoreCase(Constant.SUCCESS)) {
						responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
								serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ADMIN_PROFILE_UPDATED_SUCCESS, false));
					} else {
						responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
								HttpStatus.INTERNAL_SERVER_ERROR, result[0]);
					}
				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : Get Admin Profile Detail
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/getAdminProfileDetail", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage getAdminDetailById(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
			if (user != null) {
				if (user.getRole().getRoleName().equalsIgnoreCase(SmartRoles.SUPERADMIN.name())) {
					DtoUser users = serviceUser.getAdminProfileDetail(dtoUser);
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ADMIN_DETAIL_SUCCESS, false), users);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}
		return responseMessage;
	 }
	
	*//**
	 * @description : Save mac address
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/saveMacAddress", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage saveMacAddress(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			User user = repositoryUser.findByUserIdAndIsDeleted(dtoUser.getUserId(), false);
			if (user != null) {
				boolean response = serviceUser.saveMacAddress(dtoUser, user);
				if (response) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MAC_ADDRESS_ADDED, false));
				} else {
					responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
				}
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_NOT_FOUND, false));
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : delete mac address
	 * @param request
	 * @param dtoUser
	 * @return
	 *//*
	@RequestMapping(value = "/deleteMacAddress", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage deleteMacAddress(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			boolean response = serviceUser.deleteMacAddressOfUser(dtoUser);
			if (response) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.MAC_ADDRESS_DELETED, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.BAD_REQUEST, false));
			}
		}
		return responseMessage;
	}

	@RequestMapping(value = "/getAllUsersList", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage getAllUsersList(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			DtoSearch dtoSearch2 = serviceUser.getAllUsersList(dtoSearch);
			if (dtoSearch2.getRecords() != null) {
				@SuppressWarnings("unchecked")
				List<DtoUser> list = (List<DtoUser>) dtoSearch2.getRecords();
				if (list != null && !list.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false),
							dtoSearch2);

				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));

				}
			}
		}
		return responseMessage;
	}

	@RequestMapping(value = "/updateIpCheckedStatus", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage updateIpCheckedStatus(HttpServletRequest request, @RequestBody DtoUser dtoUser) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else if (dtoUser.getUserIds() != null && !dtoUser.getUserIds().isEmpty()) {

			boolean response = serviceUser.updateIpCheckStatusOfUser(dtoUser);
			if (response) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.IP_CHECKED_STATUS_UPDATED, false));
			} else {
				responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
						HttpStatus.INTERNAL_SERVER_ERROR,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false), null);
			}
		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.LIST_IS_EMPTY, false));
		}
		return responseMessage;
	}

	*//**
	 * @description : It will return all the Header and their side bar
	 * @param dtoScreenDetail
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/headerDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage loginScreen(HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;

		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {

			List<DtoHeader> dtoHeaderList = serviceUser.getHeaderDetail();
			if (dtoHeaderList != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.HEADER_RECORD_FOUND, false),
						dtoHeaderList);

			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : It will return all the Header and their side bar
	 * @param dtoScreenCategory
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/sideBarMenuDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage sideBarMenuDetail(HttpServletRequest request) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;

		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			List<DtoHeader> dtoHeaderList = serviceUser.getSideBarMenuDetail();
			if (dtoHeaderList != null) {
				responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.HEADER_RECORD_FOUND, false),
						dtoHeaderList);
			} else {
				responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
						serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
			}
		}
		return responseMessage;
	}
	
	@RequestMapping(value = { "/sideMenuByHeaderId" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public ResponseMessage sideMenuByModuleId(HttpServletRequest request, @RequestBody DtoHeader dtoHeader) {
		LOGGER.info("Get Screen Detail");
		ResponseMessage responseMessage = null;

		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {

			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		}

		List<DtoHeader> dtoHeaderList = serviceUser.getSideMenuByHeaderId(dtoHeader);
		if (dtoHeaderList != null) {
			responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.HEADER_RECORD_FOUND, false),
					dtoHeaderList);
		} else {
			responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));
		}

		return responseMessage;
	}

	@RequestMapping(value = "/getActiveUsersList", method = RequestMethod.PUT, produces = "application/json")
	public ResponseMessage getActiveUsersList(HttpServletRequest request, @RequestBody DtoSearch dtoSearch) {
		ResponseMessage responseMessage = null;
		UserSession session = sessionManager.validateUserSessionId(request);
		if (session == null) {
			responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
					serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
			return responseMessage;
		} else {
			dtoSearch = serviceUser.getActiveSessionUsersList(dtoSearch, session);
			if (dtoSearch.getRecords() != null) {
				@SuppressWarnings("unchecked")
				List<DtoUser> list = (List<DtoUser>) dtoSearch.getRecords();
				if (list != null && !list.isEmpty()) {
					responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.USER_SUCCESS, false), dtoSearch);
				} else {
					responseMessage = new ResponseMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
							serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.RECORD_NOT_FOUND, false));

				}
			}
		}
		return responseMessage;
	}

	*//**
	 * @description : List Of UserDetail
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/getAllUserDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseMessage getAllUserDetail(HttpServletRequest request) {
		LOGGER.info("GetAll UserDetail URL called");
		ResponseMessage responseMessage = null;
		List<DtoUserDetail> userDetailList = serviceUser.getUserDetailList();
		if (userDetailList != null && userDetailList.size() > 0) {
			responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
					serviceResponse.getMessageByShortAndIsDeleted("USER_DETAILS_SUCCESS", false), userDetailList);

		} else {
			responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
					serviceResponse.getMessageByShortAndIsDeleted("USER_DETAILS_FAIL", false));
		}
		return responseMessage;
	}*/
}
