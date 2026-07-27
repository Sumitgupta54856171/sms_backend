package com.example.schoolsystem.service;

import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.repository.Enrollementrepo;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Dashboardservice {

    public final Enrollementrepo enrollementrepo;

    public ResponseEntity<?> getEnrollments(Long session){

        List<Enrollement_session> en = enrollementrepo.findAllBySession_SessionId(session);
        List<String> classNo = en.stream().map(s->s.getClass_no()).collect(Collectors.toList());
        return ResponseEntity.ok(classNo);
    }


}
