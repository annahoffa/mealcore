import React, { useState, useContext, useEffect } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';

import MainContent from '../../components/main-content/main-content.component';
import SignUpForm from '../../components/sign-up-form/sign-up-form.component';
import SignUpSuccess from '../../components/sign-up-success/sign-up-success.component';

import './sign-up-page.styles.scss';


const SignUpPage = () => {
  const [successMsg, setSuccessMsg] = useState(false);
  const authContext = useContext(AuthContext);

  useEffect(() => {
    window.scrollTo(0, 0)
  }, [successMsg])

  return (
    !authContext.isLoggedIn ? (
      <MainContent>
        {successMsg ? (<SignUpSuccess />) : (<SignUpForm isCompleted={setSuccessMsg} />)}
      </MainContent>
    ) : (
      <Redirect to='/dashboard' />
    )
  );
};

export default SignUpPage;
