package com.loan.service.DealFacilityService.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.loan.service.DealFacilityService.User.Post;

@Service
@CrossOrigin
public interface PostRepository extends JpaRepository<Post, Integer>{

}
