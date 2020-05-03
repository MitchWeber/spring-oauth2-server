import React from 'react';
import {Container} from "@material-ui/core";
import {SignUp} from "./components/signup/SignUp";
import {SignIn} from "./components/signin/SignIn";

export const App = () => {
    return (
        <Container component="main">
            <SignIn/>
            <SignUp/>
        </Container>
    );
}
