import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

import { BrowserRouter } from 'react-router-dom';
//import { makeServer } from './server';
import { CookiesProvider } from 'react-cookie';
import { ThemeProvider, CssBaseline } from '@material-ui/core';
import { theme } from './mealcore-material-ui-theme';
import ContextProvider from './appContext/providers';


if(process.env.NODE_ENV === 'development') {
  //makeServer({ environment: 'development' });
}

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <CookiesProvider>
        <ContextProvider>
          <ThemeProvider theme={theme}>
            <CssBaseline />
            <App />
          </ThemeProvider>
        </ContextProvider>
      </CookiesProvider>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root'),
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
