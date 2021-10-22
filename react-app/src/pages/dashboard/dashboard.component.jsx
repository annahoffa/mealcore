import React, { useContext, useEffect, useState } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';
import apiCall from '../../utils/apiCall';

//import MainContent from '../../components/main-content/main-content.component';
import ShowNutritionalRequirements from '../../components/show-nutritional-requirements/show-nutritional-requirements.component';
import DashboardProductsList from '../../components/dashboard-products-list/dashboard-products-list.component';
import Calendar from '../../components/calendar/calendar.component';
import Skeleton from '@material-ui/lab/Skeleton';
import { Grid, Typography } from '@material-ui/core';

import './dashboard.styles.scss';


const DashboardPage = () => {
  const authContext = useContext(AuthContext);
  const [maxNutritionalValues, setMaxNutritionalValues] = useState();
  const [userProducts, setUserProducts] = useState();

  const getNutritionalRequirements = () => {
    if(authContext.isLoggedIn) {
      apiCall('/api/user/getNutritionalRequirements', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setMaxNutritionalValues(data))
      .catch(error => console.log(error));
    }
  };

  const getUserProducts = () => {
    if(authContext.isLoggedIn) {
      apiCall('/api/user/getUserProducts', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setUserProducts(data))
      .catch(error => console.log(error));
    }
  };

  useEffect(() => {
    getNutritionalRequirements();
    getUserProducts();
  }, []);

  return (
    authContext.isLoggedIn ? (
      <div className='dashboard-container'>
        <Grid container style={{ width: '90%' }} spacing={8}>
          <Grid item xs={12} md={4}>
            <Typography variant='h6'>Wybierz dzień:</Typography>
            <Calendar setUserProducts={setUserProducts} />
            <br />
            <Typography variant='h6'>Twoje dzienne zapotrzebowanie:</Typography>
            <br />
            {(maxNutritionalValues === undefined && userProducts === undefined)
              ? <Skeleton />
              : <ShowNutritionalRequirements maxValues={maxNutritionalValues} currentValues={userProducts} />}
          </Grid>
          <Grid item xs={12} md={8}>
            <Typography variant='h6'>Lista zjedzonych produktów:</Typography>
            <br />
            {userProducts === undefined ? <Skeleton /> : <DashboardProductsList productsList={userProducts.products} />}
          </Grid>
        </Grid>
      </div>
    ) : (
      <Redirect to='/' />
    )
  );
};

export default DashboardPage;
