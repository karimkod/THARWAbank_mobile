package com.tharwa.solid.tharwa.enumration

/**
 * Created by LE on 05/03/2018.
 */
enum class CodeStatus (val status: Int)
{   succ200(200),succ_201(201),err_400(400),err_401(401),
    err_403(403),err_404(404),err_405(405),err_422(422),
    err_500(500),err_503(503)
}

/*
400-> Not a number
401-> wrong pin
500-> Erreur serveur




*/