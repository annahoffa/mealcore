import React, { useContext } from 'react';
import { useHistory } from 'react-router-dom';
import apiCall from '../../utils/apiCall';
import { AuthContext } from '../../appContext/providers';


const LogOutPage = () => {
  console.log('inside logOut component');
  let history = useHistory();
  const authContext = useContext(AuthContext);

  const handleLogOut = () => {
    // TODO fix errors
    apiCall('/api/user/logout', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .catch(error => console.log(error));
    // if success
    authContext.logOut();
    console.log('Wylogowano');
    history.push({ pathname: '/' });
  };

  return (
    <>
      {handleLogOut()}
    </>
  );
};

export default LogOutPage;
