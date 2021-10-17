import React, { useContext } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';

import MainContent from '../../components/main-content/main-content.component';
import UserAccountSettings from '../../components/user-account-settings/user-account-settings.component';

import './my-account.styles.scss';


const MyAccountPage = () => {
  const authContext = useContext(AuthContext);

  return (
    authContext.isLoggedIn ? (
      <MainContent>
        <UserAccountSettings />
      </MainContent>
    ) : (
      <Redirect to='/' />
    )
  );
};

export default MyAccountPage;
