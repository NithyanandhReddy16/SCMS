package com.scms.scms_backend.service;

import com.scms.scms_backend.entity.*;
import com.scms.scms_backend.repository.*;
import com.scms.scms_backend.dto.ComplaintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepo;
    private final UserRepository userRepo;

    public Complaint createComplaint(ComplaintRequest dto,String email){
        User user = userRepo.findByEmail(email).orElseThrow();

        Complaint c = new Complaint();
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        c.setCategory(dto.getCategory());
        c.setUser(user);

        return complaintRepo.save(c);
    }

    public Page<Complaint> getMy(String email, Pageable pageable){
        User user = userRepo.findByEmail(email).orElseThrow();
        return complaintRepo.findByUser(user,pageable);
    }

    public Page<Complaint> getAll(Status status,String category,Pageable pageable){
        if(status!=null && category!=null)
            return complaintRepo.findByStatusAndCategory(status,category,pageable);
        if(status!=null)
            return complaintRepo.findByStatus(status,pageable);
        if(category!=null)
            return complaintRepo.findByCategory(category,pageable);
        return complaintRepo.findAll(pageable);
    }

    public Complaint updateStatus(Long id,Status status,String note){
        Complaint c = complaintRepo.findById(id).orElseThrow();

        if(status==Status.RESOLVED && (note==null || note.isBlank()))
            throw new RuntimeException("Resolution note required");

        c.setStatus(status);
        c.setResolutionNote(note);
        c.setUpdatedAt(LocalDateTime.now());

        return complaintRepo.save(c);
    }
}