import Grid from "@material-ui/core/Grid";
import {SignUpForm} from "./SignUpForm";
import Typography from "@material-ui/core/Typography";
import {SocialSignUp} from "./SocialSignUp";
import Link from "@material-ui/core/Link";
import React from "react";

export const SignUp = () => {
    return (
        <Grid component={"section"} container xs={12} spacing={3} alignItems={"center"} direction={"column"}>
            <Grid item xs={6}>
                <SignUpForm/>
            </Grid>
            <Grid item xs={6}>
                <Typography variant={"subtitle1"}>OR</Typography>
            </Grid>
            <Grid item xs={6}>
                <SocialSignUp />
            </Grid>
            <Grid item xs={6}>
                <Link href="#" variant="body2">
                    Already have an account? Sign in
                </Link>
            </Grid>
        </Grid>
    )
}
