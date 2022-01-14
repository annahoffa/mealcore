import React, { useState, useContext, useEffect } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';
import MainContent from '../../components/main-content/main-content.component';
import NewProductForm from '../../components/new-product-form';


const SuggestNewProductPage = () => {
  const authContext = useContext(AuthContext);

  return (
    authContext.isLoggedIn ? (
      <MainContent>
        <NewProductForm />
      </MainContent>
    ) : (
      <Redirect to='/' />
    )
  );
};

export default SuggestNewProductPage;
