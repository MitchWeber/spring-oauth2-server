import 'react-app-polyfill/ie11';
import React from 'react';
import ReactDOM from 'react-dom';
import {App} from './App';
import * as serviceWorker from './serviceWorker';
import 'typeface-roboto';

// @ts-ignore
window.renderOAuth2 = (containerId) => {
    ReactDOM.render(
        <App />,
        document.getElementById(containerId),
    );
    serviceWorker.register();
};

// @ts-ignore
window.unmountOAuth2 = (containerId) => {
    // @ts-ignore
    ReactDOM.unmountComponentAtNode(document.getElementById(containerId));
    serviceWorker.unregister();
};
