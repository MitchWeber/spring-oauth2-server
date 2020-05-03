import 'react-app-polyfill/ie11';
import * as React from 'react';
import * as ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import 'typeface-roboto';
import {SignIn} from "./components/signin/SignIn";

// @ts-ignore
window.renderOAuth2SignIn = (containerId) => {
    ReactDOM.render(
        <SignIn />,
        document.getElementById(containerId),
    );
    serviceWorker.register();
};

// @ts-ignore
window.unmountOAuth2SignIn = (containerId) => {
    // @ts-ignore
    ReactDOM.unmountComponentAtNode(document.getElementById(containerId));
    serviceWorker.unregister();
};
