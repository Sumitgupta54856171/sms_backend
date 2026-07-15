package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.JwtAuthResponse;
import com.example.schoolsystem.dto.LoginRequest;
import com.example.schoolsystem.entity.*;
import com.example.schoolsystem.repository.*;
import com.example.schoolsystem.util.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public  class Userservice {
    private final Userrepo userrepo;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final Enrollementrepo enrollementrepo;
    private final Sessionrepo sessionrepo;
    private final ClassTeacherAssignmentrepo ctar;
    private final Teacherrepo teacherrepo;

    public String register(){
        return "registered";
    }
    public ResponseEntity<JwtAuthResponse> login(LoginRequest loginRequest, HttpServletResponse response) throws Exception {

        System.out.println("start login");
        User checkrole = userrepo.findByEmail(loginRequest.getEmail());
        if(checkrole == null){
            return ResponseEntity.badRequest().body(null);
        }
        System.out.println("check the user role is exits "+checkrole.getRole());
        Role rolecheck =checkrole.getRole();

        if(rolecheck == Role.TEACHER){

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtUtils.generateJwtToken(auth);
            String role = auth.getAuthorities().iterator().next().getAuthority();
            Optional<Session> e = sessionrepo.findByIs_currentIsTrue(true);
            System.out.println(e.get().getSession_name()+" this is a session id");
            Teacher teacherdetail = teacherrepo.findByEmail(loginRequest.getEmail());

            ClassTeacherAssignment cla = ctar.findByTeacher_EmailAndSessionId(checkrole.getEmail(),e.get().getSessionId());
            String gradeClass = cla.getGradeClass().replace(" ", "_");
           	ResponseCookie classCookie = ResponseCookie.from("teacherId",teacherdetail.getId().toString())
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();

            ResponseCookie roleCookie = ResponseCookie.from("role", "TEACHER")
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();



            if (e.isPresent()) {

                System.out.println(e.get().getSessionId());
                Long sessionId = e.get().getSessionId();
                System.out.println(sessionId);

                ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionId.toString())
                        .path("/")
                        .httpOnly(false)
                        .secure(false)
                        .sameSite("Lax")
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, sessionCookie.toString(), classCookie.toString(), roleCookie.toString())
                        .body(new JwtAuthResponse(jwt, role));
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, classCookie.toString(), roleCookie.toString())
                    .body(new JwtAuthResponse(jwt, role));

        }
        else if(rolecheck == Role.ACCOUNTANT){

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtUtils.generateJwtToken(auth);
            String role = auth.getAuthorities().iterator().next().getAuthority();
            Optional<Session> e = sessionrepo.findByIs_currentIsTrue(true);
            System.out.println(e.get().getSession_name()+" this is a session id");

            ResponseCookie roleCookie = ResponseCookie.from("role", "ACCOUNTANT")
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();

            if (e.isPresent()) {

                System.out.println(e.get().getSessionId());
                Long sessionId = e.get().getSessionId();
                System.out.println(sessionId);

                ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionId.toString())
                        .path("/")
                        .httpOnly(false)
                        .secure(false)
                        .sameSite("Lax")
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, sessionCookie.toString(), roleCookie.toString())
                        .body(new JwtAuthResponse(jwt, role));
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, roleCookie.toString())
                    .body(new JwtAuthResponse(jwt, role));

        }
        else if(rolecheck == Role.ADMIN){

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = jwtUtils.generateJwtToken(auth);
            String role = auth.getAuthorities().iterator().next().getAuthority();
            Optional<Session> e = sessionrepo.findByIs_currentIsTrue(true);
            System.out.println(e.get().getSession_name()+" this is a session id");

            ResponseCookie roleCookie = ResponseCookie.from("role", "ADMIN")
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();

            if (e.isPresent()) {

                System.out.println(e.get().getSessionId());
                Long sessionId = e.get().getSessionId();
                System.out.println(sessionId);

                ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionId.toString())
                        .path("/")
                        .httpOnly(false)
                        .secure(false)
                        .sameSite("Lax")
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, sessionCookie.toString(), roleCookie.toString())
                        .body(new JwtAuthResponse(jwt, role));
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, roleCookie.toString())
                    .body(new JwtAuthResponse(jwt, role));

        }

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtUtils.generateJwtToken(auth);
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Optional<Session> e = sessionrepo.findByIs_currentIsTrue(true);
        System.out.println(e.get().getSession_name()+" this is a session id");

        ResponseCookie roleCookie = ResponseCookie.from("role", role)
                .path("/")
                .httpOnly(false)
                .secure(false)
                .sameSite("Lax")
                .build();

        if (e.isPresent()) {

            System.out.println(e.get().getSessionId());
            Long sessionId = e.get().getSessionId();
            System.out.println(sessionId);

            ResponseCookie sessionCookie = ResponseCookie.from("sessionId", sessionId.toString())
                    .path("/")
                    .httpOnly(false)
                    .secure(false)
                    .sameSite("Lax")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, sessionCookie.toString(), roleCookie.toString())
                    .body(new JwtAuthResponse(jwt, role));
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, roleCookie.toString())
                .body(new JwtAuthResponse(jwt, role));
    }

    public String logout(){
        return "logged out";
    }
}
