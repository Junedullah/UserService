/**SmartSoftware User - Service */
/**
 * Description: serviceWhiteListIp
 * Name of Project: SmartSoftware
 * Created on: March 22, 2020
 * Modified on: March 22, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ss.model.WhitelistIp;
import com.ss.model.dto.DtoSearch;
import com.ss.model.dto.DtoWhiteListIp;
import com.ss.repository.RepositoryException;
import com.ss.repository.RepositoryWhiteListIp;


@Service("serviceWhiteListIp")
public class ServiceWhiteListIp {

	private static final Logger LOGGER = Logger.getLogger(ServiceWhiteListIp.class);

	@Autowired
	RepositoryWhiteListIp repositoryWhiteListIp;

	@Autowired(required = false)
	HttpServletRequest httpServletRequest;

	@Autowired
	RepositoryException repositoryException;

	private static final String USER_ID ="userid";
	/**
	 * Description: save white list IP
	 * @param dtoWhiteListIp
	 * @return
	 */
	public DtoWhiteListIp addWhiteListIP(DtoWhiteListIp dtoWhiteListIp) {

		int loggedInUserId = Integer.parseInt(this.httpServletRequest.getHeader(USER_ID));

		WhitelistIp whitelistIp = this.repositoryWhiteListIp.findByIpAddressAndIsDeleted(dtoWhiteListIp.getIpAddress(),
				false);
		if (whitelistIp != null) {
			dtoWhiteListIp.setMessageType("IP_ALREADY_ADDED");
			return dtoWhiteListIp;
		} else {
			whitelistIp = new WhitelistIp();
			whitelistIp.setCreatedBy(loggedInUserId);
			whitelistIp.setCreatedDate(new Date());
			whitelistIp.setDescription(dtoWhiteListIp.getDescription());
			whitelistIp.setIpAddress(dtoWhiteListIp.getIpAddress());
			whitelistIp.setIsActive(true);
			whitelistIp.setIsDeleted(false);
			whitelistIp.setUpdatedDate(new Date());
			whitelistIp = this.repositoryWhiteListIp.saveAndFlush(whitelistIp);
			if (whitelistIp != null) {
				return new DtoWhiteListIp(whitelistIp);
			}
		}
		return null;
	}

	/**
	 * Description: search white list IP
	 * @param dtoSearch
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public DtoSearch searchWhiteListIPs(DtoSearch dtoSearch) {
		if (dtoSearch != null) {
			dtoSearch.setTotalCount(
					this.repositoryWhiteListIp.predictiveWhitelistIpSearchTotalCount(dtoSearch.getSearchKeyword()));
			List<WhitelistIp> whitelistIpList = this.repositoryWhiteListIp.predictiveWhitelistIpSearchWithPagination(
					dtoSearch.getSearchKeyword(),
					new PageRequest(dtoSearch.getPageNumber(), dtoSearch.getPageSize(), Direction.DESC, "createdDate"));
			if (whitelistIpList != null && !whitelistIpList.isEmpty()) {
				List<DtoWhiteListIp> dtoWhiteListIpList = new ArrayList<>();
				for (WhitelistIp whitelistIp : whitelistIpList) {
					dtoWhiteListIpList.add(new DtoWhiteListIp(whitelistIp));
				}
				dtoSearch.setRecords(dtoWhiteListIpList);
			}
		}
		return dtoSearch;
	}

	/**
	 * Description: get white list IP by id
	 * @param whiteListIpId
	 * @return
	 */
	public DtoWhiteListIp getWhiteListIPById(int whiteListIpId) {
		DtoWhiteListIp dtoWhiteListIp = null;
		WhitelistIp whitelistIp = repositoryWhiteListIp.findByWhitelistIpIdAndIsDeleted(whiteListIpId, false);
		if (whitelistIp != null) {
			dtoWhiteListIp = new DtoWhiteListIp(whitelistIp);
		}
		return dtoWhiteListIp;
	}

	/**
	 * Description: update white list IP
	 * @param dtoWhiteListIp
	 * @return
	 */
	public DtoWhiteListIp updateWhiteListIPById(DtoWhiteListIp dtoWhiteListIp) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		WhitelistIp whitelistIp = repositoryWhiteListIp
				.findByWhitelistIpIdAndIsDeleted(dtoWhiteListIp.getWhitelistIpId(), false);
		if (whitelistIp != null) {
			whitelistIp.setDescription(dtoWhiteListIp.getDescription());
			whitelistIp.setIpAddress(dtoWhiteListIp.getIpAddress());
			whitelistIp.setUpdatedBy(loggedInUserId);
			this.repositoryWhiteListIp.saveAndFlush(whitelistIp);
		} else {
			dtoWhiteListIp = null;
		}
		return dtoWhiteListIp;

	}

	/**
	 * Description: delete white list IP (one or more)
	 * @param deleteIds
	 * @return
	 */
	public boolean deleteWhiteListIPById(List<Integer> deleteIds) {
		boolean status = false;
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		try {
			repositoryWhiteListIp.deleteWhiteListIps(deleteIds, true, loggedInUserId);
			status = true;
		} catch (NumberFormatException e) {
			LOGGER.info(Arrays.toString(e.getStackTrace()));
		}
		return status;
	}

	/**
	 * Description: block/unblock white list IP
	 * @param dtoWhiteListIp
	 * @return
	 */
	public DtoWhiteListIp blockUnblockWhiteListIP(DtoWhiteListIp dtoWhiteListIp) {
		int loggedInUserId = Integer.parseInt(httpServletRequest.getHeader(USER_ID));
		WhitelistIp whitelistIp = repositoryWhiteListIp
				.findByWhitelistIpIdAndIsDeleted(dtoWhiteListIp.getWhitelistIpId(), false);
		if (whitelistIp != null) {
			whitelistIp.setIsActive(dtoWhiteListIp.getIsActive());
			whitelistIp.setUpdatedBy(loggedInUserId);
			this.repositoryWhiteListIp.saveAndFlush(whitelistIp);
		}
		return dtoWhiteListIp;
	}

}
