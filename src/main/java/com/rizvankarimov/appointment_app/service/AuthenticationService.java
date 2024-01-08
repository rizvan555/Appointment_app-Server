package com.rizvankarimov.appointment_app.service;

import com.rizvankarimov.appointment_app.entity.User;

public interface AuthenticationService
{

    User signInAndReturnJWT(User signInRequest);
}