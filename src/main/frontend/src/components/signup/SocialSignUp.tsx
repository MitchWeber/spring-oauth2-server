import React from "react";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";

export const SocialSignUp = () => {
    return (
        <Grid component="section" container spacing={3}>
            <Grid item xs={12}>
                {
                    socialSignupButton({
                        label: "Sign up with Facebook",
                        href: "/oauth2/authorization/facebook"
                    })
                }
            </Grid>
            <Grid item xs={12}>
                {
                    socialSignupButton({
                        label: "Sign up with GitHub",
                        href: "/oauth2/authorization/github"
                    })
                }
            </Grid>
        </Grid>
    );
}

const socialSignupButton = (props: { label: string, href: string }) => {
    console.log(window.location.origin);
    return (
        <Button variant="contained" fullWidth color="primary" href={`${process.env.REACT_APP_SELF_URL}${props.href}`}>
            {props.label}
        </Button>
    )
}
