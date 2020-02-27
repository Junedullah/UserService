/**SmartSoftware User - Service */
/**
 * Description: Interface for RepositoryUserDetailPagination 
 * Name of Project: SmartSoftware
 * Created on: March 23, 2020
 * Modified on: March 23, 2020 10:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ss.model.UserDetail;


/**
 * Description: Interface for RepositoryUserDetailPagination 
 * Name of Project: BTI
 * Created on: May 12, 2017
 * Modified on: May 12, 2017 10:19:22 AM
 * @author seasia
 * Version: 
 */

@Repository("repositoryUserDetailPagination")
public interface RepositoryUserDetailPagination extends PagingAndSortingRepository<UserDetail, Long> {
	/**
	 * @param deleted
	 * @param pageRequest
	 * @return
	 */
	Page<UserDetail> findByIsDeleted(Boolean deleted, final Pageable pageRequest);
}