import React, { createContext } from 'react';
import useLocalStorage from '../utils/useLocalStorageHook';


let defaultState = {
  isLoggedIn: false,
  isAdmin: false,
  token: null,
};

export const AuthContext = createContext(defaultState);


const ContextProvider = (props) => {
  const [isAuth, setAuth] = useLocalStorage({ ...defaultState }, 'userAuth');

  const logIn = () => {
    setAuth(prevState => ({ ...prevState, isLoggedIn: true }));
  };

  const logInAdmin = () => {
    setAuth(prevState => ({ ...prevState, isAdmin: true }));
  };

  const logOut = () => {
    setAuth(prevState => ({ ...prevState, isLoggedIn: false, isAdmin: false }));
  };

  return (
    <AuthContext.Provider value={{ ...isAuth, logIn: logIn, logOut: logOut, logInAdmin: logInAdmin }}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default ContextProvider;
