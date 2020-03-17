/**
 * SST - SMART SOFTWARE TECH.
 * Copyright @ 2020 SST.
 *
 * All rights reserved.
 *
 * THIS PRODUCT CONTAINS CONFIDENTIAL INFORMATION  OF SST.
 * USE, DISCLOSURE OR REPRODUCTION IS PROHIBITED WITHOUT THE
 * PRIOR EXPRESS WRITTEN PERMISSION OF SST.
 */
package com.ss.repository;

import com.ss.model.WhitelistIp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Description: Interface for WhiteListIp 
 * Name of Project:SST
 * Created on: FEB 18, 2020
 * Modified on: FEB 19, 2020 11:19:38 AM
 *
 * @author Tehmina ALi
 * Version:
 */
@Repository("repositoryWhiteListIp")
public interface RepositoryWhiteListIp extends JpaRepository<WhitelistIp, Integer> {

	/**
	 * @param isDeleted
	 * @return
	 */
	public List<WhitelistIp> findByIsDeleted(Boolean isDeleted);

	/**
	 * @param ipAddress
	 * @param isActive
	 * @param isDeleted
	 * @return
	 */
	public List<WhitelistIp> findByIpAddressAndIsActiveAndIsDeleted(String ipAddress, Boolean isActive,
                                                                    Boolean isDeleted);

	/**
	 * @param ipAddress
	 * @param isDeleted
	 * @return
	 */
	public WhitelistIp findByIpAddressAndIsDeleted(String ipAddress, Boolean isDeleted);

	/**
	 * @param searchKeyWord
	 * @param pageable
	 * @return
	 */
	@Query("select wli from WhitelistIp wli where wli.ipAddress like %:searchKeyWord% and wli.isDeleted=false")
	public List<WhitelistIp> predictiveWhitelistIpSearchWithPagination(@Param("searchKeyWord") String searchKeyWord,
                                                                       Pageable pageable);

	/**
	 * @param searchKeyWord
	 * @return
	 */
	@Query("select count(*) from WhitelistIp wli where wli.ipAddress like %:searchKeyWord% and wli.isDeleted=false")
	public Integer predictiveWhitelistIpSearchTotalCount(@Param("searchKeyWord") String searchKeyWord);

	/**
	 * @param whiteListIpId
	 * @param deleted
	 * @return
	 */
	public WhitelistIp findByWhitelistIpIdAndIsDeleted(Integer whiteListIpId, Boolean deleted);

	/**
	 * @param idList
	 * @param deleted
	 * @param updateById
	 */
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update WhitelistIp wli set wli.isDeleted =:deleted, wli.updatedBy=:updateById where wli.whitelistIpId IN (:idList)")
	void deleteWhiteListIps(@Param("idList") List<Integer> idList, @Param("deleted") boolean deleted,
                            @Param("updateById") Integer updateById);

}
