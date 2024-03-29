package com.rizvankarimov.appointment_app.service;


import com.rizvankarimov.appointment_app.entity.User;
import com.rizvankarimov.appointment_app.repository.UserRepository;
import com.rizvankarimov.appointment_app.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService
{
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final JwtRefreshTokenService jwtRefreshTokenService;
    private final UserRepository userRepository;



    @Override
    public User signInAndReturnJWT(User signInRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        com.rizvankarimov.appointment_app.security.UserPrincipal userPrincipal = (com.rizvankarimov.appointment_app.security.UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();
        signInUser.setAccessToken(jwt);
        signInUser.setRefreshToken(jwtRefreshTokenService.createRefreshToken(signInUser.getId()).getTokenId());

        return signInUser;
    }

    @Override
    public Object findUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

}
