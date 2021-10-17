import React from 'react';
import { AuthContext } from './providers';


// TODO for later
const AuthConsumer = (props) => {
  const { children } = props;

  return (
    <AuthContext.Consumer>
    </AuthContext.Consumer>
  );

};
