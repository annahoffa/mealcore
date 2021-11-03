import React, { useContext, useEffect, useState } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';
import apiCall from '../../utils/apiCall';

//import MainContent from '../../components/main-content/main-content.component';
import ShowNutritionalRequirements from '../../components/show-nutritional-requirements/show-nutritional-requirements.component';
import DashboardElementsColumn from '../../components/dashboard-elements-column/dashboard-elements-column.component';
import DashboardProduct from '../../components/dashboard-product/dashboard-product.component';
import DashboardExercise from '../../components/dashboard-exercise/dashboard-exercise.component';
import Calendar from '../../components/calendar/calendar.component';
import Skeleton from '@material-ui/lab/Skeleton';
import { Grid, Typography } from '@material-ui/core';

import './dashboard.styles.scss';


const DashboardPage = () => {
  const authContext = useContext(AuthContext);
  const [maxNutritionalValues, setMaxNutritionalValues] = useState();
  const [userProducts, setUserProducts] = useState();
  const [userExercises, setUserExercises] = useState();

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

  const getUserExercises = () => {
    if(authContext.isLoggedIn) {
      apiCall('/api/user/getUserExercises', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .then(data => setUserExercises(data))
      .catch(error => console.log(error));
    }
  };

  useEffect(() => {
    getNutritionalRequirements();
    getUserProducts();
    getUserExercises();
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
          <Grid item xs={12} md={4}>
            <Typography variant='h6'>Lista zjedzonych produktów:</Typography>
            <br />
            {userProducts === undefined ? <Skeleton /> : <DashboardElementsColumn items={userProducts.products} component={DashboardProduct} />}
          </Grid>
          <Grid item xs={12} md={4}>
            <Typography variant='h6'>Dzienna aktywność:</Typography>
            <br />
            {userExercises === undefined ? <Skeleton /> : <DashboardElementsColumn items={userExercises.exercises} component={DashboardExercise} />}
          </Grid>
        </Grid>
      </div>
    ) : (
      <Redirect to='/' />
    )
  );
};

export default DashboardPage;
