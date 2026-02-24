package com.scms.scms_backend.repository;

import com.scms.scms_backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    Page<Complaint> findByUser(User user, Pageable pageable);
    Page<Complaint> findByStatus(Status status, Pageable pageable);
    Page<Complaint> findByCategory(String category, Pageable pageable);
    Page<Complaint> findByStatusAndCategory(Status status,String category,Pageable pageable);
}