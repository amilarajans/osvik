package com.origins.osvik.repository;

import com.origins.osvik.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Amila-Kumara on 3/12/2016.
 */
public abstract interface AuthorityRepository extends JpaRepository<Authority, String> {
}
