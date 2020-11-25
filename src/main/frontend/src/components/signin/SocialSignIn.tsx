import React from "react";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";

export const SocialSignIn = () => {
    const redirectUrl = `${process.env.REACT_APP_FRONTEND_URL}/profile`;
    return (
        <Grid component="section" container spacing={3}>


            <Grid item xs={12}>
                {
                    socialSignupButton({
                        label: "Sign in with Facebook",
                        href: `/oauth2/authorization/facebook?redirect_url=${redirectUrl}`
                    })
                }
            </Grid>
            <Grid item xs={12}>
                {
                    socialSignupButton({
                        label: "Sign in with GitHub",
                        href: `/oauth2/authorization/github?redirect_url=${redirectUrl}`
                    })
                }
            </Grid>
        </Grid>
    );
}

const socialSignupButton = (props: { label: string, href: string }) => {
    return (
        <Button variant="contained" fullWidth color="primary" href={`${process.env.REACT_APP_BACKEND_URL}${props.href}`}>
            {props.label}
        </Button>
    )
}
