import 'react-app-polyfill/ie11';
import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import 'typeface-roboto';
import {SignUp} from "./components/signup/SignUp";

// @ts-ignore
window.renderSignUp = (containerId) => {
    ReactDOM.render(
        <SignUp/>,
        document.getElementById(containerId),
    );
    serviceWorker.register();
};

// @ts-ignore
window.unmountSignUp = (containerId) => {
    // @ts-ignore
    ReactDOM.unmountComponentAtNode(document.getElementById(containerId));
    serviceWorker.unregister();
};
