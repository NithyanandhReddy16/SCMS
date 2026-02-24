package com.scms.scms_backend.contoller;

import com.scms.scms_backend.service.ComplaintService;
import com.scms.scms_backend.dto.ComplaintRequest;
import com.scms.scms_backend.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.*;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService service;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Complaint create(@RequestBody ComplaintRequest dto, Authentication auth){
        return service.createComplaint(dto,auth.getName());
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public Page<Complaint> my(Authentication auth,@RequestParam int page,@RequestParam int size){
        return service.getMy(auth.getName(),PageRequest.of(page,size));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Complaint> all(@RequestParam(required=false) Status status,
                               @RequestParam(required=false) String category,
                               @RequestParam int page,
                               @RequestParam int size){
        return service.getAll(status,category,PageRequest.of(page,size));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Complaint update(@PathVariable Long id,@RequestParam Status status,@RequestParam(required=false) String note){
        return service.updateStatus(id,status,note);
    }
}