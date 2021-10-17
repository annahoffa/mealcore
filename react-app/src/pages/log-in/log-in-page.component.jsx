import React, { useContext } from 'react';
import { Redirect } from 'react-router';
import { Link } from 'react-router-dom';
import { AuthContext } from '../../appContext/providers';

import { Button, Typography } from '@material-ui/core';
import MainContent from '../../components/main-content/main-content.component';
import LogInForm from '../../components/log-in-form/log-in-form.component';

import './log-in-page.styles.scss';


const LogInPage = () => {
  const authContext = useContext(AuthContext);

  return (
    !authContext.isLoggedIn ? (
      <MainContent>
        <div className='horizontal-container'>
          <div className='section'>
            <Typography className='section-title' variant='h5' align='center'>Nie masz jeszcze konta?</Typography>
            <Button
              variant='outlined'
              color='primary'
              to='/signup'
              component={Link}
            >
              Załóż konto
            </Button>
          </div>
          <div className='vertical-line' />
          <div className='section'>
            <div className='section-title'>
              <Typography variant='h5' align='center'>Mam już konto</Typography>
              <Typography variant='subtitle1' align='center'>Zaloguj się swoim adresem e-mail</Typography>
            </div>
            <LogInForm />
          </div>
        </div>
      </MainContent>
  ) : (
    <Redirect to="/dashboard" />
    )
  );
};

export default LogInPage;
