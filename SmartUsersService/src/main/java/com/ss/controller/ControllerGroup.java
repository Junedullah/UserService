package com.ss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ss.authetication.SessionManager;
import com.ss.repository.RepositoryAccessRole;
import com.ss.repository.RepositoryRoleGroup;
import com.ss.repository.RepositoryUserGroup;
import com.ss.service.ServiceAccessRole;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ss.config.ResponseMessage;
import com.ss.constant.MessageLabel;
import com.ss.model.AccessRole;
import com.ss.model.RoleGroup;
import com.ss.model.UserGroup;
import com.ss.model.UserSession;
import com.ss.model.dto.DtoAccessRole;
import com.ss.model.dto.DtoRoleGroup;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoUserGroup;

import com.ss.service.ServiceResponse;
import com.ss.service.ServiceUserGroup;
/*
 * Description: ControllerUser
 * Name of Project: SmartSoftware
 * Created on: MARCH 23, 2020
 * @author Tehmina Ali
 * Version:
*/


@RestController
@RequestMapping("/group")
public class ControllerGroup {
    private static final Logger LOGGER = Logger.getLogger(ControllerGroup.class);
    @Autowired
    ServiceAccessRole serviceAccessRole;

    @Autowired
    ServiceUserGroup serviceUserGroup;

    @Autowired
    SessionManager sessionManager;

    @Autowired
    ServiceResponse serviceResponse;

    @Autowired
    RepositoryAccessRole repositoryAccessRole;

    @Autowired
    RepositoryRoleGroup repositoryRoleGroup;

    @Autowired
    RepositoryUserGroup repositoryUserGroup;

    /**
     * @param dtoAccessRole
     * @param request
     * @return
     * @description : Save Role
     */
    @RequestMapping(value = "/saveRole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseMessage saveRole(@RequestBody DtoAccessRole dtoAccessRole, HttpServletRequest request) {
        ResponseMessage responseMessage = null;
        UserSession session = sessionManager.validateUserSessionId(request);
        if (session != null) {
            AccessRole accessRole = repositoryAccessRole.findByRoleNameAndIsDeleted(dtoAccessRole.getRoleName(), false);
            if (accessRole == null) {
                int id = this.serviceAccessRole.saveAccessRole(dtoAccessRole);
                if (id > 0) {
                    dtoAccessRole.setAccessRoleId(id);
                    responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_SUCCESS, false), dtoAccessRole);
                } else {
                    responseMessage = new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
                }
            } else {
                responseMessage = new ResponseMessage(HttpStatus.CREATED.value(), HttpStatus.CREATED,
                        serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ACCESS_ROLE_NAME_EXIT, false));
            }
        } else {
            responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
                    serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
        }
        return responseMessage;
    }

    /**
     * @param dtoAccessRole
     * @param request
     * @return
     * @description : Get Role Detail
     */
    @RequestMapping(value = "/getRoleDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseMessage getRoleDetails(@RequestBody DtoAccessRole dtoAccessRole, HttpServletRequest request) {
        ResponseMessage responseMessage = null;

        UserSession session = sessionManager.validateUserSessionId(request);
        if (session != null) {
            dtoAccessRole = this.serviceAccessRole.getAccessRoleDetails(dtoAccessRole.getId());
            if (dtoAccessRole != null) {
                responseMessage = new ResponseMessage(HttpStatus.FOUND.value(), HttpStatus.FOUND,
                        serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_DETAILS_SUCCESS, false), dtoAccessRole);
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

    /**
     * @param dtoAccessRole
     * @param request
     * @return
     * @description : Update Role
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseMessage updateRole(@RequestBody DtoAccessRole dtoAccessRole, HttpServletRequest request) {
        ResponseMessage responseMessage = null;
        UserSession session = sessionManager.validateUserSessionId(request);
        if (session != null) {
            AccessRole accessRole = repositoryAccessRole.findByRoleNameAndAndIdNotEqual(dtoAccessRole.getRoleName(), dtoAccessRole.getId());
            if (accessRole == null) {
                int id = this.serviceAccessRole.saveAccessRole(dtoAccessRole);
                if (id > 0) {
                    dtoAccessRole.setAccessRoleId(id);
                    responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_UPDATE, false), dtoAccessRole);
                } else {
                    responseMessage = new ResponseMessage(HttpStatus.NOT_MODIFIED.value(), HttpStatus.NOT_MODIFIED,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
                }
            } else {
                responseMessage = new ResponseMessage(HttpStatus.FOUND.value(),
                        HttpStatus.FOUND,
                        serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_GROUP_NAME_EXIT, false));
            }

        } else {
            responseMessage = new ResponseMessage(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED,
                    serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.FORBIDDEN, false));
        }
        return responseMessage;
    }

    /**
     * @param dtoSearch
     * @param request
     * @return
     * @description : Search Access Roles
     */
    @RequestMapping(value = "/searchAccessRoles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseMessage searchRoles(@RequestBody DtoSearch dtoSearch, HttpServletRequest request) {
        ResponseMessage responseMessage = null;
        UserSession session = sessionManager.validateUserSessionId(request);
        if (session != null) {
            dtoSearch = this.serviceAccessRole.searchAcessRole(dtoSearch);
            if (dtoSearch != null && dtoSearch.getRecords() != null) {
                responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
                        this.serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_LIST_SUCCESS, false), dtoSearch);
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

    /**
     * @description : Delete Roles
     * @param dtoAccessRole
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteRoles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, headers = "Accept=application/json")
    public ResponseMessage deleteRoles(@RequestBody DtoAccessRole dtoAccessRole, HttpServletRequest request) {
        ResponseMessage responseMessage = null;

        UserSession session = sessionManager.validateUserSessionId(request);
        if (session != null) {
            if (dtoAccessRole.getIds() != null && !dtoAccessRole.getIds().isEmpty()) {
                boolean status = this.serviceAccessRole.deleteMultipleRoles(dtoAccessRole.getIds());
                if (status) {
                    responseMessage = new ResponseMessage(HttpStatus.OK.value(), HttpStatus.OK,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ROLE_DELETE_SUCCESS, false));
                } else {
                    responseMessage = new ResponseMessage(HttpStatus.NOT_MODIFIED.value(), HttpStatus.NOT_MODIFIED,
                            serviceResponse.getMessageByShortAndIsDeleted(MessageLabel.ERROR_OCCURED, false));
                }
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

}
