import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import Link from "@material-ui/core/Link";
import React from "react";
import {SignInForm} from "./SignInForm";
import {SocialSignIn} from "./SocialSignIn";

export const SignIn = () => {
    return (
        <Grid component={"section"} container xs={12} spacing={3} alignItems={"center"} direction={"column"}>
            <Grid item xs={6}>
                <SignInForm/>
            </Grid>
            <Grid item xs={6}>
                <Typography variant={"subtitle1"}>OR</Typography>
            </Grid>
            <Grid item xs={6}>
                <SocialSignIn/>
            </Grid>
            <Grid item xs={6}>
                <Link href="#" variant="body2">
                    Don't have an account? Sign up
                </Link>
            </Grid>
        </Grid>
    )
}
