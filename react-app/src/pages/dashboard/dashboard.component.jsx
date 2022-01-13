import React, { useContext, useState } from 'react';
import { Redirect } from 'react-router';
import { AuthContext } from '../../appContext/providers';
import Skeleton from '@material-ui/lab/Skeleton';
import { Grid, makeStyles, Tooltip, Typography } from '@material-ui/core';
import ShowNutritionalRequirements from '../../components/show-nutritional-requirements/show-nutritional-requirements.component';
import DashboardElementsColumn from '../../components/dashboard-elements-column/dashboard-elements-column.component';
import DashboardExercisesToolbar from '../../components/dashboard-exercises-toolbar/dashboard-exercises-toolbar.component';
import DashboardExercise from '../../components/dashboard-exercise/dashboard-exercise.component';
import Calendar from '../../components/calendar';
import CategorizeProducts from '../../components/categorize-products';


import './dashboard.styles.scss';
import DashboardProvider, { DashboardProviderContext } from './dashboard-provider';
import ToolbarManageNote from '../../components/toolbar-manage-note';
import ToolbarManageAllergySymptoms from '../../components/toolbar-manage-allergy-symptoms';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import DashboardModal from './dashboard-modal';


const useStyles = makeStyles((theme) => ({
  toolbar: {
    '&>*': {
      marginRight: '0.2em',
    },
  },
  icon: {
    maxWidth: 'max-content',
    maxHeight: 'fit-content',
    backgroundColor: '#F6F6F6',
    borderRadius: '5px',
  },
}));

const DashboardPage = () => {
  const authContext = useContext(AuthContext);
  const [open, setOpen] = useState(false);
  const { toolbar, icon } = useStyles();
  const {
    date,
    setDate,
    nutritionalRequirementsQuery,
    userProductsQuery,
    userExercisesQuery,
  } = useContext(DashboardProviderContext);

  return (
    authContext.isLoggedIn ? (
      <div className='dashboard-container'>
        <Grid container style={{ width: '90%' }} spacing={8}>
          <Grid item xs={12} sm={12} md={12} lg={12} xl={4}>
            <Typography variant='h6'>Wybierz dzień:</Typography>
            <Calendar onDateChange={setDate} />
            <br />
            <Typography variant='h6'>Twoje dzienne zapotrzebowanie:</Typography>
            <br />
            {(nutritionalRequirementsQuery.isSuccess && userProductsQuery.isSuccess)
              ? <ShowNutritionalRequirements maxValues={nutritionalRequirementsQuery.data}
                currentValues={userProductsQuery.data} />
              : <Skeleton />}
          </Grid>
          <Grid item xs={12} md={6} xl={4}>
            <Typography variant='h6'>Lista zjedzonych produktów:</Typography>
            <br />
            <div className={toolbar}>
              <Tooltip title='Dodaj produkt' className={icon}>
                <IconButton onClick={() => setOpen(true)}>
                  <AddIcon />
                </IconButton>
              </Tooltip>
              <ToolbarManageNote />
              <ToolbarManageAllergySymptoms />
            </div>
            {userProductsQuery.isSuccess ?
              <CategorizeProducts userProducts={userProductsQuery.data.products} date={date} /> : <Skeleton />}
          </Grid>
          <Grid item xs={12} md={6} xl={4}>
            <Typography variant='h6'>Dzienna aktywność:</Typography>
            <br />
            <DashboardExercisesToolbar />
            {userExercisesQuery.isSuccess ?
              <DashboardElementsColumn items={userExercisesQuery.data.exercises} component={DashboardExercise} /> :
              <Skeleton />}
          </Grid>
        </Grid>
        <DashboardModal open={open} setOpen={setOpen} />
      </div>
    ) : (
      <Redirect to='/' />
    )
  );
};

const withProvider = () => <DashboardProvider><DashboardPage /></DashboardProvider>;

export default withProvider;
