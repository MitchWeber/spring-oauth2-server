import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Snackbar from "@material-ui/core/Snackbar";
import Alert from "@material-ui/lab/Alert";
import LockOutlinedIcon from "@material-ui/icons/LockOutlined"
import React, {useState} from "react";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import Link from "@material-ui/core/Link";
import {makeStyles} from "@material-ui/core/styles";
import axios from "axios";
import {ErrorParser} from "../ErrorParser";


const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

export const SignInForm = () => {
    const initialErrorsState = {username: undefined, password: undefined};
    const defaultErrorMessage = "Upps... Sorry something went wrong! We are working on a Fix!";
    const usernamePasswordErrorMessage = "Username or Password wrong!";

    const classes = useStyles();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState(initialErrorsState);
    const [unknownError, setUnknownError] = useState(false);
    const [unknownErrorMessage, setUnknownErrorMessage] = useState(defaultErrorMessage);

    const submit = async () => {

        setErrors(initialErrorsState);
        setUnknownErrorMessage(defaultErrorMessage);

        // TODO: Continue!!!!
        await axios.post(`${process.env.REACT_APP_SELF_URL}/login`, {username: email, password})
            .then(resp => console.log(resp))
            .catch(error => {
                if (error.response && error.response.data) {
                    setErrors(ErrorParser.parse(error.response.data.errors));
                } else {
                    if (error.response && error.response.status === 401) {
                        setUnknownErrorMessage(usernamePasswordErrorMessage);
                    }
                    setUnknownError(true);
                }
            });
    };


    return (
        <div className={classes.paper}>
            <Avatar className={classes.avatar}>
                <LockOutlinedIcon/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign in
            </Typography>
            <form className={classes.form} noValidate>
                <TextField
                    variant="outlined"
                    margin="normal"
                    error={!!errors.username}
                    helperText={errors.username}
                    required
                    fullWidth
                    label="Email Address"
                    name="email"
                    autoComplete="email"
                    autoFocus
                    value={email}
                    onChange={event => setEmail(event.target.value)}
                />
                <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    error={!!errors.password}
                    helperText={errors.password}
                    name="password"
                    label="Password"
                    type="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={event => setPassword(event.target.value)}
                />
                <Button
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    onClick={submit}
                    className={classes.submit}
                >
                    Sign In
                </Button>
                <Grid container>
                    <Grid item xs>
                        <Link href="#" variant="body2">
                            Forgot password?
                        </Link>
                    </Grid>
                    <Grid item>
                        <Link href="#" variant="body2">
                            {"Don't have an account? Sign Up"}
                        </Link>
                    </Grid>
                </Grid>
            </form>

            <Snackbar
                open={unknownError}
                anchorOrigin={({vertical: 'top', horizontal: 'center'})}
                autoHideDuration={6000}
                onClose={() => setUnknownError(false)}>
                <Alert onClose={() => setUnknownError(false)} severity="error">
                    {unknownErrorMessage}
                </Alert>
            </Snackbar>

        </div>
    )
}
