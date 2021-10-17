import React, { createContext } from 'react';
import useLocalStorage from '../utils/useLocalStorageHook';


let defaultState = {
  isLoggedIn: false,
  token: null,
};

export const AuthContext = createContext(defaultState);


const ContextProvider = (props) => {
  const [isAuth, setAuth] = useLocalStorage({ ...defaultState }, 'userAuth');

  const logIn = () => {
    setAuth({ isLoggedIn: true });
  };

  const logOut = () => {
    setAuth({ isLoggedIn: false });
  };

  // TODO: check for errors
  return (
    <AuthContext.Provider value={{ ...isAuth, logIn: logIn, logOut: logOut }}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default ContextProvider;
