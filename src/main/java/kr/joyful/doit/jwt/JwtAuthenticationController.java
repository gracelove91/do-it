package kr.joyful.doit.jwt;

import kr.joyful.doit.jwt.dto.JwtAuthenticationRequest;
import kr.joyful.doit.jwt.dto.JwtAuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailService;

    @PostMapping("/api/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws Exception {
        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getEmail());
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDetails,authenticationRequest.getPassword()));
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtTokenUtil.generateToken(userDetails,JwtTokenType.AUTH)
                                                                , jwtTokenUtil.generateToken(userDetails,JwtTokenType.REFRESH)));
    }
}