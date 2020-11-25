import React, {useState} from "react";
import Container from "@material-ui/core/Container";
import {Avatar} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import Checkbox from "@material-ui/core/Checkbox";
import Button from "@material-ui/core/Button";
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import {makeStyles} from "@material-ui/core/styles";
import axios from "axios";
import Alert from "@material-ui/lab/Alert";
import Snackbar from "@material-ui/core/Snackbar";
import FormHelperText from "@material-ui/core/FormHelperText";
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
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

export const SignUpForm = () => {

    const classes = useStyles();

    const initialErrorsState = {firstName: undefined, lastName: undefined, email: undefined, password: undefined, termsOfUse: undefined};
    const defaultErrorMessage = "Upps... Sorry something went wrong! We are working on a Fix!";

    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [termsOfUse, setTermsOfUse] = useState("");

    const [errors, setErrors] = useState(initialErrorsState);
    const [unknownError, setUnknownError] = useState(false);

    const submit = async () => {

        setErrors(initialErrorsState);

        await axios.post(`${process.env.REACT_APP_BACKEND_URL}/signup`, {
            firstName,
            lastName,
            email,
            password,
            termsOfUse
        })
            .then(resp => console.log(resp))
            .catch(error => {
                if (error.response && error.response.data) {
                    setErrors(ErrorParser.parse(error.response.data.errors));
                } else {
                    setUnknownError(true);
                }
            });;
    }

    return (
        <Container component="section" className={classes.paper}>
            <Avatar className={classes.avatar}>
                <LockOutlinedIcon/>
            </Avatar>
            <Typography component="h1" variant="h5">
                Sign up
            </Typography>
            <form noValidate className={classes.form}>
                <Grid container spacing={2}>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            autoComplete="fname"
                            name="firstName"
                            variant="outlined"
                            required
                            error={!!errors.firstName}
                            helperText={errors.firstName}
                            fullWidth
                            id="firstName"
                            label="First Name"
                            value={firstName}
                            onChange={(event => setFirstName(event.target.value))}
                            autoFocus
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="lastName"
                            label="Last Name"
                            name="lastName"
                            error={!!errors.lastName}
                            helperText={errors.lastName}
                            value={lastName}
                            onChange={(event => setLastName(event.target.value))}
                            autoComplete="lname"
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            id="email"
                            error={!!errors.email}
                            helperText={errors.email}
                            label="Email Address"
                            name="email"
                            autoComplete="email"
                            value={email}
                            onChange={(event => setEmail(event.target.value))}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            variant="outlined"
                            required
                            fullWidth
                            error={!!errors.password}
                            helperText={errors.password}
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            value={password}
                            onChange={(event => setPassword(event.target.value))}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <FormControlLabel
                            control={<Checkbox
                                value="termsOfUse"
                                checked={Boolean(termsOfUse)}
                                onChange={(event => setTermsOfUse(event.target.checked+""))}
                                color="primary"/>}
                            label="I accept the terms of use."
                        />
                        <FormHelperText error >{errors.termsOfUse}</FormHelperText>
                    </Grid>
                </Grid>
                <Button
                    type="button"
                    fullWidth
                    onClick={submit}
                    variant="contained"
                    color="primary"
                    className={classes.submit}
                >
                    Sign Up
                </Button>
            </form>

            <Snackbar
                open={unknownError}
                anchorOrigin={({vertical: 'top', horizontal: 'center'})}
                autoHideDuration={6000}
                onClose={() => setUnknownError(false)}>
                <Alert onClose={() => setUnknownError(false)} severity="error">
                    {defaultErrorMessage}
                </Alert>
            </Snackbar>
        </Container>
    );
}
